package markov.cost

import markov.map.MapSize
import markov.map.Position
import markov.map.SimulationMap
import markov.movement.Action
import markov.movement.ActionType
import markov.movement.Movement
import markov.movement.Probability
import markov.output.FakeOutput
import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class CostMapControllerTest {
    @ParameterizedTest
    @MethodSource("rawMoving")
    fun `만들 수 있는 최선의 메뉴얼을 만든다`(rawMoving: Map<Position, Action>) {
        val simulationMap = SimulationMap(MapSize(2, 2), Position(0, 0), Position(1, 1), Position(0, 0))
        Assertions.assertThatCode { CostMapController(FakeOutput).findCostMap(simulationMap, Movement(rawMoving)) }
            .doesNotThrowAnyException()
    }

    companion object {
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
