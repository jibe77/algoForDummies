package algo.chap07.tri;

import java.util.Arrays;

/**
 * Implémente le tri rapide (QuickSort) sur un tableau int[]. 
 * Il choisit un pivot, met tous les nombres plus petits à gauche 
 * et tous les nombres plus grands à droite, puis trie récursivement 
 * les sous-tableaux.
 */
public class TriRapide {

    public static int[] triRapide(int[] data) {
        triRapide(data, 0, data.length - 1);
        return data;
    }   

    private static int[] triRapide(int[] data, int left, int right) {
        if (right <= left) {
            return data;
        } else {
            int pivotIndex = partition(data, left, right);
            triRapide(data, left, pivotIndex - 1);
            triRapide(data, pivotIndex + 1, right);
            return data;
        }
    }

    /**
     * Réorganiser les éléments entre left et right autour du pivot (ici pivot = data[left]) et renvoyer la position finale du pivot.
     * @param data
     * @param left
     * @param right
     * @return
     */
    private static int partition(int[] data, int left, int right) {
        int pivot = data[left];
        int lIndex = left + 1;
        int rIndex = right;

        while (true) {
            // avancer lIndex tant qu'on est dans les limites et que data[lIndex] <= pivot
            while (lIndex <= rIndex && lIndex <= right && data[lIndex] <= pivot) {
                lIndex++;
            }
            // reculer rIndex tant qu'on est dans les limites et que data[rIndex] >= pivot
            while (rIndex >= lIndex && rIndex >= left && data[rIndex] >= pivot) {
                rIndex--;
            }
            // si les indices se croisent, sortir
            if (rIndex <= lIndex) {
                break;
            }
            // échanger les éléments aux indices lIndex et rIndex
            swap(data, lIndex, rIndex);
            System.out.println(Arrays.toString(data));
        }

        // placer le pivot à sa position finale
        swap(data, left, rIndex);
        System.out.println(Arrays.toString(data));
        return rIndex;
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }


}
