package algo.chap6;

import org.junit.jupiter.api.Test;

import algo.chap6.StackLIFO;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class StackLIFOTest {
    @Test
    public void testLIFO() {
        StackLIFO stack = new StackLIFO(3);
        assertTrue(stack.push(1));
        assertTrue(stack.push(2));
        assertTrue(stack.push(3));
        stack.displayStack();
        assertFalse(stack.push(4)); // La pile est pleine
        assertEquals(3, stack.pop());
        stack.displayStack();
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
        assertEquals(-1, stack.pop()); // La pile est vide
        assertTrue(true); // Test de base
    }
}
