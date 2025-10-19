package algo.chap10.network_clusters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.jupiter.api.Test;

public class ExplorationAleatoireTest {

    @Test
    void choisirCheminAleatoire_reproduitSelectionPseudoAleatoire() {
        Map<String, List<String>> graph = ExplorationAleatoire.creerGraphe();
        List<List<String>> chemins = ExplorationAleatoire.listerCheminsSimples(graph, "A", "H");

        Random random = new Random(0);
        int expectedIndex = random.nextInt(chemins.size());

        List<String> chemin = ExplorationAleatoire.choisirCheminAleatoire(0);
        assertIterableEquals(chemins.get(expectedIndex), chemin);
    }

    @Test
    void listerCheminsSimples_enumerationCompleteDepuisALaSource() {
        Map<String, List<String>> graph = ExplorationAleatoire.creerGraphe();
        List<List<String>> chemins = ExplorationAleatoire.listerCheminsSimples(graph, "A", "H");

        assertEquals(3, chemins.size(), "Nombre de chemins simples inattendu");
        assertIterableEquals(List.of("A", "B", "C", "D", "E", "G", "H"), chemins.get(0));
        assertIterableEquals(List.of("A", "F", "E", "G", "H"), chemins.get(1));
        assertIterableEquals(List.of("A", "H"), chemins.get(2));
    }
}
