package ru.ovrays.graphontext.service

import ru.ovrays.graphontext.configuration.StorageProperties
import ru.tinkoff.kora.common.Component
import java.nio.file.Files
import kotlin.io.path.Path

@Component
class StorageService(
    private val storageProperties: StorageProperties
) {
    private val storagePath = Path(storageProperties.directory)

    fun writeFile(filename: String, content: ByteArray): String {
        val filePath = storagePath.resolve(filename)

        Files.createFile(filePath)
        Files.write(filePath, content)

        return filePath.toString()
    }

    fun readFile(filename: String): ByteArray {
        val filePath = storagePath.resolve(filename)

        return Files.readAllBytes(filePath)
    }
}
