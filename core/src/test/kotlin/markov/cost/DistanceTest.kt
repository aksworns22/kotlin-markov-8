package markov.cost

import markov.map.Position
import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class DistanceTest {
    @ParameterizedTest
    @MethodSource("manhattanDistance")
    fun `도착지까지의 거리를 계산한다`(current: Position, destination: Position, expectedValue: Int) {
        Assertions.assertThat(Distance.fromManhattan(current, destination))
            .isEqualTo(Distance(expectedValue))
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
