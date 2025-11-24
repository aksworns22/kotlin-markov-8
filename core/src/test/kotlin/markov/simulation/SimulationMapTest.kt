package markov.simulation

import markov.map.MapSize
import markov.map.Position
import markov.map.SimulationMap
import markov.movement.Action
import markov.movement.ActionType
import markov.movement.Movement
import markov.movement.Probability
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class SimulationMapTest {
    @ParameterizedTest
    @MethodSource("nextPosition")
    fun `확률에 따라 위치를 이동한다`(probability: Int, nextPosition: Position) {
        val map = SimulationMap(
            size = MapSize(3, 3),
            start = Position(0, 0),
            destination = Position(2, 2),
            current = Position(1, 1)
        )
        val action = Action(
            mapOf(
                ActionType.UP to Probability(start = 1, end = 25),
                ActionType.DOWN to Probability(start = 26, end = 50),
                ActionType.LEFT to Probability(start = 51, end = 75),
                ActionType.RIGHT to Probability(start = 76, end = 100)
            )
        )
        val movement = Movement(
            mapOf(
                Position(0, 0) to action,
                Position(0, 1) to action,
                Position(0, 2) to action,
                Position(1, 0) to action,
                Position(1, 1) to action,
                Position(1, 2) to action,
                Position(2, 0) to action,
                Position(2, 1) to action,
                Position(2, 2) to action,
            )
        )
        val position = map.nextPosition(movement.nextPosition(map.current, probability))
        assertThat(position).isEqualTo(nextPosition)
    }

    @ParameterizedTest
    @MethodSource("stayConditions")
    fun `이동이 불가능한 방향으로의 입력은 제자리에 멈춘다`(movingValue: Map<Position, Action>) {
        val position = Position(0, 0)
        val map = SimulationMap(MapSize(1, 1), position, position, position)
        assertThat(map.nextPosition(Movement(movingValue).nextPosition(map.current, 100))).isEqualTo(position)
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

        @JvmStatic
        fun stayConditions(): List<Arguments> {
            return listOf(
                Arguments.of(
                    mapOf(
                        Position(0, 0) to Action(
                            mapOf(
                                ActionType.UP to Probability(start = 1, end = 100),
                                ActionType.DOWN to Probability.ZERO,
                                ActionType.LEFT to Probability.ZERO,
                                ActionType.RIGHT to Probability.ZERO
                            )
                        )
                    )
                ),
                Arguments.of(
                    mapOf(
                        Position(0, 0) to Action(
                            mapOf(
                                ActionType.UP to Probability.ZERO,
                                ActionType.DOWN to Probability(start = 1, end = 100),
                                ActionType.LEFT to Probability.ZERO,
                                ActionType.RIGHT to Probability.ZERO
                            )
                        )
                    )
                ),
                Arguments.of(
                    mapOf(
                        Position(0, 0) to Action(
                            mapOf(
                                ActionType.UP to Probability.ZERO,
                                ActionType.DOWN to Probability.ZERO,
                                ActionType.LEFT to Probability(start = 1, end = 100),
                                ActionType.RIGHT to Probability.ZERO
                            )
                        )
                    )
                ),
                Arguments.of(
                    mapOf(
                        Position(0, 0) to Action(
                            mapOf(
                                ActionType.UP to Probability.ZERO,
                                ActionType.DOWN to Probability.ZERO,
                                ActionType.LEFT to Probability.ZERO,
                                ActionType.RIGHT to Probability(start = 1, end = 100)
                            )
                        )
                    )
                ),
            )
        }
    }
}
