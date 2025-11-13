package markov

import markov.map.MapSize
import markov.map.SimulationMap
import markov.output.ConsoleOutput
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.io.PrintStream

class OutputTest {
    private lateinit var outputStream: OutputStream

    @BeforeEach
    fun setUp() {
        outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))
    }

    @AfterEach
    fun tearDown() {
        System.setOut(System.out)
        println(output())
    }

    fun output(): String {
        return outputStream.toString().trim()
    }

    @Test
    fun `지도를 성공적으로 읽어 처리한다면 성공 메시지를 출력한다`() {
        SimulationMap.of(MapSize(2, 2), listOf("s .", ". d"), ConsoleOutput)
        Assertions.assertThat(output()).contains("[SUCCESS]")
    }
}
