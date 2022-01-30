package ru.clevertec.task.concurrency;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A class that implements the CustomList interface. Custom version of LinkedList *
 *
 * @author Denis Shpadaruk
 */
public class MyLinkedListImpl<E> implements List<E> {

    /** class wrapper to store an object */
    private Node<E> node;

    /** declaring objects for synchronization */
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    /** Constructor - creating a new object */
    public MyLinkedListImpl() {
    }

    /**
     * @return number of objects
     */
    @Override
    public int size() {
        try {
            readLock.lock();

            int size = 0;
            Iterator itr = new MyLinkedListIterator(node);

            if (!isEmpty())
                size++;

            while (itr.hasNext()) {
                itr.next();
                size++;
            }
            return size;
        } finally {
            readLock.unlock();
        }
    }

    /**
     * check for emptiness
     *
     * @return boolean
     */
    @Override
    public boolean isEmpty() {
        try {
            readLock.lock();

            if (node.getData() == null) {
                return true;
            }
            return false;
        } finally {
            readLock.unlock();
        }
    }

    /**
     * checking for the presence of an object in an array
     *
     * @param o check for this object
     * @return true if found, false if otherwise
     */
    @Override
    public boolean contains(Object o) {
        try {
            readLock.lock();

            MyLinkedListIterator itr = new MyLinkedListIterator(node);
            if (isEmpty())
                return false;

            if (node.getData().equals(o))
                return true;

            while (itr.hasNext()) {
                Node findNode = (Node) itr.next();
                if (findNode.getData().equals(o)) {
                    return true;
                }
            }
            return false;
        } finally {
            readLock.unlock();
        }
    }

    /**
     * adding an object
     *
     * @param e added object
     */
    @Override
    public boolean add(E e) {
        try {
            writeLock.lock();
            MyLinkedListIterator itr = new MyLinkedListIterator(node);
            Node targetNode = node;

            if (node == null) {
                node = new Node(e);
                return true;
            }

            while (itr.hasNext()) {
                targetNode = (Node) itr.next();
            }

            if (targetNode.getNext() == null) {
                targetNode.setNext(new Node(e));
                return true;
            }

            return false;
        } finally {
            writeLock.unlock();
        }
    }


    /**
     * adding object by index
     */
    @Override
    public void add(int index, E element) {
        try {
            writeLock.lock();
            Node oldNode = node;
            Node targetNode = node;
            Node newNode = new Node(element);
            MyLinkedListIterator itr = new MyLinkedListIterator<>(node);

            if (index <= size() && index >= 0) {
                for (int i = 0; itr.hasNext() && index >= i; i++) {
                    if (index == 0 && index == i) {
                        if (node != null) {
                            newNode.setNext(node);
                        }
                        node = newNode;
                    }

                    if (index != 0 && index == i) {
                        oldNode.setNext(newNode);
                        newNode.setNext(targetNode);
                    }
                    oldNode = targetNode;
                    targetNode = (Node) itr.next();
                }
            }
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * deleting an object by index
     * * @return remote object
     */
    @Override
    public E remove(int index) {
        try {
            writeLock.lock();
            if (index == 0) {
                node = node.getNext();
            } else {
                Node currentNode = node;
                for (int i = 0; i < index - 1; i++) {
                    currentNode = currentNode.getNext();
                }
                currentNode.setNext(currentNode.getNext().getNext());
            }
            return (E) node;
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * deleting an object by object
     * * @return success(true if deleted otherwise false)
     */
    @Override
    public boolean remove(Object o) {
        try {
            writeLock.lock();
            MyLinkedListIterator itr = new MyLinkedListIterator(node);

            if (o == null)
                throw new NullPointerException("object == null");

            for (int index = 0; index < size(); index++) {
                if (o.equals(get(index))) {
                    remove(index);
                    return true;
                }
            }

            return false;
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * get object by index
     */
    @Override
    public E get(int index) {
        try {
            readLock.lock();
            MyLinkedListIterator itr = new MyLinkedListIterator(node);
            Node targetNode = node;
            if (index >= 0 && index < size()) {
                for (int i = 0; i <= index; i++) {
                    if (index == i) {
                        return (E) targetNode.getData();
                    }
                    targetNode = (Node) itr.next();
                }
            }
            return (E) targetNode.getData();
        } finally {
            readLock.unlock();
        }
    }


    /**
     * replaces an object by index
     *
     * @param index   - index in array
     * @param element - object to replace
     * @return
     */
    @Override
    public E set(int index, E element) {
        try {
            writeLock.lock();
            MyLinkedListIterator itr = new MyLinkedListIterator(node);
            Node targetNode = node;
            if (index <= size() && index >= 0) {
                for (int i = 0; index >= i; i++) {
                    if (index == i) {
                        targetNode.setData(element);
                    }

                    if (itr.hasNext())
                        targetNode = (Node) itr.next();
                }
            }
            return (E) node.getData();
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * clears the array
     */
    @Override
    public void clear() {
        try {
            writeLock.lock();
            MyLinkedListIterator itr = new MyLinkedListIterator(node);

            if (node != null) {
                node.setData(null);
            }

            while (itr.hasNext()) {
                Node next = (Node) itr.next();
                next.setData(null);
            }
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * Returns the index of the first found object
     *
     * @param o desired object
     * @return from 0 and above - the index of the found first object. If -1 then object not found
     */
    @Override
    public int indexOf(Object o) {
        try {
            readLock.lock();
            MyLinkedListIterator itr = new MyLinkedListIterator(node);
            Node findNode = node;
            if (o == null)
                throw new NullPointerException("object == null");

            for (int index = 0; index < size(); index++) {
                if (findNode.getData().equals(o))
                    return index;

                findNode = (Node) itr.next();
            }

            return -1;
        } finally {
            readLock.unlock();
        }
    }

    /**
     * Returns the index of the last found object
     *
     * @param o desired object
     * @return from 0 and above - the index of the found last object. If -1 then object not found
     */
    @Override
    public int lastIndexOf(Object o) {
        try {
            readLock.lock();

            MyLinkedListIterator itr = new MyLinkedListIterator(node);
            Node findNode = node;
            int lastIndex = -1;
            if (o == null)
                throw new NullPointerException("object == null");

            for (int index = 0; index < size(); index++) {
                if (findNode.getData().equals(o))
                    lastIndex = index;

                findNode = (Node) itr.next();
            }

            return lastIndex;
        } finally {
            readLock.unlock();
        }
    }


    /**
     * @return Object[] of the given collection
     */
    @Override
    public Object[] toArray() {
        try {
            readLock.lock();
            Object[] result = new Object[size()];

            for (int index = 0; index < size(); index++) {
                result[index] = get(index);
            }

            return result;
        } finally {
            readLock.unlock();
        }
    }

    /**
     * @return an iterator over the elements in this list in proper sequence
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            int counter = 0;

            @Override
            public boolean hasNext() {
                return counter < size();
            }

            @Override
            public E next() {
                return get(counter++);
            }
        };
    }


    private class MyLinkedListIterator<E> implements Iterator<E> {
        private Node<E> targetNode;

        public MyLinkedListIterator(Node node) {
            this.targetNode = node;
        }

        @Override
        public boolean hasNext() {
            if (targetNode.getNext() != null)
                return true;
            else
                return false;
        }

        @Override
        public E next() {
            targetNode = targetNode.getNext();
            return (E) targetNode;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    //Not implemented
    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    //Not implemented
    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    //Not implemented
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    //Not implemented
    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    //Not implemented
    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    //Not implemented
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    //Not implemented
    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    //Not implemented
    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    //Not implemented
    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }
}
