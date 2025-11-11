package algo.chap16;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FibonacciMemoizedCalculatorTest {

    @Test
    void fibOfSevenReturnsThirteenUsingCache() {
        FibonacciMemoizedCalculator calculator = new FibonacciMemoizedCalculator();
        int firstCall = calculator.fib(7);
        int secondCall = calculator.fib(7); // Doit utiliser le cache

        assertEquals(13, firstCall, "fib(7) doit être égal à 13");
        assertEquals(firstCall, secondCall, "Le cache doit retourner la même valeur sans recalcul");
    }
}
