package algo.chap15;

import java.util.ArrayList;
import java.util.List;

public class ChangeCalculator {

    /**
     * Calcule la liste des billets nécessaires pour un montant donné.
     *
     * @param amount montant à rendre
     * @param denominations liste des billets disponibles (doit être triée en ordre décroissant)
     * @return une liste des billets utilisés
     */
    public static List<Integer> change(int amount, int[] denominations) {
        List<Integer> resultingChange = new ArrayList<>();

        for (int bill : denominations) {
            while (amount >= bill) {
                resultingChange.add(bill);
                amount -= bill;
            }
        }
        return resultingChange;
    }

    public static void main(String[] args) {
        int[] currency = {100, 50, 20, 10, 5, 1};
        int amount = 367;
        List<Integer> result = change(amount, currency);
        System.out.printf("Change: %s (using %d bills)%n", result, result.size());
    }
}
