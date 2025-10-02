package corejava;

import java.util.ArrayDeque;
import java.util.Deque;

public class StackUsingDeque<T> {


    Deque<T> deque = new ArrayDeque<>();

    public void push(T element) {
        deque.addFirst(element);
    }

    public T pop() {
        if (deque.isEmpty()) {
            return null;
        }
        return deque.removeFirst();
    }

    public T peek() {
        if (deque.isEmpty()) {
            return null;
        }
        return deque.peekFirst();
    }

    public boolean isEmpty() {
        return deque.isEmpty();
    }

    public int size() {
        return deque.size();
    }


    public static void main(String[] args) {
        StackUsingDeque<String> stack = new StackUsingDeque<>();

        stack.push("abc");

        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }


    }
}
