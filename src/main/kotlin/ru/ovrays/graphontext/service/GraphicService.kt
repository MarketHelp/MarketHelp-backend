package ru.ovrays.graphontext.service

import org.jetbrains.kotlinx.dataframe.DataColumn
import org.jetbrains.kotlinx.dataframe.DataFrame
import ru.ovrays.graphontext.model.Graphic
import ru.ovrays.graphontext.model.GraphicFormat.ALL
import ru.tinkoff.kora.common.Component

@Component
class GraphicService(
    private val storageService: StorageService,
) {
    fun createGraphic(
        filename: String,
        dataFrame: DataFrame<*>,
        format: String,
        childPieDataFrame: DataFrame<*>? = null,
        childBarDataFrame: DataFrame<*>? = null,
    ): Graphic = when (format) {
        ALL.value -> createCombinedGraphic(filename, dataFrame, childPieDataFrame!!, childBarDataFrame!!)
        else -> createSingleGraphic(filename, dataFrame, format)
    }

    private fun createCombinedGraphic(
        filename: String,
        dataFrame: DataFrame<*>,
        childPieDataFrame: DataFrame<*>,
        childBarDataFrame: DataFrame<*>,
    ): Graphic {
        val template = storageService.readTemplate(ALL.value)
        val values = dataFrame.columns()
            .associateWith(DataColumn<Any?>::values)
            .mapKeys { it.key.name() }
            .mapValues { it.value.filterNotNull().map(Any::toString) }

        val singleValues = values.filter { it.value.size == 1 }
            .mapValues { it.value.first() }

        val multiValues = values.filter { it.key !in singleValues }
        var content = template.replace(PRODUCT_NAME_PLACEHOLDER, singleValues["productName"]!!)
            .replace(PRODUCT_IMAGE_PLACEHOLDER, singleValues["productImage"]!!)
            .replace(PRODUCT_RATING_PLACEHOLDER, singleValues["productRating"]!!)
            .replace(PRODUCT_PRICE_PLACEHOLDER, singleValues["productPrice"]!!)
            .replace(PRODUCT_DESCRIPTION_PLACEHOLDER, singleValues["productDescription"]!!)

        multiValues["recommendations"]!!.forEachIndexed { index, value ->
            content = content.replace("$RECOMMENDATION_PLACEHOLDER${index + 1}%", value)
        }

        content = applyColumns(content, childPieDataFrame, X_PIE_COLUMN_PLACEHOLDER, Y_PIE_COLUMN_PLACEHOLDER)
        content = applyColumns(content, childBarDataFrame, X_BAR_COLUMN_PLACEHOLDER, Y_BAR_COLUMN_PLACEHOLDER)

        return Graphic(filename, content.toByteArray())
    }

    private fun createSingleGraphic(
        filename: String,
        dataFrame: DataFrame<*>,
        format: String
    ): Graphic {
        val template = storageService.readTemplate(format)
        val content = applyColumns(template, dataFrame)
            .toByteArray()

        return Graphic(filename, content)
    }

    private fun applyColumns(
        content: String,
        dataFrame: DataFrame<*>,
        xPlaceholder: String = X_COLUMN_PLACEHOLDER,
        yPlaceholder: String = Y_COLUMN_PLACEHOLDER
    ): String {
        val columnValues = dataFrame.columns()
            .map(DataColumn<*>::values)

        return applyYColumn(applyXColumn(content, columnValues[0], xPlaceholder), columnValues[1], yPlaceholder)
    }

    private fun applyXColumn(
        template: String,
        column: Iterable<*>,
        placeholder: String = X_COLUMN_PLACEHOLDER
    ): String {
        val xColumnValue = column.joinToString(transform = { "\"$it\"" })

        return template.replace(placeholder, xColumnValue)
    }

    private fun applyYColumn(
        template: String,
        column: Iterable<*>,
        placeholder: String = Y_COLUMN_PLACEHOLDER
    ): String {
        val xColumnValue = column.joinToString()

        return template.replace(placeholder, xColumnValue)
    }

    companion object {
        private const val X_COLUMN_PLACEHOLDER = "%X_ARRAY%"
        private const val X_PIE_COLUMN_PLACEHOLDER = "%X_PIE_ARRAY%"
        private const val X_BAR_COLUMN_PLACEHOLDER = "%X_BAR_ARRAY%"

        private const val Y_COLUMN_PLACEHOLDER = "%Y_ARRAY%"
        private const val Y_PIE_COLUMN_PLACEHOLDER = "%Y_PIE_ARRAY%"
        private const val Y_BAR_COLUMN_PLACEHOLDER = "%Y_BAR_ARRAY%"

        private const val PRODUCT_NAME_PLACEHOLDER = "%PRODUCT_NAME%"
        private const val PRODUCT_IMAGE_PLACEHOLDER = "%PRODUCT_IMAGE%"
        private const val PRODUCT_RATING_PLACEHOLDER = "%PRODUCT_RATING%"
        private const val PRODUCT_PRICE_PLACEHOLDER = "%PRODUCT_PRICE%"
        private const val PRODUCT_DESCRIPTION_PLACEHOLDER = "%PRODUCT_DESCRIPTION%"
        private const val RECOMMENDATION_PLACEHOLDER = "%RECOMMENDATION_"
    }
}
