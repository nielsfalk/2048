import Direction.Left
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GridTest {
    @Test
    fun print() {
        Grid(mapOf(Position(Row(1), Col(2)) to 3)).toString() shouldEqual
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
                ).asGrid() shouldEqual Grid(mapOf(Position(Row(1), Col(2)) to 3))
    }

    @Test
    internal fun `merge left`() {
        ("" +
                "1,1,2,2\n" +
                " ,3,3, \n" +
                " ,4,5,6\n" +
                "7,7,7,7"
                ).asGrid()
            .merge(Left).toString() shouldEqual
                "2, ,3, \n" +
                " ,4, , \n" +
                " ,5,6,7\n" +
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
}

