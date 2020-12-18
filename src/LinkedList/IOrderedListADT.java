package LinkedList;

public interface IOrderedListADT<T> extends ICircularLinkedList{

    public void add(T elem); // añade el elemento en su posición
    public void merge(OrderedListADT<T> lista); // fusiona la lista con los elementos de la lista actual (el coste debe ser O(n))

}
