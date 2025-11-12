package algo.chap16;

import java.util.Objects;

/**
 * Implémentation de la distance de Levenshtein reprenant la construction
 * matricielle du script Python fourni.
 */
public class LevenshteinDistanceCalculator {

    /**
     * Calcule la distance de Levenshtein entre deux chaînes.
     *
     * @param s1 première chaîne
     * @param s2 seconde chaîne
     * @return distance minimale d'édition
     */
    public int distance(String s1, String s2) {
        Objects.requireNonNull(s1, "s1 ne peut pas être null");
        Objects.requireNonNull(s2, "s2 ne peut pas être null");

        int m = s1.length();
        int n = s2.length();

        int[][] D = new int[m + 1][n + 1];

        for (int j = 0; j <= n; j++) {
            D[0][j] = j;
        }
        for (int i = 1; i <= m; i++) {
            D[i][0] = i;
        }

        for (int j = 1; j <= n; j++) {
            for (int i = 1; i <= m; i++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    D[i][j] = D[i - 1][j - 1];
                } else {
                    int deletion = D[i - 1][j] + 1;
                    int insertion = D[i][j - 1] + 1;
                    int substitution = D[i - 1][j - 1] + 1;
                    D[i][j] = Math.min(Math.min(deletion, insertion), substitution);
                }
            }
        }

        return D[m][n];
    }
}
