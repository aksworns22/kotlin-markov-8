package markov.manual

import markov.map.MapSize
import markov.map.Position
import markov.map.SimulationMap
import markov.output.ConsoleOutput
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class ManualTest {
    @ParameterizedTest
    @MethodSource("manhattanDistance")
    fun `도착지까지의 거리를 계산한다`(current: Position, destination: Position, expectedValue: Int) {
        assertThat(Distance.fromManhattan(current, destination))
            .isEqualTo(Distance(expectedValue))
    }

    @Test
    fun `모든 위치에 대해 거리를 계산한다`() {
        assertThat(DistanceMap.from(SimulationMap.of(MapSize(3, 3), listOf("s . .", ". d .", ". . ."), ConsoleOutput)))
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
    }
}
