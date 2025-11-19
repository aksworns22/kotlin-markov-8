package markov.output

import markov.simulation.Simulations

object ConsoleOutput : MessageOutput, SimulationOutput {
    override fun println(message: Message) {
        println(message.full)
    }

    override fun println(simulations: Simulations) {
        for (simulation in simulations.log) {
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
}
