import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path

object DataLoader {
    fun load(data: Data): InputStream {
        return javaClass.getResourceAsStream(data.path) ?: throw IllegalStateException("File not found")
    }

    fun load(path: Path): InputStream {
        return Files.newInputStream(path)
    }
}
