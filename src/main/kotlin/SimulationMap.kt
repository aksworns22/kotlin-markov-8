data class Position(val x: Int, val y: Int)

data class SimulationMap(
    val size: MapSize,
    val start: Position,
    val end: Position,
    val current: Position
) {
    companion object {
        const val INVALID_SIZE_ERROR_MESSAGE = "선언된 크기와 실제 지도의 크기다 일치하지 않습니다"
        fun of(mapSize: MapSize, rawMapData: List<String>): SimulationMap {
            require(rawMapData.size == mapSize.height) { INVALID_SIZE_ERROR_MESSAGE }
            val mapData = rawMapData.map { it.split(" ") }
            mapData.forEach {
                require(it.size == mapSize.width) { INVALID_SIZE_ERROR_MESSAGE }
            }
            var start: Position = Position(0, 0)
            var end: Position = Position(0, 0)
            for (y in 0 until mapSize.height) {
                for (x in 0 until mapSize.width) {
                    if (mapData[y][x] == "s") {
                        start = Position(x, y)
                    }
                    if (mapData[y][x] == "d") {
                        end = Position(x, y)
                    }
                }
            }
            return SimulationMap(size = mapSize, start = start, end = end, current = start)
        }
    }
}
