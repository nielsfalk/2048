import org.amshove.kluent.shouldEqual
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class GridTest{
    @Test
    fun print() {
        Grid(mapOf(Position(Row(1), Col(2)) to 3)).toString() shouldEqual
                " , , , , \n" +
                " , ,3, , \n" +
                " , , , , \n" +
                " , , , , \n" +
                " , , , , "
    }
}