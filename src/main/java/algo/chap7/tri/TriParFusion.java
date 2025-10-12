package algo.chap7.tri;

public class TriParFusion {

    /**
     * Décompose le jeu de données en parties séparées et trie ces parties.
     * Ensuite il s'agit de fusionner ces parties triées pour obtenir le jeu de données final trié.
     * @param data
     * @return
     */
    public static int[] triParFusion(int[] data) {
        if (data.length < 2) {
            return data;
        }
        int mid = data.length / 2;
        int[] left = new int[mid];
        System.arraycopy(data, 0, left, 0, mid);
        int[] right = new int[data.length - mid];
        System.arraycopy(data, mid, right, 0, data.length - mid);

        left = triParFusion(left);
        right = triParFusion(right);

        int[] merged = fusion(left, right);
        return merged;
    }

    private static int[] fusion(int[] left, int[] right) {
        if (left.length == 0) {
            return left;
        }
        if (right.length == 0) {
            return right;
        }
        int totalLength = left.length + right.length;
        int[] result = new int[totalLength];
        int resultIndex = 0;
        int leftIndex = 0;
        int rightIndex = 0;
        
        // continue jusqu'à ce que tous les éléments des deux tableaux soient traités
        while (resultIndex < totalLength) {
            // effectue les comparaisons nécessaires et fusionne les parties en fonction des valeurs
            if (left[leftIndex] < right[rightIndex]) {
                result[resultIndex] = left[leftIndex];
                resultIndex++;
                leftIndex++;
            } else {
                result[resultIndex] = right[rightIndex];
                resultIndex++;
                rightIndex++;
            }
            // Quand le côté gauche ou droit est plus long, ajouter au résultat les éléments qui restent
            if ((leftIndex == left.length)) {
                result[resultIndex] = right[rightIndex];
                resultIndex++;
            }
            if ((rightIndex == right.length)) {
                result[resultIndex] = left[leftIndex];
                resultIndex++;
            }
        }
        return result;
    }

}
