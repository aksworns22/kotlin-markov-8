package markov.input

import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path

object DataLoader {
    const val FILE_NOT_FOUND_ERROR_MESSAGE = "파일을 찾을 수 없습니다"
    fun load(data: Data): InputStream {
        return this::class.java.classLoader.getResourceAsStream(data.path)
            ?: throw IllegalStateException(FILE_NOT_FOUND_ERROR_MESSAGE)
    }

    fun load(path: Path): InputStream {
        return Files.newInputStream(path)
    }
}
