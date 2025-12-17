package MyFinalLib.PriorityFnGTree;

import MyFinalLib.FnList.*;

public interface FnGtree<T> {
    T value();
    int priority();
    FnList<FnGtree<T>> children();
}
