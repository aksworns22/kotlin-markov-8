package markov.movement

import markov.map.MapSize
import markov.output.Message
import markov.output.MessageOutput
import markov.output.MessageType

class MovementController(val mapSize: MapSize, val output: MessageOutput) {
    fun readMovement(rawMovement: List<String>): Movement {
        val movement = Movement.of(rawMovement)
        movement.validateWith(mapSize)
        output.println(Message(MessageType.SUCCESS, "위치 별 이동 확률을 불러왔습니다"))
        return movement
    }
}
