package markov.movement

import markov.map.MapSize
import markov.map.Position
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class MovementTest {
    @Test
    fun `위치 별 이동 확률을 저장한다`() {
        val movement = Movement.of(
            listOf(
                "0,0:10,10,10,70",
                "1,0:10,10,10,70",
                "0,1:10,10,10,70",
                "1,1:10,10,10,70"
            )
        )
        assertThat(movement).isEqualTo(
            Movement(
                mapOf(
                    Position(0, 0) to Action(
                        mapOf(
                            ActionType.UP to Probability(start = 1, end = 10),
                            ActionType.RIGHT to Probability(start = 11, end = 20),
                            ActionType.DOWN to Probability(start = 21, end = 30),
                            ActionType.LEFT to Probability(start = 31, end = 100)
                        )
                    ),
                    Position(1, 0) to Action(
                        mapOf(
                            ActionType.UP to Probability(start = 1, end = 10),
                            ActionType.RIGHT to Probability(start = 11, end = 20),
                            ActionType.DOWN to Probability(start = 21, end = 30),
                            ActionType.LEFT to Probability(start = 31, end = 100)
                        )
                    ),
                    Position(0, 1) to Action(
                        mapOf(
                            ActionType.UP to Probability(start = 1, end = 10),
                            ActionType.RIGHT to Probability(start = 11, end = 20),
                            ActionType.DOWN to Probability(start = 21, end = 30),
                            ActionType.LEFT to Probability(start = 31, end = 100)
                        )
                    ),
                    Position(1, 1) to Action(
                        mapOf(
                            ActionType.UP to Probability(start = 1, end = 10),
                            ActionType.RIGHT to Probability(start = 11, end = 20),
                            ActionType.DOWN to Probability(start = 21, end = 30),
                            ActionType.LEFT to Probability(start = 31, end = 100)
                        )
                    ),
                )
            )
        )
    }

    @Test
    fun `지도 크기와 동일한 위치 개수가 아니라면 예외를 발생시킨다`() {
        assertThatThrownBy {
            Movement.of(
                listOf(
                    "0,0:10,10,10,70",
                    "1,0:10,10,10,70",
                    "0,1:10,10,10,70",
                    "1,1:10,10,10,70"
                )
            ).validateWith(MapSize(1, 1))
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(Movement.MAP_SIZE_COMPATIBLE_ERROR_MESSAGE)
    }

    @Test
    fun `모든 위치에 대한 정보가 존재하는 지 않는다면 예외를 발생시킨다`() {
        assertThatThrownBy {
            Movement.of(
                listOf(
                    "0,0:10,10,10,70",
                    "0,1:10,10,10,70",
                    "1,1:10,10,10,70"
                )
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(Movement.MISSING_POSITION_ERROR_MESSAGE)
    }
}
