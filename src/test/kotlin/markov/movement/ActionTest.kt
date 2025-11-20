package markov.movement

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ActionTest {
    @ParameterizedTest
    @ValueSource(strings = ["100", "100,0", "70,20,15,0"])
    fun `이동 확률의 총합이 유효 범위를 벗어나면 예외를 발생시킨다`(rawAction: String) {
        assertThatThrownBy { Action.of(rawAction) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(Action.INVALID_PROBABILITY_ERROR_MESSAGE)
    }
}
