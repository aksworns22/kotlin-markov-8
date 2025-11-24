package markov.map

import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

class MapSizeTest {
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
}
