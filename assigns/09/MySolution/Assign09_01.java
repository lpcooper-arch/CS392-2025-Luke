import MyPQueue.*;

public class Assign09_01 {
    // HX: There is NO Assign09_01

    public static void main(String[] args) {
        // Test method for MyPQueueArray


         MyPQueueArray<Integer> pq = new MyPQueueArray<>(10, Integer::compareTo);

        // insertion
        pq.enque$raw(5);
        pq.enque$raw(3);
        pq.enque$raw(8);
        pq.enque$raw(1);
        pq.enque$raw(4);

        // check top
        System.out.println(pq.top$raw()); // top should be 8

        // check dequeue
        while (!pq.isEmpty()) {
            System.out.print(pq.deque$raw() + " ");
        }
        System.out.println(); // should be 8 5 4 3 1


        
        try {
            pq.top$exn();   // should throw an error
        } catch (MyPQueueEmptyExn e) {
            System.out.println("Correctly caught MyPQueueEmptyExn");
        }

        
        try {
            for (int i = 0; i < 10; i++) {
                pq.enque$exn(i);
            }
            System.out.println("Queue is full? " + pq.isFull());


            pq.enque$exn(999); // should throw an error

        } catch (MyPQueueFullExn e) {
            System.out.println("Correctly caught MyPQueueFullExn");
        }
    }
}