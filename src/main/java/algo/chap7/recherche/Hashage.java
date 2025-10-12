package algo.chap7.recherche;

import java.util.HashMap;
import java.util.Map;

public class Hashage {

    /**
     * HashSet calcule automatiquement le hashcode de chaque élément (String ici).
     * Lorsqu’on appelle .contains(), Java ne parcourt pas toute la liste : il calcule le même hashcode et va directement à la “case” correspondante → très rapide ⚡ 
     */
    void main() {
        Map<String, Integer> ages = new HashMap<>();

        // Insertion des données
        ages.put("Alice", 30);
        ages.put("Bob", 25);
        ages.put("Charlie", 28);

        // Recherche rapide par clé
        String cle = "Charlie";
        if (ages.containsKey(cle)) {
            IO.println(cle + " a " + ages.get(cle) + " ans.");
        } else {
            IO.println(cle + " n'est pas dans la table.");
        }
    }
}
