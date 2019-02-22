import Direction.*

inline class Position(val value: Pair<Row, Col>) {
    val row: Row get() = value.first
    val col: Col get() = value.second

    constructor(row: Row, col: Col) : this(row to col)

    fun neighbour(direction: Direction) =
        when (direction) {
            Left -> Position(row, col - 1)
            Up -> Position(row - 1, col)
            Right -> Position(row, col + 1)
            Down -> Position(row + 1, col)
        }
}

inline class Row(val value: Int) {
    operator fun minus(amount: Int): Row {
        val newValue = value - amount
        return Row(newValue)
    }

    operator fun plus(amount: Int): Row {
        val newValue = value + amount
        return Row(newValue)
    }
}

inline class Col(val value: Int) {
    operator fun minus(amount: Int): Col {
        val newValue = value - amount
        return Col(newValue)
    }

    operator fun plus(amount: Int): Col {
        val newValue = value + amount
        return Col(newValue)
    }
}
