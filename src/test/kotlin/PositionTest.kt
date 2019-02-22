import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

class PositionTest {
    @Test
    fun `construct position`() {
        val position = Position(Row(3), Col(2))
        position.col shouldEqual Col(2)
        position.row shouldEqual Row(3)
    }
}