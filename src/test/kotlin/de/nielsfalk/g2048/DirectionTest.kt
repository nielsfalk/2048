package de.nielsfalk.g2048

import de.nielsfalk.g2048.Direction.*
import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory


class DirectionTest {
    @TestFactory
    fun opposite() =
        mapOf(
            Left to Right,
            Right to Left,
            Up to Down,
            Down to Up
        )
            .map { (direction, expectedOpposite) ->
                dynamicTest("opposite of $direction is $expectedOpposite") {
                    direction.opposite shouldEqual expectedOpposite
                }
            }
}