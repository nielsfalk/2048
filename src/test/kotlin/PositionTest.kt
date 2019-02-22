import Direction.*
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

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

    @TestFactory
    fun neigbour(): List<DynamicTest> {
        val position = Position(Row(1),Col(1))
        return mapOf(
            Left to Position(Row(1),Col(0)),
            Right to Position(Row(1),Col(2)),
            Up to Position(Row(0),Col(1)),
            Down to Position(Row(2),Col(1))
        )
            .map { (direction, expectedNeighbour) ->
                dynamicTest("$direction of position is $expectedNeighbour") {
                    position.neighbour(direction) shouldEqual expectedNeighbour
                }
            }
    }
}