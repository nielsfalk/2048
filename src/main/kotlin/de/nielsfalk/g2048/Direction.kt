package de.nielsfalk.g2048

enum class Direction {
    Left, Up, Right, Down;

    val opposite: Direction
        get() = Direction.values().get((this.ordinal + 2) % Direction.values().size)
}