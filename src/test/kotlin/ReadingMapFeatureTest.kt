import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource


class ReadingMapFeatureTest {
    @Test
    fun `지도 파일을 읽어온다`() {
        assertThatCode { DataLoader.load(Data.MAP).available() }
            .doesNotThrowAnyException()
    }

    @Test
    fun `지도 크기를 읽어온다`() {
        assertThatCode { MapSize(1, 1) }
            .doesNotThrowAnyException()
    }

    @ParameterizedTest
    @CsvSource(value = ["0, 1", "1, 0", "-1, 1", "1, -1", "0, 0", "-1, -1"])
    fun `지도 크기가 양수가 아니라면 예외를 발생시킨다`(width: Int, height: Int) {
        assertThatThrownBy { MapSize(width, height) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining(MapSize.INVALID_VALUE_ERROR_MESSAGE)
    }

    @ParameterizedTest
    @ValueSource(strings = ["a", "1.5", "1.0", "-1", "0", " ", "", "@"])
    fun `지도 크기로 입력받은 값이 올바르지 않다면 예외를 발생시킨다`(invalidSize: String) {
        assertThatThrownBy { MapSize.of(invalidSize, invalidSize) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining(MapSize.INVALID_VALUE_ERROR_MESSAGE);
    }

    @Test
    fun `지도의 위치 정보를 읽어온다`() {
        assertThat(SimulationMap.of(MapSize(2, 2), listOf("s .", ". d")))
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
        assertThatThrownBy {
            val mapSize = MapSize(2, 2)
            SimulationMap.of(mapSize, listOf("s .", invalidMapRow))
        }.isInstanceOf(IllegalArgumentException::class.java).hasMessage(SimulationMap.INVALID_SIZE_ERROR_MESSAGE)
    }

    @ParameterizedTest
    @MethodSource("rawMap")
    fun `지도가 출발지와 도착지를 포함하지 않는다면 예외를 발생시킨다`(mapSize: MapSize, rawMap: List<String>) {
        assertThatThrownBy { SimulationMap.of(mapSize, rawMap) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(SimulationMap.LOCATION_FINDING_ERROR_MESSAGE)
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
    }
}
