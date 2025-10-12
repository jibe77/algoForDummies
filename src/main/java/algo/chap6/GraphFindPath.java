package algo.chap6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GraphFindPath {

    public static List<String> findPath(Map<String, List<String>> graph, String start, String end) {
        return findPath(graph, start, end, new ArrayList<>());
    }

    public static List<String> findPath(Map<String, List<String>> graph, String start, String end, List<String> path) {
        path.add(start);
        if (start.equals(end)) {
            System.out.println("Path found: " + path);
            return path;
        }
        if (!graph.containsKey(start)) {
            return Collections.emptyList();
        }
        for (String node : graph.get(start)) {
            System.out.println("Examen du sommet " + node);
            if (!path.contains(node)) {
                List<String> newPath = findPath(graph, node, end, path);
                if (!newPath.isEmpty()) {
                    return newPath;
                }
            }
        }
        return Collections.emptyList();
    }
}
