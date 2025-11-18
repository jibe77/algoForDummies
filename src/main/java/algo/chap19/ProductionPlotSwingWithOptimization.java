package algo.chap19;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ProductionPlotSwingWithOptimization extends JPanel {

    private final double[] a, c1, c2, c3, border;
    private final double[][] points;
    private final String[] pointLabels;
    private double xMax, yMax;

    // Optimisation
    private double optA, optB, maxProfit;
    private final int profitA = 3000;
    private final int profitB = 2500;

    public ProductionPlotSwingWithOptimization() {
        // Résultats (contraintes)
        Map<String, Map<String, Integer>> res = new HashMap<>();
        res.put("res_1", Map.of("A",2, "B",3, "t",30, "n",2));
        res.put("res_2", Map.of("A",3, "B",2, "t",30, "n",2));
        res.put("res_3", Map.of("A",3, "B",3, "t",22, "n",3));

        int nPoints = 31;
        a = new double[nPoints];
        c1 = new double[nPoints];
        c2 = new double[nPoints];
        c3 = new double[nPoints];
        border = new double[nPoints];

        for(int i=0;i<nPoints;i++){
            a[i] = i;
            c1[i] = ((res.get("res_1").get("t")*res.get("res_1").get("n"))-
                     res.get("res_1").get("A")*a[i])/(double)res.get("res_1").get("B");
            c2[i] = ((res.get("res_2").get("t")*res.get("res_2").get("n"))-
                     res.get("res_2").get("A")*a[i])/(double)res.get("res_2").get("B");
            c3[i] = ((res.get("res_3").get("t")*res.get("res_3").get("n"))-
                     res.get("res_3").get("A")*a[i])/(double)res.get("res_3").get("B");
            border[i] = Math.min(Math.min(c1[i],c2[i]),c3[i]);
        }

        points = new double[][]{{0,0},{20,0},{0,20},{16,6},{6,16}};
        pointLabels = new String[]{"P0","P1","P2","P3","P4"};

        // Zoom automatique
        xMax = Arrays.stream(a).max().orElse(30);
        yMax = Arrays.stream(border).max().orElse(30);
        for(double[] p: points){
            xMax = Math.max(xMax,p[0]);
            yMax = Math.max(yMax,p[1]);
        }

        // Résolution du problème linéaire simple à 2 variables
        solveLinearProblem(res);

        // Interaction clic sur point
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e){
                for(int i=0;i<points.length;i++){
                    int px = valueToPixelX(points[i][0]);
                    int py = valueToPixelY(points[i][1]);
                    if(Math.abs(e.getX()-px)<6 && Math.abs(e.getY()-py)<6){
                        JOptionPane.showMessageDialog(ProductionPlotSwingWithOptimization.this,
                                "Point "+pointLabels[i]+" : ("+points[i][0]+","+points[i][1]+")");
                    }
                }
            }
        });
    }

    private void solveLinearProblem(Map<String, Map<String,Integer>> res){
        // Brute-force sur toutes les intersections possibles des contraintes pour 2 variables
        List<double[]> candidates = new ArrayList<>();

        // Intersections avec axes
        for(double A=0;A<=xMax;A++){
            // B de chaque contrainte
            double B1 = ((res.get("res_1").get("t")*res.get("res_1").get("n"))-res.get("res_1").get("A")*A)/res.get("res_1").get("B");
            double B2 = ((res.get("res_2").get("t")*res.get("res_2").get("n"))-res.get("res_2").get("A")*A)/res.get("res_2").get("B");
            double B3 = ((res.get("res_3").get("t")*res.get("res_3").get("n"))-res.get("res_3").get("A")*A)/res.get("res_3").get("B");
            double Bmin = Math.min(Math.min(B1,B2),B3);
            if(Bmin>=0) candidates.add(new double[]{A,Bmin});
        }

        // Vérification des points candidats
        maxProfit = -1;
        for(double[] p: candidates){
            double A = p[0], B = p[1];
            if(A<0||B<0) continue;
            // Vérifie toutes les contraintes
            boolean ok = true;
            for(Map<String,Integer> r: res.values()){
                if(r.get("A")*A + r.get("B")*B > r.get("t")*r.get("n")){ ok=false; break;}
            }
            if(ok){
                double profit = profitA*A + profitB*B;
                if(profit>maxProfit){
                    maxProfit = profit;
                    optA = A;
                    optB = B;
                }
            }
        }

        // Affichage résultats
        System.out.printf("Optimal A = %.1f, Optimal B = %.1f\n", optA,optB);
        System.out.printf("Maximum profit = %.1f\n", maxProfit);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        int w = getWidth();
        int h = getHeight();
        int margin = 50;
        int plotWidth = w - 2*margin;
        int plotHeight = h - 2*margin;

        // Fond
        g.setColor(Color.WHITE);
        g.fillRect(0,0,w,h);

        // Grille
        g.setColor(new Color(220,220,220));
        int nGrid = 6;
        for(int i=0;i<=nGrid;i++){
            int x = margin + i*plotWidth/nGrid;
            int y = h - margin - i*plotHeight/nGrid;
            g.drawLine(x, margin, x, h-margin); // vertical
            g.drawLine(margin, y, w-margin, y); // horizontal
        }

        // Axes
        g.setColor(Color.BLACK);
        g.drawLine(margin,h-margin,w-margin,h-margin); // X
        g.drawLine(margin,margin,margin,h-margin);     // Y

        // Échelle X
        for(int i=0;i<=nGrid;i++){
            int x = margin + i*plotWidth/nGrid;
            int val = (int)(i*xMax/nGrid);
            g.drawString(""+val,x-10,h-margin+20);
        }

        // Échelle Y
        for(int i=0;i<=nGrid;i++){
            int y = h - margin - i*plotHeight/nGrid;
            int val = (int)(i*yMax/nGrid);
            g.drawString(""+val,5,y+5);
        }

        g.drawString("Qty Model A", w/2,h-10);
        g.drawString("Qty Model B",5,20);

        // Zone réalisable
        Polygon poly = new Polygon();
        for(int i=0;i<a.length;i++){
            poly.addPoint(valueToPixelX(a[i]), valueToPixelY(border[i]));
        }
        g.setColor(new Color(255,255,0,128));
        g.fillPolygon(poly);

        // Contraintes
        g.setColor(Color.RED); drawLineSeries(g,a,c1);
        g.setColor(Color.BLUE); drawLineSeries(g,a,c2);
        g.setColor(Color.GREEN); drawLineSeries(g,a,c3);

        // Points candidats
        g.setColor(Color.MAGENTA);
        for(int i=0;i<points.length;i++){
            int px = valueToPixelX(points[i][0]);
            int py = valueToPixelY(points[i][1]);
            g.fillOval(px-4, py-4, 8,8);
            g.drawString(pointLabels[i], px+5, py-5);
        }

        // Point optimal
        g.setColor(Color.BLACK);
        int optX = valueToPixelX(optA);
        int optY = valueToPixelY(optB);
        g.fillOval(optX-6,optY-6,12,12);
        g.drawString("OPT",optX+5,optY-5);
    }

    private void drawLineSeries(Graphics g, double[] xVals, double[] yVals){
        for(int i=0;i<xVals.length-1;i++){
            int x1 = valueToPixelX(xVals[i]);
            int y1 = valueToPixelY(yVals[i]);
            int x2 = valueToPixelX(xVals[i+1]);
            int y2 = valueToPixelY(yVals[i+1]);
            g.drawLine(x1,y1,x2,y2);
        }
    }

    private int valueToPixelX(double val){
        int margin=50;
        int plotWidth = getWidth()-2*margin;
        return margin + (int)(val/xMax*plotWidth);
    }

    private int valueToPixelY(double val){
        int margin=50;
        int plotHeight = getHeight()-2*margin;
        return getHeight() - margin - (int)(val/yMax*plotHeight);
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Production Constraints with Optimization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900,600);
        frame.add(new ProductionPlotSwingWithOptimization());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
