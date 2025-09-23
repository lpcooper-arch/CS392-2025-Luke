public class Assign03_03<T> extends MyQueueBase<T> {

    int nitm = -1;
    FnList<T> frnt = null;
    FnList<T> rear = null;

    public Assign03_03() {
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
}
