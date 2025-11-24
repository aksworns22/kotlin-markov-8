package markov.simulation

import markov.map.MapSize
import markov.map.Position
import markov.map.SimulationMap
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SimulationTest {
    @Test
    fun `제한 시간을 초과하면 시뮬레이션을 종료한다`() {
        val map = SimulationMap(MapSize(2, 1), Position(0, 0), Position(1, 0), Position(0, 0))
        assertThat(Simulation(map, SimulationTime(1), SimulationTime(1)).state)
            .isEqualTo(SimulationState.FAIL)
    }

    @Test
    fun `목적지에 도착하면 시뮬레이션을 종료한다`() {
        val map = SimulationMap(MapSize(2, 1), Position(0, 0), Position(1, 0), Position(1, 0))
        assertThat(Simulation(map, SimulationTime(0), SimulationTime(3)).state).isEqualTo(SimulationState.SUCCESS)
    }
}
