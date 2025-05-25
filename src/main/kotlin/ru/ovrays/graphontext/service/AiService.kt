package ru.ovrays.graphontext.service

import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.dataframe.api.emptyDataFrame
import ru.ovrays.graphontext.client.YaMarketClient
import ru.ovrays.graphontext.model.GraphicFormat.BAR
import ru.ovrays.graphontext.model.GraphicFormat.PIE
import java.util.*

@Suppress("MagicNumber", "UnusedParameter")
class AiService(
    private val yaMarketClient: YaMarketClient
) {
    fun getStatistics(
        shopId: Long,
        productId: UUID,
        format: String
    ): DataFrame<*> = when (format) {
        BAR.value -> {
            dataFrameOf("x", "y")("Описание", 50, "Качество", 40, "Упаковка", 60, "Доставка", 80)
        }

        PIE.value -> {
            dataFrameOf("x", "y")("Положительные", 60, "Отрицательные", 20, "Нейтральные", 20)
        }

        else -> {
            emptyDataFrame<Any>()
        }
    }
}
