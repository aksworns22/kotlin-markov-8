package markov.simulation

import markov.map.MapSize
import markov.map.Position
import markov.map.SimulationMap
import markov.output.ConsoleOutput
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class SimulationTest {
    @ParameterizedTest
    @MethodSource("nextPosition")
    fun `확률에 따라 위치를 이동한다`(probability: Int, nextPosition: Position) {
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
                        ActionType.UP to Probability(start = 1, end = 25),
                        ActionType.DOWN to Probability(start = 26, end = 50),
                        ActionType.LEFT to Probability(start = 51, end = 75),
                        ActionType.RIGHT to Probability(start = 76, end = 100)
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

    @Test
    fun `제한 시간을 초과하면 시뮬레이션을 종료한다`() {
        val map = SimulationMap.of(MapSize(2, 1), listOf("s d"), ConsoleOutput)
        assertThat(Simulation(map, SimulationTime(1), SimulationTime(1), Moving(mapOf())).isEnd)
            .isEqualTo(true)
    }

    companion object {
        @JvmStatic
        fun nextPosition(): List<Arguments> {
            return listOf(
                Arguments.of(1, Position(1, 1).next(ActionType.UP)),
                Arguments.of(25, Position(1, 1).next(ActionType.UP)),
                Arguments.of(26, Position(1, 1).next(ActionType.DOWN)),
                Arguments.of(51, Position(1, 1).next(ActionType.LEFT)),
                Arguments.of(76, Position(1, 1).next(ActionType.RIGHT)),
                Arguments.of(100, Position(1, 1).next(ActionType.RIGHT)),
            )
        }
    }
}
