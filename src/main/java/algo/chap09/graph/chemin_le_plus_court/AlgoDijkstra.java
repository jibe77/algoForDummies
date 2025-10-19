package algo.chap09.graph.chemin_le_plus_court;

import java.util.*;

/**
 * Implémentation de l'algorithme de Dijkstra pour un graphe pondéré
 * à poids non négatifs.
 * 
 *  On part de la source, distance 0.
 *  On propage progressivement les distances les plus courtes connues.
 *  On garde toujours dans une file de priorité (tas) le sommet le plus prometteur (le plus proche connu).
 *  On met à jour les voisins si on trouve un meilleur chemin (on appelle ça une relaxation).
 */
public class AlgoDijkstra {

    public static class Result {
        public final Map<String, Double> dist;
        public final Map<String, String> parent;

        public Result(Map<String, Double> dist, Map<String, String> parent) {
            this.dist = dist;
            this.parent = parent;
        }

        public List<String> getPathTo(String target) {
            List<String> path = new ArrayList<>();
            String current = target;
            while (current != null) {
                path.add(current);
                current = parent.get(current);
            }
            Collections.reverse(path);
            return path;
        }
    }

    /**
     * Calcule les plus courts chemins depuis un sommet source.
     * @param graph Graphe représenté comme Map<noeud, List<voisin, poids>>
     * @param source Sommet de départ
     * @return un objet Result contenant les distances et les parents
     */
    public static Result compute(Map<String, List<Edge>> graph, String source) {
        Map<String, Double> dist = new HashMap<>();
        Map<String, String> parent = new HashMap<>();
        PriorityQueue<Node> heap = new PriorityQueue<>(Comparator.comparingDouble(n -> n.distance));

        for (String v : graph.keySet()) {
            dist.put(v, Double.POSITIVE_INFINITY);
            parent.put(v, null);
        }
        dist.put(source, 0.0);
        heap.add(new Node(source, 0.0));

        while (!heap.isEmpty()) {
            Node node = heap.poll();
            String u = node.name;
            double d = node.distance;

            if (d > dist.get(u)) continue; // entrée obsolète

            for (Edge e : graph.getOrDefault(u, List.of())) {
                if (e.weight < 0) {
                    throw new IllegalArgumentException("Dijkstra ne supporte pas les poids négatifs");
                }
                double newDist = dist.get(u) + e.weight;
                if (newDist < dist.get(e.target)) {
                    dist.put(e.target, newDist);
                    parent.put(e.target, u);
                    heap.add(new Node(e.target, newDist));
                }
            }
        }

        return new Result(dist, parent);
    }

    /** Représente une arête sortante. */
    public static class Edge {
        public final String target;
        public final double weight;

        public Edge(String target, double weight) {
            this.target = target;
            this.weight = weight;
        }
    }

    /** Élément du tas (noeud + distance courante). */
    private static class Node {
        public final String name;
        public final double distance;

        public Node(String name, double distance) {
            this.name = name;
            this.distance = distance;
        }
    }
}