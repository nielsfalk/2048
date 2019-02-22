typealias Fields = Map<Position, Int>

data class Grid(
    val fields: Fields,
    val rowCount: Int = 4,
    val colCount: Int = 4
) {
    val rows = (0..rowCount - 1).map(::Row)
    val cols = (0..colCount - 1).map(::Col)

    override fun toString(): String =
        rows.map { row ->
            cols.map { col ->
                fields[Position(row, col)]?.toString() ?: " "
            }.joinToString(separator = ",")
        }.joinToString(separator = "\n")

    fun merge(left: Direction) = copy(
        fields.toMutableMap()
            .apply {
                for (row in rows) {
                    for (col in cols) {
                        val currentPosition = Position(row, col)
                        val currentVal = get(currentPosition)
                        val positionToMerge = Position(row, col + 1)
                        if (currentVal != null) {
                            remove(positionToMerge, currentVal)
                            put(currentPosition, currentVal + 1)
                        }
                    }
                }
            }
            .toMap()
    )

    fun trim(left: Direction) = copy(
        fields.toMutableMap()
            .apply {
                for (row in rows) {
                    for (col in cols) {
                        val currentPosition = Position(row, col)
                        if (get(currentPosition) == null) {
                            for (nextColInt in col.value until colCount) {
                                val nextPosition = Position(currentPosition.row, Col(nextColInt))
                                val nextValue = get(nextPosition)
                                if (nextValue!=null){
                                    put(currentPosition, nextValue)
                                    remove(nextPosition)
                                    break
                                }
                            }
                        }
                    }
                }
            }
            .toMap()
    )

    fun command(left: Direction) = trim(left).merge(left).trim(left)
}

fun String.asGrid(): Grid {
    val rowStrings = split("\n")
    return Grid(
        rowStrings
            .withIndex()
            .flatMap { (row, value) ->
                value.split(",")
                    .withIndex()
                    .map { (col, value) ->
                        value.toIntOrNull()
                            ?.let {
                                Position(Row(row), Col(col)) to it
                            }
                    }.filterNotNull()
            }.toMap(),
        rowCount = rowStrings.size,
        colCount = rowStrings.first().split(",").size
    )
}

enum class Direction {
    Left, Right, Up, Down
}