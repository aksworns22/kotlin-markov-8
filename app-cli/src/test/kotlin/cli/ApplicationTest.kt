package cli

import markov.input.Data
import markov.input.DataLoader
import markov.simulation.SimulationTime
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.io.PrintStream
import java.nio.file.Path
import kotlin.io.path.writeText

class ApplicationTest {
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
    fun `지도 파일과 위치 파일을 읽어 메뉴얼과 시뮬레이션 결과를 출력한다`(@TempDir tempDirectory: Path) {
        val mapFile = tempDirectory.resolve(Data.MAP.path)
        val rawMapSize = "2x2"
        val rawMap = listOf("s .", ". d")
        mapFile.writeText("$rawMapSize\n${rawMap[0]}\n${rawMap[1]}")
        val movementFile = tempDirectory.resolve(Data.PROBABILITY.path)
        val rawMovement = listOf("0,0:10,10,10,70", "0,1:10,10,10,70", "1,0:10,10,10,70", "1,1:10,10,10,70")
        movementFile.writeText("${rawMovement[0]}\n${rawMovement[1]}\n${rawMovement[2]}\n${rawMovement[3]}")
        runApplication(DataLoader.load(mapFile), DataLoader.load(movementFile), SimulationTime(3))
        Assertions.assertThat(output())
            .contains(
                listOf(
                    "[SUCCESS] 지도를 불러왔습니다",
                    "[SUCCESS] 위치 별 이동 확률을 불러왔습니다",
                    "Position(x=0, y=0) :",
                    "Position(x=1, y=0) :",
                    "Position(x=0, y=1) :",
                    "Position(x=1, y=1) :",
                    "[최종 결과]",
                )
            )
    }
}
