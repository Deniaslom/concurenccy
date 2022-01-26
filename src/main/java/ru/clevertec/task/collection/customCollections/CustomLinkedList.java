package ru.clevertec.task.collection.customCollections;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A class that implements the CustomList interface. Custom version of LinkedList *
 *
 * @author Denis Shpadaruk
 */
public class CustomLinkedList<E> implements List<E> {

    /** class wrapper to store an object */
    private CustomNode<E> node;

    /** declaring objects for synchronization */
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    /**
     * Constructor - creating a new object
     */
    public CustomLinkedList() {
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
                CustomNode findNode = (CustomNode) itr.next();
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
            CustomNode targetNode = node;

            if (node == null) {
                node = new CustomNode(e);
                return true;
            }

            while (itr.hasNext()) {
                targetNode = (CustomNode) itr.next();
            }

            if (targetNode.getNext() == null) {
                targetNode.setNext(new CustomNode(e));
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
            if (index <= size() && index >= 0) {
                int i = 0;
                while (listIterator().hasNext() && index >= i) {
                    i++;
                    listIterator().next();
                    if (index == i) {
                        CustomNode oldNode = new CustomNode(node.getData());
                        node.setData(element);
                        node.setNext(oldNode);
                    }
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
                CustomNode currentNode = node;
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
            CustomNode targetNode = node;
            if (index >= 0 && index < size()) {
                for (int i = 0; i <= index; i++) {
                    if (index == i) {
                        return (E) targetNode.getData();
                    }
                    targetNode = (CustomNode) itr.next();
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
            if (index <= size() && index >= 0) {
                int i = 0;
                while (listIterator().hasNext() && index >= i) {
                    i++;
                    listIterator().next();
                    if (index == i) {
                        node.setData(element);
                    }
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
                CustomNode next = (CustomNode) itr.next();
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
            CustomNode findNode = node;
            if (o == null)
                throw new NullPointerException("object == null");

            for (int index = 0; index < size(); index++) {
                if (findNode.getData().equals(o))
                    return index;

                findNode = (CustomNode) itr.next();
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
            CustomNode findNode = node;
            int lastIndex = -1;
            if (o == null)
                throw new NullPointerException("object == null");

            for (int index = 0; index < size(); index++) {
                if (findNode.getData().equals(o))
                    lastIndex = index;

                findNode = (CustomNode) itr.next();
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
        private CustomNode<E> targetNode;

        public MyLinkedListIterator(CustomNode node) {
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
