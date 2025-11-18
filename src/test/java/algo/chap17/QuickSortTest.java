package algo.chap17;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * QuickSort est un algorithme de tri par division (divide and conquer) qui fonctionne en trois étapes :
 * Choisir un pivot (élément de référence)
 * Partitionner la liste autour du pivot
 * Trier récursivement les deux parties
 */
public class QuickSortTest {
    
    private QuickSort quickSort;
    
    @BeforeEach
    public void setUp() {
        quickSort = new QuickSort();
    }
    
    @Test
    @DisplayName("Test QuickSort avec stratégie leftmost sur liste triée")
    public void testQuickSortLeftmostSorted() {
        List<Integer> series = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        
        quickSort.resetOperations();
        List<Integer> sorted = quickSort.quicksort(series, new QuickSort.ChooseLeftmost());
        
        assertEquals(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), sorted);
        assertTrue(quickSort.getOperations() > 0, "Des opérations doivent être comptées");
    }
    
    @Test
    @DisplayName("Test QuickSort avec stratégie leftmost sur range(25)")
    public void testQuickSortRange25() {
        // Créer une liste comme range(25) en Python
        List<Integer> series = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            series.add(i);
        }
        
        quickSort.resetOperations();
        List<Integer> sorted = quickSort.quicksort(series, new QuickSort.ChooseLeftmost());
        
        assertEquals(series, sorted, "La liste déjà triée devrait rester inchangée");
        System.out.println("Operations pour range(25) avec leftmost: " + quickSort.getOperations());
    }
    
    @Test
    @DisplayName("Test QuickSort avec liste non triée")
    public void testQuickSortUnsorted() {
        List<Integer> series = Arrays.asList(5, 2, 8, 1, 9, 3, 7, 4, 6);
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        
        List<Integer> sorted = quickSort.quicksort(series, new QuickSort.ChooseRandom(42));
        
        assertEquals(expected, sorted);
    }
    
    @Test
    @DisplayName("Test QuickSort avec duplicatas")
    public void testQuickSortWithDuplicates() {
        List<Integer> series = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5);
        List<Integer> expected = Arrays.asList(1, 1, 2, 3, 3, 4, 5, 5, 5, 6, 9);
        
        List<Integer> sorted = quickSort.quicksort(series, new QuickSort.ChooseRandom(0));
        
        assertEquals(expected, sorted);
    }
    
    @Test
    @DisplayName("Test QuickSort avec liste vide")
    public void testQuickSortEmpty() {
        List<Integer> series = new ArrayList<>();
        
        List<Integer> sorted = quickSort.quicksort(series, new QuickSort.ChooseLeftmost());
        
        assertTrue(sorted.isEmpty());
    }
    
    @Test
    @DisplayName("Test QuickSort avec un seul élément")
    public void testQuickSortSingleElement() {
        List<Integer> series = Arrays.asList(42);
        
        List<Integer> sorted = quickSort.quicksort(series, new QuickSort.ChooseLeftmost());
        
        assertEquals(Arrays.asList(42), sorted);
    }
    
    @Test
    @DisplayName("Test QuickSort avec deux éléments")
    public void testQuickSortTwoElements() {
        List<Integer> series = Arrays.asList(5, 2);
        
        List<Integer> sorted = quickSort.quicksort(series, new QuickSort.ChooseLeftmost());
        
        assertEquals(Arrays.asList(2, 5), sorted);
    }
    
    @Test
    @DisplayName("Test QuickSort avec trois éléments")
    public void testQuickSortThreeElements() {
        List<Integer> series = Arrays.asList(5, 2, 8);
        
        List<Integer> sorted = quickSort.quicksort(series, new QuickSort.ChooseLeftmost());
        
        assertEquals(Arrays.asList(2, 5, 8), sorted);
    }
    
    @Test
    @DisplayName("Test QuickSort avec liste inversée")
    public void testQuickSortReversed() {
        List<Integer> series = Arrays.asList(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        quickSort.resetOperations();
        List<Integer> sorted = quickSort.quicksort(series, new QuickSort.ChooseRandom(123));
        
        assertEquals(expected, sorted);
    }
    
    @Test
    @DisplayName("Test QuickSort stratégie random avec seed")
    public void testQuickSortRandomWithSeed() {
        List<Integer> series = Arrays.asList(5, 2, 8, 1, 9, 3, 7, 4, 6);
        
        // Même seed devrait donner même nombre d'opérations
        quickSort.resetOperations();
        quickSort.quicksort(new ArrayList<>(series), new QuickSort.ChooseRandom(42));
        int ops1 = quickSort.getOperations();
        
        quickSort.resetOperations();
        quickSort.quicksort(new ArrayList<>(series), new QuickSort.ChooseRandom(42));
        int ops2 = quickSort.getOperations();
        
        assertEquals(ops1, ops2, "Même seed devrait produire même nombre d'opérations");
    }
    
    @Test
    @DisplayName("Test QuickSort stratégie median-of-three")
    public void testQuickSortMedianOfThree() {
        List<Integer> series = Arrays.asList(5, 2, 8, 1, 9, 3, 7, 4, 6);
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        
        List<Integer> sorted = quickSort.quicksort(series, new QuickSort.ChooseMedianOfThree());
        
        assertEquals(expected, sorted);
    }
    
    @Test
    @DisplayName("Comparaison des stratégies sur liste triée")
    public void testCompareStrategiesOnSortedList() {
        List<Integer> series = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            series.add(i);
        }
        
        // Leftmost (pire cas pour liste triée)
        quickSort.resetOperations();
        quickSort.quicksort(new ArrayList<>(series), new QuickSort.ChooseLeftmost());
        int leftmostOps = quickSort.getOperations();
        
        // Random (meilleur en moyenne)
        quickSort.resetOperations();
        quickSort.quicksort(new ArrayList<>(series), new QuickSort.ChooseRandom(0));
        int randomOps = quickSort.getOperations();
        
        // Median-of-three (bon compromis)
        quickSort.resetOperations();
        quickSort.quicksort(new ArrayList<>(series), new QuickSort.ChooseMedianOfThree());
        int medianOps = quickSort.getOperations();
        
        System.out.println("Opérations sur liste triée [0..99]:");
        System.out.println("  Leftmost: " + leftmostOps);
        System.out.println("  Random: " + randomOps);
        System.out.println("  Median-of-three: " + medianOps);
        
        // Random et Median devraient être plus efficaces que Leftmost sur liste triée
        assertTrue(randomOps < leftmostOps || medianOps < leftmostOps, 
            "Random ou Median devrait être plus efficace que Leftmost sur liste triée");
    }
    
    @Test
    @DisplayName("Test compteur d'opérations se réinitialise correctement")
    public void testOperationsCounterReset() {
        List<Integer> series = Arrays.asList(5, 2, 8, 1, 9);
        
        quickSort.quicksort(series, new QuickSort.ChooseLeftmost());
        int ops1 = quickSort.getOperations();
        assertTrue(ops1 > 0);
        
        quickSort.resetOperations();
        assertEquals(0, quickSort.getOperations());
        
        quickSort.quicksort(series, new QuickSort.ChooseLeftmost());
        int ops2 = quickSort.getOperations();
        assertTrue(ops2 > 0);
        assertEquals(ops1, ops2, "Mêmes entrées devraient donner mêmes opérations");
    }
    
    @Test
    @DisplayName("Test QuickSort avec tous les éléments identiques")
    public void testQuickSortAllIdentical() {
        List<Integer> series = Arrays.asList(7, 7, 7, 7, 7, 7, 7);
        List<Integer> expected = Arrays.asList(7, 7, 7, 7, 7, 7, 7);
        
        List<Integer> sorted = quickSort.quicksort(series, new QuickSort.ChooseRandom());
        
        assertEquals(expected, sorted);
    }
    
    @Test
    @DisplayName("Test QuickSort grande liste")
    public void testQuickSortLargeList() {
        List<Integer> series = new ArrayList<>();
        for (int i = 1000; i >= 1; i--) {
            series.add(i);
        }
        
        long startTime = System.nanoTime();
        List<Integer> sorted = quickSort.quicksort(series, new QuickSort.ChooseRandom(999));
        long endTime = System.nanoTime();
        
        long duration = (endTime - startTime) / 1_000_000; // en ms
        
        // Vérifier que c'est trié
        for (int i = 0; i < sorted.size() - 1; i++) {
            assertTrue(sorted.get(i) <= sorted.get(i + 1), 
                "La liste devrait être triée");
        }
        
        System.out.println("Tri de 1000 éléments en " + duration + "ms");
        assertTrue(duration < 1000, "Le tri devrait prendre moins d'une seconde");
    }
}