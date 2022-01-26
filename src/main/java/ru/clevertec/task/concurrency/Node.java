package ru.clevertec.task.concurrency;

/**
 * node class containing the object itself and a link to the next node
 * @autor Denis Shpadaruk
 */
public class Node<E> {
    private E data;
    private Node<E> next;

    public Node() {
    }

    /**
     * Constructor which takes an int value which is stored as the data in this Node object.
     */
    public Node(E data) {
        this.data = data;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
