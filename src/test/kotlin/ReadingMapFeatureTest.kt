import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.Test

class ReadingMapFeatureTest {
    @Test
    fun `지도 파일을 읽어온다`() {
        assertThatCode { DataLoader.load(Data.MAP).available() }
            .doesNotThrowAnyException()
    }
}
