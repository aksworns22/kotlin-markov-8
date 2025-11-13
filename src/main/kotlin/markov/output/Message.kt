package markov.output

data class Message(val type: MessageType, val content: String) {
    val full = "${type.output} $content"
}
