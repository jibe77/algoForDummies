package algo.chap18;

import java.util.*;

public class SATSolver {

    private static final Random random = new Random();

    /** Retourne v ou -v aléatoirement */
    public static int signed(int v) {
        return random.nextBoolean() ? v : -v;
    }

    /** Génère i clauses aléatoires avec seed */
    public static List<int[]> createClauses(int i, long seed) {
        Random r = new Random(seed);
        List<int[]> clauses = new ArrayList<>();
        for (int j = 0; j < i; j++) {
            int a = r.nextInt(i);
            int b = r.nextInt(i);
            a = r.nextBoolean() ? a : -a;
            b = r.nextBoolean() ? b : -b;
            clauses.add(new int[]{a, b});
        }
        return clauses;
    }

    /** Génère une solution aléatoire */
    public static Map<Integer, Boolean> createRandomSolution(int n) {
        Map<Integer, Boolean> solution = new HashMap<>();
        for (int i = 0; i < n; i++) {
            solution.put(i, random.nextBoolean());
        }
        return solution;
    }

    /** Méthode intermédiaire compatible avec SolutionStarter (ignore clauses) */
    public static Map<Integer, Boolean> createRandomSolutionStarter(int n, List<int[]> clauses) {
        return createRandomSolution(n);
    }

    /** Vérifie la solution et retourne la liste des clauses non satisfaites */
    public static List<Integer> checkSolution(Map<Integer, Boolean> solution, List<int[]> clauses) {
        List<Integer> violations = new ArrayList<>();
        for (int k = 0; k < clauses.size(); k++) {
            int[] clause = clauses.get(k);
            int a = clause[0], b = clause[1];
            boolean satisfied = (solution.get(Math.abs(a)) == (a > 0)) || 
                                (solution.get(Math.abs(b)) == (b > 0));
            if (!satisfied) {
                violations.add(k);
            }
        }
        return violations;
    }

    /** Algorithme 2-SAT probabiliste */
    public static Pair<List<Integer>, Map<Integer, Boolean>> sat2(
            List<int[]> clauses, int n, SolutionStarter starter) {

        boolean notSolved = true;
        List<Integer> history = new ArrayList<>();
        Map<Integer, Boolean> solution = null;

        int maxExternal = (int) Math.round(Math.log(n) / Math.log(2));

        outer:
        for (int ext = 0; ext < maxExternal; ext++) {
            solution = starter.start(n, clauses);
            for (int intern = 0; intern < 2 * n * n; intern++) {
                List<Integer> unsatisfied = checkSolution(solution, clauses);
                history.add(unsatisfied.size());

                if (unsatisfied.isEmpty()) {
                    System.out.println("Solution in " + (ext + 1) + " external loops, " +
                            (intern + 1) + " internal loops");
                    notSolved = false;
                    break outer;
                } else {
                    int r1 = unsatisfied.get(random.nextInt(unsatisfied.size()));
                    int r2 = random.nextInt(2);
                    int clauseToFix = clauses.get(r1)[r2];
                    solution.put(Math.abs(clauseToFix), clauseToFix > 0);
                }
            }
        }

        if (notSolved) {
            System.out.println("Not solvable");
        }

        return new Pair<>(history, solution);
    }

    /** Starter par défaut : solution aléatoire */
    public interface SolutionStarter {
        Map<Integer, Boolean> start(int n, List<int[]> clauses);
    }

    /** Pair simple */
    public static class Pair<A,B> {
        public final A first;
        public final B second;
        public Pair(A a, B b) { first = a; second = b; }
    }

    /** Example de "better start" similaire au Python */
    public static Map<Integer, Boolean> betterStart(int n, List<int[]> clauses) {
        Map<Integer, Set<Integer>> clauseDict = new HashMap<>();
        for (int[] pair : clauses) {
            for (int clause : pair) {
                int key = Math.abs(clause);
                clauseDict.computeIfAbsent(key, k -> new HashSet<>()).add(clause);
            }
        }

        Map<Integer, Boolean> solution = createRandomSolution(n);
        for (Map.Entry<Integer, Set<Integer>> entry : clauseDict.entrySet()) {
            if (entry.getValue().size() == 1) {
                int val = entry.getValue().iterator().next();
                solution.put(entry.getKey(), val > 0);
            }
        }
        return solution;
    }
}
