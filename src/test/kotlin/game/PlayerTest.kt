package game

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class PlayerTest {
    @Test
    fun `when player move is called should change user position`() {
        val player = Player(1, 0)
        player.move(10)
        assertEquals(10, player.position)
    }
}