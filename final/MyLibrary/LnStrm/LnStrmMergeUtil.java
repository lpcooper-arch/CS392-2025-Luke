package MyLibrary.LnStrm;


import java.util.function.ToIntBiFunction;

public class LnStrmMergeUtil {
    
    public static <T> LnStrm<T> mergeLnStrm(LnStrm<LnStrm<T>> fxss, ToIntBiFunction<T, T> cmpr) {
        return new LnStrm<T>(() -> {
            LnStcn<LnStrm<T>> xss = fxss.eval0();
            if (xss == null || xss.nilq()) return null;

            LnStrm<T> xs1 = xss.head;
            LnStrm<LnStrm<T>> xss_tail = xss.tail;

            LnStcn<T> stcn1 = xs1.eval0();
            if (stcn1 == null || stcn1.nilq()) {
                return mergeLnStrm(xss_tail, cmpr).eval0();
            }

            T x1 = stcn1.head;
            LnStrm<T> xs1_tail = stcn1.tail;

            LnStcn<LnStrm<T>> xss_tail_stcn = xss_tail.eval0();
            if (xss_tail_stcn == null || xss_tail_stcn.nilq()) {
                return new LnStcn<>(x1, xs1_tail);
            }

            LnStrm<T> xs2 = xss_tail_stcn.head;
            LnStrm<LnStrm<T>> xss_rest = xss_tail_stcn.tail;

            LnStcn<T> stcn2 = xs2.eval0();
            if (stcn2 == null || stcn2.nilq()) {
                return mergeLnStrm(new LnStrm<>(() -> new LnStcn<>(xs1, xss_rest)), cmpr).eval0();
            }

            T x2 = stcn2.head;
            LnStrm<T> xs2_tail = stcn2.tail;

            int cmp = cmpr.applyAsInt(x1, x2);

            if (cmp <= 0) {
                // x1 is smaller: output x1, put xs2 first, then xs1_tail, then rest
                LnStrm<LnStrm<T>> new_xss = new LnStrm<>(() -> 
                    new LnStcn<>(xs2, new LnStrm<>(() -> new LnStcn<>(xs1_tail, xss_rest))));
                return new LnStcn<>(x1, mergeLnStrm(new_xss, cmpr));
            } else {
                // x2 is smaller: output x2, put xs1 first, then xs2_tail, then rest
                LnStrm<LnStrm<T>> new_xss = new LnStrm<>(() -> 
                    new LnStcn<>(xs1, new LnStrm<>(() -> new LnStcn<>(xs2_tail, xss_rest))));
                return new LnStcn<>(x2, mergeLnStrm(new_xss, cmpr));
            }
        });
    }
}

// contains LnStrm merge from Assign06_01