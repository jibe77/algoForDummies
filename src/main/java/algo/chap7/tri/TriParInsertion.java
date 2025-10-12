package algo.chap7.tri;

public class TriParInsertion {

    /** 
     * Tri par insertion : utilise un élément unique comme point de départ et 
     * ajoute progressivement les autres éléments à la liste triée.
     */
    public static int[] triParInsertion(int[] data) {
        for (int scanIndex = 1; scanIndex < data.length; scanIndex++) {
            int key = data[scanIndex];
            int minIndex = scanIndex - 1;
            while (minIndex >= 0 && data[minIndex] > key) {
                data[minIndex + 1] = data[minIndex];
                minIndex--;
            }
            data[minIndex + 1] = key;
        }
        return data;
    }
}
