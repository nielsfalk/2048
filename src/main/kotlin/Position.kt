inline class Position(val value: Pair<Row, Col>) {
    val row: Row get() = value.first
    val col: Col get() = value.second
    constructor(row: Row, col: Col) : this(row to col)
}

inline class Row(val value:Int)

inline class Col(val value:Int)