package MyFinalLib.PriorityFnGTree;

import MyFinalLib.LnStrm.*;
import java.util.Comparator;
import MyFinalLib.MyPQueue.*;

public class FnGtreeSUtil {
//

	public static<T> LnStrm<T>
	PFirstEnumerate(FnGtree<T> root) {
	// HX-2025-12-02:
	// This method enumerates nodes according
	// to their priority numbers (obtained by
	// calling priority()

		Comparator<FnGtree<T>> cmp = (a, b) -> a.priority() - b.priority();
		MyPQueueArray<FnGtree<T>> pq = new MyPQueueArray<FnGtree<T>>(1024, cmp);
		pq.enque$raw(root);
		return PFirstEnumerate_helper(pq);
	}

	private static <T> LnStrm<T> PFirstEnumerate_helper(MyPQueueArray<FnGtree<T>> pq) {
		return new LnStrm<>(
			() -> {
				if (pq.isEmpty()) {
					return new LnStcn<T>();
				} else {
					FnGtree<T> node = pq.deque$raw();
					node.children().foritm(child -> pq.enque$raw(child));
					return new LnStcn<>(node.value(), PFirstEnumerate_helper(pq));
				}
			}
		);
	}


//
} // end of [public class FnGtreeSUtil{...}]
