package algo.chap06;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class GraphFindPathTest {

    @Test
    public void testGraph() {
        Map<String, List<String>> graph = new HashMap<>();
        graph.put("A", Arrays.asList("B", "F"));
        graph.put("B", Arrays.asList("A", "C"));
        graph.put("C", Arrays.asList("B", "D"));
        graph.put("D", Arrays.asList("C", "E"));
        graph.put("E", Arrays.asList("D", "F"));
        graph.put("F", Arrays.asList("A", "E"));

        assertIterableEquals(
            Arrays.asList("B","A","F","E"), 
            GraphFindPath.findPath(graph, "B", "E"));
    }
}
