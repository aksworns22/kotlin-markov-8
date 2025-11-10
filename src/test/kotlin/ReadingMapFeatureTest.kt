import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class ReadingMapFeatureTest {
    @Test
    fun `지도 파일을 읽어온다`() {
        assertThatCode { DataLoader.load(Data.MAP).available() }
            .doesNotThrowAnyException()
    }

    @ParameterizedTest
    @CsvSource(value = ["0, 1", "1, 0", "-1, 1", "1, -1", "0, 0", "-1, -1"])
    fun `지도 크기가 양수가 아니라면 예외를 발생시킨다`(width: Int, height: Int) {
        assertThatThrownBy { MapSize(width, height) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining(MapSize.INVALID_VALUE_ERROR_MESSAGE);
    }
}
