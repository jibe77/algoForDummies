package algo.chap10.network_clusters;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.LinkedHashMap;

/**
 * Échantillonnage aléatoire d'un chemin entre deux sommets, inspiré de l'exemple NetworkX.
 *
 * <p>On utilise la même structure de graphe orienté que dans {@link DegresDeSeparation} : chaque
 * entrée de la map correspond aux arcs sortants d'un sommet. L'exploration énumère tous les chemins
 * simples entre A et H, puis en sélectionne un pseudo-aléatoirement en reproduisant la graine du
 * script Python (seed = 0).</p>
 */
public final class ExplorationAleatoire {

    private ExplorationAleatoire() {
        // Utilitaire.
    }

    public static void main(String[] args) {
        List<String> cheminSelectionne = choisirCheminAleatoire(0);
        System.out.println("The selected path is: " + cheminSelectionne);
    }

    public static List<String> choisirCheminAleatoire(long seed) {
        Map<String, List<String>> graph = creerGraphe();
        List<List<String>> chemins = listerCheminsSimples(graph, "A", "H");
        Random random = new Random(seed);
        int index = random.nextInt(chemins.size());

        for (List<String> chemin : chemins) {
            System.out.println("Path Candidate: " + chemin);
        }

        return chemins.get(index);
    }

    static Map<String, List<String>> creerGraphe() {
        Map<String, List<String>> data = new LinkedHashMap<>();
        data.put("A", List.of("B", "F", "H"));
        data.put("B", List.of("A", "C"));
        data.put("C", List.of("B", "D"));
        data.put("D", List.of("C", "E"));
        data.put("E", List.of("D", "F", "G"));
        data.put("F", List.of("E", "A"));
        data.put("G", List.of("E", "H"));
        data.put("H", List.of("G", "A"));
        return data;
    }

    static List<List<String>> listerCheminsSimples(Map<String, List<String>> graph, String source, String cible) {
        List<List<String>> chemins = new ArrayList<>();
        dfs(graph, source, cible, new ArrayList<>(), new HashSet<>(), chemins);
        return chemins;
    }

    private static void dfs(
            Map<String, List<String>> graph,
            String courant,
            String cible,
            List<String> cheminActuel,
            Set<String> visites,
            List<List<String>> chemins
    ) {
        cheminActuel.add(courant);
        visites.add(courant);

        if (courant.equals(cible)) {
            chemins.add(new ArrayList<>(cheminActuel));
        } else {
            for (String voisin : graph.getOrDefault(courant, List.of())) {
                if (!visites.contains(voisin)) {
                    dfs(graph, voisin, cible, cheminActuel, visites, chemins);
                }
            }
        }

        cheminActuel.remove(cheminActuel.size() - 1);
        visites.remove(courant);
    }
}
