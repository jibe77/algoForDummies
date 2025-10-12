package algo.chap7.tri;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import algo.chap7.tri.TriRapide;

public class TriRapideTest {

    @Test
    public void triRapideTest() {
        int[] data = {9, 5, 7, 4, 2, 8, 1, 10, 6, 3};
        int[] expected = {1,2,3,4,5,6,7,8,9,10};
        int[] result = TriRapide.triRapide(data);
        assertArrayEquals(expected, result);
        ;
        
        
    }
}
