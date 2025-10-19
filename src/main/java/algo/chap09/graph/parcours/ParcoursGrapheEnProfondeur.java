package algo.chap09.graph.parcours;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParcoursGrapheEnProfondeur {

    public static List<String> parcoursEnProfondeur(Map<String, List<String>> graphe, String depart) {
    Deque<String> pile = new ArrayDeque<>();
    Map<String, String> parents = new HashMap<>();
    List<String> chemin = new ArrayList<>();

    pile.push(depart);
    parents.put(depart, depart);

    while (!pile.isEmpty()) {
        System.out.println("Stack is: " + pile);
        String sommet = pile.pop();
        System.out.println("Processing " + sommet);

        for (String voisin : graphe.getOrDefault(sommet, List.of())) {
            if (!parents.containsKey(voisin)) {
                parents.put(voisin, sommet);
                pile.push(voisin);
                System.out.println("Adding " + voisin + " to the stack");
            }
        }

        chemin.add(parents.get(sommet) + ">" + sommet);
    }

    // En Python, path[1:] signifie "à partir du 2e élément"
    if (chemin.size() > 1) {
        return chemin.subList(1, chemin.size());
    } else {
        return List.of();
    }
}

}
