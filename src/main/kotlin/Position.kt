inline class Position(val value: Pair<Row, Col>) {
    val row: Row get() = value.first
    val col: Col get() = value.second
    constructor(row: Row, col: Col) : this(row to col)
}

inline class Row(override val value: Int):InlineInt
inline class Col(override val value: Int):InlineInt

interface InlineInt{
    val value:Int
    operator fun minus(amount: Int): Row? {
        val newValue = value - amount
        return if (newValue<0) null else Row(newValue)
    }

    operator fun plus(amount: Int): Row? {
        val newValue = value + amount
        return if (newValue<0) null else Row(newValue)
    }
}