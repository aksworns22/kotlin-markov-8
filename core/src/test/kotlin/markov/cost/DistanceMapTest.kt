package markov.cost

import markov.map.MapSize
import markov.map.Position
import markov.map.SimulationMap
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class DistanceMapTest {
    @Test
    fun `모든 위치에 대해 거리를 계산한다`() {
        Assertions.assertThat(
            DistanceMap.from(
                SimulationMap(
                    MapSize(3, 3),
                    Position(0, 0),
                    Position(1, 1),
                    Position(0, 0)
                )
            )
        )
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
}
