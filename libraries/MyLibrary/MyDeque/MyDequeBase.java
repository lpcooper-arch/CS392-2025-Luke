package MyLibrary.MyDeque;

import Library.FnList.*;
import java.util.function.Consumer;
import java.util.function.BiConsumer;

public abstract class MyDequeBase<T> implements MyDeque<T> {
//
    public
    boolean isEmpty()
    {
	return (size() <= 0);
    }
//
    public T fpeek$opt() {
	return (isEmpty() ? null : fpeek$raw());
    }
    public T fpeek$exn() throws MyDequeEmptyExn {
	T fpeek = fpeek$opt();
	if (fpeek != null) return fpeek; else throw new MyDequeEmptyExn();
    }
//
    public T fdeque$opt() {
	return (isEmpty() ? null : fdeque$raw());
    }
    public T fdeque$exn() throws MyDequeEmptyExn {
	T fdeque = fdeque$opt();
	if (fdeque != null) return fdeque; else throw new MyDequeEmptyExn();
    }
//
    public boolean fenque$opt(T itm) {
	if (isFull()) {
	    return false;
	} else {
	    fenque$raw(itm); return true;
	}
    }

    public void fenque$exn(T itm) throws MyDequeFullExn {
        boolean res = fenque$opt(itm);
	if (!res) throw new MyDequeFullExn(); else return;
    }
//
    public void System$out$print() {
    	System.out.print("MyQueue(");
	this.iforitm
	(
          (i, itm) ->
	  {
	      if (i > 0) {
		  System.out.print(",");
	      }
	      System.out.print(itm.toString());
	  }
	);
	System.out.print(")");
    }
//
    public void rforitm
	(Consumer<? super T> action) {
	final FnList<T> itms[] =
	    (FnList<T>[]) new Object[1];
	itms[0] = new FnList<T>();
	foritm(itm -> itms[0] = new FnList(itm, itms[0]));
	(itms[0]).foritm(action);
    }
//
    public void irforitm
	(BiConsumer<Integer, ? super T> action) {
	final FnList<T> itms[] =
	    (FnList<T>[]) new Object[1];
	itms[0] = new FnList<T>();
	foritm(itm -> itms[0] = new FnList(itm, itms[0]));
	(itms[0]).iforitm(action);
    }
//
} // end of [public abstract class MyQueueBase<T>{...}]
