package markov.manual

import markov.map.Position
import markov.map.SimulationMap


@JvmInline
value class DistanceMap(val values: Map<Position, Distance>) {
    companion object {
        fun from(simulationMap: SimulationMap): DistanceMap {
            val distances: MutableMap<Position, Distance> = mutableMapOf()
            for (y in 0..<simulationMap.size.height) {
                for (x in 0..<simulationMap.size.width) {
                    val currentPosition = Position(x, y)
                    distances[currentPosition] = Distance.fromManhattan(currentPosition, simulationMap.destination)
                }
            }
            return DistanceMap(distances)
        }
    }
}
