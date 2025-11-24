package markov.map

import markov.output.Message
import markov.output.MessageOutput
import markov.output.MessageType

class SimulationMapController(val output: MessageOutput) {
    fun readMap(rawMap: List<String>): SimulationMap? {
        try {
            val simulationMap =
                getSimulationMap(rawMap[MAP_SIZE_FILE_LINE], rawMap.subList(MAP_SIZE_FILE_LINE, rawMap.size))
            output.println(Message(MessageType.SUCCESS, Message.MAP_CREATION_SUCCESS))
            return simulationMap
        } catch (err: IllegalArgumentException) {
            output.println(Message(MessageType.ERROR, err.message ?: Message.UNEXPECTED_ERROR))
        }
        return null
    }

    private fun getMapSize(rawMapDeclaration: String): MapSize {
        val mapDeclaration = rawMapDeclaration.split(SIZE_DELIMITER)
        require(mapDeclaration.size == REQUIRED_COMPONENT_SIZE) { MapSize.INVALID_FORMAT_ERROR_MESSAGE }
        return MapSize.of(mapDeclaration[WIDTH_INDEX], mapDeclaration[HEIGHT_INDEX])
    }

    private fun getSimulationMap(mapDeclaration: String, rawMap: List<String>): SimulationMap {
        val mapSize = getMapSize(mapDeclaration)
        return SimulationMap.of(mapSize, rawMap)
    }

    companion object {
        const val MAP_SIZE_FILE_LINE = 1
        const val SIZE_DELIMITER = "x"
        const val REQUIRED_COMPONENT_SIZE = 2
        const val WIDTH_INDEX = 0
        const val HEIGHT_INDEX = 1
    }
}
