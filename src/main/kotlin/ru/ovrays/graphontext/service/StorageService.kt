package ru.ovrays.graphontext.service

import com.pdfcrowd.Pdfcrowd.HtmlToImageClient
import org.apache.commons.io.output.ByteArrayOutputStream
import ru.ovrays.graphontext.configuration.CrowdProperties
import ru.ovrays.graphontext.configuration.StorageProperties
import ru.tinkoff.kora.common.Component
import java.io.ByteArrayInputStream
import java.nio.file.Files
import kotlin.io.path.Path
import kotlin.io.path.createParentDirectories
import kotlin.io.path.readBytes
import kotlin.io.path.writeBytes

@Component
class StorageService(
    private val crowdProperties: CrowdProperties,
    private val storageProperties: StorageProperties,
) {
    private val crowdClient = HtmlToImageClient(crowdProperties.name, crowdProperties.key)
        .setOutputFormat("png")

    private val outputPath = Path(storageProperties.output)
    private val templatePath = Path(storageProperties.templates)

    fun writeHtmlToImage(filename: String, byteArray: ByteArray): String {
        val inputStream = ByteArrayInputStream(byteArray)
        val outputStream = ByteArrayOutputStream()

        crowdClient.convertStreamToStream(inputStream, outputStream)

        return writeFile(filename, outputStream.toByteArray())
    }

    private fun writeFile(filename: String, content: ByteArray): String {
        val filePath = outputPath.resolve(filename)

        filePath.createParentDirectories()
        filePath.writeBytes(content)

        return filePath.toString()
    }

    fun readFile(filename: String): ByteArray {
        val filePath = outputPath.resolve(filename)

        return filePath.readBytes()
    }

    fun readTemplate(templateName: String): String {
        val filePath = templatePath.resolve("$templateName.html")

        return Files.readString(filePath)
    }
}
