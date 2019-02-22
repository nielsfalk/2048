import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

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
}

