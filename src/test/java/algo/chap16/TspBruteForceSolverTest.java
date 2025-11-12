package algo.chap16;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TspBruteForceSolverTest {

    private final int[][] distances = {
            {0, 10, 15, 20},
            {10, 0, 35, 25},
            {15, 35, 0, 30},
            {20, 25, 30, 0}
    };

    @Test
    void findsSameBestRouteAsPythonExample() {
        TspBruteForceSolver solver = new TspBruteForceSolver();
        TspBruteForceSolver.Result result = solver.solve(distances);

        assertEquals(List.of(0, 2, 3, 1, 0), result.path(), "La meilleure route doit être [0, 2, 3, 1, 0]");
        assertEquals(80, result.distance(), "La distance totale doit être 80 kms");
    }

    @Test
    void symmetricRouteCountMatchesPythonPermutationCalculation() {
        long count = TspBruteForceSolver.symmetricRouteCount(13);
        assertEquals(3_113_510_400L, count, "perm(13, 13) / 2 doit valoir 3 113 510 400");
    }
}
