package algo.chap06;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BinaryTreeTest {

    @Test
    public void testBinaryTree() {
        BinaryTree root = new BinaryTree("Root");
        BinaryTree branchA = new BinaryTree("Branch A");
        BinaryTree branchB = new BinaryTree("Branch B");
        root.setLeft(branchA);
        root.setRight(branchB);

        BinaryTree leafC = new BinaryTree("Leaf C");
        BinaryTree leafD = new BinaryTree("Leaf D");
        BinaryTree leafE = new BinaryTree("Leaf E");
        BinaryTree leafF = new BinaryTree("Leaf F");

        branchA.setLeft(leafC);
        branchA.setRight(leafD);
        branchB.setLeft(leafE);
        branchB.setRight(leafF);

        assertEquals("Root", root.traverse());
    }
}
