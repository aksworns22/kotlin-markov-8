package markov

import markov.input.Data
import markov.input.DataLoader
import markov.manual.ManualController
import markov.map.MapReader
import markov.map.SimulationMapController
import markov.movement.MovementController
import markov.movement.MovementReader
import Console
import markov.random.OneToHundredGenerator
import markov.random.RandomGenerator
import markov.simulation.SimulationController
import markov.simulation.SimulationTime
import java.io.InputStream
import kotlin.random.Random

fun runApplication(mapStream: InputStream, movementStream: InputStream) {
    val map = SimulationMapController(Console).readMap(MapReader.read(mapStream))
    val movement =
        MovementController(
            map!!.size,
            Console
        ).readMovement(MovementReader.read(movementStream))
    ManualController(Console).findBestManual(map, movement!!)
    SimulationController(Console).startFrom(map, SimulationTime(10), movement, OneToHundredGenerator)
}

fun main() {
    runApplication(DataLoader.load(Data.MAP), DataLoader.load(Data.PROBABILITY))
}
