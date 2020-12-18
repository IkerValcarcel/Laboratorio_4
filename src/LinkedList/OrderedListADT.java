package LinkedList;

public class OrderedListADT<T> extends CircularLinkedList implements IOrderedListADT{

    public OrderedListADT(){
        super();
    }

    @Override
    public void add(Object elem) {
        Node<T> newElem = new Node<T>((T) elem);
        if (getLast() == null) {
            newElem.setNext(newElem);
            setLast(newElem);
        }else{
            Comparable<T> newElemComparable = (Comparable) newElem.data();
            Node<T> aux = getLast();
            boolean stop = false;
            do{
                if (newElemComparable.compareTo(aux.next().data()) <= 0) stop = true;
                else aux = aux.next();
            }while(aux != getLast() && !stop);
            newElem.setNext(aux.next());
            aux.setNext(newElem);
            if (!stop) setLast(newElem);
        }
        setSize(size()+1);
    }

    @Override
    public void merge(OrderedListADT list) {
        if (isEmpty()){
            setLast(list.getLast());
            this.setSize(list.size());
        }else if (!list.isEmpty()){
            int indThis = 0;
            int indList = 0;
            Node<T> nThis = getLast().next();
            Node<T> nList = list.getLast().next();
            Node<T> newNode;
            Node<T> first;
            Comparable<T> comp = (Comparable<T>) nThis.data();
            boolean stop = false;
            if (comp.compareTo(nList.data()) > 0){
                newNode = nList;
                indList++;
                nList = nList.next();
            }else{
                newNode = nThis;
                indThis++;
                nThis = nThis.next();
            }
            first = newNode;
            while (!stop){
                if (indList >= list.size()){
                    newNode.setNext(nThis);
                    this.setLast(this.getLast());
                    this.getLast().setNext(first);
                    stop = true;
                }else if (indThis >= this.size()){
                    newNode.setNext(nList);
                    this.setLast((list.getLast()));
                    this.getLast().setNext(first);
                    stop = true;
                }else{
                    comp = (Comparable<T>) nThis.data();
                    if (comp.compareTo(nList.data()) > 0){
                        newNode.setNext(nList);
                        nList = nList.next();
                        indList++;
                    }else{
                        newNode.setNext(nThis);
                        nThis = nThis.next();
                        indThis++;
                    }
                    newNode = newNode.next();
                }
            }
            this.setSize(size() + list.size());
        }

    }
}
