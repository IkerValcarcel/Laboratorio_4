package LinkedList;

public class UnorderedListADT<T> extends CircularLinkedList implements IUnorderedListADT{

    public UnorderedListADT(){
        super();
    }

    @Override
    public void addToFront(Object elem) {
        Node<T> newElem = new Node<T>((T) elem);
        if (this.isEmpty()){
            setLast(newElem);
            getLast().setNext(getLast());
        }else{
            newElem.setNext(getLast().next());
            getLast().setNext(newElem);
        }
        setSize(size()+1);
    }

    @Override
        public void addToRear(Object elem) {
        Node<T> newElem = new Node<T>((T) elem);
        if (this.isEmpty()){
            setLast(newElem);
            getLast().setNext(getLast());
        }else{
            newElem.setNext(getLast().next());
            getLast().setNext(newElem);
            setLast(newElem);
        }
        setSize(size()+1);
    }

    @Override
    public void addAfter(Object elem, Object target) {
        if (!isEmpty()) {
            T after = (T) target;
            if (after.equals(last())){
                addToRear(elem);
            }else{
                Node<T> toAdd = new Node<T>((T) elem);
                Node<T> aux = getLast();
                boolean stop = false;
                do {
                    if (aux.data().equals(toAdd.data())) stop = true;
                    else aux = aux.next();
                }
                while (aux != getLast() && !stop);
                if (stop){
                    toAdd.setNext(aux.next());
                    aux.setNext(toAdd);
                }
            }
        }

    }
}
