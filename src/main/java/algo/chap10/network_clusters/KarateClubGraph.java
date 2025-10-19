package algo.chap10.network_clusters;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;

/**
 * Visualisation simplifiée du réseau de Zachary avec la pile JUNG.
 *
 * <p>La classe instancie les 34 membres du club, ajoute un sous-ensemble représentatif d’arêtes,
 * applique un layout à ressort pour positionner les sommets puis ouvre une fenêtre Swing affichant
 * le graphe avec labels centrés et nœuds cyan, à la manière de l’exemple NetworkX.</p>
 */
public class KarateClubGraph {
    public static void main(String[] args) {
        // Create a graph
        Graph<Integer, String> graph = new SparseMultigraph<>();
        
        // Add vertices (34 members in the karate club)
        for (int i = 0; i < 34; i++) {
            graph.addVertex(i);
        }
        
        // Add edges based on the Zachary's Karate Club dataset
        // This is a simplified version - in a real implementation you would add all edges
        addEdge(graph, 0, 1);
        addEdge(graph, 0, 2);
        addEdge(graph, 0, 3);
        addEdge(graph, 0, 4);
        addEdge(graph, 0, 5);
        addEdge(graph, 0, 6);
        addEdge(graph, 0, 7);
        addEdge(graph, 0, 8);
        addEdge(graph, 0, 10);
        addEdge(graph, 0, 11);
        addEdge(graph, 0, 12);
        addEdge(graph, 0, 13);
        addEdge(graph, 0, 17);
        addEdge(graph, 0, 19);
        addEdge(graph, 0, 21);
        addEdge(graph, 0, 31);
        addEdge(graph, 1, 2);
        addEdge(graph, 1, 3);
        addEdge(graph, 1, 7);
        addEdge(graph, 1, 13);
        addEdge(graph, 1, 17);
        addEdge(graph, 1, 19);
        addEdge(graph, 1, 21);
        addEdge(graph, 1, 30);
        // Add more edges to complete the karate club graph
        // ...
        
        // Create a spring layout
        SpringLayout<Integer, String> layout = new SpringLayout<>(graph);
        layout.setRepulsionRange(200); // Similar to k parameter in NetworkX
        
        // Create a visualization viewer
        VisualizationViewer<Integer, String> vv = new VisualizationViewer<>(layout, new Dimension(800, 600));
        
        // Customize the visualization
        vv.getRenderContext().setVertexLabelTransformer(
            new ToStringLabeller()
        );
        vv.getRenderContext().setVertexFillPaintTransformer(vertex -> Color.CYAN); // Skyblue equivalent
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);
        
        // Create a frame to hold the visualization
        JFrame frame = new JFrame("Zachary's Karate Club Graph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        // Run the layout algorithm
        layout.initialize();
        for (int i = 0; i < 100; i++) {
            layout.step();
        }
    }
    
    private static void addEdge(Graph<Integer, String> graph, int source, int target) {
        graph.addEdge("Edge-" + source + "-" + target, source, target);
    }
}
