package algo.chap18;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SATSolverGUI {

    public static void main(String[] args) {
        int n = 100; // nombre de variables pour l'exemple
        var clauses = SATSolver.createClauses(n, 0);

        // Exécute l'algorithme 2-SAT
        SATSolver.Pair<List<Integer>, java.util.Map<Integer, Boolean>> result =
                SATSolver.sat2(clauses, n, SATSolver::createRandomSolutionStarter);

        List<Integer> history = result.first;

        // Crée le graphique
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("2-SAT Evolution");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600);
            frame.add(new GraphPanel(history));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    /** Panel pour dessiner l'évolution du nombre de clauses non satisfaites */
    static class GraphPanel extends JPanel {
        private final List<Integer> history;
        private int maxY;

        public GraphPanel(List<Integer> history) {
            this.history = history;
            this.maxY = history.stream().max(Integer::compareTo).orElse(1);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int width = getWidth();
            int height = getHeight();

            // Fond blanc
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);

            // Dessiner la grille
            g.setColor(new Color(220, 220, 220));
            int xSteps = 10;
            int ySteps = 10;
            for (int i = 0; i <= xSteps; i++) {
                int x = 50 + i * (width - 100) / xSteps;
                g.drawLine(x, 50, x, height - 50);
            }
            for (int i = 0; i <= ySteps; i++) {
                int y = 50 + i * (height - 100) / ySteps;
                g.drawLine(50, y, width - 50, y);
            }

            // Axes
            g.setColor(Color.BLACK);
            g.drawLine(50, height - 50, width - 50, height - 50); // X
            g.drawLine(50, 50, 50, height - 50); // Y

            // Échelle X
            for (int i = 0; i <= xSteps; i++) {
                int x = 50 + i * (width - 100) / xSteps;
                int step = i * history.size() / xSteps;
                g.drawString(String.valueOf(step), x - 10, height - 35);
            }

            // Échelle Y
            for (int i = 0; i <= ySteps; i++) {
                int y = 50 + i * (height - 100) / ySteps;
                int val = maxY - i * maxY / ySteps;
                g.drawString(String.valueOf(val), 5, y + 5);
            }

            // Tracer la courbe
            int nPoints = history.size();
            if (nPoints < 2) return;

            double xScale = (double) (width - 100) / (nPoints - 1);
            double yScale = (double) (height - 100) / maxY;

            for (int i = 0; i < nPoints - 1; i++) {
                int x1 = (int) (50 + i * xScale);
                int y1 = (int) ((height - 50) - history.get(i) * yScale);
                int x2 = (int) (50 + (i + 1) * xScale);
                int y2 = (int) ((height - 50) - history.get(i + 1) * yScale);

                // Vert si solution trouvée (0 clauses non satisfaites)
                if (history.get(i + 1) == 0) g.setColor(Color.GREEN);
                else g.setColor(Color.BLUE);

                g.drawLine(x1, y1, x2, y2);
            }

            // Labels axes
            g.setColor(Color.BLACK);
            g.drawString("Steps", width / 2 - 30, height - 10);
            g.drawString("Unsatisfied Clauses", 5, 20);
        }
    }
}
