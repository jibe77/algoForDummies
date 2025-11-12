package algo.chap16;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TspDynamicProgrammingSolverTest {

    private final int[][] distances = {
            {0, 10, 15, 20},
            {10, 0, 35, 25},
            {15, 35, 0, 30},
            {20, 25, 30, 0}
    };

    @Test
    void matchesBruteForceResultOnReferenceMatrix() {
        TspDynamicProgrammingSolver solver = new TspDynamicProgrammingSolver();
        TspDynamicProgrammingSolver.Result result = solver.solve(distances);

        assertEquals(List.of(0, 2, 3, 1, 0), result.path(), "Le chemin optimal doit correspondre à la version dynamique Python");
        assertEquals(80, result.distance(), "La distance totale doit être 80 kms");
    }

    @Test
    void handlesTwoCityCase() {
        int[][] simple = {
                {0, 7},
                {7, 0}
        };
        TspDynamicProgrammingSolver solver = new TspDynamicProgrammingSolver();
        TspDynamicProgrammingSolver.Result result = solver.solve(simple);

        assertEquals(List.of(0, 1, 0), result.path(), "Avec deux villes le trajet doit être 0 -> 1 -> 0");
        assertEquals(14, result.distance(), "La distance totale doit doubler l'aller simple");
    }
}
