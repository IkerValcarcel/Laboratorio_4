package LinkedList;

public class Node<T> {

    private Node<T> next;
    private T data;

    public Node(T pData){
        data = pData;
    }

    public void setNext(Node<T> pNext) {
        next = pNext;
    }
    public Node<T> next(){
        return next;
    }
    public T data(){
        return data;
    }
}

