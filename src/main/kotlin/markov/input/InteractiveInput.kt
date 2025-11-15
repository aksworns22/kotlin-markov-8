package markov.input

interface InteractiveInput {
    fun readIntegerNumber(): Int
}

object OnlyOneInput : InteractiveInput {
    override fun readIntegerNumber() = 1
}
