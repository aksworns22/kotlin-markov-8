package markov.simulation

import markov.map.MapSize
import markov.map.Position
import markov.map.SimulationMap
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class SimulationTest {

    @ParameterizedTest
    @MethodSource("nextPosition")
    fun `확률에 따라 위치를 이동한다`(probability: Double, nextPosition: Position) {
        val map = SimulationMap(
            size = MapSize(3, 3),
            start = Position(0, 0),
            destination = Position(2, 2),
            current = Position(1, 1)
        )
        val timeLimit = SimulationTime(2)
        val currentTime = SimulationTime(0)
        val moving = Moving(
            mapOf(
                Position(1, 1) to Action(
                    mapOf(
                        ActionType.UP to 0.25,
                        ActionType.DOWN to 0.25,
                        ActionType.RIGHT to 0.25,
                        ActionType.LEFT to 0.25
                    )
                )
            )
        )
        val nextSimulation = Simulation(map, currentTime, timeLimit, moving).next(probability)
        assertAll(
            {
                assertThat(nextSimulation.map.current)
                    .isEqualTo(nextPosition)
            },
            {
                assertThat(nextSimulation.current)
                    .isEqualTo(SimulationTime(currentTime.time + 1))
            }
        )
    }

    companion object {
        @JvmStatic
        fun nextPosition(): List<Arguments> {
            return listOf(
                Arguments.of(0.0, Position(1, 1).next(ActionType.UP)),
                Arguments.of(0.2, Position(1, 1).next(ActionType.UP)),
                Arguments.of(0.45, Position(1, 1).next(ActionType.DOWN)),
                Arguments.of(0.74, Position(1, 1).next(ActionType.RIGHT)),
                Arguments.of(0.95, Position(1, 1).next(ActionType.LEFT)),
                Arguments.of(0.99, Position(1, 1).next(ActionType.LEFT)),
            )
        }
    }
}
