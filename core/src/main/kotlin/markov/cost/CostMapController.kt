package markov.cost

import markov.map.SimulationMap
import markov.movement.Movement
import markov.output.CostMapOutput

class CostMapController(val output: CostMapOutput) {
    fun findCostMap(simulationMap: SimulationMap, movement: Movement): CostMap {
        val distanceMap = DistanceMap.from(simulationMap)
        var costMap1 = CostMap.from(simulationMap.destination, movement)
        var manual2 = costMap1.improve(simulationMap, distanceMap, movement)
        var manual3 = manual2.improve(simulationMap, distanceMap, movement)
        var beforeGap = costMap1.maxGapWith(manual2)
        var afterGap = manual2.maxGapWith(manual3)
        val epsilon = 1e-9
        require(afterGap <= CostMap.DEFAULT_DISCOUNT_FACTOR * beforeGap + epsilon) { FINDING_MANUAL_ERROR_MESSAGE }
        while (afterGap > STOP_THRESHOLD) {
            costMap1 = manual2
            manual2 = manual3
            manual3 = manual3.improve(simulationMap, distanceMap, movement)
            beforeGap = costMap1.maxGapWith(manual2)
            afterGap = manual2.maxGapWith(manual3)
            require(afterGap <= CostMap.DEFAULT_DISCOUNT_FACTOR * beforeGap + epsilon) { FINDING_MANUAL_ERROR_MESSAGE }
        }
        require(afterGap <= CostMap.DEFAULT_DISCOUNT_FACTOR * beforeGap + epsilon) { FINDING_MANUAL_ERROR_MESSAGE }
        output.println(manual3)
        return manual3
    }

    companion object {
        const val STOP_THRESHOLD = 1e-3
        const val FINDING_MANUAL_ERROR_MESSAGE = "최선의 메뉴얼 탐색에 실패했습니다"
    }
}
