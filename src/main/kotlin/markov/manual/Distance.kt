package markov.manual

import markov.map.Position
import kotlin.math.abs

@JvmInline
value class Distance(val value: Int) {
    companion object {
        fun fromManhattan(current: Position, destination: Position): Distance {
            return Distance(abs(current.x - destination.x) + abs(current.y - destination.y))
        }
    }
}
