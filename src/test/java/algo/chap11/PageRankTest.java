package algo.chap11;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;

class PageRankTest {

    private static final double EPSILON = 1.0e-6;

    @Test
    void rank_withSimpleCycle_returnsUniformRanks() {
        Map<String, List<String>> graph = new LinkedHashMap<>();
        graph.put("A", List.of("B"));
        graph.put("B", List.of("C"));
        graph.put("C", List.of("A"));

        Map<String, Double> ranks = PageRank.rank(graph);

        assertEquals(3, ranks.size(), "Every node in the cycle should be ranked");
        ranks.forEach((node, rank) -> assertEquals(1.0 / 3.0, rank, EPSILON, "Cycle nodes share rank equally"));
        double sum = ranks.values().stream().mapToDouble(Double::doubleValue).sum();
        assertEquals(1.0, sum, EPSILON, "Ranks must be a probability distribution");
    }

    @Test
    void rank_handlesDanglingNodesByRedistributingProbabilityMass() {
        Map<String, List<String>> graph = new LinkedHashMap<>();
        graph.put("A", List.of("B", "C"));
        graph.put("B", List.of("C"));
        graph.put("C", List.of());
        graph.put("D", List.of("C"));

        Map<String, Double> ranks = PageRank.rank(graph, 0.85, 1.0e-8, 10_000);

        double sum = ranks.values().stream().mapToDouble(Double::doubleValue).sum();
        assertEquals(1.0, sum, EPSILON, "Ranks must stay normalised even with dangling nodes");
        assertTrue(ranks.get("C") > ranks.get("B"), "Dangling nodes should boost the target node");
        assertTrue(ranks.get("C") > ranks.get("A"), "Node collecting most links should dominate");
        ranks.values().forEach(rank -> assertTrue(rank > 0.0, "Ranks should stay positive"));
    }

    @Test
    void rank_includesTargetsMissingFromGraphDefinition() {
        Map<String, List<String>> graph = new LinkedHashMap<>();
        graph.put("A", List.of("B", "C"));
        graph.put("B", List.of("A"));

        Map<String, Double> ranks = PageRank.rank(graph);

        assertTrue(ranks.containsKey("C"), "Targets absent from the adjacency list must still be ranked");
        double sum = ranks.values().stream().mapToDouble(Double::doubleValue).sum();
        assertEquals(1.0, sum, EPSILON);
    }

    @Test
    void rank_withEmptyGraph_returnsEmptyMap() {
        Map<String, List<String>> graph = new LinkedHashMap<>();

        Map<String, Double> ranks = PageRank.rank(graph);

        assertTrue(ranks.isEmpty(), "Empty graphs produce no ranks");
    }

    @Test
    void rank_withNullGraph_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> PageRank.rank(null));
    }

    @Test
    void rank_withInvalidDampingFactor_throwsIllegalArgumentException() {
        Map<String, List<String>> graph = new LinkedHashMap<>();
        graph.put("A", List.of("B"));

        assertThrows(IllegalArgumentException.class, () -> PageRank.rank(graph, 0.0, 1.0e-6, 100));
        assertThrows(IllegalArgumentException.class, () -> PageRank.rank(graph, 1.0, 1.0e-6, 100));
    }

    @Test
    void rank_withNonPositiveTolerance_throwsIllegalArgumentException() {
        Map<String, List<String>> graph = new LinkedHashMap<>();
        graph.put("A", List.of("B"));

        assertThrows(IllegalArgumentException.class, () -> PageRank.rank(graph, 0.85, 0.0, 100));
        assertThrows(IllegalArgumentException.class, () -> PageRank.rank(graph, 0.85, -1.0, 100));
    }

    @Test
    void rank_withNonPositiveIterationLimit_throwsIllegalArgumentException() {
        Map<String, List<String>> graph = new LinkedHashMap<>();
        graph.put("A", List.of("B"));

        assertThrows(IllegalArgumentException.class, () -> PageRank.rank(graph, 0.85, 1.0e-6, 0));
        assertThrows(IllegalArgumentException.class, () -> PageRank.rank(graph, 0.85, 1.0e-6, -10));
    }

    @Test
    void sortByRank_ordersEntriesDescending() {
        Map<String, Double> ranks = new LinkedHashMap<>();
        ranks.put("A", 0.2);
        ranks.put("B", 0.6);
        ranks.put("C", 0.2);
        ranks.put("D", 0.4);

        List<Entry<String, Double>> sorted = PageRank.sortByRank(ranks);

        List<String> expectedOrder = List.of("B", "D", "A", "C");
        List<String> actualOrder = new ArrayList<>();
        sorted.forEach(entry -> actualOrder.add(entry.getKey()));
        assertIterableEquals(expectedOrder, actualOrder);
    }

    @Test
    void sortByRank_withNullOrEmptyInput_returnsEmptyList() {
        assertTrue(PageRank.sortByRank(null).isEmpty());
        assertTrue(PageRank.sortByRank(Map.of()).isEmpty());
    }
}
