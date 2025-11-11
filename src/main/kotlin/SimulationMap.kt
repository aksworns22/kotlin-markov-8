data class Position(val x: Int, val y: Int)

data class SimulationMap(
    val size: MapSize,
    val start: Position,
    val end: Position,
    val current: Position
)
