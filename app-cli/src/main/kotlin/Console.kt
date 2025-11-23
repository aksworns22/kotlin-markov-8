import markov.cost.CostMap
import markov.output.CostMapOutput
import markov.output.Message
import markov.output.MessageOutput
import markov.output.SimulationOutput
import markov.simulation.Simulation
import kotlin.collections.iterator

object Console : MessageOutput, SimulationOutput, CostMapOutput {
    override fun println(message: Message) {
        println(message.full)
    }

    override fun println(costMap: CostMap) {
        println("====== 최선의 행동 메뉴얼 ======")
        for (recommendAction in costMap.costMap) {
            println("${recommendAction.key} : ${String.format("%.2f", recommendAction.value.value)}")
        }
        println("============================")
    }

    override fun println(simulation: Simulation) {
        val position = simulation.map.current
        println("[시간: ${simulation.current}] x: ${position.x}, y: ${position.y}")

        if (simulation.isSuccess) {
            println("[최종 결과] 목적지에 시간 내에 도착했습니다")
        }

        if (simulation.isFail) {
            println("[최종 결과] 목적지에 시간 내에 도착하지 못했습니다")
        }
    }
}
