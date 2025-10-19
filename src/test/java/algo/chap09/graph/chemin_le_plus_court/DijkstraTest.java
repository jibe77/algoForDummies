package algo.chap09.graph.chemin_le_plus_court;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class DijkstraTest {

    @Test
    public void testShortestPath() {
        // Construction du graphe
        Map<String, List<AlgoDijkstra.Edge>> graph = new HashMap<>();
        graph.put("A", List.of(new AlgoDijkstra.Edge("B", 4), new AlgoDijkstra.Edge("C", 2)));
        graph.put("B", List.of(new AlgoDijkstra.Edge("C", 1), new AlgoDijkstra.Edge("D", 5)));
        graph.put("C", List.of(new AlgoDijkstra.Edge("B", 3), new AlgoDijkstra.Edge("D", 8), new AlgoDijkstra.Edge("E", 10)));
        graph.put("D", List.of(new AlgoDijkstra.Edge("E", 2)));
        graph.put("E", List.of());

        // Calcul de Dijkstra depuis A
        AlgoDijkstra.Result result = AlgoDijkstra.compute(graph, "A");

        // Vérification des distances minimales
        assertEquals(0.0, result.dist.get("A"));
        assertEquals(4.0, result.dist.get("B"));
        assertEquals(2.0, result.dist.get("C"));
        assertEquals(9.0, result.dist.get("D"));
        assertEquals(11.0, result.dist.get("E"));

        // Vérification du plus court chemin jusqu’à E
        List<String> path = result.getPathTo("E");
        assertEquals(List.of("A", "B", "D", "E"), path);
    }

    @Test
    public void testNoNegativeWeights() {
        Map<String, List<AlgoDijkstra.Edge>> graph = new HashMap<>();
        graph.put("A", List.of(new AlgoDijkstra.Edge("B", -1.0)));
        graph.put("B", List.of());

        assertThrows(IllegalArgumentException.class, () -> {
            AlgoDijkstra.compute(graph, "A");
        });
    }
}