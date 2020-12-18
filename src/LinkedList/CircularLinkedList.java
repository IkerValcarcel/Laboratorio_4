package LinkedList;

public abstract class CircularLinkedList<T> implements ICircularLinkedList{

    private String descr;
    private Node<T> last;
    private int count;

    public CircularLinkedList(){
        descr = "";
        last = null;
        count = 0;
    }

    public Node<T> getLast(){
        return last;
    }

    public void setLast(Node<T> pLast){
        last = pLast;
    }

    public void setSize(int pCount){
        count = pCount;
    }

    @Override
    public void setDescr(String nom) {
        descr = nom;
    }

    @Override
    public String getDescr() {
        return descr;
    }

    @Override
    public T removeFirst() {
        if (last != null){
            Node<T> aux;
            count--;
            if (last.next() == last){
                aux = last;
                last = null;
                return aux.data();
            }else{
                aux = last.next();
                last.setNext(aux.next());
                return aux.data();
            }
        }else{
            return null;
        }
    }

    @Override
    public T removeLast() {
        if (last != null){
            Node<T> aux;
            count--;
            if (last.next() == last){
                aux = last;
                last = null;
            }else{
                aux = last;
                Node<T> act = last.next();
                while (act.next() != last){
                    act = act.next();
                }
                act.setNext(last.next());
                last = act;
            }
            return aux.data();
        }else {
            return null;
        }
    }

    @Override
    public T remove(Object elem) {
        if (isEmpty()){
            return null;
        }else if (size() == 1){
            T removeElem = (T)elem;
            if (removeElem.equals(last())){
                return removeLast();
            }else{
                return null;
            }
        }else{
            T removeElem = (T)elem;
            Node<T> aux = getLast();
            boolean stop = false;
            do{
                if (aux.next().data().equals(removeElem)) stop = true;
                else aux = aux.next();
            }while (aux != getLast() && !stop);
            if (stop){
                Node<T> removed = aux.next();
                aux.setNext(removed.next());
                if (removed == last){
                    last = aux;
                }
                setSize(size()-1);
                return removed.data();
            }else{
                return null;
            }
        }
    }

    @Override
    public T first() {
        if (last == null) {
            return null;
        } else {
            return last.next().data();
        }
    }

    @Override
    public T last() {
        if (last == null){
            return null;
        }else{
            return this.last.data();
        }
    }

    @Override
    public boolean contains(Object elem) {
        return find(elem) != null;
    }

    @Override
    public T find(Object elem) {
        if (last != null) {
            T nElem = (T) elem;
            Node<T> aux = last;
            boolean stop = false;
            do {
                if (aux.data().equals(nElem)) stop = true;
                else aux = aux.next();
            }
            while (aux != last && !stop);
            if (stop) {
                return aux.data();
            }
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return last == null;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public IteratorCLL iterator() {
        return new IteratorCLL(last);
    }

}
