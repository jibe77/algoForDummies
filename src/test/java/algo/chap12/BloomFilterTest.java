package algo.chap12;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BloomFilterTest {

    @Test
    void noFalseNegativesOnInsertedItems() {
        BloomFilter<String> bf = new BloomFilter<>(5_000, 0.01);
        for (int i = 0; i < 5_000; i++) {
            bf.put("key-" + i);
        }
        for (int i = 0; i < 5_000; i++) {
            assertTrue(bf.mightContain("key-" + i),
                    "Un élément inséré ne doit jamais être signalé absent (pas de faux négatif).");
        }
    }

    @Test
    void falsePositiveRateIsReasonable() {
        int n = 10_000;
        double targetFpp = 0.01; // 1%
        BloomFilter<String> bf = new BloomFilter<>(n, targetFpp);

        // Ensemble A : inséré
        for (int i = 0; i < n; i++) {
            bf.put("A-" + i);
        }

        // Ensemble B : disjoint
        int trials = 10_000;
        int falsePositives = 0;
        for (int i = 0; i < trials; i++) {
            if (bf.mightContain("B-" + i)) falsePositives++;
        }
        double observed = falsePositives / (double) trials;

        // On tolère un peu au-dessus de la cible théorique, car aléatoire.
        assertTrue(observed <= 0.03,
                "Taux de faux positifs trop élevé: " + observed);
    }
}
