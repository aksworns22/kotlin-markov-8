package markov.output

import markov.map.MapSize
import markov.map.Position
import markov.map.SimulationMap
import markov.random.OnlyOneGenerator
import markov.simulation.Action
import markov.simulation.ActionType
import markov.simulation.Movement
import markov.simulation.Probability
import markov.simulation.Simulation
import markov.simulation.SimulationIterator
import markov.simulation.SimulationTime

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
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
        SimulationMap.of(MapSize(2, 2), listOf("s .", ". d"), ConsoleOutput)
        Assertions.assertThat(output()).contains("[SUCCESS]")
    }

    @Test
    fun `최종 목적지에 시간 내에 도달하는 경우 진행 상황을 콘솔에 출력한다`() {
        val map = SimulationMap.of(MapSize(3, 1), listOf("s . d"), ConsoleOutput)
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
        val simulationMaps = SimulationIterator.startFrom(map, limitTime, movement, OnlyOneGenerator)
        (0..limitTime.time).forEach { currentTime ->
            ConsoleOutput.println(
                SimulationResult.of(
                    Simulation(simulationMaps[currentTime], SimulationTime(currentTime), limitTime)
                )
            )
        }
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
        val map = SimulationMap.of(MapSize(3, 1), listOf("s . d"), ConsoleOutput)
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
        val simulationMaps = SimulationIterator.startFrom(map, limitTime, movement, OnlyOneGenerator)
        (0..limitTime.time).forEach { currentTime ->
            ConsoleOutput.println(
                SimulationResult.of(
                    Simulation(simulationMaps[currentTime], SimulationTime(currentTime), limitTime)
                )
            )
        }
        assertThat(output())
            .contains(
                listOf(
                    "[시간: 0] x: 0, y: 0",
                    "[시간: 1] x: 1, y: 0",
                    "[최종 결과] 목적지에 시간 내에 도착하지 못했습니다"
                )
            )
    }
}
