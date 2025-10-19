package algo.chap09.graph.parcours;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class ParcoursGrapheEnLargeur {

    /**
     * Parcours en largeur (Breadth-First Search, BFS) d’un graphe non pondéré.
     * 
     * L’idée est d’explorer tous les voisins d’un nœud avant de passer aux nœuds suivants.
     * 
     * On utilise une file (Queue) pour gérer les nœuds à visiter, et un ensemble (Set) pour éviter de revisiter les mêmes nœuds.
     * 
     * @param graphe Représenté par une Map où chaque clé est un nœud et la valeur est la liste de ses voisins.
     * @param depart Le nœud de départ pour le parcours.
     * @return La liste des nœuds visités dans l'ordre du parcours en largeur.
     */
    public static List<String> parcoursEnLargeur(Map<String, List<String>> graphe, String depart) {
        Queue<String> file = new LinkedList<>();
        Set<String> enFile = new HashSet<>();
        List<String> chemin = new ArrayList<>();

        file.add(depart);
        enFile.add(depart);

        while (!file.isEmpty()) {
            System.out.println("Queue is: " + file);
            String sommet = file.poll();
            System.out.println("Processing " + sommet);

            for (String voisin : graphe.getOrDefault(sommet, List.of())) {
                if (!enFile.contains(voisin)) {
                    enFile.add(voisin);
                    file.add(voisin);
                    chemin.add(sommet + ">" + voisin);
                    System.out.println("Adding " + voisin + " to the queue");
                }
            }
        }

        return chemin;
    }

}
