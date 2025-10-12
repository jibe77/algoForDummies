package algo.chap6;

import java.util.ArrayDeque;

public class StackLIFO {

    private ArrayDeque<Integer> stack;
    private int size;

    public StackLIFO(int size) {
        this.size = size;
        stack = new ArrayDeque<>(size);
    }

    public void displayStack() {
        System.out.println("La pile contient maintenant : " + stack);
    }

    public boolean push(int value) {
        if (stack.size() < size) {
            stack.push(value);
            return true;
        } else {
            System.out.println("La pile est pleine !");
            return false;
        }
    }

    public int pop() {
        if (!stack.isEmpty()) {
            int pop = stack.pop();
            System.out.println("L'élément " + pop + " a été retiré de la pile.");
            return pop;
        } else {
            System.out.println("La pile est vide !");
            return -1;
        }
    }
}
