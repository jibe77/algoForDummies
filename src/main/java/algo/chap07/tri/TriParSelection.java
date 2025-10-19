package algo.chap07.tri;

import java.util.Arrays;

public class TriParSelection {

    /**
     * Le programme recherche le plus petit élément de la liste et le place en tête de liste.
     * Il répète l’opération pour le reste de la liste, en ignorant le début déjà trié.
     * @param data
     * @return data trié
     */
    public static int[] triParSelection(int[] data) {
        for (int scanIndex = 0; scanIndex < data.length; scanIndex++) {
            int minIndex = scanIndex;

            // Cherche le plus petit élément dans la partie non triée
            for (int compIndex = scanIndex + 1; compIndex < data.length; compIndex++) {
                if (data[compIndex] < data[minIndex]) {
                    minIndex = compIndex;
                }
            }

            // Si un plus petit élément a été trouvé, on échange
            if (minIndex != scanIndex) {
                int temp = data[scanIndex];
                data[scanIndex] = data[minIndex];
                data[minIndex] = temp;

                // Affiche l’état du tableau après chaque échange
                System.out.println(Arrays.toString(data));
            }
        }
        return data;
    }
}
