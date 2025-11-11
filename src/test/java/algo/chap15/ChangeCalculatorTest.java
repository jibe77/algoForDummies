package algo.chap15;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ChangeCalculatorTest {

    @Test
    void testBasicChange() {
        int[] currency = {100, 50, 20, 10, 5, 1};
        List<Integer> result = ChangeCalculator.change(367, currency);

        // Vérifie le nombre de billets
        assertEquals(8, result.size(), "Le nombre de billets devrait être 8");

        // Vérifie la composition
        List<Integer> expected = List.of(100, 100, 100, 50, 10, 5, 1, 1);
        assertEquals(expected, result, "La composition des billets est incorrecte");
    }

    @Test
    void testExactBill() {
        int[] currency = {100, 50, 20, 10, 5, 1};
        List<Integer> result = ChangeCalculator.change(100, currency);
        assertEquals(List.of(100), result);
    }

    @Test
    void testZeroAmount() {
        int[] currency = {100, 50, 20, 10, 5, 1};
        List<Integer> result = ChangeCalculator.change(0, currency);
        assertTrue(result.isEmpty(), "Aucun billet ne doit être utilisé pour un montant nul");
    }

    @Test
    void testSmallDenomination() {
        int[] currency = {5, 2, 1};
        List<Integer> result = ChangeCalculator.change(7, currency);
        List<Integer> expected = List.of(5, 2);
        assertEquals(expected, result);
    }
}
