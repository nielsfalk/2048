import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class PositionTest {
    @Test
    fun `construct position`() {
        val position = Position(Row(3), Col(2))
        position.col shouldEqual Col(2)
        position.row shouldEqual Row(3)
    }

    @Test
    internal fun `minus on row`() {
        Row(2)-1 shouldEqual Row(1)
    }

    @Test
    internal fun `plus on row`() {
        Row(2)+1 shouldEqual Row(3)
    }
}