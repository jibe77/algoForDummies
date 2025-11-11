package algo.chap16;

import java.util.HashMap;
import java.util.Map;

/**
 * Variante mémoïsée du calcul de Fibonacci qui conserve les valeurs déjà
 * calculées pour éviter de recalculer les sous-résultats.
 */
public class FibonacciMemoizedCalculator {

    private final Map<Integer, Integer> cache = new HashMap<>();

    public FibonacciMemoizedCalculator() {
        cache.put(0, 0);
        cache.put(1, 1);
    }

    public int fib(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n doit être supérieur ou égal à 0");
        }
        return fibWithDepth(n, 0);
    }

    private int fibWithDepth(int n, int depth) {
        Integer cached = cache.get(n);
        if (cached != null) {
            System.out.printf("lvl %d: returning cached fib(%d) = %d%n", depth, n, cached);
            return cached;
        }

        System.out.printf("lvl %d: summing fib(%d) and fib(%d)%n", depth, n - 1, n - 2);
        int value = fibWithDepth(n - 1, depth + 1) + fibWithDepth(n - 2, depth + 1);
        cache.put(n, value);
        return value;
    }

    public static void main(String[] args) {
        FibonacciMemoizedCalculator calculator = new FibonacciMemoizedCalculator();
        int n = 7;
        int result = calculator.fib(n);
        System.out.printf("fib(%d) = %d%n", n, result);
    }
}
