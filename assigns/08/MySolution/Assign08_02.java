import MyLibrary.FnList.*;
// import MyLibrary.LnList.*;
import MyLibrary.FnTuple.*;
import MyLibrary.MyMap00.*;
import MyLibrary.LnStrm.*;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class Assign08_02<V> implements MyMap00<String, V> {
    // HX-2025-11-12:
    // Please give an implementation of hash table
    // based on open addressing. The probing strategy
    // chosen for handling collisions is quadratic probing.
    private FnTupl2<String, FnList<V>> table[];
    private boolean[] deleted; // tracks deleted slots for lazy deletion
    private int capacity;
    private int size; // number of non-deleted keys
    private int totalEntries; // includes deleted entries
    
    @SuppressWarnings("unchecked")
    public Assign08_02(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.totalEntries = 0;
        this.table = (FnTupl2<String, FnList<V>>[]) new FnTupl2[capacity];
        this.deleted = new boolean[capacity];
    }
    
    public Assign08_02() {
        this(101); // default capacity
    }
    
    // hash function
    private int hashCode(String key) {
        int hash = key.hashCode();
        hash = (hash < 0) ? -hash : hash;
        return hash % capacity;
    }
    
    // quadratic probing implementation
    private int probe(String key, int i) {
        int h = hashCode(key);
        return (h + i * i) % capacity;
    }
    
    @Override
    public int size() {
        return size;
    }
    
    @Override
    public boolean isFull() {
        return totalEntries >= capacity * 0.7;
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
            for (int i = index; i < capacity; i++) {
                if (table[i] != null && !deleted[i]) {
                    FnTupl2<String, FnList<V>> entry = table[i];
                    LnStrm<FnTupl2<String, FnList<V>>> tail = strmizeHelper(i + 1);
                    return new LnStcn<>(entry, tail);
                }
            }
            return new LnStcn<FnTupl2<String, FnList<V>>>();
        };
        return new LnStrm<FnTupl2<String, FnList<V>>>(supplier);
    }
    
    // private helper method to find index of a key
    // returns -1 if not found
    private int findIndex(String key) {
        for (int i = 0; i < capacity; i++) {
            int index = probe(key, i);
            
            if (table[index] == null) {
                return -1;
            }
            
            if (!deleted[index] && table[index].s0().equals(key)) {
                return index;
            }
        }
        return -1;
    }
    
    // private helper method to find next available index for insertion based on key
    // returns -1 if table is full
    // otherwise returns insertion index
    private int findInsertIndex(String key) {
        int deletedIndex = -1;
        
        for (int i = 0; i < capacity; i++) {
            int index = probe(key, i);
            
            if (table[index] == null) {
                return (deletedIndex != -1) ? deletedIndex : index;
            }
            
            if (deleted[index] && deletedIndex == -1) {
                deletedIndex = index;
            }
            
            if (!deleted[index] && table[index].s0().equals(key)) {
                return index;
            }
        }
        
        return (deletedIndex != -1) ? deletedIndex : -1;
    }
    
    @Override
    public FnList<V> search$raw(String key) {
        int index = findIndex(key);
        return table[index].s1();
    }
    
    @Override
    public FnList<V> search$exn(String key) {
        int index = findIndex(key);
        if (index == -1) {
            throw new RuntimeException("Key not found: " + key);
        }
        return table[index].s1();
    }
    
    @Override
    public FnList<V> search$opt(String key) {
        int index = findIndex(key);
        return (index == -1) ? null : table[index].s1();
    }
    
    @Override
    public void insert$raw(String key, V val) {
        int index = findInsertIndex(key);
        
        if (index == -1) {
            throw new RuntimeException("Hashtable is full");
        }
        
        if (table[index] == null || deleted[index]) {
            FnList<V> valueList = new FnList<>(val, new FnList<>());
            table[index] = new FnTupl2<>(key, valueList);
            deleted[index] = false;
            size++;
            totalEntries++;
        } else {
            FnList<V> newValues = new FnList<>(val, table[index].s1());
            table[index] = new FnTupl2<>(key, newValues);
        }
    }
    
    @Override
    public void insert$exn(String key, V val) {
        if (isFull()) {
            throw new RuntimeException("Hashtable is full");
        }
        insert$raw(key, val);
    }
    
    @Override
    public boolean insert$opt(String key, V val) {
        if (isFull()) {
            return false;
        }
        try {
            insert$raw(key, val);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }
    
    @Override
    public FnList<V> remove$raw(String key) {
        int index = findIndex(key);
        FnList<V> values = table[index].s1();
        deleted[index] = true;
        size--;
        return values;
    }
    
    @Override
    public FnList<V> remove$exn(String key) {
        int index = findIndex(key);
        if (index == -1) {
            throw new RuntimeException("Key not found: " + key);
        }
        FnList<V> values = table[index].s1();
        deleted[index] = true;
        size--;
        return values;
    }
    
    @Override
    public FnList<V> remove$opt(String key) {
        int index = findIndex(key);
        if (index == -1) {
            return null;
        }
        FnList<V> values = table[index].s1();
        deleted[index] = true;
        size--;
        return values;
    }
    
    @Override
    public void foritm(BiConsumer<? super String, ? super V> work) {
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null && !deleted[i]) {
                String key = table[i].s0();
                FnList<V> values = table[i].s1();
                values.foritm(val -> work.accept(key, val));
            }
        }
    }
    
    // prints out the Hashtable contents
    public void printTable() {
        System.out.println("Hashtable (Quadratic Probing):");
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                if (deleted[i]) {
                    System.out.println("Index " + i + ": [DELETED]");
                } else {
                    System.out.print("Index " + i + ": [" + table[i].s0() + ":");
                    table[i].s1().System$out$print();
                    System.out.println("]");
                }
            }
        }
        System.out.println("Active keys: " + size);
        System.out.println("Total entries: " + totalEntries);
    }

    // main method for testing code
    public static void main(String[] args) {
        
        Assign08_02<Integer> map = new Assign08_02<>(10);
        
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