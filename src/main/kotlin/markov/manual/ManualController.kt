package markov.manual

import markov.map.SimulationMap
import markov.movement.Movement
import markov.output.ManualOutput

class ManualController(val output: ManualOutput) {
    fun findBestManual(simulationMap: SimulationMap, movement: Movement): Manual {
        val distanceMap = DistanceMap.from(simulationMap)
        var manual1 = Manual.from(simulationMap.destination, movement)
        var manual2 = manual1.improve(simulationMap, distanceMap, movement)
        var manual3 = manual2.improve(simulationMap, distanceMap, movement)
        var beforeGap = manual1.maxGapWith(manual2)
        var afterGap = manual2.maxGapWith(manual3)
        require(afterGap <= Manual.DEFAULT_DISCOUNT_FACTOR * beforeGap) { FINDING_MANUAL_ERROR_MESSAGE }
        while (afterGap > STOP_THRESHOLD) {
            manual1 = manual2
            manual2 = manual3
            manual3 = manual3.improve(simulationMap, distanceMap, movement)
            beforeGap = manual1.maxGapWith(manual2)
            afterGap = manual2.maxGapWith(manual3)
            require(afterGap <= Manual.DEFAULT_DISCOUNT_FACTOR * beforeGap) { FINDING_MANUAL_ERROR_MESSAGE }
        }
        require(afterGap <= Manual.DEFAULT_DISCOUNT_FACTOR * beforeGap) { FINDING_MANUAL_ERROR_MESSAGE }
        return manual2
    }

    companion object {
        const val STOP_THRESHOLD = 1e-3
        const val FINDING_MANUAL_ERROR_MESSAGE = "최선의 메뉴얼 탐색에 실패했습니다"
    }
}
