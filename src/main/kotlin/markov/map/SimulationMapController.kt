package markov.map

import markov.output.Message
import markov.output.MessageOutput
import markov.output.MessageType

class SimulationMapController(val output: MessageOutput) {
    fun readMap(size: MapSize, rawMap: List<String>): SimulationMap? {
        try {
            val simulationMap = SimulationMap.of(size, rawMap)
            output.println(Message(MessageType.SUCCESS, Message.MAP_CREATION_SUCCESS))
            return simulationMap
        } catch (err: IllegalArgumentException) {
            output.println(Message(MessageType.ERROR, err.message ?: Message.UNEXPECTED_ERROR))
        }
        return null
    }
}
