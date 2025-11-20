package markov.movement

import markov.map.Position
import org.assertj.core.api.Assertions.assertThat
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
}
