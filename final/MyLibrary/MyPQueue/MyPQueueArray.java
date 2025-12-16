package MyPQueue;

import java.util.Comparator;

public class MyPQueueArray<T> extends MyPQueueBase<T> {
    
    // Maxheap implementation

    private T[] arr;
    private int size;
    private int capacity;
    private Comparator<T> cmp;

    @SuppressWarnings("unchecked")
    public MyPQueueArray(int capacity, Comparator<T> cmp) {
        this.arr = (T[]) new Object[capacity];
        this.capacity = capacity;
        this.cmp = cmp;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isFull() {
        return size == capacity;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public T top$raw() {
        return arr[0];
    }

    @Override
    public void enque$raw(T x) {
        if (size == arr.length) grow();
        arr[size] = x;
        siftUp(size);
        size++;
    }

    @Override
    public T deque$raw() {
        if (size == 0) return null;
        T result = arr[0];
        size--;
        arr[0] = arr[size];
        arr[size] = null;
        siftDown(0);
        return result;
    }

    private void siftUp(int i) {
        while (i > 0) {
            int p = (i - 1) / 2;
            if (cmp.compare(arr[i], arr[p]) <= 0) break;
            T tmp = arr[i];
            arr[i] = arr[p];
            arr[p] = tmp;
            i = p;
        }
    }

    private void siftDown(int i) {
        while (true) {
            int l = 2 * i + 1;
            int r = 2 * i + 2;
            int largest = i;

            if (l < size && cmp.compare(arr[l], arr[largest]) > 0)
                largest = l;
            if (r < size && cmp.compare(arr[r], arr[largest]) > 0)
                largest = r;

            if (largest == i) break;

            T tmp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = tmp;

            i = largest;
        }
    }

    @SuppressWarnings("unchecked")
    private void grow() {
        T[] newArr = (T[]) new Object[arr.length * 2];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        arr = newArr;
        capacity = arr.length;
    }
}
