package markov

import markov.map.MapSize

object InvalidFixtures {
    val RAW_MAPS = listOf(
        listOf("2x1", "s ."),
        listOf("2x1", ". d"),
        listOf("2x2", ". d"),
        listOf("2x2", ". d", "s x"),
        listOf("2xn", ". d"),
        listOf("1", "s d"),
        listOf("2x2", ". d", "s d")
    )

    val RAW_MOVEMENTS = listOf(
        listOf(MapSize(2, 1), listOf("0,0:25,25,25,25")),
        listOf(MapSize(1, 1), listOf("0,0:-25,50,25,25")),
        listOf(MapSize(1, 1), listOf("0,0:25,50,25,25")),
        listOf(MapSize(2, 2), listOf("0,0:25,25,25,25", "1,0:25,25,25,25", "1,1:25,25,25,25"))
    )
}
