package markov.cost

import markov.map.SimulationMap
import markov.movement.Movement
import markov.output.CostMapOutput

class CostMapController(val output: CostMapOutput) {
    fun findCostMap(simulationMap: SimulationMap, movement: Movement): CostMap {
        val distanceMap = DistanceMap.from(simulationMap)
        var previousCostMap = CostMap.from(simulationMap.destination, movement)
        var currentCostMap = previousCostMap.improve(simulationMap, distanceMap, movement)
        var nextCostMap = currentCostMap.improve(simulationMap, distanceMap, movement)
        var previousGap = previousCostMap.maxGapWith(currentCostMap)
        var currentGap = currentCostMap.maxGapWith(nextCostMap)
        require(currentGap <= CostMap.DEFAULT_DISCOUNT_FACTOR * previousGap + EPSILON) { FINDING_MANUAL_ERROR_MESSAGE }
        while (currentGap > STOP_THRESHOLD) {
            previousCostMap = currentCostMap
            currentCostMap = nextCostMap
            nextCostMap = nextCostMap.improve(simulationMap, distanceMap, movement)
            previousGap = previousCostMap.maxGapWith(currentCostMap)
            currentGap = currentCostMap.maxGapWith(nextCostMap)
            require(currentGap <= CostMap.DEFAULT_DISCOUNT_FACTOR * previousGap + EPSILON) { FINDING_MANUAL_ERROR_MESSAGE }
        }
        require(currentGap <= CostMap.DEFAULT_DISCOUNT_FACTOR * previousGap + EPSILON) { FINDING_MANUAL_ERROR_MESSAGE }
        output.println(nextCostMap)
        return nextCostMap
    }

    companion object {
        const val EPSILON = 1e-9
        const val STOP_THRESHOLD = 1e-3
        const val FINDING_MANUAL_ERROR_MESSAGE = "최선의 메뉴얼 탐색에 실패했습니다"
    }
}
