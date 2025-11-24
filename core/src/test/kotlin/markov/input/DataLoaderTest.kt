package markov.input

import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.Test

class DataLoaderTest {
    @Test
    fun `지도 파일을 읽어온다`() {
        assertThatCode { DataLoader.load(Data.MAP).available() }
            .doesNotThrowAnyException()
    }

    @Test
    fun `위치 별 확률 정보 파일을 읽어온다`() {
        assertThatCode { DataLoader.load(Data.PROBABILITY).available() }
            .doesNotThrowAnyException()
    }
}
