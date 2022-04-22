import java.util.*;

public class DoubleLinkedList<E> extends AbstractSequentialList<E>
{  // Data fields
    private Node<E> head = null;   // points to the head of the list
    private Node<E> tail = null;   //points to the tail of the list
    private int size = 0;    // the number of items in the list

    public DoubleLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public void add(int index, E obj)
    {
        ListIterator<E> iter = listIterator(index);
        iter.add(obj);
    }

    public void addFirst(E obj) { // Fill Here
        this.add(0, obj);
    }
    public void addLast(E obj) { // Fill Here
        this.add(size, obj);
    }

    public E get(int index) {
        if (size == 0 || size <= index) {
            throw new IndexOutOfBoundsException();
        }
        ListIterator<E> iter = listIterator(index);
        return iter.next();
    }

    public E getFirst() { return head.data;  }
    public E getLast() { return tail.data;  }

    public int size() {  
        return size;
    } // Fill Here

    public E remove(int index) {
        E returnValue = null;
        ListIterator<E> iter = listIterator(index);
        if (iter.hasNext())
        {
            returnValue = iter.next();
            iter.remove();
        }
        else {   throw new IndexOutOfBoundsException();  }
        return returnValue;
    }

    public int indexOf(Object o) {
        int index = 0;
        ListIterator<E> iter = listIterator(index);
        for (int i = 0; i < size; i++) {
            if (iter.next().equals(o)) {
                return i;
            }
        }
        return -1;
    }

    public boolean contains(Object o) {
        ListIterator<E> iter = listIterator(0);
        // return true if found (index not -1)
        return indexOf(o) != -1;
    }

    public Iterator iterator() { return new ListIter(0);  }
    public ListIterator listIterator() { return new ListIter(0);  }
    public ListIterator listIterator(int index){return new ListIter(index);}
    public ListIterator listIterator(ListIterator iter)
    {     return new ListIter( (ListIter) iter);  }

    // Inner Classes
    private static class Node<E>
    {     
        private E data;
        private Node<E> next = null;
        private Node<E> prev = null;

        private Node(E dataItem)  //constructor
        {   data = dataItem;   }
    }  // end class Node

    /**
     * Inner class listIterator to iterate through Double linked list
     */
    public class ListIter implements ListIterator<E>
    {
        private Node<E> nextItem;      // the current node
        private Node<E> lastItemReturned;   // the previous node
        private int index = 0;   //

        /**
         * Constructor for ListIter class
         * @param i is index
         */
        public ListIter(int i) {
           // validate index
            if (i < 0 || i > size) {
               throw new IndexOutOfBoundsException("Invalid index " + i);
           }
            // initialize lir to null
           lastItemReturned = null;

            // check for end of list edge case
           if (i == size) {
               index = size;
               nextItem = null;
           }
           else {  // start at the beginning
               nextItem = head;
               for (index = 0; index < i; index++) {
                   nextItem = nextItem.next;
               }
           }// end else
        }  // end constructor

        /**
         * Copy constructor for ListIter
         * @param other
         */
        public ListIter(ListIter other) {
            nextItem = other.nextItem;
            index = other.index;
        }

        public boolean hasNext() {
            return nextItem != null;
        } // Fill Here

        public boolean hasPrevious() {
            if (size == 0)
                return false;
             //at end of list, next item is null--if size is not zero, we know there is a previous
            return (nextItem == null || nextItem.prev != null);

            //return (nextItem == null && size != 0) || nextItem.prev != null;
        } // Fill Here

        public int previousIndex() {
            if(!hasPrevious())
                return -1;
            else
                return index - 1;
        } // Fill Here

        public int nextIndex() {
            if (!hasNext())
                return size;
            else
                return index;
        } // Fill here

        public void set(E o) {
            if (lastItemReturned == null) {
                throw new IllegalStateException();
            }
            lastItemReturned.data = o;
        }  // not implemented

        public void remove() {
            if (lastItemReturned == null) {
                throw new IllegalStateException();
            }
            if (lastItemReturned.prev == null) {
                // Only 1 node
                if (lastItemReturned.next == null) {
                    head = null;
                    tail = null;
                }
                // lastItemReturned is first node
                else {
                    lastItemReturned.next.prev = null;
                    head = lastItemReturned.next;
                }
            }
            else {
                if (lastItemReturned.next == null) {
                    tail = lastItemReturned.prev;
                }
                lastItemReturned.prev.next = lastItemReturned.next;
                lastItemReturned.next = lastItemReturned.prev;
            }
            index--;
            size--;

            lastItemReturned = null;
        }      // not implemented

        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            lastItemReturned = nextItem;
            nextItem = nextItem.next;
            index++;

            return lastItemReturned.data; // Fill Here
        }

        public E previous() {
            if (!hasPrevious())
                throw new NoSuchElementException();
            // if next is null, then the previous item must be the tail of the list
            if (nextItem == null) {
                nextItem = tail;
            }
            else {
                nextItem = nextItem.prev;
            }
            lastItemReturned = nextItem;
            index--;
            return lastItemReturned.data; // Fill Here
        }

        public void add(E obj) {
            // Add to empty list
            if (head == null) {
                head = new Node<>(obj);
                tail = head;
            }
            // Add to front of list
            else if(nextItem == head) {
                Node<E> newNode = new Node<>(obj);
                // new Node links to next item, which is the head node
                newNode.next = nextItem;
                // link head node back to new Node for double linked list
                nextItem.prev = newNode;
                // make the new node the head
                head = newNode;
            }
            // Add to tail of list
            else if(nextItem == null) {
                Node<E> newNode = new Node<>(obj);
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
            }
            // Add to middle
            else {
                Node<E> newNode = new Node<>(obj);
                //
                newNode.prev = nextItem.prev;
                nextItem.prev.next = newNode;
                newNode.next = nextItem;
                nextItem.prev = newNode;
            }
            size++;
            index++;
            lastItemReturned = null;

            // Fill Here
        }
    }// end of inner class ListIter
}// end of class DoubleLinkedList