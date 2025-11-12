package algo.chap16;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LevenshteinDistanceCalculatorTest {

    @Test
    void saturdayVsSundayMatchesPythonExample() {
        LevenshteinDistanceCalculator calculator = new LevenshteinDistanceCalculator();
        int distance = calculator.distance("Saturday", "Sunday");
        assertEquals(3, distance, "La distance entre Saturday et Sunday doit être 3");
    }

    @Test
    void identicalStringsHaveZeroDistance() {
        LevenshteinDistanceCalculator calculator = new LevenshteinDistanceCalculator();
        assertEquals(0, calculator.distance("algo", "algo"), "Deux chaînes identiques ont une distance nulle");
    }

    @Test
    void emptyStringDistanceEqualsLength() {
        LevenshteinDistanceCalculator calculator = new LevenshteinDistanceCalculator();
        assertEquals(5, calculator.distance("", "tests"), "Insérer cinq caractères doit coûter 5 opérations");
        assertEquals(4, calculator.distance("code", ""), "Supprimer quatre caractères doit coûter 4 opérations");
    }
}
