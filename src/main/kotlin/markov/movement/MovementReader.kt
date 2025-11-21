package markov.movement

import java.io.InputStream

object MovementReader {
    fun read(inputStream: InputStream): List<String> {
        val rawMovement = inputStream.bufferedReader().readLines()
        return rawMovement
    }
}
