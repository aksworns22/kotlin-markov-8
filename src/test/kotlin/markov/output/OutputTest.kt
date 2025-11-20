package markov.output

import markov.map.MapSize
import markov.map.Position
import markov.map.SimulationMap
import markov.map.SimulationMapController
import markov.random.OnlyOneGenerator
import markov.movement.Action
import markov.movement.ActionType
import markov.movement.Movement
import markov.movement.MovementController
import markov.movement.Probability
import markov.simulation.SimulationController
import markov.simulation.SimulationTime

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.io.PrintStream

class OutputTest {
    private lateinit var outputStream: OutputStream

    @BeforeEach
    fun setUp() {
        outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))
    }

    @AfterEach
    fun tearDown() {
        System.setOut(System.out)
        println(output())
    }

    fun output(): String {
        return outputStream.toString().trim()
    }

    @Test
    fun `지도를 성공적으로 읽어 처리한다면 성공 메시지를 출력한다`() {
        SimulationMapController(Console).readMap(listOf("2x2", "s .", ". d"))
        Assertions.assertThat(output()).contains("[SUCCESS]")
    }

    @Test
    fun `최종 목적지에 시간 내에 도달하는 경우 진행 상황을 콘솔에 출력한다`() {
        val map = SimulationMap(MapSize(3, 1), Position(0, 0), Position(2, 0), Position(0, 0))
        val limitTime = SimulationTime(5)
        val movement = Movement(
            mapOf(
                Position(0, 0) to Action(
                    mapOf(
                        ActionType.UP to Probability.NO,
                        ActionType.DOWN to Probability.NO,
                        ActionType.LEFT to Probability.NO,
                        ActionType.RIGHT to Probability(1, 100),
                    )
                ),
                Position(1, 0) to Action(
                    mapOf(
                        ActionType.UP to Probability.NO,
                        ActionType.DOWN to Probability.NO,
                        ActionType.LEFT to Probability.NO,
                        ActionType.RIGHT to Probability(1, 100),
                    )
                ),
                Position(2, 0) to Action(
                    mapOf(
                        ActionType.UP to Probability.NO,
                        ActionType.DOWN to Probability.NO,
                        ActionType.LEFT to Probability.NO,
                        ActionType.RIGHT to Probability(1, 100),
                    )
                )
            )
        )
        val simulations = SimulationController(Console).startFrom(map, limitTime, movement, OnlyOneGenerator)
        Console.println(simulations)
        assertThat(output())
            .contains(
                listOf(
                    "[시간: 0] x: 0, y: 0",
                    "[시간: 1] x: 1, y: 0",
                    "[시간: 2] x: 2, y: 0",
                    "[최종 결과] 목적지에 시간 내에 도착했습니다"
                )
            )
    }


    @Test
    fun `최종 목적지에 시간 내에 도달하는 못하는 경우 진행 상황을 콘솔에 출력한다`() {
        val map = SimulationMap(MapSize(3, 1), Position(0, 0), Position(2, 0), Position(0, 0))
        val limitTime = SimulationTime(1)
        val movement = Movement(
            mapOf(
                Position(0, 0) to Action(
                    mapOf(
                        ActionType.UP to Probability.NO,
                        ActionType.DOWN to Probability.NO,
                        ActionType.LEFT to Probability.NO,
                        ActionType.RIGHT to Probability(1, 100),
                    )
                )
            )
        )
        val simulations = SimulationController(Console).startFrom(map, limitTime, movement, OnlyOneGenerator)
        Console.println(simulations)
        assertThat(output())
            .contains(
                listOf(
                    "[시간: 0] x: 0, y: 0",
                    "[시간: 1] x: 1, y: 0",
                    "[최종 결과] 목적지에 시간 내에 도착하지 못했습니다"
                )
            )
    }

    @ParameterizedTest
    @MethodSource("readingMapWithError")
    fun `지도를 읽어오는 기능에 문제가 있다면 ERROR로 시작하는 에러 메시지를 출력한다`(rawMap: List<String>) {
        SimulationMapController(Console).readMap(rawMap)
        assertThat(output()).contains("[ERROR]")
    }

    @Test
    fun `위치 별 이동 확률을 불러오는데 문제가 없다면 SUCCESS로 시작하는 성공 메시지를 출력한다`() {
        MovementController(MapSize(2, 2), Console).readMovement(
            listOf(
                "0,0:10,10,10,70",
                "1,0:10,10,10,70",
                "0,1:10,10,10,70",
                "1,1:10,10,10,70"
            )
        )
        assertThat(output()).contains("[SUCCESS]")
    }

    companion object {
        @JvmStatic
        fun `readingMapWithError`(): List<Arguments> {
            return listOf(
                Arguments.of(listOf("2x1", "s .")),
                Arguments.of(listOf("2x1", ". d")),
                Arguments.of(listOf("2x2", ". d")),
                Arguments.of(listOf("2x2", ". d", "s x")),
                Arguments.of(listOf("2xn", ". d")),
                Arguments.of(listOf("1", "s d")),
                Arguments.of(listOf("2x2", ". d", "s d")),
            )
        }
    }
}
