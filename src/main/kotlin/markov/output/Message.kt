package markov.output

data class Message(val type: MessageType, val content: String) {
    val full = "${type.output} $content"

    companion object {
        const val MAP_CREATION_SUCCESS = "지도를 불러왔습니다"
        const val UNEXPECTED_ERROR = "예상치 못한 예외가 발생했습니다"
    }
}
