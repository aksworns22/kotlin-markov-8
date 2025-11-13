interface MessageOutput {
    fun println(message: Message)
}

object ConsoleOutput : MessageOutput {
    override fun println(message: Message) {
        println(message.full)
    }
}
