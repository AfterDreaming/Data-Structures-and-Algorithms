import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/*
 * LazyList.java
 *
 * Version 1.0
 * Copyright 2014 BobSoft Inc
 */

/**
 * Class for lists that support lazy deletion
 * @author Yufan Zhuang
 * @version 1.0
 *
 */
public class LazyDeleteLinkedList<T> implements LazyDeleteList<T>{
    private int size;
    private class Node {
        private Node previous, next;
        private T data;
        private boolean deleted = false;
        private Node(T data) {
            this.data = data;
        }
    }
    private Node head, tail;
    private Stack deletedList = new Stack();

    private class Itr implements Iterator<T> {
        private Node current;
        public  Itr() {
            this.current = head;
        }
        public boolean hasNext(){
            return current != null;
        }
        public T next() {
            if (current == null) throw new NoSuchElementException();
            T toReturn = (T)current.data;
            current = current.next;
            return toReturn;
        }
        public  void remove(){
            throw new UnsupportedOperationException();
        }
    }
     /**
      * Checks whether this list is empty
      * This should be an O(1) operation
      * @return true if there are no undeleted elements in the list, false otherwise
      */
    public boolean isEmpty() {
        return size == 0;
    }
      
    /**
    * Get the number of undeleted elements in the list
    * This should be an O(1) operation
    * @return the count of undeleted elements
    */
      public int size() {
            return size;
    }
      
    /**
    * Add a new element to the list.  This element is placed into a deleted node,
    * or if none exists at the end of the list.
    * This should be an O(1) operation for all cases
    * @param data the data element to add to the list
    */
    public void add(T data) {

        Node newNode = new Node(data);
        if(isEmpty() && deletedList.empty() ) {
            head = newNode;
            tail = newNode;
        } else if (!deletedList.empty()) {
            Node spot = (Node) deletedList.pop();
            spot.data = data;
            spot.deleted = false;
            //System.out.println(spot.data + "is added");
        } else {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }
        size++;

    }
      
    /**
    * Remove all the deleted nodes and ensure list contains only undeleted nodes.
    * This is an O(n)
    * @return the number of nodes removed (should count all nodes 
    *              that are removed from the list
    */
    public int compress() {
        int toReturn = deletedList.size();
        Node spot;
        if(isEmpty()) {
            head = null;
            tail = null;
        }
        while (!deletedList.empty() && !isEmpty()) {
            spot = (Node)deletedList.pop();
                //if the spot is head
            if (spot == head) {
                spot.next.previous = null;
                head = spot.next;
            } else if (spot == tail) {
                //if the spot is tail
                spot.previous.next = null;
                tail = spot.previous;
            } else {
                //if the spot is in middle
                spot.previous.next = spot.next;
                spot.next.previous = spot.previous;
            }
        }
        return toReturn;
    }
      
    /**
    * Remove everything from the list
    * This is an O(1) operation (you may assume the Stack's clear operation is O(1) )
    */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }
      
    /**
    * Checks whether the list contains a certain value
    * This is an O(n) operation
    * @param data the item to check for
    * @return true if list has this item and it is undeleted, false otherwise
    */
    public boolean contains(T data) {
        if(isEmpty()) return false;
        Node current = head;
        while (current != tail) {

            if (current.data.equals(data) && !current.deleted) return true;
            current = current.next;
        }
        //System.out.println(current.data);
        if (current.data.equals(data) && !current.deleted) return true;
        return false;
    }
      
    /**
    * Removes an item from the list using lazy deletion (the node is marked deleted, but
    * not actually removed.  If duplicate items are present, this removes the first one found.
    * This is an O(n) operation based on having to find the appropriate item
    *
    * @param data the item being deleted
    * @return true if item was in the list and undeleted, false otherwise.
    */
    public boolean remove(T data) {
        if(isEmpty()) return false;
        Node current = head;
        while (current != tail) {
            if (current.data.equals(data) && !current.deleted){
                current.deleted = true;
                size--;
                deletedList.push(current);
                return true;
            } 
            current = current.next;
        };
        // if the tail is removed
        if (current.data.equals(data) && !current.deleted) {
            current.deleted = true;
            //current.previous.next = null;
            //tail = current.previous;
            size--;
            deletedList.push(current);
            return true;
        };
        return false;
    }
      
    /**
    * This is an O(1) operation (You may assume that Stack.size() is O(1) )
    * @return the number of deleted nodes in the list that are available for use
    */
    public int deletedNodeCount() {
        return deletedList.size();
    }
      
    /**
    * This is an O(n) operation due to the compress function.
    * @return the iterator for this collection.  Asking for an iterator causes a compress of the collection.
    */
    public Iterator<T> iterator() {
        return  new Itr();
    }
}
