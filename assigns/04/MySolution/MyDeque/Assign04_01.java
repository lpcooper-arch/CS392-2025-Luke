package MyDeque;

import java.util.function.Consumer;
import java.util.function.BiConsumer;


public class Assign04_01<T> extends Assign03_03<T> {
//
    /*
      HX-2025-09-24:
      Please first copy your implementation of Assign03_03
      to this class.
     */

    /*
      The following four higher-order methods are declared
      in MyQueueBase<T>:
      
      public void foritm(Consumer<? super T> action);
      public void iforitm(BiConsumer<Integer, ? super T> action);
      public rforitm(Consumer<? super T> action);
      public irforitm(BiConsumer<Integer, ? super T> action);

      Please implement them for your two list based queue.
    */


    public void foritm(Consumer<? super T> action) {
      fillEmptyFront();
      frnt.foritm(action);
    }

    public void iforitm(BiConsumer<Integer, ? super T> action) {
      fillEmptyFront();
      frnt.iforitm(action);
    }
    
    
    public void rforitm(Consumer<? super T> action) {
      fillEmptyFront();
      frnt.reverse().rforitm(action);
    }

    public void irforitm(BiConsumer<Integer, ? super T> action) {
      fillEmptyFront();
      frnt.reverse().irforitm(action);
    }


} // end of [public class Assign04_01<T> extends MyQueueBase<T>{...}]
