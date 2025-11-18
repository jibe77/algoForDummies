package algo.chap17;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuickSelectTest {
    
    private QuickSelect quickSelect;
    
    @BeforeEach
    public void setUp() {
        quickSelect = new QuickSelect(0); // Seed fixe pour reproductibilité
    }
    
    @Test
    @DisplayName("Test quickselect avec une série simple triée")
    public void testQuickselectSortedSeries() {
        List<Integer> series = Arrays.asList(1, 2, 3, 4, 5);
        
        assertEquals(1.0, quickSelect.quickselect(series, 0), "Le 1er élément devrait être 1");
        assertEquals(3.0, quickSelect.quickselect(series, 2), "Le 3e élément devrait être 3");
        assertEquals(5.0, quickSelect.quickselect(series, 4), "Le 5e élément devrait être 5");
    }
    
    @Test
    @DisplayName("Test quickselect avec une série non triée")
    public void testQuickselectUnsortedSeries() {
        List<Integer> series = Arrays.asList(5, 2, 8, 1, 9, 3, 7);
        List<Integer> sorted = new ArrayList<>(series);
        Collections.sort(sorted);
        
        for (int k = 0; k < series.size(); k++) {
            double result = quickSelect.quickselect(new ArrayList<>(series), k);
            assertEquals((double) sorted.get(k), result, 
                "L'élément à l'index " + k + " devrait être " + sorted.get(k));
        }
    }
    
    @Test
    @DisplayName("Test quickselect avec des duplicatas")
    public void testQuickselectWithDuplicates() {
        List<Integer> series = Arrays.asList(3, 1, 3, 3, 2, 3, 1);
        List<Integer> sorted = new ArrayList<>(series);
        Collections.sort(sorted); // [1, 1, 2, 3, 3, 3, 3]
        
        assertEquals(1.0, quickSelect.quickselect(new ArrayList<>(series), 0));
        assertEquals(2.0, quickSelect.quickselect(new ArrayList<>(series), 2));
        assertEquals(3.0, quickSelect.quickselect(new ArrayList<>(series), 4));
    }
    
    @Test
    @DisplayName("Test médiane avec nombre impair d'éléments")
    public void testMedianOddNumberOfElements() {
        List<Integer> series = Arrays.asList(5, 2, 8, 1, 9);
        // Trié: [1, 2, 5, 8, 9], médiane = 5
        assertEquals(5.0, quickSelect.median(series), 0.001);
    }
    
    @Test
    @DisplayName("Test médiane avec nombre pair d'éléments")
    public void testMedianEvenNumberOfElements() {
        List<Integer> series = Arrays.asList(1, 2, 3, 4, 5, 6);
        // Médiane = (3 + 4) / 2 = 3.5
        assertEquals(3.5, quickSelect.median(series), 0.001);
    }
    
    @Test
    @DisplayName("Test médiane avec la série de 501 éléments comme dans le code Python")
    public void testMedianLargeSeries() {
        // Générer la même série que le code Python avec seed=0
        List<Integer> series = QuickSelect.generateRandomSeries(501, 1, 25, 0);
        
        // Calculer la médiane avec notre implémentation
        double myMedian = quickSelect.median(series);
        
        // Calculer la médiane de référence en triant
        List<Integer> sorted = new ArrayList<>(series);
        Collections.sort(sorted);
        double expectedMedian = sorted.get(250); // 251ème élément (index 250)
        
        assertEquals(expectedMedian, myMedian, 0.001, 
            "La médiane devrait correspondre au 251ème élément de la série triée");
    }
    
    @Test
    @DisplayName("Test quickselect trouve le 251ème élément")
    public void test251stElement() {
        List<Integer> series = QuickSelect.generateRandomSeries(501, 1, 25, 0);
        
        // Trouver le 251ème élément (index 250)
        double element251 = quickSelect.quickselect(new ArrayList<>(series), 250);
        
        // Vérifier avec la méthode de tri
        List<Integer> sorted = new ArrayList<>(series);
        Collections.sort(sorted);
        double expected = sorted.get(250);
        
        assertEquals(expected, element251, 0.001);
    }
    
    @Test
    @DisplayName("Test médiane avec un seul élément")
    public void testMedianSingleElement() {
        List<Integer> series = Arrays.asList(42);
        assertEquals(42.0, quickSelect.median(series), 0.001);
    }
    
    @Test
    @DisplayName("Test médiane avec deux éléments")
    public void testMedianTwoElements() {
        List<Integer> series = Arrays.asList(10, 20);
        assertEquals(15.0, quickSelect.median(series), 0.001);
    }
    
    @Test
    @DisplayName("Test exception avec série vide")
    public void testEmptySeries() {
        List<Integer> series = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> {
            quickSelect.quickselect(series, 0);
        });
    }
    
    @Test
    @DisplayName("Test exception avec k invalide")
    public void testInvalidK() {
        List<Integer> series = Arrays.asList(1, 2, 3, 4, 5);
        
        assertThrows(IllegalArgumentException.class, () -> {
            quickSelect.quickselect(series, -1);
        }, "k négatif devrait lever une exception");
        
        assertThrows(IllegalArgumentException.class, () -> {
            quickSelect.quickselect(series, 5);
        }, "k >= taille devrait lever une exception");
    }
    
    @Test
    @DisplayName("Test médiane avec tous les éléments identiques")
    public void testMedianAllIdentical() {
        List<Integer> series = Arrays.asList(7, 7, 7, 7, 7);
        assertEquals(7.0, quickSelect.median(series), 0.001);
    }
    
    @Test
    @DisplayName("Test performance avec grande série")
    public void testPerformanceLargeSeries() {
        List<Integer> series = QuickSelect.generateRandomSeries(10000, 1, 1000, 42);
        
        long startTime = System.nanoTime();
        double median = quickSelect.median(series);
        long endTime = System.nanoTime();
        
        long duration = (endTime - startTime) / 1_000_000; // en millisecondes
        
        assertTrue(duration < 1000, "L'opération devrait prendre moins d'une seconde");
        assertTrue(median > 0, "La médiane devrait être positive");
    }
}