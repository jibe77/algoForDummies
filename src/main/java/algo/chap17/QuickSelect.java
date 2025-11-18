package algo.chap17;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * QuickSelect est un algorithme de sélection qui permet 
 * de trouver le k-ième plus petit élément 
 * d'une liste sans avoir besoin de la trier entièrement. 
 * C'est une variante optimisée du tri QuickSort.
 * 
 * QuickSelect est considéré comme un algorithme 
 * probabiliste (ou randomisé) en raison de son utilisation 
 * de l'aléatoire dans le choix du pivot.
 * 
 * Avec un pivot aléatoire, la complexité ESPÉRÉE est O(n), 
 * même si les données sont triées ou organisées de manière défavorable.
 * 
 * QuickSelect est un algorithme de Las Vegas :
 *
        ✅ Le résultat est toujours correct
        🎲 Le temps d'exécution est aléatoire
        📊 On peut garantir un temps moyen avec forte probabilité
 *
 */
public class QuickSelect {
    private Random random;
    
    public QuickSelect() {
        this.random = new Random();
    }
    
    public QuickSelect(long seed) {
        this.random = new Random(seed);
    }
    
    /**
     * Trouve le k-ième plus petit élément (indexé à 0) dans la série
     * @param series la liste d'entiers
     * @param k l'index de l'élément recherché (0-based)
     * @return le k-ième plus petit élément
     */
    public double quickselect(List<Integer> series, int k) {
        if (series.isEmpty()) {
            throw new IllegalArgumentException("La série ne peut pas être vide");
        }
        
        if (k < 0 || k >= series.size()) {
            throw new IllegalArgumentException("k doit être entre 0 et " + (series.size() - 1));
        }
        
        // Choisir un pivot aléatoire
        int pivot = series.get(random.nextInt(series.size()));
        
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        
        // Partitionner la série
        for (int item : series) {
            if (item < pivot) {
                left.add(item);
            } else if (item > pivot) {
                right.add(item);
            }
        }
        
        int lengthLeft = left.size();
        
        // Si k est dans la partie gauche
        if (lengthLeft > k) {
            return quickselect(left, k);
        }
        
        k -= lengthLeft;
        
        // Calculer le nombre de duplicatas du pivot
        int duplicates = series.size() - (lengthLeft + right.size());
        
        // Si k correspond à un duplicata du pivot
        if (duplicates > k) {
            return (double) pivot;
        }
        
        k -= duplicates;
        
        // Si k est dans la partie droite
        return quickselect(right, k);
    }
    
    /**
     * Calcule la médiane d'une série
     * @param series la liste d'entiers
     * @return la médiane
     */
    public double median(List<Integer> series) {
        if (series.isEmpty()) {
            throw new IllegalArgumentException("La série ne peut pas être vide");
        }
        
        int n = series.size();
        
        if (n % 2 != 0) {
            // Nombre impair d'éléments
            return quickselect(series, n / 2);
        } else {
            // Nombre pair d'éléments - moyenne des deux éléments centraux
            double left = quickselect(new ArrayList<>(series), (n - 1) / 2);
            double right = quickselect(new ArrayList<>(series), (n + 1) / 2);
            return (left + right) / 2.0;
        }
    }
    
    /**
     * Génère une série aléatoire pour les tests
     */
    public static List<Integer> generateRandomSeries(int n, int min, int max, long seed) {
        Random rand = new Random(seed);
        List<Integer> series = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            series.add(rand.nextInt(max - min + 1) + min);
        }
        return series;
    }
}