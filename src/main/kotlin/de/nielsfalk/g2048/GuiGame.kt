package de.nielsfalk.g2048

import java.awt.*
import java.awt.Color.*
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.KeyEvent.*
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.image.BufferStrategy
import javax.swing.JFrame


class GuiGame(val width: Int, val height: Int) {
    var gameRunning = true
    val strategy: BufferStrategy
    var grid = Grid()

    init {
        val canvas = Canvas().apply {
            setBounds(0, 0, this@GuiGame.width, this@GuiGame.height)
            setIgnoreRepaint(true)
            addKeyListener(object : KeyAdapter() {
                override fun keyPressed(event: KeyEvent) {
                    grid = when (val keyCode = event.keyCode) {
                        in VK_LEFT..VK_DOWN -> grid.command(keyCodeToDirection(keyCode))
                        VK_SPACE -> Grid()
                        else -> grid
                    }
                }

                private fun keyCodeToDirection(keyCode: Int) = Direction.values()[keyCode - VK_LEFT]
            })
        }
        JFrame("2048").apply {
            contentPane.apply {
                preferredSize = Dimension(this@GuiGame.width, this@GuiGame.height)
                layout = null
                add(canvas)
            }
            pack()
            isResizable = false
            isVisible = true
            addWindowListener(object : WindowAdapter() {
                override fun windowClosing(e: WindowEvent?) {
                    gameRunning = false
                    System.exit(0)
                }
            })
        }
        canvas.requestFocus()
        canvas.createBufferStrategy(2)
        strategy = canvas.getBufferStrategy()
    }

    fun gameLoop() {
        while (gameRunning) {
            draw(grid)
            Thread.sleep(1)
        }
    }

    private fun draw(grid: Grid) {
        draw {
            color = green
            fillRect(0, 0, width, height)
            val cellHeight = height / grid.rowCount
            val cellWidths = width / grid.colCount
            grid.emptyPositions().forEach {
                val yOffset = cellHeight * it.row.value
                val xOffset = cellWidths * it.col.value
                color = darkGray
                fillRect(xOffset + 1, yOffset + 1, cellWidths - 2, cellHeight - 2)
            }
            for ((position, tile) in grid.cells.mapValues { tile(it.value) }) {
                val yOffset = cellHeight * position.row.value
                val xOffset = cellWidths * position.col.value
                color = tile.background
                fillRect(xOffset + 1, yOffset + 1, cellWidths - 2, cellHeight - 2)
                color = tile.textColor
                font = Font("Arial", Font.PLAIN, 60)
                drawString(
                    tile.text,
                    (xOffset + 10).toFloat(),
                    (yOffset + cellHeight / 2).toFloat()
                )
            }
        }
    }

    private fun draw(function: Graphics2D.() -> Unit) {
        (strategy.drawGraphics as Graphics2D).apply {
            function(this)
            dispose()
        }
        strategy.show()
    }
}

fun main() {
    GuiGame(600, 600).gameLoop()
}

private fun tile(value: Int): Tile {
    val text = "${Math.pow(2.0, value.toDouble()).toInt()}"
    return when (value) {
        1 -> Tile(text, white, Color(0x405EA7))
        2 -> Tile(text, black, Color(0x2994CC))
        3 -> Tile(text, black, Color(0x65B051))
        4 -> Tile(text, black, Color(0xCFDE4F))
        5 -> Tile(text, black, Color(0xF2ED55))
        6 -> Tile(text, black, Color(0xF7BE3D))
        7 -> Tile(text, black, Color(0xF69D39))
        8 -> Tile(text, black, Color(0xEE5631))
        9 -> Tile(text, black, Color(0xEA332D))
        10 -> Tile(text, white, Color(0xA31F47))
        11 -> Tile(text, white, Color(0x7B3693))
        12 -> Tile(text, white, Color(0x45308D))
        else -> Tile(text, white, black)
    }
}

data class Tile(val text: String, val textColor: Color, val background: Color)
