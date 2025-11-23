package markov.input

import markov.simulation.SimulationTime
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class SimulationInputTest {
    @ParameterizedTest
    @ValueSource(strings = ["-1", "1.2", "x", " "])
    fun `시간 제한의 입력이 양수가 아니라면 예외를 던진다`(rawTime: String) {
        assertThatThrownBy { SimulationTime.of(rawTime) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(SimulationTime.INVALID_VALUE_ERROR_MESSAGE)
    }
}
