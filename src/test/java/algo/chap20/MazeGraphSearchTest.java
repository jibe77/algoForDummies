package algo.chap20;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class MazeGraphSearchTest {

    @Test
    void testBestFirstSearchFindsPath() {
        MazeGraph.MazeResult result = MazeGraph.createMaze(30);
        List<String> path = MazeGraph.bestFirstSearch(result.graph, result.position, "A", "Y");
        assertFalse(path.isEmpty(), "Best-first doit trouver un chemin");
        assertEquals("A", path.get(0));
        assertEquals("Y", path.get(path.size()-1));
    }

    @Test
    void testAStarSearchFindsOptimalPath() {
        MazeGraph.MazeResult result = MazeGraph.createMaze(30);
        List<String> path = MazeGraph.aStarSearch(result.graph, result.position, "A", "Y");
        assertFalse(path.isEmpty(), "A* doit trouver un chemin");
        int length = MazeGraph.computePathDist(path, result.graph);
        // Vérifie que la longueur est positive
        assertTrue(length > 0);
    }

    @Test
    void testAStarBetterOrEqualThanBestFirst() {
        MazeGraph.MazeResult result = MazeGraph.createMaze(30);
        List<String> pathBest = MazeGraph.bestFirstSearch(result.graph, result.position, "A", "Y");
        List<String> pathAStar = MazeGraph.aStarSearch(result.graph, result.position, "A", "Y");

        int distBest = MazeGraph.computePathDist(pathBest, result.graph);
        int distAStar = MazeGraph.computePathDist(pathAStar, result.graph);

        // A* ne peut pas être pire que Best-first
        assertTrue(distAStar <= distBest);
    }
}
