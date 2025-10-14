package org.chap9.graph.file_priorite;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class FileDePrioriteTest {

    @Test
    public void testPriorityQueueOperations() {
        List<String> input = List.of(
                "push 3 A",
                "push 1 B",
                "push 5 C",
                "push 9 Z",
                "push 2 A",  // Met à jour la priorité de A
                "pop",
                "pop",
                "pop"
        );

        List<String> expected = List.of("(1, B)", "(2, A)", "(5, C)");
        FileDePriorite file = new FileDePriorite();
        List<String> actual = file.processOperations(input);

        assertEquals(expected, actual);

        List<String> input2 = List.of(
                "push 8 D",
                "push 1 E",
                "push 5 F",
                "push 2 G", 
                "pop",
                "pop",
                "pop",
                "pop"
        );

        List<String> actual2 = file.processOperations(input2);
        List<String> expected2 = List.of("(1, E)", "(2, G)", "(5, F)", "(8, D)");
        assertEquals(expected2, actual2);

        List<String> input3 = List.of( "pop");
        List<String> actual3 = file.processOperations(input3);
        List<String> expected3 = List.of("(9, Z)");
        assertEquals(expected3, actual3);
    }
    
}
