package algo.chap17;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

/* 
 * QuickSort est un algorithme de tri par division (divide and conquer) qui fonctionne en trois étapes :
 *
 *  Choisir un pivot (élément de référence)
 *  Partitionner la liste autour du pivot
 *  Trier récursivement les deux parties
 * 
 * ça utilise la méthode Las Vegas car 
 * ✅ Pivot aléatoire → comportement probabiliste
 * ✅ Las Vegas → résultat toujours correct, temps variable
 * ✅ O(n log n) en moyenne garanti, peu importe les données
 */
public class QuickSort {
    
    private int operations = 0;
    
    /**
     * Interface fonctionnelle pour les stratégies de choix de pivot
     */
    @FunctionalInterface
    public interface PivotStrategy {
        int choosePivot(List<Integer> series);
    }
    
    /**
     * Stratégie : choisir le premier élément (leftmost)
     */
    public static class ChooseLeftmost implements PivotStrategy {
        @Override
        public int choosePivot(List<Integer> series) {
            return series.get(0);
        }
    }
    
    /**
     * Stratégie : choisir un élément aléatoire
     */
    public static class ChooseRandom implements PivotStrategy {
        private Random random;
        
        public ChooseRandom() {
            this.random = new Random();
        }
        
        public ChooseRandom(long seed) {
            this.random = new Random(seed);
        }
        
        @Override
        public int choosePivot(List<Integer> series) {
            return series.get(random.nextInt(series.size()));
        }
    }
    
    /**
     * Stratégie : choisir l'élément médian (pivot médian de 3)
     */
    public static class ChooseMedianOfThree implements PivotStrategy {
        @Override
        public int choosePivot(List<Integer> series) {
            int size = series.size();
            if (size <= 2) return series.get(0);
            
            int first = series.get(0);
            int middle = series.get(size / 2);
            int last = series.get(size - 1);
            
            // Trouver la médiane des trois
            if ((first >= middle && first <= last) || (first >= last && first <= middle))
                return first;
            else if ((middle >= first && middle <= last) || (middle >= last && middle <= first))
                return middle;
            else
                return last;
        }
    }
    
    /**
     * Tri QuickSort avec stratégie de pivot configurable
     * @param series la liste à trier
     * @param pivotStrategy la stratégie de choix du pivot
     * @return une nouvelle liste triée
     */
    public List<Integer> quicksort(List<Integer> series, PivotStrategy pivotStrategy) {
        // Compter les opérations (similaire au code Python)
        operations += series.size();
        
        // Cas de base : petites listes
        if (series.size() <= 3) {
            List<Integer> sorted = new ArrayList<>(series);
            Collections.sort(sorted);
            return sorted;
        }
        
        // Choisir le pivot selon la stratégie
        int pivot = pivotStrategy.choosePivot(series);
        
        // Compter les duplicatas
        int duplicates = 0;
        for (int item : series) {
            if (item == pivot) {
                duplicates++;
            }
        }
        
        // Partitionner
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        
        for (int item : series) {
            if (item < pivot) {
                left.add(item);
            } else if (item > pivot) {
                right.add(item);
            }
        }
        
        // Récursion et reconstruction
        List<Integer> result = new ArrayList<>();
        result.addAll(quicksort(left, pivotStrategy));
        
        // Ajouter tous les duplicatas du pivot
        for (int i = 0; i < duplicates; i++) {
            result.add(pivot);
        }
        
        result.addAll(quicksort(right, pivotStrategy));
        
        return result;
    }
    
    /**
     * Réinitialise le compteur d'opérations
     */
    public void resetOperations() {
        operations = 0;
    }
    
    /**
     * Retourne le nombre d'opérations effectuées
     */
    public int getOperations() {
        return operations;
    }
    
    /**
     * Méthode de commodité pour trier avec la stratégie leftmost
     */
    public List<Integer> quicksortLeftmost(List<Integer> series) {
        return quicksort(series, new ChooseLeftmost());
    }
    
    /**
     * Méthode de commodité pour trier avec une stratégie aléatoire
     */
    public List<Integer> quicksortRandom(List<Integer> series) {
        return quicksort(series, new ChooseRandom());
    }
    
    /**
     * Méthode principale pour démonstration
     */
    public static void main(String[] args) {
        QuickSort qs = new QuickSort();
        
        // Créer une série de 0 à 24 (comme range(25) en Python)
        List<Integer> series = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            series.add(i);
        }
        
        System.out.println("Liste originale : " + series);
        
        // Test avec stratégie leftmost
        qs.resetOperations();
        List<Integer> sortedList = qs.quicksort(series, new ChooseLeftmost());
        System.out.println("\n=== Stratégie LEFTMOST ===");
        System.out.println("Liste triée : " + sortedList);
        System.out.println("Operations: " + qs.getOperations());
        
        // Test avec stratégie random
        qs.resetOperations();
        List<Integer> sortedRandom = qs.quicksort(new ArrayList<>(series), new ChooseRandom(42));
        System.out.println("\n=== Stratégie RANDOM ===");
        System.out.println("Liste triée : " + sortedRandom);
        System.out.println("Operations: " + qs.getOperations());
        
        // Test avec stratégie médian de 3
        qs.resetOperations();
        List<Integer> sortedMedian = qs.quicksort(new ArrayList<>(series), new ChooseMedianOfThree());
        System.out.println("\n=== Stratégie MEDIAN OF THREE ===");
        System.out.println("Liste triée : " + sortedMedian);
        System.out.println("Operations: " + qs.getOperations());
        
        // Test avec liste non triée
        List<Integer> unsorted = new ArrayList<>();
        Collections.addAll(unsorted, 5, 2, 8, 1, 9, 3, 7, 4, 6);
        
        qs.resetOperations();
        List<Integer> sortedUnsorted = qs.quicksort(unsorted, new ChooseRandom());
        System.out.println("\n=== Liste non triée ===");
        System.out.println("Avant : " + unsorted);
        System.out.println("Après : " + sortedUnsorted);
        System.out.println("Operations: " + qs.getOperations());
    }
}