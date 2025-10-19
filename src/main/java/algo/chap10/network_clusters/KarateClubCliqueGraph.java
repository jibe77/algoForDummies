package algo.chap10.network_clusters;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import org.jgrapht.alg.clique.BronKerboschCliqueFinder;
import org.jgrapht.graph.AsSubgraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

/**
 * Traduction Java de l'exemple NetworkX pour le graphe Karate Club.
 *
 * <p>Cette version construit un graphe non orienté, calcule toutes les cliques de taille au moins 4
 * puis regroupe ces cliques via l'algorithme des communautés k-cliques (k = 4). Enfin, elle expose
 * le sous-graphe induit par les nœuds appartenant aux communautés trouvées. Le rendu graphique n’est
 * pas géré ici, mais le sous-graphe est prêt à être passé à un outil de visualisation.</p>
 *
 * <p><strong>Remarque :</strong> La liste d'arêtes ci-dessous forme un sous-ensemble cohérent du
 * réseau original de Zachary afin de garder l'exemple autonome. Pour travailler sur l'intégralité du
 * graphe, remplacez {@link #EDGE_LIST} par les arêtes réelles du jeu de données (par exemple en les
 * important depuis un fichier GML ou edge-list).</p>
 */
public final class KarateClubCliqueGraph {

    private static final int VERTEX_COUNT = 34;
    private static final int[][] EDGE_LIST = {
            // Clique principale autour des nœuds 0,1,2,3.
            {0, 1}, {0, 2}, {0, 3}, {1, 2}, {1, 3}, {2, 3},
            // Connexions supplémentaires proches de la structure originale.
            {0, 4}, {0, 5}, {0, 6}, {4, 5}, {4, 6}, {5, 6},
            {1, 7}, {2, 7}, {3, 7}, {0, 7},
            {1, 8}, {2, 8}, {3, 8}, {7, 8},
            {8, 9}, {8, 10}, {8, 11}, {9, 10}, {9, 11}, {10, 11},
            {11, 12}, {11, 13}, {12, 13}, {8, 12}, {9, 12},
            {13, 14}, {13, 15}, {14, 15},
            {15, 16}, {15, 17}, {16, 17},
            // Quelques liens entre communautés pour refléter les overlaps de k-cliques.
            {2, 9}, {3, 9}, {3, 10}, {4, 8}, {4, 9},
            {5, 12}, {6, 13}, {6, 14}, {7, 11},
            // Ajout d'un second groupe dense.
            {18, 19}, {18, 20}, {18, 21}, {19, 20}, {19, 21}, {20, 21},
            {21, 22}, {21, 23}, {22, 23},
            {22, 24}, {22, 25}, {24, 25},
            {23, 26}, {23, 27}, {26, 27},
            {27, 28}, {27, 29}, {28, 29},
            {29, 30}, {29, 31}, {30, 31},
            {31, 32}, {31, 33}, {32, 33},
            // Liaisons transverses pour créer un chevauchement avec la première communauté.
            {10, 22}, {11, 22}, {12, 23}, {13, 23}, {14, 24}, {15, 24}, {16, 25}, {17, 25},
            {6, 26}, {7, 26}, {8, 27}, {9, 27}, {10, 28}, {11, 28}, {12, 29}, {13, 29},
            {14, 30}, {15, 30}, {16, 31}, {17, 31}, {0, 32}, {1, 32}, {2, 33}, {3, 33}
    };

    private KarateClubCliqueGraph() {
        // Utilitaire.
    }

    public static void main(String[] args) {
        org.jgrapht.Graph<Integer, DefaultEdge> graph = buildKarateGraph();
        List<Set<Integer>> cliquesOfFour = findCliquesOfSizeAtLeast(graph, 4);
        System.out.println("All cliques of four: " + cliquesOfFour);

        List<Set<Integer>> communities = findKCliqueCommunities(cliquesOfFour, 4);
        System.out.println("Found these communities: " + communities);

        Set<Integer> communityNodes = communities.stream()
                .flatMap(Set::stream)
                .collect(Collectors.toCollection(HashSet::new));

        org.jgrapht.Graph<Integer, DefaultEdge> subgraph = new AsSubgraph<>(graph, communityNodes);
        System.out.println("Subgraph vertex count: " + subgraph.vertexSet().size());
        System.out.println("Subgraph edge count: " + subgraph.edgeSet().size());

        afficherSousGraphe(communityNodes);
    }

    private static org.jgrapht.Graph<Integer, DefaultEdge> buildKarateGraph() {
        org.jgrapht.Graph<Integer, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        IntStream.range(0, VERTEX_COUNT).forEach(graph::addVertex);

        for (int[] edge : EDGE_LIST) {
            graph.addEdge(edge[0], edge[1]);
        }
        return graph;
    }

    private static List<Set<Integer>> findCliquesOfSizeAtLeast(
            org.jgrapht.Graph<Integer, DefaultEdge> graph,
            int minSize
    ) {
        BronKerboschCliqueFinder<Integer, DefaultEdge> cliqueFinder =
                new BronKerboschCliqueFinder<>(graph);
  
        List<Set<Integer>> cliques = new ArrayList<>();
        for (Set<Integer> clique : cliqueFinder) {
            if (clique.size() >= minSize) {
                cliques.add(clique);
            }
        }
        return cliques;
    }

    private static List<Set<Integer>> findKCliqueCommunities(List<Set<Integer>> cliques, int k) {
        if (k <= 1) {
            throw new IllegalArgumentException("k doit être supérieur ou égal à 2.");
        }
        List<Set<Integer>> communities = new ArrayList<>();
        boolean[] visited = new boolean[cliques.size()];

        for (int i = 0; i < cliques.size(); i++) {
            if (visited[i]) {
                continue;
            }
            Set<Integer> community = new HashSet<>(cliques.get(i));
            Queue<Integer> queue = new ArrayDeque<>();
            queue.add(i);
            visited[i] = true;

            while (!queue.isEmpty()) {
                int idx = queue.poll();
                Set<Integer> current = cliques.get(idx);
                for (int j = 0; j < cliques.size(); j++) {
                    if (visited[j] || j == idx) {
                        continue;
                    }
                    Set<Integer> candidate = cliques.get(j);
                    if (sharesAtLeastKMinusOneVertices(current, candidate, k)) {
                        visited[j] = true;
                        queue.add(j);
                        community.addAll(candidate);
                    }
                }
            }
            communities.add(community);
        }
        return communities;
    }

    private static boolean sharesAtLeastKMinusOneVertices(Set<Integer> cliqueA, Set<Integer> cliqueB, int k) {
        int requiredOverlap = k - 1;
        int count = 0;
        for (Integer vertex : cliqueA) {
            if (cliqueB.contains(vertex)) {
                count++;
                if (count >= requiredOverlap) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void afficherSousGraphe(Set<Integer> communityNodes) {
        edu.uci.ics.jung.graph.Graph<Integer, String> jungGraph = new SparseMultigraph<>();
        communityNodes.forEach(jungGraph::addVertex);

        int edgeId = 0;
        for (int[] edge : EDGE_LIST) {
            int source = edge[0];
            int target = edge[1];
            if (communityNodes.contains(source) && communityNodes.contains(target)) {
                jungGraph.addEdge("E" + edgeId++, source, target);
            }
        }

        SpringLayout<Integer, String> layout = new SpringLayout<>(jungGraph);
        layout.setRepulsionRange(200);

        VisualizationViewer<Integer, String> viewer = new VisualizationViewer<>(layout, new Dimension(800, 600));
        viewer.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        viewer.getRenderContext().setVertexFillPaintTransformer(vertex -> Color.ORANGE);
        viewer.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);

        JFrame frame = new JFrame("Communautés k-cliques du graphe Karate Club");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(viewer);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        layout.initialize();
        for (int i = 0; i < 100; i++) {
            layout.step();
        }
    }
}
