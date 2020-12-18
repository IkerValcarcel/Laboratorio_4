package LinkedList;

import java.util.Iterator;

public class IteratorCLL<T> implements Iterator {

    private Node<T> actual;
    private Node<T> last;

    public IteratorCLL(Node<T> pLast){
        last = pLast;
    }


    @Override
    public boolean hasNext() {
        if (actual == null && last != null){
            return true;
        }else if (actual == null){
            return false;
        }else{
            return actual.next() != last;
        }
    }

    @Override
    public Node<T> next() {
        if (actual==null && last != null){
            actual = last.next();
        }else{
            actual = actual.next();
        }
        return actual;
    }
}
