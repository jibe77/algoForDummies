package algo.chap16;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Implémentation brute-force du problème du voyageur de commerce.
 * Inspirée directement du script Python fourni : on teste toutes les permutations
 * des villes (sauf la ville de départ 0) et on conserve la meilleure route trouvée.
 */
public class TspBruteForceSolver {

    /**
     * Représente une solution composée du chemin et de sa distance.
     *
     * @param path     ordre des sommets visités (incluant le retour à 0)
     * @param distance distance totale parcourue
     */
    public record Result(List<Integer> path, int distance) {
    }

    /**
     * Calcule brute-force la meilleure tournée en considérant toutes les permutations.
     *
     * @param distances matrice des distances, distances[i][j] = distance de i vers j
     * @return meilleure solution trouvée
     */
    public Result solve(int[][] distances) {
        validateDistanceMatrix(distances);
        int nodes = distances.length;

        if (nodes <= 1) {
            return new Result(List.of(0, 0), 0);
        }

        List<Integer> candidates = new ArrayList<>();
        for (int i = 1; i < nodes; i++) {
            candidates.add(i);
        }

        BestSolution best = new BestSolution();
        permute(candidates, 0, distances, best);
        return best.toResult();
    }

    /**
     * Calcule le nombre de permutations symétriques (n! / 2) comme dans le code Python.
     *
     * @param nodes nombre de noeuds considérés
     * @return nombre de routes distinctes si l'on ignore le sens de parcours
     */
    public static long symmetricRouteCount(int nodes) {
        if (nodes < 0) {
            throw new IllegalArgumentException("Le nombre de noeuds doit être positif");
        }
        if (nodes <= 1) {
            return 0;
        }
        return factorial(nodes) / 2L;
    }

    private static long factorial(int value) {
        long result = 1;
        for (int i = 2; i <= value; i++) {
            result *= i;
        }
        return result;
    }

    private void permute(List<Integer> nodes, int index, int[][] distances, BestSolution best) {
        if (index == nodes.size()) {
            evaluate(nodes, distances, best);
            return;
        }
        for (int i = index; i < nodes.size(); i++) {
            Collections.swap(nodes, index, i);
            permute(nodes, index + 1, distances, best);
            Collections.swap(nodes, index, i);
        }
    }

    private void evaluate(List<Integer> pathWithoutStart, int[][] distances, BestSolution best) {
        int totalDistance = 0;
        int start = 0;
        int current = start;

        for (int next : pathWithoutStart) {
            totalDistance += distances[current][next];
            current = next;
        }
        totalDistance += distances[current][start];

        if (totalDistance <= best.distance) {
            List<Integer> fullPath = new ArrayList<>(pathWithoutStart.size() + 2);
            fullPath.add(start);
            fullPath.addAll(pathWithoutStart);
            fullPath.add(start);
            best.update(fullPath, totalDistance);
        }
    }

    private void validateDistanceMatrix(int[][] distances) {
        Objects.requireNonNull(distances, "La matrice de distances ne peut pas être nulle");
        if (distances.length == 0) {
            throw new IllegalArgumentException("La matrice de distances doit contenir au moins un sommet");
        }
        int size = distances[0].length;
        if (size == 0) {
            throw new IllegalArgumentException("La matrice de distances doit être carrée");
        }
        for (int[] row : distances) {
            if (row == null || row.length != size) {
                throw new IllegalArgumentException("La matrice de distances doit être carrée et complète");
            }
        }
    }

    private static final class BestSolution {
        private List<Integer> path = null;
        private int distance = Integer.MAX_VALUE;

        private void update(List<Integer> newPath, int newDistance) {
            this.path = List.copyOf(newPath);
            this.distance = newDistance;
            System.out.printf("Best solution so far: %s, %d kms%n", this.path, this.distance);
        }

        private Result toResult() {
            if (path == null) {
                return new Result(List.of(), 0);
            }
            return new Result(path, distance);
        }
    }
}
