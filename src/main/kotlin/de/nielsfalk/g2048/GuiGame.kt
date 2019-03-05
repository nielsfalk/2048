package de.nielsfalk.g2048

import java.awt.Canvas
import java.awt.Dimension
import java.awt.Graphics2D
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.image.BufferStrategy
import javax.swing.JFrame


class GuiGame(val width: Int, val height: Int) {
    var gameRunning = true
    val strategy: BufferStrategy


    init {
        val canvas = Canvas().apply {
            setBounds(0, 0, this@GuiGame.width, this@GuiGame.height)
            setIgnoreRepaint(true)
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
            draw()
            Thread.sleep(1)
        }
    }

    private fun draw() {
        draw {

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

