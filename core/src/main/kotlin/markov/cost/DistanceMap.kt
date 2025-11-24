package markov.cost

import markov.map.Position
import markov.map.SimulationMap


@JvmInline
value class DistanceMap(val values: Map<Position, Distance>) {
    companion object {
        fun from(simulationMap: SimulationMap): DistanceMap {
            val distances: MutableMap<Position, Distance> = mutableMapOf()
            for (y in SimulationMap.MAP_MINIMUM_BOUND..<simulationMap.size.height) {
                for (x in SimulationMap.MAP_MINIMUM_BOUND..<simulationMap.size.width) {
                    val currentPosition = Position(x, y)
                    distances[currentPosition] = Distance.fromManhattan(currentPosition, simulationMap.destination)
                }
            }
            return DistanceMap(distances)
        }
    }
}
