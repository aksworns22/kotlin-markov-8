package markov.map

import java.io.InputStream

data class SimulationMap(
    val size: MapSize,
    val start: Position,
    val destination: Position,
    val current: Position
) {
    fun next(position: Position): SimulationMap {
        val nextPosition = nextPosition(position)
        return SimulationMap(size, start, destination, nextPosition)
    }

    fun nextPosition(position: Position): Position {
        val nextX = position.x.coerceIn(0, size.width - 1)
        val nextY = position.y.coerceIn(0, size.height - 1)
        return Position(nextX, nextY)
    }

    companion object {
        const val INVALID_SIZE_ERROR_MESSAGE = "선언된 크기와 실제 지도의 크기다 일치하지 않습니다"
        const val LOCATION_FINDING_ERROR_MESSAGE = "위치를 찾을 수 없습니다"
        const val TOO_MANY_LOCATION_ERROR = "출발지 혹은 도착지는 여러개 존재할 수 없습니다"
        const val INVALID_LOCATION_ERROR_MESSAGE = "알 수 없는 위치를 포함하고 있습니다"
        fun of(mapSize: MapSize, rawMap: List<String>): SimulationMap {
            require(rawMap.size == mapSize.height) { INVALID_SIZE_ERROR_MESSAGE }
            val map = rawMap.map { it.split(" ") }
            map.forEach {
                require(it.size == mapSize.width) { INVALID_SIZE_ERROR_MESSAGE }
            }
            val start = getPosition(mapSize, map, Location.START)
            val destination = getPosition(mapSize, map, Location.DESTINATION)
            requireSingleStartAndDestination(map)
            requireOnlyValidLocation(map)
            return SimulationMap(size = mapSize, start = start, destination = destination, current = start)
        }

        fun initializeFrom(mapStream: InputStream): SimulationMap {
            val map = mapStream.bufferedReader().readLines()
            require(map.size >= 2) { "유효하지 않은 형식입니다" }
            val mapDeclaration = map[0].split("x")
            val mapSize = MapSize.of(mapDeclaration[0], mapDeclaration[1])
            return of(mapSize, map.subList(1, map.size))
        }

        private fun getPosition(mapSize: MapSize, map: List<List<String>>, location: Location): Position {
            for (y in 0 until mapSize.height) {
                val x = map[y].indexOfFirst { it == location.symbol }
                if (x >= 0) return Position(x, y)
            }
            throw IllegalArgumentException(LOCATION_FINDING_ERROR_MESSAGE)
        }

        private fun requireSingleStartAndDestination(map: List<List<String>>) {
            require(countPosition(map, Location.START) == 1) { TOO_MANY_LOCATION_ERROR }
            require(countPosition(map, Location.DESTINATION) == 1) { TOO_MANY_LOCATION_ERROR }
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
