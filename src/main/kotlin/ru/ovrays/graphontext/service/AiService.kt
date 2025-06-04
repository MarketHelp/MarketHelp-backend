package ru.ovrays.graphontext.service

import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.add
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import ru.ovrays.graphontext.client.YaMarketClient
import ru.ovrays.graphontext.model.GraphicFormat.BAR
import ru.ovrays.graphontext.model.GraphicFormat.PIE
import java.util.*
import kotlin.random.Random

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
            val values = List(4) { Random.nextInt(30, 80) }

            dataFrameOf("x", "y")(
                "Описание", values[0],
                "Качество", values[1],
                "Упаковка", values[2],
                "Доставка", values[3]
            )
        }

        PIE.value -> {
            dataFrameOf("x", "y")("Положительные", 60, "Отрицательные", 20, "Нейтральные", 20)
        }

        else -> {
            val recommendations = dataFrameOf("recommendations")(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt"
            )

            dataFrameOf(
                "productName",
                "productImage",
                "productRating",
                "productPrice",
                "productDescription"
            )(
                "Продукт №1",
                "https://img.freepik.com/premium-photo/" +
                        "red-shopping-baskets-parcel-box-float-air-smartphone-online-shopping-concept_149391-330.jpg",
                5,
                "150",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
            )
                .add(recommendations)
        }
    }
}
