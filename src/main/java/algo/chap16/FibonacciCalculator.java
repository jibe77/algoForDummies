package algo.chap16;

/**
 * Calcule la suite de Fibonacci en reproduisant le comportement récursif
 * du script Python fourni, avec un affichage du niveau d'appel.
 */
public class FibonacciCalculator {

    /**
     * Calcule la valeur de Fibonacci pour n.
     *
     * @param n indice (doit être >= 0)
     * @return valeur de Fibonacci correspondante
     */
    public int fib(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n doit être supérieur ou égal à 0");
        }
        return fibWithDepth(n, 0);
    }

    private int fibWithDepth(int n, int depth) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }

        System.out.printf("lvl %d: summing fib(%d) and fib(%d)%n", depth, n - 1, n - 2);
        return fibWithDepth(n - 1, depth + 1) + fibWithDepth(n - 2, depth + 1);
    }

    public static void main(String[] args) {
        FibonacciCalculator calculator = new FibonacciCalculator();
        int n = 7;
        int result = calculator.fib(n);
        System.out.printf("fib(%d) = %d%n", n, result);
    }
}
