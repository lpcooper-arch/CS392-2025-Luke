package MyDeque;

import java.util.function.Consumer;

import Library.MyQueue.MyQueueFullExn;

import java.util.function.BiConsumer;


// This is the solution to Assign04_02

public class MyDequeList<T> implements MyQueue<T> {
    
    private int nitm = 0;
    private FnList<T> frnt = null;
    private FnList<T> rear = null;

    public MyDequeList() {
        frnt = new FnList<>();
        rear = new FnList<>();
    }

    private void fillEmptyFront() { //Helper method for when the front FnList is empty
        if (frnt.nilq()) {
            frnt = rear.reverse();
            rear = new FnList<>();
        }
    }

    private void fillEmptyRear() { //Same as fillEmptyFront, but for the rear instead
        if (rear.nilq()) {
            rear = frnt.reverse();
            frnt = new FnList<>();
        }
    }

    public int size() {
        return nitm;
    }

    public boolean isFull() {
        return false;
    }

    public boolean isEmpty() {
        return frnt.nilq() && rear.nilq();
    }



    public T fpeek$raw() {
        fillEmptyFront();
        return frnt.hd();
    }

    public T fpeek$opt() {
        if (isEmpty()) return null;
        return fpeek$raw();
    }

    public T fpeek$exn() {
        if (isEmpty()) throw new MyQueueEmptyExn();
        return fpeek$raw();
    }



    public T rpeek$raw() {
        fillEmptyRear();
        return rear.hd();
    }

    public T rpeek$opt() {
        if (isEmpty()) return null;
        return rpeek$raw();
    }

    public T  rpeek$exn() {
        if (isEmpty()) throw new MyQueueEmptyExn();
        return rpeek$raw();
    }



    public void fenque$raw(T itm) {
        fillEmptyFront();
        frnt.prepend(itm);
        nitm ++;
    }

    public void fenque$opt(T itm) {
        fenque$raw(itm);
    }

    public boolean fenque$exn(T itm) {
        fenque$raw(itm);
        return true;
    }


    public void renque$raw(T itm) {
        fillEmptyRear();
        rear.prepend(itm);
        nitm ++;
    }

    public void renque$opt(T itm) {
        renque$raw(itm);
    }

    public boolean renque$exn(T itm) {
        renque$raw(itm);
        return true;
    }


    public T fdeque$raw() {
        fillEmptyFront();
        T val = frnt.removeFirst();
        if (val != null) nitm--;
        return val;
    }

    public T fdeque$opt() {
        if (isEmpty()) return null;
        return fdeque$raw();
    }

    public T fdeque$exn() {
        if (isEmpty()) throw new MyQueueEmptyExn();
        return fdeque$raw();
    }


    public T rdeque$raw() {
        fillEmptyRear();
        T val = rear.removeFirst();
        if (val != null) nitm--;
        return val;
    }

    public T rdeque$opt() {
        if (isEmpty()) return null;
        return rdeque$raw();
    }

    public T rdeque$exn() {
        if (isEmpty()) throw new MyQueueEmptyExn();
        return rdeque$raw();
    }



    public T top$raw() {
        return fpeek$raw();
    }

    public T top$opt() {
        return fpeek$opt();
    }

    public T top$exn() {
        return fpeek$raw();
    }


    public T deque$raw() {
        return fdeque$raw();
    }

    public T deque$opt() {
        return fdeque$opt();
    }

    public T deque$exn() {
        return fdeque$exn();
    }


     public void enque$raw(T itm) {
        renque$raw(itm);
    }

    public void enque$exn(T itm) throws MyQueueFullExn {
        renque$raw(itm);
    }

    public boolean enque$opt(T itm) {
        renque$raw(itm);
        return true;
    }


     public void System$out$print() {
        System.out.print("Assign04_02 front: ");
        frnt.System$out$print();
        System.out.print("Assign04_02 rear: ");
        rear.System$out$print();
    }


    public void foritm(Consumer<? super T> action) {
        frnt.foritm(action);
        rear.reverse().foritm(action);
    }

    public void iforitm(BiConsumer<Integer, ? super T> action) {
        frnt.iforitm(action);
        rear.reverse().iforitm(action);
    }

    public void rforitm(Consumer<? super T> action) {
        rear.rforitm(action);
        frnt.reverse().foritm(action);
    }

    public void irforitm(BiConsumer<Integer, ? super T> action) {
        rear.irforitm(action);
        frnt.reverse().iforitm(action);
    }
}