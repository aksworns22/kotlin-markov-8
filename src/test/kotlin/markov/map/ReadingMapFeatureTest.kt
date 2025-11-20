package markov.map

import markov.input.Data
import markov.input.DataLoader
import markov.output.Console
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.nio.file.Path
import kotlin.io.path.writeText

class ReadingMapFeatureTest {
    @Test
    fun `지도 파일을 읽어온다`() {
        Assertions.assertThatCode { DataLoader.load(Data.MAP).available() }
            .doesNotThrowAnyException()
    }

    @Test
    fun `지도 크기를 읽어온다`() {
        Assertions.assertThatCode { MapSize(1, 1) }
            .doesNotThrowAnyException()
    }

    @ParameterizedTest
    @CsvSource(value = ["0, 1", "1, 0", "-1, 1", "1, -1", "0, 0", "-1, -1"])
    fun `지도 크기가 양수가 아니라면 예외를 발생시킨다`(width: Int, height: Int) {
        Assertions.assertThatThrownBy { MapSize(width, height) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining(MapSize.INVALID_VALUE_ERROR_MESSAGE)
    }

    @ParameterizedTest
    @ValueSource(strings = ["a", "1.5", "1.0", "-1", "0", " ", "", "@"])
    fun `지도 크기로 입력받은 값이 올바르지 않다면 예외를 발생시킨다`(invalidSize: String) {
        Assertions.assertThatThrownBy { MapSize.of(invalidSize, invalidSize) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining(MapSize.INVALID_VALUE_ERROR_MESSAGE)
    }

    @Test
    fun `지도의 위치 정보를 읽어온다`() {
        Assertions.assertThat(SimulationMap(MapSize(2, 2), Position(0, 0), Position(1, 1), Position(0, 0)))
            .isEqualTo(
                SimulationMap(
                    MapSize(2, 2),
                    start = Position(0, 0),
                    destination = Position(1, 1),
                    current = Position(0, 0)
                )
            )
    }

    @ParameterizedTest
    @ValueSource(strings = [". . d", "d", ""])
    fun `실제 지도 데이터가 선언된 크기와 일치하는지 않으면 예외를 발생시킨다`(invalidMapRow: String) {
        Assertions.assertThatThrownBy {
            val mapSize = MapSize(2, 2)
            SimulationMap.of(mapSize, listOf("s .", invalidMapRow))
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(SimulationMap.INVALID_SIZE_ERROR_MESSAGE)
    }

    @ParameterizedTest
    @MethodSource("rawMap")
    fun `지도가 출발지와 도착지를 포함하지 않는다면 예외를 발생시킨다`(mapSize: MapSize, rawMap: List<String>) {
        Assertions.assertThatThrownBy { SimulationMap.of(mapSize, rawMap) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(SimulationMap.LOCATION_FINDING_ERROR_MESSAGE)
    }

    @ParameterizedTest
    @MethodSource("duplicatedLocationRawMap")
    fun `지도가 출발지와 도착지가 각 두개 이상 포함하면 예외를 발생시킨다`(mapSize: MapSize, rawMap: List<String>) {
        Assertions.assertThatThrownBy { SimulationMap.of(mapSize, rawMap) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(SimulationMap.TOO_MANY_LOCATION_ERROR)
    }

    @ParameterizedTest
    @ValueSource(strings = ["@ . d", "1 . d", ". \n d"])
    fun `지도에 유효하지 않은 값이 포함되었다면 예외를 발생시킨다`(rowMap: String) {
        Assertions.assertThatThrownBy { SimulationMap.of(MapSize(3, 2), listOf("s . .", rowMap)) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(SimulationMap.INVALID_LOCATION_ERROR_MESSAGE)
    }

    @Test
    fun `출발지와 도착지가 안내된 올바른 지도 파일로부터 필요한 데이터를 읽어온다`(@TempDir tempDirectory: Path) {
        val mapFile = tempDirectory.resolve(Data.MAP.path)
        val rawMapSize = "3x2"
        val rawMap = listOf("s . .", ". d .")
        mapFile.writeText("$rawMapSize\n${rawMap[0]}\n${rawMap[1]}")
        Assertions.assertThat(SimulationMapController(Console).readMap(MapReader.read(DataLoader.load(mapFile))))
            .isEqualTo(SimulationMap.of(MapSize(3, 2), rawMap))
    }

    companion object {
        @JvmStatic
        fun rawMap(): List<Arguments> {
            return listOf(
                Arguments.of(
                    MapSize(2, 2), listOf(". .", ". d")
                ),
                Arguments.of(
                    MapSize(2, 2), listOf(". .", ". s")
                ),
                Arguments.of(
                    MapSize(1, 1), listOf(".")
                )
            )
        }

        @JvmStatic
        fun duplicatedLocationRawMap(): List<Arguments> {
            return listOf(
                Arguments.of(
                    MapSize(2, 2), listOf("s .", "s d")
                ),
                Arguments.of(
                    MapSize(2, 2), listOf("s d", ". d")
                ),
                Arguments.of(
                    MapSize(3, 2), listOf("s . d", "s . d")
                )
            )
        }
    }
}
