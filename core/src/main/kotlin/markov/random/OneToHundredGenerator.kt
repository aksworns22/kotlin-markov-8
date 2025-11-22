package markov.random

import kotlin.random.Random

object OneToHundredGenerator : RandomGenerator {
    override fun generate(): Int {
        return Random.nextInt(1, 101)
    }
}
