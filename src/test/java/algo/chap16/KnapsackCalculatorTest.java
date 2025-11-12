package algo.chap16;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KnapsackCalculatorTest {

    private final int[] values = {3, 4, 3, 5, 8, 10};
    private final int[] weights = {2, 3, 4, 4, 5, 9};
    private final int capacity = 20;

    @Test
    void solvesSampleProblemLikePythonVersion() {
        KnapsackCalculator calculator = new KnapsackCalculator();
        KnapsackCalculator.Result result = calculator.solve(values, weights, capacity);

        assertEquals(List.of(0, 3, 4, 5), result.items(), "Le meilleur ensemble doit correspondre à la version Python");
        assertEquals(26, result.totalValue(), "La valeur totale doit être 26");
        assertEquals(20, result.totalWeight(), "Le poids total doit remplir la capacité");
    }

    @Test
    void exposesMemoEntriesForIntermediateStates() {
        KnapsackCalculator calculator = new KnapsackCalculator();
        calculator.solve(values, weights, capacity);
        KnapsackCalculator.Result memoEntry = calculator.getMemoEntry(2, 10);

        assertEquals(List.of(0, 1, 2), memoEntry.items(), "Le mémo (2, 10) doit reprendre les items [0, 1, 2]");
        assertEquals(10, memoEntry.totalValue(), "La valeur totale pour (2, 10) doit être 10");
        assertEquals(9, memoEntry.totalWeight(), "Le poids total pour (2, 10) doit être 9");
    }
}
