package algo.chap20;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Le code génère un labyrinthe aléatoire sous forme de graphe pondéré.
 * Les fonctions de distance servent de heuristiques pour la recherche de chemin.
 * Les algorithmes Best-first et A* explorent ce graphe pour trouver un chemin optimal entre deux nœuds.
 * Les tests garantissent que les briques de base (distances, reconstruction, poids) fonctionnent correctement.
 * 
 * Best-first search
 * Explore en priorité les nœuds les plus proches du but selon l’heuristique (ici Manhattan).
 * Rapide mais pas toujours optimal.
 * A*
 * Combine le coût réel parcouru (g(n)) et l’heuristique (h(n)).
 * Explore les chemins prometteurs mais garde la garantie d’optimalité si l’heuristique est admissible.
 * Dans notre cas, la distance Manhattan est admissible → A* trouve toujours le chemin le plus court.
 */
public class MazeGraph {

    // ... (fonctions déjà vues)

    public static List<String> bestFirstSearch(Map<String, Map<String, Integer>> graph,
                                               Map<String, double[]> coords,
                                               String start, String goal) {
        Map<String, Integer> closedList = new HashMap<>();
        closedList.put(start, manhattanDist(start, goal, coords));
        Map<String, String> path = new HashMap<>();
        Set<String> openList = new HashSet<>(graph.keySet());

        while (!openList.isEmpty()) {
            Set<String> candidates = new HashSet<>(openList);
            candidates.retainAll(closedList.keySet());
            if (candidates.isEmpty()) break;

            String minNode = candidates.stream()
                    .min(Comparator.comparingInt(closedList::get))
                    .orElse(null);

            if (minNode == null) break;
            if (minNode.equals(goal)) {
                return reconstructPath(path, start, goal);
            }

            openList.remove(minNode);
            for (String neighbor : nodeNeighbors(graph, minNode)) {
                if (!closedList.containsKey(neighbor)) {
                    closedList.put(neighbor, manhattanDist(neighbor, goal, coords));
                    path.put(neighbor, minNode);
                }
            }
        }
        return Collections.emptyList();
    }

    public static List<String> aStarSearch(Map<String, Map<String, Integer>> graph,
                                           Map<String, double[]> coords,
                                           String start, String goal) {
        Map<String, Integer> closedList = new HashMap<>();
        closedList.put(start, manhattanDist(start, goal, coords));
        Map<String, Integer> visited = new HashMap<>();
        visited.put(start, 0);
        Map<String, String> path = new HashMap<>();
        Set<String> openList = new HashSet<>(graph.keySet());

        while (!openList.isEmpty()) {
            Set<String> candidates = new HashSet<>(openList);
            candidates.retainAll(closedList.keySet());
            if (candidates.isEmpty()) break;

            String minNode = candidates.stream()
                    .min(Comparator.comparingInt(closedList::get))
                    .orElse(null);

            if (minNode == null) break;
            if (minNode.equals(goal)) {
                return reconstructPath(path, start, goal);
            }

            openList.remove(minNode);
            int currentWeight = visited.get(minNode);

            for (String neighbor : nodeNeighbors(graph, minNode)) {
                int newWeight = currentWeight + graphWeight(graph, minNode, neighbor);
                if (!visited.containsKey(neighbor) || newWeight < visited.get(neighbor)) {
                    visited.put(neighbor, newWeight);
                    closedList.put(neighbor, manhattanDist(neighbor, goal, coords) + newWeight);
                    path.put(neighbor, minNode);
                }
            }
        }
        return Collections.emptyList();
    }
}
