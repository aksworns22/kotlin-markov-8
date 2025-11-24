package cli

import markov.input.Data
import markov.input.DataLoader
import markov.cost.CostMapController
import markov.map.MapReader
import markov.map.SimulationMapController
import markov.movement.MovementController
import markov.movement.MovementReader
import markov.output.Message
import markov.output.MessageType
import markov.random.OneToHundredRandomGenerator
import markov.simulation.SimulationController
import markov.simulation.SimulationTime
import java.awt.TrayIcon
import java.io.InputStream

fun runApplication(mapStream: InputStream, movementStream: InputStream, simulationTime: SimulationTime) {
    try {
        val map = SimulationMapController(Console).readMap(MapReader.read(mapStream))
        val movement =
            MovementController(
                map!!.size,
                Console
            ).readMovement(MovementReader.read(movementStream))
        CostMapController(Console).findCostMap(map, movement!!)
        SimulationController(Console).startFrom(map, SimulationTime(10), movement, OneToHundredRandomGenerator)
    } catch (_: IllegalArgumentException) {
        println("오류가 발생해 프로그램을 종료합니다")
    }
}

fun main(args: Array<String>) {
    if (args.size != 1) {
        println("시뮬레이션 시간만을 인자로 전달해야합니다.")
    }
    runApplication(DataLoader.load(Data.MAP), DataLoader.load(Data.PROBABILITY), SimulationTime.of(args[0]))
}
