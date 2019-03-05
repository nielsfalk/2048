package de.nielsfalk.g2048

import de.nielsfalk.g2048.Direction.*
import kotlin.random.Random

typealias Fields = Map<Position, Int>

data class Grid(
    val fields: Fields,
    val rowCount: Int = 4,
    val colCount: Int = 4
) {
    constructor() : this(Grid(emptyMap()).newItem().newItem().fields)

    val rows = (0 until rowCount).map(::Row)
    val cols = (0 until colCount).map(::Col)

    fun onAllPositions(from: Direction = Left, function: (Position) -> Unit) {
        (if (from == Down) rows.reversed() else rows)
            .forEach { row ->
                (if (from == Right) cols.reversed() else cols)
                    .forEach { col ->
                        function(Position(row, col))
                    }
            }
    }

    override fun toString(): String =
        rows.joinToString(separator = "\n") { row ->
            cols.joinToString(separator = ",") { col ->
                fields[Position(row, col)]?.toString() ?: " "
            }
        }

    fun merge(direction: Direction) = copy(
        fields.toMutableMap()
            .apply {
                onAllPositions(from = direction) { currentPosition ->
                    val currentVal = get(currentPosition)
                    val positionToMerge = currentPosition.neighbour(direction.opposite)
                    val potentialSameValue = get(positionToMerge)
                    if (currentVal != null && currentVal == potentialSameValue) {
                        remove(positionToMerge, currentVal)
                        put(currentPosition, currentVal + 1)
                    }
                }
            }
            .toMap()
    )

    fun trim(direction: Direction) = copy(
        fields.toMutableMap()
            .apply {
                onAllPositions(from = direction) { currentPosition ->
                    if (get(currentPosition) == null) {
                        var nextPosition = currentPosition.neighbour(direction.opposite)
                        while (nextPosition.isInDimension(rowCount, colCount)) {
                            val nextValue = get(nextPosition)
                            if (nextValue != null) {
                                put(currentPosition, nextValue)
                                remove(nextPosition)
                                break
                            }
                            nextPosition = nextPosition.neighbour(direction.opposite)
                        }
                    }
                }
            }
            .toMap()
    )

    fun command(direction: Direction): Grid {
        val result = trim(direction)
            .merge(direction)
            .trim(direction)
        return if (this == result) this else result.newItem()
    }

    fun emptyPositions(): List<Position> =
        rows.flatMap { row ->
            cols.map { col -> Position(row, col) }
                .filter { fields[it] == null }
        }

    fun newItem(): Grid {
        return copy(
            fields.toMutableMap().apply {
                val emptyPositions = emptyPositions()
                if (!emptyPositions.isEmpty()) {

                    val randomPosition = emptyPositions[nextRandomInt(emptyPositions.size)]
                    if (get(randomPosition) != null) {
                        println()
                    }
                    put(randomPosition, nextRandomInt(2) + 1)
                }
            }.toMap()
        )
    }

    fun gameOver(): Boolean = allFilled() && nothingToMerge()

    private fun nothingToMerge(): Boolean {
        return sequenceOf(Up, Left)
            .map { merge(it) }
            .none { it != this }
    }

    private fun allFilled(): Boolean {
        val emptyPositions = emptyPositions()
        return emptyPositions.isEmpty()
    }
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
                                Position(
                                    Row(row),
                                    Col(col)
                                ) to it
                            }
                    }.filterNotNull()
            }.toMap(),
        rowCount = rowStrings.size,
        colCount = rowStrings.first().split(",").size
    )
}

var nextRandomInt: (Int) -> Int = { Random.nextInt(it) }
