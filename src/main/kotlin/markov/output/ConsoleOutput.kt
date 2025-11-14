package markov.output

import markov.simulation.SimulationState

object ConsoleOutput : MessageOutput, SimulationOutput {
    override fun println(message: Message) {
        println(message.full)
    }

    override fun println(simulationResult: SimulationResult) {
        val position = simulationResult.currentPosition
        println("[시간: ${simulationResult.currentTime}] x: ${position.x}, y: ${position.y}")

        if (simulationResult.state == SimulationState.SUCCESS) {
            println("[최종 결과] 목적지에 시간 내에 도착했습니다")
        }

        if (simulationResult.state == SimulationState.FAIL) {
            println("[최종 결과] 목적지에 시간 내에 도착하지 못했습니다")
        }

    }
}
