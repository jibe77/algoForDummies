package algo.chap6;

public class BinaryTree {

    private String data;
    private BinaryTree left;
    private BinaryTree right;

    public BinaryTree(String data) {
        this.data = data;
    }

    public String traverse() {
        return traverse(this);
    }

    public String traverse(BinaryTree node) {
        if (node.getData() == null) {
            return "--Node is empty--";
        } else {
            System.out.println(node.getData());
        }
    
        if (node.getLeft() != null) {
            traverse(node.getLeft());
        } 
        if (node.getRight() != null) {
            traverse(node.getRight());
        }
    
        return node.getData();
    }

     public BinaryTree(String data, BinaryTree left, BinaryTree right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }

    public String getData() {
        return data;
    }

        public BinaryTree getLeft() {
        return left;
    }

    public void setLeft(BinaryTree left) {
        this.left = left;
    }

    public BinaryTree getRight() {
        return right;
    }

    public void setRight(BinaryTree right) {
        this.right = right;
    }
}
