package algo.chap16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Implémentation dynamique du TSP inspirée du script Python fourni.
 * Utilise une approche Held-Karp en mémoïsant les meilleurs chemins pour chaque
 * sous-ensemble de villes terminant sur une ville donnée.
 */
public class TspDynamicProgrammingSolver {

    /**
     * Représente un chemin et la distance associée.
     *
     * @param path     ordre des sommets visités (retour à 0 inclus)
     * @param distance distance totale parcourue
     */
    public record Result(List<Integer> path, int distance) {
    }

    /**
     * Résout le TSP par programmation dynamique.
     *
     * @param distances matrice carrée des distances
     * @return meilleure route trouvée
     */
    public Result solve(int[][] distances) {
        validateDistanceMatrix(distances);
        int nodes = distances.length;
        if (nodes <= 1) {
            return new Result(List.of(0, 0), 0);
        }

        Map<State, Entry> memo = initializeBaseStates(distances);

        for (int subsetSize = 2; subsetSize < nodes; subsetSize++) {
            Map<State, Entry> newMemo = new HashMap<>();
            for (long subsetMask : generateSubsetMasks(nodes, subsetSize)) {
                for (int ending = 1; ending < nodes; ending++) {
                    if ((subsetMask & (1L << ending)) == 0) {
                        continue;
                    }
                    long subsetWithoutEnding = subsetMask & ~(1L << ending);
                    Entry bestEntry = null;
                    int bestDistance = Integer.MAX_VALUE;

                    for (int intermediate = 1; intermediate < nodes; intermediate++) {
                        if (intermediate == ending) {
                            continue;
                        }
                        if ((subsetWithoutEnding & (1L << intermediate)) == 0) {
                            continue;
                        }
                        State previousState = new State(subsetWithoutEnding, intermediate);
                        Entry previousEntry = memo.get(previousState);
                        if (previousEntry == null) {
                            continue;
                        }
                        int candidateDistance = previousEntry.distance + distances[previousState.endingCity()][ending];
                        if (candidateDistance < bestDistance) {
                            bestDistance = candidateDistance;
                            bestEntry = previousEntry.appendCity(ending, candidateDistance);
                        }
                    }

                    if (bestEntry != null) {
                        newMemo.put(new State(subsetMask, ending), bestEntry);
                    }
                }
            }
            memo = newMemo;
        }

        return buildFinalResult(distances, memo);
    }

    private Map<State, Entry> initializeBaseStates(int[][] distances) {
        Map<State, Entry> memo = new HashMap<>();
        for (int idx = 1; idx < distances.length; idx++) {
            long subsetMask = (1L << 0) | (1L << idx);
            List<Integer> basePath = List.of(0, idx);
            memo.put(new State(subsetMask, idx), new Entry(basePath, distances[0][idx]));
        }
        return memo;
    }

    private Result buildFinalResult(int[][] distances, Map<State, Entry> memo) {
        int bestDistance = Integer.MAX_VALUE;
        List<Integer> bestPath = null;

        for (Map.Entry<State, Entry> memoEntry : memo.entrySet()) {
            State state = memoEntry.getKey();
            Entry entry = memoEntry.getValue();
            int candidateDistance = entry.distance + distances[state.endingCity()][0];
            if (candidateDistance < bestDistance) {
                bestDistance = candidateDistance;
                bestPath = entry.appendCity(0, candidateDistance).path();
            }
        }

        if (bestPath == null) {
            return new Result(List.of(), 0);
        }
        return new Result(bestPath, bestDistance);
    }

    private List<Long> generateSubsetMasks(int nodes, int subsetSize) {
        List<Long> subsets = new ArrayList<>();
        int[] cities = new int[nodes - 1];
        for (int i = 0; i < nodes - 1; i++) {
            cities[i] = i + 1;
        }
        generateSubsetMasksRecursive(cities, subsetSize, 0, 0, subsets);
        return subsets;
    }

    private void generateSubsetMasksRecursive(int[] cities, int subsetSize, int index, long currentMask, List<Long> subsets) {
        if (subsetSize == 0) {
            subsets.add(currentMask | 1L); // ajoute la ville 0
            return;
        }
        if (index >= cities.length) {
            return;
        }
        // Inclut la ville courante
        generateSubsetMasksRecursive(cities, subsetSize - 1, index + 1, currentMask | (1L << cities[index]), subsets);
        // Exclut la ville courante
        generateSubsetMasksRecursive(cities, subsetSize, index + 1, currentMask, subsets);
    }

    private void validateDistanceMatrix(int[][] distances) {
        Objects.requireNonNull(distances, "La matrice de distances ne peut pas être nulle");
        if (distances.length == 0) {
            throw new IllegalArgumentException("La matrice doit contenir au moins un sommet");
        }
        int size = distances[0].length;
        if (size == 0) {
            throw new IllegalArgumentException("La matrice doit être carrée");
        }
        for (int[] row : distances) {
            if (row == null || row.length != size) {
                throw new IllegalArgumentException("La matrice doit être carrée et complète");
            }
        }
    }

    private record State(long subsetMask, int endingCity) {
    }

    private static final class Entry {
        private final List<Integer> path;
        private final int distance;

        private Entry(List<Integer> path, int distance) {
            this.path = path;
            this.distance = distance;
        }

        private Entry appendCity(int city, int newDistance) {
            List<Integer> extended = new ArrayList<>(path);
            extended.add(city);
            return new Entry(List.copyOf(extended), newDistance);
        }

        private List<Integer> path() {
            return path;
        }

        private int distance() {
            return distance;
        }
    }
}
