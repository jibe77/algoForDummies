package algo.chap7.recherche;

public class BinaryTree {

    static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
            left = null;
            right = null;
        }
    }

    Node root;

    public BinaryTree(int value) {
        root = new Node(value);
    }

    // exemple d’insertion simple
    public void insertLeft(Node parent, int value) {
        parent.left = new Node(value);
    }

    public void insertRight(Node parent, int value) {
        parent.right = new Node(value);
    }

    // affichage en ordre infixe (inorder)
    public void inorder(Node node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.value + " ");
            inorder(node.right);
        }
    }

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree(1);
        tree.insertLeft(tree.root, 2);
        tree.insertRight(tree.root, 3);
        tree.insertLeft(tree.root.left, 4);
        tree.insertRight(tree.root.left, 5);

        System.out.print("Inorder : ");
        tree.inorder(tree.root);
    }
}
