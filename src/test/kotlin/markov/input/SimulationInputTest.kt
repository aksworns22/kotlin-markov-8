package markov.input

import markov.simulation.SimulationTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SimulationInputTest {
    @Test
    fun `시간 제한을 콘솔에서 입력받는다`() {
        assertThat(SimulationTime.from(OnlyOneInput))
            .isEqualTo(SimulationTime(1))
    }
}
