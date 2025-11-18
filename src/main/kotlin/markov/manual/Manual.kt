package markov.manual

import markov.map.Position
import markov.simulation.Moving

data class Cost(val value: Int) {
    companion object {
        val INITIAL = Cost(100)
        val DESTINATION = Cost(0)
    }
}

data class Manual(val costMap: Map<Position, Cost>) {
    companion object {
        fun from(destination: Position, moving: Moving): Manual {
            val initialCosts = mutableMapOf<Position, Cost>()
            moving.probabilities.keys.forEach { position ->
                initialCosts[position] = Cost.INITIAL
            }
            initialCosts[destination] = Cost.DESTINATION
            return Manual(initialCosts)
        }
    }
}
