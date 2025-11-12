package algo.chap16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Implémentation mémoïsée de l'algorithme du sac à dos
 * en s'inspirant du script Python fourni.
 */
public class KnapsackCalculator {

    private final Map<Key, Entry> memo = new HashMap<>();
    private boolean solved = false;

    /**
     * Résultat lisible d'un calcul de sac à dos.
     *
     * @param items indices des objets retenus
     * @param totalValue valeur totale
     * @param totalWeight poids total
     */
    public record Result(List<Integer> items, int totalValue, int totalWeight) {
    }

    /**
     * Résout le problème du sac à dos en remplissant la table mémo.
     *
     * @param values   valeurs des objets
     * @param weights  poids des objets
     * @param capacity capacité maximale du sac
     * @return meilleur résultat trouvable
     */
    public Result solve(int[] values, int[] weights, int capacity) {
        if (values == null || weights == null) {
            throw new IllegalArgumentException("Les tableaux values et weights ne peuvent pas être nuls");
        }
        if (values.length != weights.length) {
            throw new IllegalArgumentException("values et weights doivent avoir la même longueur");
        }
        if (capacity < 0) {
            throw new IllegalArgumentException("La capacité doit être positive ou nulle");
        }

        memo.clear();
        solved = false;

        int itemCount = weights.length;
        for (int size = 0; size <= capacity; size++) {
            memo.put(new Key(-1, size), new Entry(List.of(), 0, 0));
        }

        for (int item = 0; item < itemCount; item++) {
            for (int size = 0; size <= capacity; size++) {
                Key currentKey = new Key(item, size);
                Entry bestWithoutItem = memo.get(new Key(item - 1, size));

                if (weights[item] > size) {
                    memo.put(currentKey, bestWithoutItem);
                    continue;
                }

                Entry residualEntry = memo.get(new Key(item - 1, size - weights[item]));
                int candidateValue = values[item] + residualEntry.totalValue;

                if (bestWithoutItem.totalValue > candidateValue) {
                    memo.put(currentKey, bestWithoutItem);
                } else {
                    int candidateWeight = weights[item] + residualEntry.totalWeight;
                    memo.put(currentKey, residualEntry.withItemAppended(item, candidateValue, candidateWeight));
                }
            }
        }

        Entry best = itemCount == 0 ? memo.get(new Key(-1, capacity))
                : memo.get(new Key(itemCount - 1, capacity));

        solved = true;
        return best.asResult();
    }

    /**
     * Permet de consulter une cellule de la table mémoïsée après un solve().
     *
     * @param itemIndex index de l'objet (peut être -1 pour la ligne initiale)
     * @param capacity  capacité considérée
     * @return résultat stocké pour cette cellule
     */
    public Result getMemoEntry(int itemIndex, int capacity) {
        if (!solved) {
            throw new IllegalStateException("Aucun calcul n'a encore été exécuté. Appelez solve() d'abord.");
        }
        Key key = new Key(itemIndex, capacity);
        Entry entry = memo.get(key);
        if (entry == null) {
            throw new IllegalArgumentException("Aucun état mémo pour l'index " + itemIndex + " et la capacité " + capacity);
        }
        return entry.asResult();
    }

    private record Key(int itemIndex, int capacity) {
    }

    private static final class Entry {
        private final List<Integer> items;
        private final int totalValue;
        private final int totalWeight;

        private Entry(List<Integer> items, int totalValue, int totalWeight) {
            this.items = Objects.requireNonNull(items);
            this.totalValue = totalValue;
            this.totalWeight = totalWeight;
        }

        private Entry withItemAppended(int itemIndex, int newValue, int newWeight) {
            List<Integer> extended = new ArrayList<>(items);
            extended.add(itemIndex);
            return new Entry(List.copyOf(extended), newValue, newWeight);
        }

        private Result asResult() {
            return new Result(items, totalValue, totalWeight);
        }
    }
}
