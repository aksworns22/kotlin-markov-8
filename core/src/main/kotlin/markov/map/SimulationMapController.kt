package markov.map

import markov.output.Message
import markov.output.MessageOutput
import markov.output.MessageType
import java.io.InputStream

class SimulationMapController(val output: MessageOutput) {
    fun readMap(rawMap: List<String>): SimulationMap? {
        try {
            val simulationMap = getSimulationMap(rawMap[0], rawMap.subList(1, rawMap.size))
            output.println(Message(MessageType.SUCCESS, Message.MAP_CREATION_SUCCESS))
            return simulationMap
        } catch (err: IllegalArgumentException) {
            output.println(Message(MessageType.ERROR, err.message ?: Message.UNEXPECTED_ERROR))
        }
        return null
    }

    private fun getMapSize(rawMapDeclaration: String): MapSize {
        val mapDeclaration = rawMapDeclaration.split("x")
        require(mapDeclaration.size == 2) { MapSize.INVALID_FORMAT_ERROR_MESSAGE }
        return MapSize.of(mapDeclaration[0], mapDeclaration[1])
    }

    private fun getSimulationMap(mapDeclaration: String, rawMap: List<String>): SimulationMap {
        val mapSize = getMapSize(mapDeclaration)
        return SimulationMap.of(mapSize, rawMap)
    }
}
