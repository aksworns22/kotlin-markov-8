package markov.map

enum class Location(val symbol: String) {
    START("s"), DESTINATION("d"), WAYPOINT(".");

    companion object {
        fun isValidSymbol(locationSymbol: String): Boolean {
            return entries.any { it.symbol == locationSymbol }
        }
    }
}
