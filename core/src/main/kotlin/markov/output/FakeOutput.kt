package markov.output

import markov.cost.CostMap
import markov.simulation.Simulation

object FakeOutput : SimulationOutput, MessageOutput, CostMapOutput {
    override fun println(costMap: CostMap) {}
    override fun println(message: Message) {}
    override fun println(simulation: Simulation) {}
}
