package markov.output

import markov.output.MessageType

data class Message(val type: MessageType, val content: String) {
    val full = "${type.output} $content"
}
