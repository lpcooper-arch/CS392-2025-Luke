import MyLibrary.LnStrm.*;
import MyLibrary.FnTuple.*;
/*
import java.util.Random;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.BiFunction;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
*/
import java.util.function.ToIntBiFunction;


public class Assign06_02 {
    
    private static LnStrm<Integer> natsFrom(int n) {
        return new LnStrm<>(() -> new LnStcn<>(n, natsFrom(n + 1)));
    }
    
    public static
	LnStrm<Integer>
	ramanujanNumbers() {
	// Return a stream of all the ramanujanNumbers
        LnStrm<FnTupl2<Integer,Integer>> pairs = cubeSumOrderedIntegerPairs();
        
        return new LnStrm<>(() -> {
            return findNextRamanujan(pairs, null, null, 0);
        });
    }

    public static
	LnStrm<
	  FnTupl2<Integer,Integer>>
	cubeSumOrderedIntegerPairs() {
	// Return a stream of all the positive integer pairs
	// that are ordered according to the sum of the cubes
	// of the two integer components
        LnStrm<Integer> nats = natsFrom(1);
        LnStrm<LnStrm<FnTupl2<Integer,Integer>>> allStreams = 
            LnStrmSUtil.map0(nats, x -> {
                LnStrm<Integer> natsFromX = natsFrom(x);
                return LnStrmSUtil.map0(natsFromX, y -> {
                    return new FnTupl2<>(x, y);
                });
            });
        
        ToIntBiFunction<FnTupl2<Integer,Integer>, FnTupl2<Integer,Integer>> cmpr = 
            (p1, p2) -> {
                int sum1 = p1.s0() * p1.s0() * p1.s0() + 
                          p1.s1() * p1.s1() * p1.s1();
                int sum2 = p2.s0() * p2.s0() * p2.s0() + 
                          p2.s1() * p2.s1() * p2.s1();
                return Integer.compare(sum1, sum2);
            };
        
        return Assign06_01.mergeLnStrm(allStreams, cmpr);
    }

    private static
    LnStcn<Integer>
    findNextRamanujan(
        LnStrm<FnTupl2<Integer,Integer>> pairs, 
        FnTupl2<Integer,Integer> prev,
        Integer prevSum,
        int count) {
        
        LnStcn<FnTupl2<Integer,Integer>> stcn = pairs.eval0();
        
        if (stcn == null || stcn.nilq()) {
            return null;
        }
        
        FnTupl2<Integer,Integer> curr = stcn.head;
        LnStrm<FnTupl2<Integer,Integer>> tail = stcn.tail;
        
        int currSum = curr.s0() * curr.s0() * curr.s0() + 
                     curr.s1() * curr.s1() * curr.s1();
        
        if (prevSum != null && currSum == prevSum) {
            int newCount = count + 1;
            if (newCount >= 2) {
                return new LnStcn<>(currSum, 
                    new LnStrm<>(() -> findNextRamanujan(tail, curr, currSum, 0)));
            } else {
                return findNextRamanujan(tail, curr, currSum, newCount);
            }
        } else {
            return findNextRamanujan(tail, curr, currSum, 1);
        }
    }

    public static void main(String[] args) {
	// Please provide some minimal testing code
        System.out.println("Testing cube sum ordered pairs:");
        LnStrm<FnTupl2<Integer,Integer>> pairs = cubeSumOrderedIntegerPairs();
        
        System.out.println("First 10 pairs:");
        LnStrm<FnTupl2<Integer,Integer>> pairsCopy = pairs;
        for (int i = 0; i < 10; i++) {
            LnStcn<FnTupl2<Integer,Integer>> stcn = pairsCopy.eval0();
            if (stcn != null && stcn.consq()) {
                FnTupl2<Integer,Integer> p = stcn.head;
                int sum = p.s0() * p.s0() * p.s0() + 
                         p.s1() * p.s1() * p.s1();
                System.out.println("(" + p.s0() + ", " + p.s1() + ") -> " + sum);
                pairsCopy = stcn.tail;
            }
        }
        
        System.out.println("\nFirst 5 Ramanujan numbers:");
        LnStrm<Integer> ramanujans = ramanujanNumbers();
        for (int i = 0; i < 5; i++) {
            LnStcn<Integer> stcn = ramanujans.eval0();
            if (stcn != null && stcn.consq()) {
                System.out.println(stcn.head);
                ramanujans = stcn.tail;
            }
        }
    }

} // end of [public class Assign06_02{...}]