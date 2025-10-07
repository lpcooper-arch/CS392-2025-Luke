package MyDeque;

import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.BiPredicate;

public class FnList<T> {
    private Node head;
    private Node tail;
    private int size;

    private class Node {
        T value;
        Node next;
        Node prev;

        Node(T value) {
            this.value = value;
        }
    }

    public FnList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public boolean nilq() {
        return size == 0;
    }

    public boolean consq() {
        return size > 0;
    }

    public T hd() {
        if (head == null) return null;
        return head.value;
    }

    public FnList<T> tl() {
        if (head == null) return null;
        FnList<T> result = new FnList<>();
        Node current = head.next;
        while (current != null) {
            result.append(current.value);
            current = current.next;
        }
        return result;
    }

    public void prepend(T value) {
        Node node = new Node(value);
        if (head == null) head = tail = node;
        else {
            node.next = head;
            head.prev = node;
            head = node;
        }

        size++;
    }

    public void append(T value) {
        Node node = new Node(value);
        if (tail == null) head = tail = node;

        else {
            node.prev = tail;
            tail.next = node;
            tail = node;
        }

        size++;
    }

    public T removeFirst() {
        if (head == null) return null;
        T val = head.value;
        head = head.next;
        if (head != null) head.prev = null;
        else tail = null;
        size--;
        return val;
    }

    public T removeLast() {
        if (tail == null) return null;
        T val = tail.value;
        tail = tail.prev;
        if (tail != null) tail.next = null;
        else head = null;
        size--;
        return val;
    }

    public FnList<T> reverse() {
        FnList<T> rev = new FnList<>();
        Node current = tail;
        while (current != null) {
            rev.append(current.value);
            current = current.prev;
        }
        return rev;
    }

    public void System$out$print() {
        System.out.print("FnList(");
        this.iforitm((i, itm) -> {
            if (i > 0) System.out.print(",");
            System.out.print(itm.toString());
        });
        System.out.println(")");
    }

    public void foritm(Consumer<? super T> action) {
        Node current = head;
        while (current != null) {
            action.accept(current.value);
            current = current.next;
        }
    }

    public void rforitm(Consumer<? super T> action) {
        Node current = tail;
        while (current != null) {
            action.accept(current.value);
            current = current.prev;
        }
    }

    public void iforitm(BiConsumer<Integer, ? super T> action) {
        Node current = head;
        int i = 0;
        while (current != null) {
            action.accept(i++, current.value);
            current = current.next;
        }
    }

    public void irforitm(BiConsumer<Integer, ? super T> action) {
        Node current = tail;
        int i = 0;
        while (current != null) {
            action.accept(i++, current.value);
            current = current.prev;
        }
    }

    public boolean forall(Predicate<? super T> pred) {
        Node current = head;
        while (current != null) {
            if (!pred.test(current.value)) return false;
            current = current.next;
        }
        return true;
    }

    public boolean rforall(Predicate<? super T> pred) {
        Node current = tail;
        while (current != null) {
            if (!pred.test(current.value)) return false;
            current = current.prev;
        }
        return true;
    }

    public boolean iforall(BiPredicate<Integer, ? super T> pred) {
        Node current = head;
        int i = 0;
        while (current != null) {
            if (!pred.test(i++, current.value)) return false;
            current = current.next;
        }
        return true;
    }

    public boolean irforall(BiPredicate<Integer, ? super T> pred) {
        Node current = tail;
        int i = 0;
        while (current != null) {
            if (!pred.test(i++, current.value)) return false;
            current = current.prev;
        }
        return true;
    }
}
