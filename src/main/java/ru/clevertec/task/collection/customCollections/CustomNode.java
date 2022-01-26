package ru.clevertec.task.collection.customCollections;

/**
 * node class containing the object itself and a link to the next node
 * @autor Denis Shpadaruk
 */
public class CustomNode<E> {
    private E data;
    private CustomNode<E> next;

    public CustomNode() {
    }

    /**
     * Constructor which takes an int value which is stored as the data in this Node object.
     */
    public CustomNode(E data) {
        this.data = data;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public CustomNode getNext() {
        return next;
    }

    public void setNext(CustomNode next) {
        this.next = next;
    }
}
