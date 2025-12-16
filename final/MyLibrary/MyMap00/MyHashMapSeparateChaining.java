package MyLibrary.MyMap00;

import MyLibrary.FnList.*;
import MyLibrary.LnList.*;
import MyLibrary.FnTuple.*;
import MyLibrary.LnStrm.*;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class MyHashMapSeparateChaining<V> implements MyMap00<String, V> {
    // HX-2025-11-12:
    // Please give an implementation of hash table
    // that uses separate chaining for handling collisions.
    private LnList<FnTupl2<String, FnList<V>>>[] table;
    private int capacity;
    private int size;

    public MyHashMapSeparateChaining() {
        this(101); // default capacity
    }

    @SuppressWarnings("unchecked")
    public MyHashMapSeparateChaining(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.table = (LnList<FnTupl2<String, FnList<V>>>[]) new LnList[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new LnList<>();
        }
    }

    // hash function
    private int hashCode(String key) {
        int hash = key.hashCode();
        hash = (hash < 0) ? -hash : hash;
        return hash % capacity;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public LnStrm<FnTupl2<String, FnList<V>>> strmize() {
        return strmizeHelper(0);
    }
    
    // private helper method
    private LnStrm<FnTupl2<String, FnList<V>>> strmizeHelper(final int index) {
        Supplier<LnStcn<FnTupl2<String, FnList<V>>>> supplier = () -> {
            // Find next non-empty bucket starting from index
            for (int i = index; i < capacity; i++) {
                if (!table[i].nilq1()) {
                    // Found a non-empty bucket, stream its entries
                    return strmizeBucket(table[i], i + 1);
                }
            }
            // No more buckets, return nil
            return new LnStcn<FnTupl2<String, FnList<V>>>();
        };
        return new LnStrm<FnTupl2<String, FnList<V>>>(supplier);
    }
    
    // private helper method to streamize a bucket
    private LnStcn<FnTupl2<String, FnList<V>>> strmizeBucket(
        LnList<FnTupl2<String, FnList<V>>> bucket, int nextIndex) {
        
        if (bucket.nilq1()) {
            return strmizeHelper(nextIndex).eval0();
        }
        
        FnTupl2<String, FnList<V>> head = bucket.hd1();
        LnList<FnTupl2<String, FnList<V>>> tail = bucket.tl1();
        
        Supplier<LnStcn<FnTupl2<String, FnList<V>>>> supplier = () -> strmizeBucket(tail, nextIndex);
        return new LnStcn<>(head, new LnStrm<FnTupl2<String, FnList<V>>>(supplier));
    }

    // private helper method to find an entry by its key
    private FnTupl2<String, FnList<V>> findEntry(String key) {
        int index = hashCode(key);
        LnList<FnTupl2<String, FnList<V>>> bucket = table[index];
        
        final FnTupl2<String, FnList<V>>[] result = new FnTupl2[1];
        bucket.foritm1(entry -> {
            if (entry.s0().equals(key)) {
                result[0] = entry;
            }
        });
        return result[0];
    }

    @Override
    public FnList<V> search$raw(String key) {
        FnTupl2<String, FnList<V>> entry = findEntry(key);
        return entry.s1();
    }

    @Override
    public FnList<V> search$exn(String key) {
        FnTupl2<String, FnList<V>> entry = findEntry(key);
        if (entry == null) {
            throw new RuntimeException("Key not found: " + key);
        }
        return entry.s1();
    }

    @Override
    public FnList<V> search$opt(String key) {
        FnTupl2<String, FnList<V>> entry = findEntry(key);
        return (entry == null) ? null : entry.s1();
    }

    @Override
    public void insert$raw(String key, V val) {
        int index = hashCode(key);
        LnList<FnTupl2<String, FnList<V>>> bucket = table[index];
        
        LnList<FnTupl2<String, FnList<V>>> newBucket = new LnList<>();
        final boolean[] found = {false};
        
        bucket.foritm1(entry -> {
            if (entry.s0().equals(key)) {
                found[0] = true;
                FnList<V> newValues = new FnList<>(val, entry.s1());
                FnTupl2<String, FnList<V>> newEntry = new FnTupl2<>(key, newValues);
                newBucket.append1(new LnList<>(newEntry, new LnList<>()));
            } else {
                newBucket.append1(new LnList<>(entry, new LnList<>()));
            }
        });
        
        if (!found[0]) {
            FnList<V> valueList = new FnList<>(val, new FnList<>());
            FnTupl2<String, FnList<V>> newEntry = new FnTupl2<>(key, valueList);
            newBucket.append1(new LnList<>(newEntry, new LnList<>()));
            size++;
        }
        
        table[index] = newBucket;
    }

    @Override
    public void insert$exn(String key, V val) {
        insert$raw(key, val);
    }

    @Override
    public boolean insert$opt(String key, V val) {
        insert$raw(key, val);
        return true;
    }

    @Override
    public FnList<V> remove$raw(String key) {
        int index = hashCode(key);
        LnList<FnTupl2<String, FnList<V>>> bucket = table[index];
        
        LnList<FnTupl2<String, FnList<V>>> newBucket = new LnList<>();
        final FnList<V>[] removedValues = new FnList[1];
        
        bucket.foritm1(entry -> {
            if (entry.s0().equals(key)) {
                removedValues[0] = entry.s1();
            } else {
                newBucket.append1(new LnList<>(entry, new LnList<>()));
            }
        });
        
        table[index] = newBucket;
        if (removedValues[0] != null) {
            size--;
        }
        return removedValues[0];
    }

    @Override
    public FnList<V> remove$exn(String key) {
        FnList<V> result = remove$raw(key);
        if (result == null) {
            throw new RuntimeException("Key not found: " + key);
        }
        return result;
    }

    @Override
    public FnList<V> remove$opt(String key) {
        return remove$raw(key);
    }

    @Override
    public void foritm(BiConsumer<? super String, ? super V> work) {
        for (int i = 0; i < capacity; i++) {
            table[i].foritm1(entry -> {
                String key = entry.s0();
                FnList<V> values = entry.s1();
                values.foritm(val -> work.accept(key, val));
            });
        }
    }

    // prints out the Hashtable contents
    public void printTable() {
        System.out.println("Hashtable (Separate Chaining):");
        for (int i = 0; i < capacity; i++) {
            if (!table[i].nilq1()) {
                System.out.print("Bucket " + i + ": ");
                table[i].foritm1(entry -> {
                    System.out.print("[" + entry.s0() + ":");
                    entry.s1().System$out$print();
                    System.out.print("] ");
                });
                System.out.println();
            }
        }
        System.out.println("Total keys: " + size);
    }

    // main method for testing code
    public static void main(String[] args) {
        
        MyHashMapSeparateChaining<Integer> map = new MyHashMapSeparateChaining<>(10);
        
        // inserting items into the hashtable
        map.insert$raw("apple", 1);
        map.insert$raw("banana", 2);
        map.insert$raw("cherry", 3);
        map.insert$raw("date", 4);
        map.insert$raw("orange", 5);
        System.out.println("Size after insertions: " + map.size());
        map.printTable();
        System.out.println("\n");
        
        // adding multiple values to existing keys
        map.insert$raw("apple", 10);
        map.insert$raw("apple", 20);
        map.insert$raw("banana", 30);
        System.out.println("Size after adding to existing keys: " + map.size());
        map.printTable();
        System.out.println("\n");
        
        // searching throughout the hashtable
        FnList<Integer> appleValues = map.search$exn("apple");
        System.out.print("Values for apple: ");
        appleValues.System$out$print();
        System.out.println();
        
        FnList<Integer> bananaValues = map.search$opt("banana");
        System.out.print("Values for banana: ");
        bananaValues.System$out$print();
        System.out.println();
        
        FnList<Integer> missingValues = map.search$opt("grape");
        System.out.println("Values for grape (missing): " + missingValues); // should be null
        System.out.println("\n");

        // removing items
        FnList<Integer> removed = map.remove$exn("banana");
        System.out.print("Removed values for banana: ");
        removed.System$out$print();
        System.out.println();
        System.out.println("Size after removal: " + map.size());
        map.printTable();
        System.out.println("\n");

        // iterate throughout all key-value pairs
        map.foritm((key, val) -> {
            System.out.println("  Key: " + key + ", Value: " + val);
        });
    }
}

// contains the HashMap implementation using Separate Chaining from Assign08_01