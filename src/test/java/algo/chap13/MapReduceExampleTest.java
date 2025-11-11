package algo.chap13;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class MapReduceExampleTest {

    @Test
    void testRemovePunctuation() {
        assertEquals("HelloWorld", MapReduceExample.removePunctuation("Hello, World!"));
        assertEquals("Bonjour", MapReduceExample.removePunctuation("Bon-jour!!"));
        assertEquals("", MapReduceExample.removePunctuation("1234?!"));
    }

    @Test
    void testCountWords() {
        List<String> words = Arrays.asList("War", "and", "Peace,", "Napoleon!");
        List<String> keywords = Arrays.asList("WAR", "PEACE", "NAPOLEON");
        List<MapReduceExample.Pair<String, Integer>> result = MapReduceExample.countWords(words, keywords);
        assertEquals(3, result.size());
        assertEquals("WAR", result.get(0).key);
        assertEquals(1, result.get(0).value);
    }

    @Test
    void testPartition() {
        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
        List<List<Integer>> parts = MapReduceExample.partition(data, 2);
        assertEquals(3, parts.size());
        assertEquals(Arrays.asList(1, 2), parts.get(0));
        assertEquals(Arrays.asList(3, 4), parts.get(1));
        assertEquals(Collections.singletonList(5), parts.get(2));
    }

    @Test
    void testShuffleSortAndReduce() {
        List<MapReduceExample.Pair<String, Integer>> list1 = Arrays.asList(
                new MapReduceExample.Pair<>("WAR", 1),
                new MapReduceExample.Pair<>("PEACE", 1)
        );
        List<MapReduceExample.Pair<String, Integer>> list2 = Arrays.asList(
                new MapReduceExample.Pair<>("WAR", 1),
                new MapReduceExample.Pair<>("RUSSIA", 1)
        );

        List<List<MapReduceExample.Pair<String, Integer>>> data = Arrays.asList(list1, list2);
        List<List<MapReduceExample.Pair<String, Integer>>> shuffled = MapReduceExample.shuffleSort(data);

        // Il doit y avoir 3 clés distinctes
        assertEquals(3, shuffled.size());

        // Réduction
        List<MapReduceExample.Pair<String, Integer>> reduced = new ArrayList<>();
        for (List<MapReduceExample.Pair<String, Integer>> group : shuffled) {
            reduced.add(MapReduceExample.reduce(group));
        }

        // Vérifie que "WAR" est compté deux fois
        Optional<MapReduceExample.Pair<String, Integer>> warPair =
                reduced.stream().filter(p -> p.key.equals("WAR")).findFirst();

        assertTrue(warPair.isPresent());
        assertEquals(2, warPair.get().value);
    }
}
