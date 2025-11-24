package markov.map

import markov.input.Data
import markov.input.DataLoader
import markov.output.FakeOutput
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import kotlin.io.path.writeText

class SimulationMapControllerTest {
    @Test
    fun `출발지와 도착지가 안내된 올바른 지도 파일로부터 필요한 데이터를 읽어온다`(@TempDir tempDirectory: Path) {
        val mapFile = tempDirectory.resolve(Data.MAP.path)
        val rawMapSize = "3x2"
        val rawMap = listOf("s . .", ". d .")
        mapFile.writeText("$rawMapSize\n${rawMap[0]}\n${rawMap[1]}")
        assertThat(SimulationMapController(FakeOutput).readMap(MapReader.read(DataLoader.load(mapFile))))
            .isEqualTo(SimulationMap.of(MapSize(3, 2), rawMap))
    }
}
