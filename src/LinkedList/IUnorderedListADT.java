package LinkedList;

public interface IUnorderedListADT<T> extends ICircularLinkedList{
    public void addToFront(T elem); // añade un elemento al comienzo
    public void addToRear(T elem); // añade un elemento al final
    public void addAfter(T elem, T target); // Añade elem detrás de otro elemento concreto, target, que ya se encuentra en la lista
}
