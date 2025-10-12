package algo.chap7.recherche;

/**

    Un arbre de recherche binaire est une structure de données hiérarchique dans laquelle :
        - chaque nœud contient une valeur (ex. un nombre) ;
        - chaque nœud a au plus deux enfants :
        - un enfant gauche ;
        - un enfant droit ;
    la structure respecte une règle d’ordre :
        - Toutes les valeurs du sous-arbre gauche < valeur du nœud
        - Toutes les valeurs du sous-arbre droit > valeur du nœud
 */
public class ArbreDeRechercheBinaire {

    // Classe interne pour représenter un nœud
    static class Node {
        int value;
        Node left, right;

        Node(int value) {
            this.value = value;
        }
    }

    private Node root;

    // Insertion récursive d’un élément dans le BST
    public void insert(int value) {
        root = insertRecursive(root, value);
    }

    private Node insertRecursive(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }

        if (value < node.value) {
            node.left = insertRecursive(node.left, value);
        } else if (value > node.value) {
            node.right = insertRecursive(node.right, value);
        }

        return node;
    }

    /** Pour trouver une valeur :
        on compare avec la racine ;
        si la valeur est plus petite → on descend à gauche ;
        si elle est plus grande → on descend à droite ;
        si on la trouve → bingo ✅ ;
        si on atteint null → elle n’existe pas ❌.
    */
    public boolean contains(int value) {
        return containsRecursive(root, value);
    }

    private boolean containsRecursive(Node node, int value) {
        if (node == null) return false;
        if (value == node.value) return true;
        return value < node.value
                ? containsRecursive(node.left, value)
                : containsRecursive(node.right, value);
    }

    // Suppression d’un élément
    public void delete(int value) {
        root = deleteRecursive(root, value);
    }

    private Node deleteRecursive(Node node, int value) {
        if (node == null) return null;

        if (value < node.value) {
            node.left = deleteRecursive(node.left, value);
        } else if (value > node.value) {
            node.right = deleteRecursive(node.right, value);
        } else {
            // cas : un ou aucun enfant
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            // cas : deux enfants → remplace par le plus petit du sous-arbre droit
            node.value = findMin(node.right);
            node.right = deleteRecursive(node.right, node.value);
        }
        return node;
    }

    private int findMin(Node node) {
        return node.left == null ? node.value : findMin(node.left);
    }

    /**
     * Pour lire les valeurs :
        In-Order (gauche → racine → droite) → affiche les valeurs triées
        Pre-Order (racine → gauche → droite) → affiche la structure
        Post-Order (gauche → droite → racine) → utile pour supprimer tout l’arbre
     */
    public void printInOrder() {
        printInOrderRecursive(root);
        System.out.println();
    }

    private void printInOrderRecursive(Node node) {
        if (node != null) {
            printInOrderRecursive(node.left);
            System.out.print(node.value + " ");
            printInOrderRecursive(node.right);
        }
    }

    // Affichage visuel (comme un arbre)
    public void printTree() {
        printTreeRecursive(root, "", false);
    }

    private void printTreeRecursive(Node node, String prefix, boolean isLeft) {
        if (node != null) {
            System.out.println(prefix + (isLeft ? "├── " : "└── ") + node.value);
            printTreeRecursive(node.left, prefix + (isLeft ? "│   " : "    "), true);
            printTreeRecursive(node.right, prefix + (isLeft ? "│   " : "    "), false);
        }
    }
}
