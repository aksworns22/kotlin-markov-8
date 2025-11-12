data class Position(val x: Int, val y: Int)

enum class Location(val symbol: String) {
    START("s"), DESTINATION("d")
}

data class SimulationMap(
    val size: MapSize,
    val start: Position,
    val destination: Position,
    val current: Position
) {
    companion object {
        const val INVALID_SIZE_ERROR_MESSAGE = "선언된 크기와 실제 지도의 크기다 일치하지 않습니다"
        fun of(mapSize: MapSize, rawMap: List<String>): SimulationMap {
            require(rawMap.size == mapSize.height) { INVALID_SIZE_ERROR_MESSAGE }
            val map = rawMap.map { it.split(" ") }
            map.forEach {
                require(it.size == mapSize.width) { INVALID_SIZE_ERROR_MESSAGE }
            }
            val start = getPosition(mapSize, map, Location.START)
            val destination = getPosition(mapSize, map, Location.DESTINATION)
            return SimulationMap(size = mapSize, start = start, destination = destination, current = start)
        }

        private fun getPosition(mapSize: MapSize, map: List<List<String>>, location: Location): Position {
            for (y in 0 until mapSize.height) {
                val x = map[y].indexOfFirst { it == location.symbol }
                if (x >= 0) return Position(x, y)
            }
            throw IllegalArgumentException("위치(${location.symbol})를 찾을 수 없습니다")
        }
    }
}
