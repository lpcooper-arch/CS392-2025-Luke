import java.util.function.Consumer;
import java.util.function.BiConsumer;

interface MyStack<T> {
//
    int size();
//
    boolean isFull(); // checks for fullness
    boolean isEmpty(); // checks for emptiness
//
    T top$raw(); // defined if !isEmpty()
    T top$opt(); // defined if !isEmpty() // T is optional
    T top$exn() throws MyStackEmptyExn; // defined if !isEmpty() 
//
    T pop$raw(); // defined if !isEmpty()
    T pop$opt(); // defined if !isEmpty() // T is optional
    T pop$exn() throws MyStackEmptyExn; // defined if !isEmpty() 
//
    void push$raw(T itm); // defined if !isFull()
    void push$exn(T itm) throws MyStackFullExn; // defined if !isFull()
    boolean push$opt(T itm); // defined if !isFull() // true/false: succ/fail
//
    void foritm(Consumer<? super T> action);
    /*
     * action (method) is performed on T
     * Performs an action for ever item T associated with the stack
     * 
     * example (in another class):
     *              MyStackList<Integer> itms = new MyStackList<Intger>();
     *              itms.foritm(itm -> System.out.println(itm));
     */

    void rforitm(Consumer<? super T> action);
    // Same as foritm, but goes in reverse

    void iforitm(BiConsumer<Integer, ? super T> action);
    /*
     * Similar to foritm, but can alter what it does to each object based on position / i
     */

    void irforitm(BiConsumer<Integer, ? super T> action);
    // Same as iforitm, but goes in reverse
//
    void System$out$print();
}
