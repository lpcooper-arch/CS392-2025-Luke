package MyFinalLib.MyQueue;

import java.util.function.Consumer;
import java.util.function.BiConsumer;


import MyFinalLib.FnList.*;

public class MyQueueTwoList<T> extends MyQueueBase<T> {

    int nitm = -1;
    FnList<T> frnt = null;
    FnList<T> rear = null;

    public MyQueueTwoList() {
	nitm = 0;
	frnt = new FnList<T>();
	rear = new FnList<T>();
    }

    public int size() {
	return nitm;
    }

    public boolean isFull() {
	return false;
    }

    public T top$raw() {
        fillEmptyFront();
        return frnt.hd();
    }

    public T deque$raw() {
        fillEmptyFront();
        T temp = frnt.hd();
        frnt = frnt.tl();
        return temp;
    }

    public void enque$raw(T itm) {
        rear = new FnList<T>(itm, rear);
    }

    public boolean isEmpty() {
        return frnt.nilq() && rear.nilq();
    }

    private void fillEmptyFront() { // If frnt is empty, rear is moved into frnt and is replaced with an empty FnList
        if (frnt.nilq()) {
            frnt = rear.reverse();
            rear = new FnList<T>();
        }
    }

    public void foritm(Consumer<? super T> action) {
      frnt.foritm(action);
      rear.reverse().foritm(action);
    }

    public void iforitm(BiConsumer<Integer, ? super T> action) {
      frnt.iforitm(action);
      final int[] index = {frnt.length()}; // changed to frnt.length() to fit with the FnList implementation in MyFinalLib.FnList

      rear.reverse().foritm(item -> action.accept(index[0]++, item));
    }
    
    
    public void rforitm(Consumer<? super T> action) {
      rear.foritm(action);
      frnt.reverse().foritm(action);
    }

    public void irforitm(BiConsumer<Integer, ? super T> action) {
      rear.iforitm(action);
      final int[] index = {rear.length()}; // changed to rear.length() to fit with the FnList implementation in MyFinalLib.FnList

      frnt.reverse().foritm(item -> action.accept(index[0]++, item));

    }
}

// taken from the two-list implementation of a queue created in Assign03_03 (and modified in Assign04_01)
