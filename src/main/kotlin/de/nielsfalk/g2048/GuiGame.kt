package de.nielsfalk.g2048

import java.awt.Canvas
import java.awt.Color.*
import java.awt.Dimension
import java.awt.Font
import java.awt.Graphics2D
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.image.BufferStrategy
import javax.swing.JFrame


class GuiGame(val width: Int, val height: Int) {
    var gameRunning = true
    val strategy: BufferStrategy
    var commandToApply: Direction? = null

    init {
        val canvas = Canvas().apply {
            setBounds(0, 0, this@GuiGame.width, this@GuiGame.height)
            setIgnoreRepaint(true)
            addKeyListener(object : KeyAdapter() {
                override fun keyPressed(event: KeyEvent) {
                    when (val code = event.keyCode) {
                        in KeyEvent.VK_LEFT..KeyEvent.VK_DOWN -> commandToApply =
                            Direction.values()[code - KeyEvent.VK_LEFT]
                    }
                }
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
        var grid = Grid()
        while (gameRunning) {
            commandToApply?.let {
                grid = grid.command(it)
                commandToApply = null
            }
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
            for ((position, value) in grid.fields) {
                val yOffset = cellHeight * position.row.value
                val xOffset = cellWidths * position.col.value

                color = when (value) {
                    1 -> red
                    2 -> blue
                    3 -> green
                    4 -> green
                    5 -> cyan
                    6 -> cyan
                    8 -> magenta
                    9 -> orange
                    else -> black
                }
                fillRect(xOffset + 1, yOffset + 1, cellWidths - 2, cellHeight - 2)
                color = white
                font = Font("Arial", Font.PLAIN, 60)
                drawString(
                    "${Math.pow(2.0, value.toDouble()).toInt()}",
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

