package markov.output

import markov.manual.Manual
import markov.simulation.Simulation

object Console : MessageOutput, SimulationOutput, ManualOutput {
    override fun println(message: Message) {
        println(message.full)
    }

    override fun println(manual: Manual) {
        println("====== 최선의 행동 메뉴얼 ======")
        for (recommendAction in manual.recommendActions) {
            println("${recommendAction.key} : ${recommendAction.value}")
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
