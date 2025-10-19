package algo.chap07.tri;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class TriParInsertionTest {

    @Test
    public void testTriParInsertion() {
        int[] data = {9, 5, 7, 4, 2, 8, 1, 10, 6, 3};
        int[] expected = {1,2,3,4,5,6,7,8,9,10};
        assertArrayEquals(expected, TriParInsertion.triParInsertion(data));
    }
}
