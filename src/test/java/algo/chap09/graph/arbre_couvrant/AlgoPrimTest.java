package algo.chap09.graph.arbre_couvrant;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AlgoPrimTest {

    /**
     * 
     */
    @Test
    public void test() {
        AlgoPrim algo = new AlgoPrim();
        /*
            | De | Vers | Poids |
            | -- | ---- | ----- |
            | 0  | 1    | 2     |
            | 0  | 3    | 6     |
            | 1  | 2    | 3     |
            | 1  | 3    | 8     |
            | 1  | 4    | 5     |
            | 2  | 4    | 7     |
            | 3  | 4    | 9     |
         */
        int graph[][] = new int[][] { { 0, 2, 0, 6, 0 },
                                      { 2, 0, 3, 8, 5 },
                                      { 0, 3, 0, 0, 7 },
                                      { 6, 8, 0, 0, 9 },
                                      { 0, 5, 7, 9, 0 } };
        // C’est le résultat final du MST, c’est-à-dire l’ensemble minimal d’arêtes 
        // reliant tous les sommets sans cycle.
        String expected = "Edge 	Weight" + 
                            "0 - 1	2" +
                            "1 - 2	3" +
                            "0 - 3	6" +
                            "1 - 4	5";
        assertEquals(expected, algo.primMST(graph));
    }
}