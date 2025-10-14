package org.chap9.graph.file_priorite;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class FileDePriorite {

    private PriorityQueue<Node> queue;
    private HashMap<String, Integer> index;
    List<String> result;

    public FileDePriorite() {
        this.queue = new PriorityQueue<>(Comparator.comparingInt(Node::priority));
        this.index = new HashMap<>();
        this.result = new ArrayList<>();
    }

    static class Node {
        int priority;
        String label;

        Node(int priority, String label) {
            this.priority = priority;
            this.label = label;
        }

        int priority() {
            return priority;
        }

        String label() {
            return label;
        }

        @Override
        public String toString() {
            return "(" + priority + ", " + label + ")";
        }
    }

    private void push(int priority, String label) {
        if (index.containsKey(label)) {
            queue.removeIf(node -> node.label.equals(label));
        }
        queue.add(new Node(priority, label));
        index.put(label, priority);
    }

    private String pop() {
        Node node = queue.poll();
        if (node != null) {
            index.remove(node.label);
            return node.toString();
        }
        return null;
    }

    /**
     * Exécute une séquence d'opérations sous forme de liste de chaînes.
     * Exemple d'entrée :
     * ["push 3 A", "push 1 B", "push 5 C", "pop", "push 2 A", "pop", "pop"]
     *
     * Sortie : ["(1, B)", "(2, A)", "(5, C)"]
     */
    public List<String> processOperations(List<String> operations) {
        result.clear();
        for (String op : operations) {
            String[] parts = op.split("\\s+");
            switch (parts[0]) {
                case "push":
                    int priority = Integer.parseInt(parts[1]);
                    String label = parts[2];
                    this.push(priority, label);
                    break;
                case "pop":
                    String res = this.pop();
                    if (res != null)
                        result.add(res);
                    break;
                default:
                    throw new IllegalArgumentException("Commande inconnue : " + op);
            }
        }

        return result;
    }

}
