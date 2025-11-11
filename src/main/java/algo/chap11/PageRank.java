package algo.chap11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class PageRank {

    private static final double DEFAULT_DAMPING = 0.85;
    private static final double DEFAULT_TOLERANCE = 1.0e-6;
    private static final int DEFAULT_MAX_ITERATIONS = 100;

    private PageRank() {
        // Utility class
    }

    public static Map<String, Double> rank(Map<String, List<String>> graph) {
        return rank(graph, DEFAULT_DAMPING, DEFAULT_TOLERANCE, DEFAULT_MAX_ITERATIONS);
    }

    public static Map<String, Double> rank(
            Map<String, List<String>> graph,
            double dampingFactor,
            double tolerance,
            int maxIterations
    ) {
        validateParameters(graph, dampingFactor, tolerance, maxIterations);

        Set<String> nodes = collectNodes(graph);
        if (nodes.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, List<String>> outgoing = normalizeOutgoing(graph, nodes);
        Map<String, List<String>> incoming = buildIncoming(outgoing, nodes);

        Map<String, Double> ranks = initialiseRanks(nodes);
        Map<String, Double> nextRanks = new LinkedHashMap<>();

        int iteration = 0;
        while (iteration < maxIterations) {
            double danglingRank = computeDanglingRank(outgoing, ranks);
            double delta = updateRanks(
                    nodes,
                    outgoing,
                    incoming,
                    ranks,
                    nextRanks,
                    dampingFactor,
                    danglingRank
            );
            ranks.putAll(nextRanks);
            if (delta < tolerance) {
                break;
            }
            iteration++;
        }

        return new LinkedHashMap<>(ranks);
    }

    public static List<Map.Entry<String, Double>> sortByRank(Map<String, Double> ranks) {
        if (ranks == null || ranks.isEmpty()) {
            return Collections.emptyList();
        }
        List<Map.Entry<String, Double>> sorted = new ArrayList<>(ranks.entrySet());
        sorted.sort(Comparator.comparingDouble(Map.Entry<String, Double>::getValue).reversed());
        return sorted;
    }

    private static void validateParameters(
            Map<String, List<String>> graph,
            double dampingFactor,
            double tolerance,
            int maxIterations
    ) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }
        if (dampingFactor <= 0.0 || dampingFactor >= 1.0) {
            throw new IllegalArgumentException("Damping factor must be in ]0, 1[");
        }
        if (tolerance <= 0.0) {
            throw new IllegalArgumentException("Tolerance must be positive");
        }
        if (maxIterations <= 0) {
            throw new IllegalArgumentException("Max iterations must be positive");
        }
    }

    private static Set<String> collectNodes(Map<String, List<String>> graph) {
        Set<String> nodes = new LinkedHashSet<>();
        for (Map.Entry<String, List<String>> entry : graph.entrySet()) {
            String source = entry.getKey();
            nodes.add(source);
            for (String target : entry.getValue()) {
                nodes.add(target);
            }
        }
        return nodes;
    }

    private static Map<String, List<String>> normalizeOutgoing(
            Map<String, List<String>> graph,
            Set<String> nodes
    ) {
        Map<String, List<String>> outgoing = new LinkedHashMap<>();
        for (String node : nodes) {
            List<String> neighbours = graph.getOrDefault(node, Collections.emptyList());
            LinkedHashSet<String> filtered = new LinkedHashSet<>();
            for (String target : neighbours) {
                if (nodes.contains(target)) {
                    filtered.add(target);
                }
            }
            outgoing.put(node, new ArrayList<>(filtered));
        }
        return outgoing;
    }

    private static Map<String, List<String>> buildIncoming(
            Map<String, List<String>> outgoing,
            Set<String> nodes
    ) {
        Map<String, List<String>> incoming = new LinkedHashMap<>();
        for (String node : nodes) {
            incoming.put(node, new ArrayList<>());
        }
        for (Map.Entry<String, List<String>> entry : outgoing.entrySet()) {
            String source = entry.getKey();
            for (String target : entry.getValue()) {
                incoming.get(target).add(source);
            }
        }
        return incoming;
    }

    private static Map<String, Double> initialiseRanks(Set<String> nodes) {
        Map<String, Double> ranks = new LinkedHashMap<>();
        double initialValue = 1.0 / nodes.size();
        for (String node : nodes) {
            ranks.put(node, initialValue);
        }
        return ranks;
    }

    private static double computeDanglingRank(
            Map<String, List<String>> outgoing,
            Map<String, Double> ranks
    ) {
        double danglingRank = 0.0;
        for (Map.Entry<String, List<String>> entry : outgoing.entrySet()) {
            if (entry.getValue().isEmpty()) {
                danglingRank += ranks.getOrDefault(entry.getKey(), 0.0);
            }
        }
        return danglingRank;
    }

    private static double updateRanks(
            Set<String> nodes,
            Map<String, List<String>> outgoing,
            Map<String, List<String>> incoming,
            Map<String, Double> currentRanks,
            Map<String, Double> nextRanks,
            double dampingFactor,
            double danglingRank
    ) {
        double baseRank = (1.0 - dampingFactor) / nodes.size();
        double spreadDangling = dampingFactor * danglingRank / nodes.size();
        double delta = 0.0;

        for (String node : nodes) {
            double rank = baseRank + spreadDangling;
            for (String parent : incoming.get(node)) {
                List<String> links = outgoing.get(parent);
                if (!links.isEmpty()) {
                    rank += dampingFactor * (currentRanks.get(parent) / links.size());
                }
            }
            delta += Math.abs(rank - currentRanks.get(node));
            nextRanks.put(node, rank);
        }

        return delta;
    }
}
