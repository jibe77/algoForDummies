package algo.chap19;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

class ProductionPlotTest {

@Test
    void testConstraintCalculation() {
        Map<String,Integer> res1 = Map.of("A",2,"B",3,"t",30,"n",2);
        double a = 10;
        double c1 = ((res1.get("t")*res1.get("n")) - res1.get("A")*a) / (double)res1.get("B");
        assertEquals((60-20)/3.0, c1, 1e-6);
    }
}
