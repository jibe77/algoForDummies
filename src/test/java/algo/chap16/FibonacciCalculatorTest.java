package algo.chap16;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FibonacciCalculatorTest {

    @Test
    void fibOfSevenReturnsThirteen() {
        FibonacciCalculator calculator = new FibonacciCalculator();
        int result = calculator.fib(7);
        assertEquals(13, result, "fib(7) doit être égal à 13");
    }
}
