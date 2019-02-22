package de.nielsfalk.g2048

import de.nielsfalk.g2048.Direction.*


fun main() {
    var grid = Grid()
    println("2048")
    println("a⮐ = left")
    println("s⮐ = down")
    println("d⮐ = right")
    println("w⮐ = up")
    while (!grid.emptyPositions().isEmpty()) {
        println(grid)
        grid = when (readLine()) {
            "a" -> grid.command(Left)
            "s" -> grid.command(Down)
            "d" -> grid.command(Right)
            "w" -> grid.command(Up)
            else -> grid
        }
    }
}

