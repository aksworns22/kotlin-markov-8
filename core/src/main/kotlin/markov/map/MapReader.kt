package markov.map

import java.io.InputStream

object MapReader {
    const val INVALID_FORMAT_ERROR_MESSAGE = "유효하지 않은 형식입니다"
    const val MIN_FILE_LINE_NUMBER = 2
    fun read(inputStream: InputStream): List<String> {
        val rawMap = inputStream.bufferedReader().readLines()
        require(rawMap.size >= MIN_FILE_LINE_NUMBER) { INVALID_FORMAT_ERROR_MESSAGE }
        return rawMap
    }
}
