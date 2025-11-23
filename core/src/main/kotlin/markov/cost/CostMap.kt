package markov.cost

import markov.map.Position
import markov.map.SimulationMap
import markov.movement.Movement
import kotlin.math.abs
import kotlin.math.max

data class CostMap(val costMap: Map<Position, Cost>) {
    fun improve(
        simulationMap: SimulationMap,
        distanceMap: DistanceMap,
        movement: Movement,
        discountFactor: Double = DEFAULT_DISCOUNT_FACTOR
    ): CostMap {
        val nextCostMap = mutableMapOf<Position, Cost>()
        for (position in costMap.keys) {
            if (simulationMap.destination == position) {
                nextCostMap[position] = Cost.DESTINATION
                continue
            }
            val baseCost: Double = distanceMap.values[position]!!.value.toDouble()
            var cost = 0.0
            for (actions in movement.probabilities[position]!!.probabilities) {
                val action = actions.key
                val probability = actions.value
                val probabilityForCost = (probability.end - probability.start + 1) / 100.0
                cost += probabilityForCost * this.costMap[simulationMap.nextPosition(position.next(action))]!!.value
            }
            nextCostMap[position] = Cost(baseCost + discountFactor * cost)
        }
        return CostMap(nextCostMap)
    }

    fun maxGapWith(costMap: CostMap): Double {
        var maxGap = Double.MIN_VALUE
        for (position in this.costMap.keys) {
            maxGap = max(maxGap, abs(this.costMap[position]!!.value - costMap.costMap[position]!!.value))
        }
        return maxGap
    }

    companion object {
        const val DEFAULT_DISCOUNT_FACTOR = 0.9
        fun from(destination: Position, movement: Movement): CostMap {
            val initialCosts = mutableMapOf<Position, Cost>()
            movement.probabilities.keys.forEach { position ->
                initialCosts[position] = Cost.INITIAL
            }
            initialCosts[destination] = Cost.DESTINATION
            return CostMap(initialCosts)
        }
    }
}
