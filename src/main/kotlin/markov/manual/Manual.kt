package markov.manual

import markov.map.Position
import markov.map.SimulationMap
import markov.simulation.ActionType
import markov.simulation.Moving

data class Cost(val value: Double) {
    companion object {
        val INITIAL = Cost(100.0)
        val DESTINATION = Cost(0.0)
    }
}

data class Manual(val costMap: Map<Position, Cost>) {
    fun improve(simulationMap: SimulationMap, distanceMap: DistanceMap, moving: Moving): Manual {
        val nextCostMap = mutableMapOf<Position, Cost>()
        for (position in costMap.keys) {
            if (simulationMap.destination == position) {
                nextCostMap[position] = Cost.DESTINATION
                continue
            }
            var cost: Double = distanceMap.values[position]!!.value.toDouble()
            for (actions in moving.probabilities[position]!!.probabilities) {
                val action = actions.key
                val probability = actions.value
                val probabilityForCost = (probability.end - probability.start + 1) / 100.0
                cost += probabilityForCost * this.costMap[simulationMap.nextPosition(position.next(action))]!!.value
            }
            nextCostMap[position] = Cost(cost)
        }
        return Manual(nextCostMap)
    }

    companion object {
        fun from(destination: Position, moving: Moving): Manual {
            val initialCosts = mutableMapOf<Position, Cost>()
            moving.probabilities.keys.forEach { position ->
                initialCosts[position] = Cost.INITIAL
            }
            initialCosts[destination] = Cost.DESTINATION
            return Manual(initialCosts)
        }
    }
}
