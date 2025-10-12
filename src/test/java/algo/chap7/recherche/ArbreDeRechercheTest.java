package algo.chap7.recherche;

import org.junit.jupiter.api.Test;

public class ArbreDeRechercheTest {

    @Test
    public void testArbreDeRecherche() {
        ArbreDeRechercheBinaire arbre = new ArbreDeRechercheBinaire();

        int[] values = {8, 3, 10, 1, 6, 4, 7, 14, 13};
        for (int v : values) {
            arbre.insert(v);
        }

        System.out.println("Arbre binaire de recherche :");
        arbre.printTree();

        System.out.print("\nParcours infixe (valeurs triées) : ");
        arbre.printInOrder();

        System.out.println("\nContient 7 ? " + arbre.contains(7));
        System.out.println("Contient 5 ? " + arbre.contains(5));

        arbre.delete(3);
        System.out.println("\nAprès suppression du 3 :");
        arbre.printTree();
    }
}
