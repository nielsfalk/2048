import Direction.Left

data class Grid(
    val fields: Map<Position, Int>,
    val rowCount: Int = 4,
    val colCount: Int = 4
) {
    val rows = (0..rowCount).map(::Row)
    val cols = (0..colCount).map(::Col)

    override fun toString(): String =
        rows.map { row ->
            cols.map { col ->
                fields[Position(row, col)]?.toString() ?: " "
            }.joinToString(separator = ",")
        }.joinToString(separator = "\n")
}

enum class Direction {
    Left, Right, Up, Down
}