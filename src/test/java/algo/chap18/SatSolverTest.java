package algo.chap18;

import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SATSolverTest {

    @Test
    void testSigned() {
        int val = SATSolver.signed(5);
        assertTrue(val == 5 || val == -5);
    }

    @Test
    void testCreateClauses() {
        List<int[]> clauses = SATSolver.createClauses(10, 42);
        assertEquals(10, clauses.size());
        for (int[] c : clauses) {
            assertEquals(2, c.length);
        }
    }

    @Test
    void testCreateRandomSolution() {
        Map<Integer, Boolean> sol = SATSolver.createRandomSolution(10);
        assertEquals(10, sol.size());
    }

    @Test
    void testCheckSolution() {
        List<int[]> clauses = Arrays.asList(new int[]{0, 1}, new int[]{-1, 2});
        Map<Integer, Boolean> sol = new HashMap<>();
        sol.put(0, true);
        sol.put(1, false);
        sol.put(2, true);
        List<Integer> violations = SATSolver.checkSolution(sol, clauses);
        assertEquals(1, violations.size());
    }

    @Test
    void testSat2() {
        List<int[]> clauses = SATSolver.createClauses(5, 1);
        SATSolver.Pair<List<Integer>, Map<Integer, Boolean>> result =
                SATSolver.sat2(clauses, 5, SATSolver::createRandomSolutionStarter);
        assertNotNull(result.first);
        assertNotNull(result.second);
    }

    @Test
    void testBetterStart() {
        List<int[]> clauses = SATSolver.createClauses(5, 1);
        Map<Integer, Boolean> sol = SATSolver.betterStart(5, clauses);
        assertEquals(5, sol.size());
    }
}
