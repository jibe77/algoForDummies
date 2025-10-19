package algo.chap10.network_clusters;

import java.awt.Color;
import java.awt.Dimension;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;

/**
 * Traduction Java du petit exemple NetworkX illustrant les degrés de séparation.
 *
 * <p>Le programme construit un graphe orienté en reprenant les arêtes définies dans le dictionnaire
 * Python, applique un layout de type ressort, puis affiche le résultat dans une fenêtre Swing avec
 * une mise en forme similaire (nœuds bleus, labels centrés).</p>
 */
public final class DegresDeSeparation {

    private DegresDeSeparation() {
        // Utilitaire.
    }

    public static void main(String[] args) {
        DirectedSparseMultigraph<String, String> graph = creerGraphe();
        afficher(graph);
    }

    private static DirectedSparseMultigraph<String, String> creerGraphe() {
        Map<String, List<String>> data = new LinkedHashMap<>();
        data.put("A", List.of("B", "F", "H"));
        data.put("B", List.of("A", "C"));
        data.put("C", List.of("B", "D"));
        data.put("D", List.of("C", "E"));
        data.put("E", List.of("D", "F", "G"));
        data.put("F", List.of("E", "A"));
        data.put("G", List.of("E", "H"));
        data.put("H", List.of("G", "A"));

        DirectedSparseMultigraph<String, String> graph = new DirectedSparseMultigraph<>();

        for (String source : data.keySet()) {
            graph.addVertex(source);
        }

        int edgeId = 0;
        for (Map.Entry<String, List<String>> entry : data.entrySet()) {
            String source = entry.getKey();
            for (String target : entry.getValue()) {
                graph.addEdge("E" + edgeId++, source, target);
            }
        }

        return graph;
    }

    private static void afficher(DirectedSparseMultigraph<String, String> graph) {
        SpringLayout<String, String> layout = new SpringLayout<>(graph);
        layout.setRepulsionRange(200);

        VisualizationViewer<String, String> viewer = new VisualizationViewer<>(layout, new Dimension(800, 600));
        viewer.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        viewer.getRenderContext().setVertexFillPaintTransformer(vertex -> Color.CYAN);
        viewer.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);
        viewer.setBackground(Color.WHITE);

        JFrame frame = new JFrame("Degrés de séparation");
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
