package de.nielsfalk.g2048

import de.nielsfalk.g2048.Direction.*
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GridTest {
    @BeforeEach
    internal fun setUp() {
        nextRandomInt = { 1 }
    }

    @Test
    fun print() {
        Grid(
            mapOf(
                Position(
                    Row(1),
                    Col(2)
                ) to 3
            )
        ).toString() shouldEqual
                " , , , \n" +
                " , ,3, \n" +
                " , , , \n" +
                " , , , "
    }

    @Test
    fun `string as grid`() {
        ("" +
                " , , , \n" +
                " , ,3, \n" +
                " , , , \n" +
                " , , , "
                ).asGrid() shouldEqual Grid(
            mapOf(
                Position(
                    Row(
                        1
                    ), Col(2)
                ) to 3
            )
        )
    }

    @Test
    fun `merge left`() {
        ("" +
                "1,1,2,2\n" +
                " ,3,3, \n" +
                " ,4,5,6\n" +
                "7,7,7,7"
                ).asGrid()
            .merge(Left).toString() shouldEqual
                "2, ,3, \n" +
                " ,4, , \n" +
                " ,4,5,6\n" +
                "8, ,8, "
    }

    @Test
    fun `trim left`() {
        ("" +
                "2, ,3, \n" +
                " ,4, , \n" +
                " ,5,6,7\n" +
                "8, ,8, "
                ).asGrid()
            .trim(Left).toString() shouldEqual
                "2,3, , \n" +
                "4, , , \n" +
                "5,6,7, \n" +
                "8,8, , "
    }

    @Test
    fun `command left`() {
        ("" +
                "1,1,2,2\n" +
                " ,3,3, \n" +
                " ,4,5,6\n" +
                "7,7,7,7"
                ).asGrid()
            .command(Left).toString() shouldEqual
                "2,3, ,2\n" +
                "4, , , \n" +
                "4,5,6, \n" +
                "8,8, , "
    }

    @Test
    fun `command right`() {
        ("" +
                "1,1,2,2\n" +
                " ,3,3, \n" +
                " ,4,5,6\n" +
                "7,7,7,7"
                ).asGrid()
            .command(Right).toString() shouldEqual
                " ,2,2,3\n" +
                " , , ,4\n" +
                " ,4,5,6\n" +
                " , ,8,8"
    }

    @Test
    fun `command down`() {
        ("" +
                "1, , ,7\n" +
                "1,3,4,7\n" +
                "2,3,5,7\n" +
                "2, ,6,7"
                ).asGrid()
            .command(Down).toString() shouldEqual
                " ,2, , \n" +
                " , ,4, \n" +
                "2, ,5,8\n" +
                "3,4,6,8"
    }


    @Test
    fun `command up`() {
        ("" +
                "1, , ,7\n" +
                "1,3,4,7\n" +
                "2,3,5,7\n" +
                "2, ,6,7"
                ).asGrid()
            .command(Up).toString() shouldEqual
                "2,4,4,8\n" +
                "3, ,5,8\n" +
                "2, ,6, \n" +
                " , , , "
    }

    @Test
    fun `emptyPositions`() {
        ("" +
                "1, , ,7\n" +
                "1,3,4,7\n" +
                "2,3,5,7\n" +
                "2, ,6,7"
                ).asGrid()
            .emptyPositions() shouldEqual
                listOf(
                    Position(Row(0), Col(1)),
                    Position(Row(0), Col(2)),
                    Position(Row(3), Col(1))
                )
    }

    @Test
    fun `newItem`() {
        ("" +
                "1, , ,7\n" +
                "1,3,4,7\n" +
                "2,3,5,7\n" +
                "2, ,6,7"
                ).asGrid()
            .newItem().toString() shouldEqual
                "1, ,2,7\n" +
                "1,3,4,7\n" +
                "2,3,5,7\n" +
                "2, ,6,7"
    }

    @Test
    fun `command - no newItem when nothing changed`() {
        ("" +
                "1, , , \n" +
                "1, , , \n" +
                "2, , , \n" +
                "2, , , "
                ).asGrid()
            .command(Left).toString() shouldEqual
                "1, , , \n" +
                "1, , , \n" +
                "2, , , \n" +
                "2, , , "
    }

    @Test
    fun gameOver() {
        ("" +
                "1,2,3,4\n" +
                "2,4,1,2\n" +
                "1,5,4,3\n" +
                "3,4,5,2").asGrid().gameOver() shouldEqual true
    }

    @Test
    fun `not gameOver`() {
        nextRandomInt = { 0 }
        ("" +
                "1,2,3,4\n" +
                "2,4,4,2\n" +
                "1,5,4,3\n" +
                "3,4,5,2").asGrid().gameOver() shouldEqual false
    }
}

