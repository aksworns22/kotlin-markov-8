package markov.map

data class SimulationMap(
    val size: MapSize,
    val start: Position,
    val destination: Position,
    val current: Position
) {
    val maximumHeight = size.height - 1
    val maximumWidth = size.width - 1
    fun next(position: Position): SimulationMap {
        val nextPosition = nextPosition(position)
        return SimulationMap(size, start, destination, nextPosition)
    }

    fun nextPosition(position: Position): Position {
        val nextX = position.x.coerceIn(MAP_MINIMUM_BOUND, maximumWidth)
        val nextY = position.y.coerceIn(MAP_MINIMUM_BOUND, maximumHeight)
        return Position(nextX, nextY)
    }

    companion object {
        const val INVALID_SIZE_ERROR_MESSAGE = "선언된 크기와 실제 지도의 크기다 일치하지 않습니다"
        const val LOCATION_FINDING_ERROR_MESSAGE = "위치를 찾을 수 없습니다"
        const val TOO_MANY_LOCATION_ERROR = "출발지 혹은 도착지는 여러개 존재할 수 없습니다"
        const val INVALID_LOCATION_ERROR_MESSAGE = "알 수 없는 위치를 포함하고 있습니다"
        const val MAP_COMPONENT_DELIMITER = " "
        const val MAP_MINIMUM_BOUND = 0
        const val REQUIRED_DESTINATION_SIZE = 1
        const val REQUIRED_START_SIZE = 1
        fun of(mapSize: MapSize, rawMap: List<String>): SimulationMap {
            require(rawMap.size == mapSize.height) { INVALID_SIZE_ERROR_MESSAGE }
            val map = rawMap.map { it.split(MAP_COMPONENT_DELIMITER) }
            map.forEach {
                require(it.size == mapSize.width) { INVALID_SIZE_ERROR_MESSAGE }
            }
            val start = getPosition(mapSize, map, Location.START)
            val destination = getPosition(mapSize, map, Location.DESTINATION)
            requireSingleStartAndDestination(map)
            requireOnlyValidLocation(map)
            return SimulationMap(size = mapSize, start = start, destination = destination, current = start)
        }

        private fun getPosition(mapSize: MapSize, map: List<List<String>>, location: Location): Position {
            for (y in MAP_MINIMUM_BOUND until mapSize.height) {
                val x = map[y].indexOfFirst { it == location.symbol }
                if (x >= MAP_MINIMUM_BOUND) return Position(x, y)
            }
            throw IllegalArgumentException(LOCATION_FINDING_ERROR_MESSAGE)
        }

        private fun requireSingleStartAndDestination(map: List<List<String>>) {
            require(countPosition(map, Location.START) == REQUIRED_START_SIZE) { TOO_MANY_LOCATION_ERROR }
            require(countPosition(map, Location.DESTINATION) == REQUIRED_DESTINATION_SIZE) { TOO_MANY_LOCATION_ERROR }
        }

        private fun countPosition(map: List<List<String>>, location: Location): Int {
            return map.sumOf { row ->
                row.count { it == location.symbol }
            }
        }

        private fun requireOnlyValidLocation(map: List<List<String>>) {
            map.forEach { row ->
                row.forEach {
                    require(Location.isValidSymbol(it)) { INVALID_LOCATION_ERROR_MESSAGE }
                }
            }
        }
    }
}
