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
      frnt.foritm(action);
      rear.reverse().foritm(action);
    }

    public void iforitm(BiConsumer<Integer, ? super T> action) {
      frnt.iforitm(action);
      final int[] index = {frnt.size()};

      rear.reverse().foritm(item -> action.accept(index[0]++, item));
    }
    
    
    public void rforitm(Consumer<? super T> action) {
      rear.foritm(action);
      frnt.reverse().foritm(action);
    }

    public void irforitm(BiConsumer<Integer, ? super T> action) {
      rear.iforitm(action);
      final int[] index = {rear.size()};

      frnt.reverse().foritm(item -> action.accept(index[0]++, item));

    }


} // end of [public class Assign04_01<T> extends MyQueueBase<T>{...}]
