package markov.map

import java.io.InputStream

object MapReader {
    fun read(inputStream: InputStream): List<String> {
        val rawMap = inputStream.bufferedReader().readLines()
        require(rawMap.size >= 2) { "유효하지 않은 형식입니다" }
        return rawMap
    }
}
