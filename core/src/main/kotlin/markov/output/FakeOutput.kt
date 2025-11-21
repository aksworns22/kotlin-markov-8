package markov.output

import markov.manual.Manual
import markov.simulation.Simulation

object FakeOutput : SimulationOutput, MessageOutput, ManualOutput {
    override fun println(manual: Manual) {}
    override fun println(message: Message) {}
    override fun println(simulation: Simulation) {}
}
