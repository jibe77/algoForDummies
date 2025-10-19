package algo.chap09.graph.arbre_couvrant;

import java.util.*;

// Implémentation de l'algorithme de Kruskal pour trouver le MST
public class AlgoKruskal {

    // Structure représentant une arête
    static class Edge implements Comparable<Edge> {
        int src, dest, weight;
        public Edge(int src, int dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }

        // Tri des arêtes par poids croissant
        public int compareTo(Edge other) {
            return this.weight - other.weight;
        }

        @Override
        public String toString() {
            return src + " - " + dest + " : " + weight;
        }
    }

    // Classe interne pour représenter un sous-ensemble (Union-Find)
    static class Subset {
        int parent, rank;
    }

    // Trouve la racine (avec compression de chemin)
    private int find(Subset[] subsets, int i) {
        if (subsets[i].parent != i)
            subsets[i].parent = find(subsets, subsets[i].parent);
        return subsets[i].parent;
    }

    // Union de deux sous-ensembles (par rang)
    private void union(Subset[] subsets, int x, int y) {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        if (subsets[xroot].rank < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        else if (subsets[xroot].rank > subsets[yroot].rank)
            subsets[yroot].parent = xroot;
        else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }

    // Fonction principale : construit le MST
    public List<Edge> kruskalMST(int[][] graph) {
        int V = graph.length;
        List<Edge> edges = new ArrayList<>();

        // Construire la liste des arêtes à partir de la matrice d'adjacence
        for (int i = 0; i < V; i++) {
            for (int j = i + 1; j < V; j++) { // éviter les doublons
                if (graph[i][j] != 0)
                    edges.add(new Edge(i, j, graph[i][j]));
            }
        }

        // Trier les arêtes par poids
        Collections.sort(edges);

        // Créer V sous-ensembles pour Union-Find
        Subset[] subsets = new Subset[V];
        for (int v = 0; v < V; v++) {
            subsets[v] = new Subset();
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }

        List<Edge> result = new ArrayList<>();
        int e = 0; // compteur d’arêtes ajoutées

        for (Edge edge : edges) {
            int x = find(subsets, edge.src);
            int y = find(subsets, edge.dest);

            // Si cette arête ne crée pas de cycle, on la prend
            if (x != y) {
                result.add(edge);
                union(subsets, x, y);
                e++;
                // Un MST a exactement V-1 arêtes
                if (e == V - 1)
                    break;
            }
        }

        return result;
    }
}