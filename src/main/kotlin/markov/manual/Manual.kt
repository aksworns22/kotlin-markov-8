package markov.manual

import markov.map.Position
import markov.map.SimulationMap
import markov.simulation.Moving

data class Cost(val value: Double) {
    companion object {
        val INITIAL = Cost(100.0)
        val DESTINATION = Cost(0.0)
    }
}

data class Manual(val costMap: Map<Position, Cost>) {
    fun improve(
        simulationMap: SimulationMap,
        distanceMap: DistanceMap,
        moving: Moving,
        discountFactor: Double = DEFAULT_DISCOUNT_FACTOR
    ): Manual {
        val nextCostMap = mutableMapOf<Position, Cost>()
        for (position in costMap.keys) {
            if (simulationMap.destination == position) {
                nextCostMap[position] = Cost.DESTINATION
                continue
            }
            val baseCost: Double = distanceMap.values[position]!!.value.toDouble()
            var cost = 0.0
            for (actions in moving.probabilities[position]!!.probabilities) {
                val action = actions.key
                val probability = actions.value
                val probabilityForCost = (probability.end - probability.start + 1) / 100.0
                cost += probabilityForCost * this.costMap[simulationMap.nextPosition(position.next(action))]!!.value
            }
            nextCostMap[position] = Cost(baseCost + discountFactor * cost)
        }
        return Manual(nextCostMap)
    }

    companion object {
        const val DEFAULT_DISCOUNT_FACTOR = 0.9
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
