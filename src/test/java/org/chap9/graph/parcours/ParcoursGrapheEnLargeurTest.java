package org.chap9.graph.parcours;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class ParcoursGrapheEnLargeurTest {

    @Test
    public void testParcoursGrapheEnLargeur() {
        // Création d'un graphe simple
        Map<String, List<String>> graphe = Map.of(
            "A", List.of("B", "C"),
            "B", List.of("A", "C", "D"),
            "C", List.of("A", "B", "D", "E"),
            "D", List.of("B", "C", "E", "F"),
            "E", List.of("C", "D", "F"),
            "F", List.of("D", "E")
        );

        System.out.println("Parcours en largeur à partir du sommet A :");
        List<String> parcours = ParcoursGrapheEnLargeur.parcoursEnLargeur(graphe, "A");
        assertIterableEquals(List.of("A>B","A>C", "B>D", "C>E","D>F"), parcours);
    }
}
