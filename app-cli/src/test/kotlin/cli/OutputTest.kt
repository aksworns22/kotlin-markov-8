package cli

import markov.InvalidFixtures
import markov.cost.CostMapController
import markov.input.Data
import markov.input.DataLoader
import markov.map.MapSize
import markov.map.Position
import markov.map.SimulationMap
import markov.map.SimulationMapController
import markov.movement.Action
import markov.movement.ActionType
import markov.movement.Movement
import markov.movement.MovementController
import markov.movement.MovementReader
import markov.movement.Probability
import markov.random.OnlyOneGenerator
import markov.simulation.SimulationController
import markov.simulation.SimulationTime
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.io.PrintStream
import java.nio.file.Path
import kotlin.io.path.writeText

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
                        ActionType.UP to Probability.Companion.NO,
                        ActionType.DOWN to Probability.Companion.NO,
                        ActionType.LEFT to Probability.Companion.NO,
                        ActionType.RIGHT to Probability(1, 100),
                    )
                ),
                Position(1, 0) to Action(
                    mapOf(
                        ActionType.UP to Probability.Companion.NO,
                        ActionType.DOWN to Probability.Companion.NO,
                        ActionType.LEFT to Probability.Companion.NO,
                        ActionType.RIGHT to Probability(1, 100),
                    )
                ),
                Position(2, 0) to Action(
                    mapOf(
                        ActionType.UP to Probability.Companion.NO,
                        ActionType.DOWN to Probability.Companion.NO,
                        ActionType.LEFT to Probability.Companion.NO,
                        ActionType.RIGHT to Probability(1, 100),
                    )
                )
            )
        )
        val simulations = SimulationController(Console).startFrom(map, limitTime, movement, OnlyOneGenerator)
        Console.println(simulations)
        Assertions.assertThat(output())
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
                        ActionType.UP to Probability.Companion.NO,
                        ActionType.DOWN to Probability.Companion.NO,
                        ActionType.LEFT to Probability.Companion.NO,
                        ActionType.RIGHT to Probability(1, 100),
                    )
                )
            )
        )
        val simulations = SimulationController(Console).startFrom(map, limitTime, movement, OnlyOneGenerator)
        Console.println(simulations)
        Assertions.assertThat(output())
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
        Assertions.assertThat(output()).contains("[ERROR]")
    }


    @Test
    fun `위치 별 이동확률 정보 파일을 읽어 최선의 행동 메뉴얼 결과를 출력한다`(@TempDir tempDirectory: Path) {
        val simulationMap = SimulationMap(MapSize(2, 2), Position(0, 0), Position(1, 1), Position(0, 0))
        val movementFile = tempDirectory.resolve(Data.PROBABILITY.path)
        val rawMovement = listOf("0,0:10,10,10,70", "0,1:10,10,10,70", "1,0:10,10,10,70", "1,1:10,10,10,70")
        movementFile.writeText("${rawMovement[0]}\n${rawMovement[1]}\n${rawMovement[2]}\n${rawMovement[3]}")
        val movement =
            MovementController(
                simulationMap.size,
                Console
            ).readMovement(MovementReader.read(DataLoader.load(movementFile)))
        CostMapController(Console).findCostMap(simulationMap, movement!!)
        Assertions.assertThat(output())
            .contains(
                listOf(
                    "Position(x=0, y=0) :",
                    "Position(x=1, y=0) :",
                    "Position(x=0, y=1) :",
                    "Position(x=1, y=1) :"
                )
            )
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
        Assertions.assertThat(output()).contains("[SUCCESS]")
    }

    @ParameterizedTest
    @MethodSource("readingMovementWithError")
    fun `위치 별 이동 확률을 읽어오는 기능에 문제가 있다면 ERROR로 시작하는 에러 메시지를 출력한다`(
        mapSize: MapSize,
        rawMovement: List<String>
    ) {
        MovementController(mapSize, Console).readMovement(rawMovement)
        Assertions.assertThat(output()).contains("[ERROR]")
    }

    @Test
    fun `계산한 행동 매뉴얼을 출력한다`() {
        val map = SimulationMap(MapSize(2, 2), Position(0, 0), Position(1, 1), Position(0, 0))
        CostMapController(Console).findCostMap(
            map, Movement.Companion.of(
                listOf(
                    "0,0:10,10,10,70",
                    "1,0:10,10,10,70",
                    "0,1:10,10,10,70",
                    "1,1:10,10,10,70"
                )
            )
        )
        Assertions.assertThat(output())
            .contains(
                listOf(
                    "Position(x=0, y=0) :",
                    "Position(x=1, y=0) :",
                    "Position(x=0, y=1) :",
                    "Position(x=1, y=1) :"
                )
            )
    }

    companion object {
        @JvmStatic
        fun readingMapWithError(): List<Arguments> {
            return InvalidFixtures.RAW_MAPS.map {
                Arguments.of(it)
            }
        }

        @JvmStatic
        fun readingMovementWithError(): List<Arguments> {
            return InvalidFixtures.RAW_MOVEMENTS.map {
                Arguments.of(it[0], it[1])
            }
        }
    }
}
