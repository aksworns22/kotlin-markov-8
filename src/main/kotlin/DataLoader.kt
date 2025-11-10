import java.io.InputStream

object DataLoader {
    fun load(data: Data): InputStream {
        return javaClass.getResourceAsStream(data.path) ?: throw IllegalStateException("File not found")
    }
}
