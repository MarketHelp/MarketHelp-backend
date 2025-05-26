package ru.ovrays.graphontext.service

import ru.ovrays.graphontext.configuration.StorageProperties
import ru.tinkoff.kora.common.Component
import java.nio.file.Files
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.createParentDirectories
import kotlin.io.path.writeBytes

@Component
class StorageService(
    private val storageProperties: StorageProperties
) {
    private val outputPath = Path(storageProperties.output)
    private val templatePath = Path(storageProperties.templates)

    fun writeFile(filename: String, content: ByteArray): String {
        val filePath = outputPath.resolve(filename)

        filePath.createParentDirectories()
        filePath.writeBytes(content)

        return filePath.toString()
    }

    fun readFile(filename: String): ByteArray {
        val filePath = outputPath.resolve(filename)

        return Files.readAllBytes(filePath)
    }

    fun readTemplate(templateName: String): String {
        val filePath = templatePath.resolve("$templateName.html")

        return Files.readString(filePath)
    }
}
