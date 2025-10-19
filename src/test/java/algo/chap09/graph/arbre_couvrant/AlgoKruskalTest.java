package algo.chap09.graph.arbre_couvrant;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

import org.junit.jupiter.api.Test;

public class AlgoKruskalTest {

    @Test
    public void testKruskalMST() {
        AlgoKruskal algo = new AlgoKruskal();

        int[][] graph = {
            {0, 2, 0, 6, 0},
            {2, 0, 3, 8, 5},
            {0, 3, 0, 0, 7},
            {6, 8, 0, 0, 9},
            {0, 5, 7, 9, 0}
        };

        List<AlgoKruskal.Edge> mst = algo.kruskalMST(graph);

        // Vérification du nombre d’arêtes
        assertEquals(4, mst.size(), "Un MST doit avoir V-1 arêtes");

        // Poids total attendu du MST : 2 + 3 + 5 + 6 = 16
        int totalWeight = mst.stream().mapToInt(e -> e.weight).sum();
        assertEquals(16, totalWeight, "Le poids total du MST doit être 16");

        // Vérification du contenu des arêtes (dans n’importe quel ordre)
        Set<String> expectedEdges = Set.of("0-1:2", "1-2:3", "1-4:5", "0-3:6");
        Set<String> resultEdges = new HashSet<>();
        for (AlgoKruskal.Edge e : mst)
            resultEdges.add(e.src + "-" + e.dest + ":" + e.weight);

        assertEquals(expectedEdges, resultEdges);
    }
}
