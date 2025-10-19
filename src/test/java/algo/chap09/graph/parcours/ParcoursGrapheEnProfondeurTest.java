package algo.chap09.graph.parcours;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class ParcoursGrapheEnProfondeurTest {

    @Test
    public void testParcoursGrapheEnProfondeur() {
        // Création d'un graphe simple
        Map<String, List<String>> graphe = Map.of(
            "A", List.of("B", "C"),
            "B", List.of("A", "C", "D"),
            "C", List.of("A", "B", "D", "E"),
            "D", List.of("B", "C", "E", "F"),
            "E", List.of("C", "D", "F"),
            "F", List.of("D", "E")
        );

        System.out.println("Parcours en profondeur à partir du sommet A :");
        List<String> parcours = ParcoursGrapheEnProfondeur.parcoursEnProfondeur(graphe, "A");
        assertIterableEquals(List.of("A>C","C>E", "E>F", "C>D", "A>B"), parcours);
    }
}
