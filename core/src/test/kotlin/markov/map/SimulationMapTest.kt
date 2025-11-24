package markov.map

import markov.movement.Action
import markov.movement.ActionType
import markov.movement.Movement
import markov.movement.Probability
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource

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
        Assertions.assertThat(position).isEqualTo(nextPosition)
    }

    @ParameterizedTest
    @MethodSource("stayConditions")
    fun `이동이 불가능한 방향으로의 입력은 제자리에 멈춘다`(movingValue: Map<Position, Action>) {
        val position = Position(0, 0)
        val map = SimulationMap(MapSize(1, 1), position, position, position)
        Assertions.assertThat(map.nextPosition(Movement(movingValue).nextPosition(map.current, 100))).isEqualTo(position)
    }

    @Test
    fun `지도의 위치 정보를 읽어온다`() {
        Assertions.assertThat(SimulationMap.of(MapSize(2, 2), listOf("s .", ". d")))
            .isEqualTo(
                SimulationMap(
                    MapSize(2, 2),
                    start = Position(0, 0),
                    destination = Position(1, 1),
                    current = Position(0, 0)
                )
            )
    }

    @ParameterizedTest
    @ValueSource(strings = [". . d", "d", ""])
    fun `실제 지도 데이터가 선언된 크기와 일치하는지 않으면 예외를 발생시킨다`(invalidMapRow: String) {
        Assertions.assertThatThrownBy {
            val mapSize = MapSize(2, 2)
            SimulationMap.of(mapSize, listOf("s .", invalidMapRow))
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(SimulationMap.INVALID_SIZE_ERROR_MESSAGE)
    }

    @ParameterizedTest
    @MethodSource("rawMap")
    fun `지도가 출발지와 도착지를 포함하지 않는다면 예외를 발생시킨다`(mapSize: MapSize, rawMap: List<String>) {
        Assertions.assertThatThrownBy { SimulationMap.of(mapSize, rawMap) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(SimulationMap.LOCATION_FINDING_ERROR_MESSAGE)
    }

    @ParameterizedTest
    @MethodSource("duplicatedLocationRawMap")
    fun `지도가 출발지와 도착지가 각 두개 이상 포함하면 예외를 발생시킨다`(mapSize: MapSize, rawMap: List<String>) {
        Assertions.assertThatThrownBy { SimulationMap.of(mapSize, rawMap) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(SimulationMap.TOO_MANY_LOCATION_ERROR)
    }

    @ParameterizedTest
    @ValueSource(strings = ["@ . d", "1 . d", ". \n d"])
    fun `지도에 유효하지 않은 값이 포함되었다면 예외를 발생시킨다`(rowMap: String) {
        Assertions.assertThatThrownBy { SimulationMap.of(MapSize(3, 2), listOf("s . .", rowMap)) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(SimulationMap.INVALID_LOCATION_ERROR_MESSAGE)
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
                                ActionType.DOWN to Probability.Companion.ZERO,
                                ActionType.LEFT to Probability.Companion.ZERO,
                                ActionType.RIGHT to Probability.Companion.ZERO
                            )
                        )
                    )
                ),
                Arguments.of(
                    mapOf(
                        Position(0, 0) to Action(
                            mapOf(
                                ActionType.UP to Probability.Companion.ZERO,
                                ActionType.DOWN to Probability(start = 1, end = 100),
                                ActionType.LEFT to Probability.Companion.ZERO,
                                ActionType.RIGHT to Probability.Companion.ZERO
                            )
                        )
                    )
                ),
                Arguments.of(
                    mapOf(
                        Position(0, 0) to Action(
                            mapOf(
                                ActionType.UP to Probability.Companion.ZERO,
                                ActionType.DOWN to Probability.Companion.ZERO,
                                ActionType.LEFT to Probability(start = 1, end = 100),
                                ActionType.RIGHT to Probability.Companion.ZERO
                            )
                        )
                    )
                ),
                Arguments.of(
                    mapOf(
                        Position(0, 0) to Action(
                            mapOf(
                                ActionType.UP to Probability.Companion.ZERO,
                                ActionType.DOWN to Probability.Companion.ZERO,
                                ActionType.LEFT to Probability.Companion.ZERO,
                                ActionType.RIGHT to Probability(start = 1, end = 100)
                            )
                        )
                    )
                ),
            )
        }

        @JvmStatic
        fun rawMap(): List<Arguments> {
            return listOf(
                Arguments.of(
                    MapSize(2, 2), listOf(". .", ". d")
                ),
                Arguments.of(
                    MapSize(2, 2), listOf(". .", ". s")
                ),
                Arguments.of(
                    MapSize(1, 1), listOf(".")
                )
            )
        }

        @JvmStatic
        fun duplicatedLocationRawMap(): List<Arguments> {
            return listOf(
                Arguments.of(
                    MapSize(2, 2), listOf("s .", "s d")
                ),
                Arguments.of(
                    MapSize(2, 2), listOf("s d", ". d")
                ),
                Arguments.of(
                    MapSize(3, 2), listOf("s . d", "s . d")
                )
            )
        }
    }
}
