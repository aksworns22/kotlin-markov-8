package markov.cost

import markov.map.MapSize
import markov.map.Position
import markov.map.SimulationMap
import markov.movement.Action
import markov.movement.ActionType
import markov.movement.Movement
import markov.movement.Probability
import markov.output.FakeOutput
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class CostMapTest {
    @ParameterizedTest
    @MethodSource("manhattanDistance")
    fun `도착지까지의 거리를 계산한다`(current: Position, destination: Position, expectedValue: Int) {
        assertThat(Distance.fromManhattan(current, destination))
            .isEqualTo(Distance(expectedValue))
    }

    @Test
    fun `모든 위치에 대해 거리를 계산한다`() {
        assertThat(DistanceMap.from(SimulationMap(MapSize(3, 3), Position(0, 0), Position(1, 1), Position(0, 0))))
            .isEqualTo(
                DistanceMap(
                    mapOf(
                        Position(0, 0) to Distance(2),
                        Position(0, 1) to Distance(1),
                        Position(0, 2) to Distance(2),
                        Position(1, 0) to Distance(1),
                        Position(1, 1) to Distance(0),
                        Position(1, 2) to Distance(1),
                        Position(2, 0) to Distance(2),
                        Position(2, 1) to Distance(1),
                        Position(2, 2) to Distance(2)
                    )
                )
            )
    }

    @ParameterizedTest
    @MethodSource("rawMoving")
    fun `위치 별 확률을 바탕으로 초기 메뉴얼을 만든다`(rawMoving: Map<Position, Action>) {
        val initialCostMap = CostMap.from(
            destination = Position(1, 1),
            Movement(rawMoving)
        )
        assertThat(initialCostMap)
            .isEqualTo(
                CostMap(
                    mapOf(
                        Position(0, 0) to Cost.INITIAL,
                        Position(0, 1) to Cost.INITIAL,
                        Position(1, 0) to Cost.INITIAL,
                        Position(1, 1) to Cost.DESTINATION
                    )
                )
            )
    }

    @ParameterizedTest
    @MethodSource("rawMoving")
    fun `이전보다 나아진 메뉴얼을 만든다`(rawMoving: Map<Position, Action>) {
        val simulationMap =
            SimulationMap(MapSize(2, 2), start = Position(0, 0), destination = Position(1, 1), current = Position(0, 0))
        val distanceMap = DistanceMap.from(simulationMap)
        val initialCostMap = CostMap.from(destination = Position(1, 1), Movement(rawMoving))
        assertThat(initialCostMap.improve(simulationMap, distanceMap, Movement(rawMoving)))
            .isEqualTo(
                CostMap(
                    mapOf(
                        Position(0, 0) to Cost(92.0),
                        Position(1, 0) to Cost(28.0),
                        Position(0, 1) to Cost(82.0),
                        Position(1, 1) to Cost.DESTINATION
                    )
                )
            )
    }

    @ParameterizedTest
    @MethodSource("rawMoving")
    fun `만들 수 있는 최선의 메뉴얼을 만든다`(rawMoving: Map<Position, Action>) {
        val simulationMap = SimulationMap(MapSize(2, 2), Position(0, 0), Position(1, 1), Position(0, 0))
        assertThatCode { CostMapController(FakeOutput).findCostMap(simulationMap, Movement(rawMoving)) }
            .doesNotThrowAnyException()
    }


    companion object {
        @JvmStatic
        fun manhattanDistance(): List<Arguments> {
            return listOf(
                Arguments.of(Position(0, 0), Position(0, 0), 0),
                Arguments.of(Position(0, 0), Position(2, 0), 2),
                Arguments.of(Position(0, 0), Position(0, 2), 2),
                Arguments.of(Position(0, 0), Position(2, 2), 4),
                Arguments.of(Position(2, 0), Position(0, 0), 2),
                Arguments.of(Position(0, 2), Position(0, 0), 2),
                Arguments.of(Position(2, 2), Position(0, 0), 4),
            )
        }

        @JvmStatic
        fun rawMoving(): List<Arguments> {
            return listOf(
                Arguments.of(
                    mapOf(
                        Position(0, 0) to Action(
                            mapOf(
                                ActionType.UP to Probability(1, 10),
                                ActionType.DOWN to Probability(11, 20),
                                ActionType.LEFT to Probability(21, 30),
                                ActionType.RIGHT to Probability(31, 100)
                            )
                        ),
                        Position(0, 1) to Action(
                            mapOf(
                                ActionType.UP to Probability(1, 10),
                                ActionType.DOWN to Probability(11, 80),
                                ActionType.LEFT to Probability(81, 90),
                                ActionType.RIGHT to Probability(91, 100)
                            )
                        ),
                        Position(1, 0) to Action(
                            mapOf(
                                ActionType.UP to Probability(1, 10),
                                ActionType.DOWN to Probability(11, 80),
                                ActionType.LEFT to Probability(81, 90),
                                ActionType.RIGHT to Probability(91, 100)
                            )
                        ),
                        Position(1, 1) to Action(
                            mapOf(
                                ActionType.UP to Probability(1, 25),
                                ActionType.DOWN to Probability(26, 50),
                                ActionType.LEFT to Probability(51, 75),
                                ActionType.RIGHT to Probability(76, 100)
                            )
                        )
                    )
                )
            )
        }
    }
}
