inline class Position(val value: Pair<Row, Col>) {
    val row: Row get() = value.first
    val col: Col get() = value.second
    constructor(row: Row, col: Col) : this(row to col)
}

inline class Row(val value: Int){
    operator fun minus(amount: Int): Row {
        val newValue = value - amount
        return Row(newValue)
    }

    operator fun plus(amount: Int): Row {
        val newValue = value + amount
        return Row(newValue)
    }
}
inline class Col( val value: Int){
    operator fun minus(amount: Int): Col {
        val newValue = value - amount
        return Col(newValue)
    }

    operator fun plus(amount: Int): Col {
        val newValue = value + amount
        return Col(newValue)
    }
}
