data class MapSize(val width: Int, val height: Int) {
    init {
        require(width > 0 && height > 0) { INVALID_VALUE_ERROR_MESSAGE }
    }

    companion object {
        const val INVALID_VALUE_ERROR_MESSAGE = "올바르지 않은 지도 크기입니다"
        fun of(rawWidth: String, rawHeight: String): MapSize {
            val width = rawWidth.toIntOrNull() ?: throw IllegalArgumentException(INVALID_VALUE_ERROR_MESSAGE)
            val height = rawHeight.toIntOrNull() ?: throw IllegalArgumentException(INVALID_VALUE_ERROR_MESSAGE)
            return MapSize(width, height)
        }
    }
}
