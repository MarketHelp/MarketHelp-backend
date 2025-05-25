package ru.ovrays.graphontext.service

import org.jetbrains.kotlinx.dataframe.DataColumn
import org.jetbrains.kotlinx.dataframe.DataFrame
import ru.ovrays.graphontext.model.Graphic
import ru.ovrays.graphontext.model.GraphicFormat.ALL
import ru.tinkoff.kora.common.Component
import java.nio.file.Files
import kotlin.io.path.Path

@Component
class GraphicService {
    fun createGraphic(
        filename: String,
        dataFrame: DataFrame<*>,
        format: String
    ): Graphic = when (format) {
        ALL.value -> createCombinedGraphic(filename, dataFrame)
        else -> createSingleGraphic(filename, dataFrame, format)
    }

    private fun createCombinedGraphic(
        filename: String,
        dataFrame: DataFrame<*>,
    ): Graphic {
        val template = getTemplate(ALL.value)
        val values = dataFrame.columns()
            .associateWith(DataColumn<*>::values)
            .mapKeys { it.key.name() }
            .mapValues { it.value.map(Any?::toString) }

        val singleValues = values.filter { it.value.size == 1 }
            .mapValues { it.value.first() }

        val multiValues = values.filter { it.key !in singleValues }
        var content = template.replace(PRODUCT_NAME_PLACEHOLDER, singleValues["productName"]!!)
            .replace(PRODUCT_RATING_PLACEHOLDER, singleValues["productRating"]!!)
            .replace(PRODUCT_PRICE_PLACEHOLDER, singleValues["productPrice"]!!)
            .replace(PRODUCT_DESCRIPTION_PLACEHOLDER, singleValues["productDescription"]!!)

        multiValues["recommendations"]!!.forEachIndexed { index, value ->
            content = template.replace(RECOMMENDATION_PLACEHOLDER + index, value)
        }

        return Graphic(filename, content.toByteArray())
    }

    private fun createSingleGraphic(
        filename: String,
        dataFrame: DataFrame<*>,
        format: String
    ): Graphic {
        val template = getTemplate(format)
        val columnValues = dataFrame.columns()
            .map(DataColumn<*>::values)

        val content = applyYColumn(applyXColumn(template, columnValues[0]), columnValues[1])
            .toByteArray()

        return Graphic(filename, content)
    }

    private fun applyXColumn(template: String, column: Iterable<*>): String {
        val xColumnValue = column.joinToString(transform = { "\"$it\"" })

        return template.replace(X_COLUMN_PLACEHOLDER, xColumnValue)
    }

    private fun applyYColumn(template: String, column: Iterable<*>): String {
        val xColumnValue = column.joinToString()

        return template.replace(Y_COLUMN_PLACEHOLDER, xColumnValue)
    }

    private fun getTemplate(format: String): String {
        val path = Path("templates/$format.html")

        return Files.readString(path)
    }

    companion object {
        private const val X_COLUMN_PLACEHOLDER = "%X_ARRAY%"
        private const val Y_COLUMN_PLACEHOLDER = "%Y_ARRAY%"

        private const val PRODUCT_NAME_PLACEHOLDER = "%PRODUCT_NAME%"
        private const val PRODUCT_RATING_PLACEHOLDER = "%PRODUCT_RATING%"
        private const val PRODUCT_PRICE_PLACEHOLDER = "%PRODUCT_PRICE%"
        private const val PRODUCT_DESCRIPTION_PLACEHOLDER = "%PRODUCT_DESCRIPTION%"
        private const val RECOMMENDATION_PLACEHOLDER = "%RECOMMENDATION_%"
    }
}
