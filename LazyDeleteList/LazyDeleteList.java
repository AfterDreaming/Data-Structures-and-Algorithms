import java.util.Iterator;

/*
 * LazyList.java
 * Author Yufan Zhuang
 * Version 1.0
 */

/**
 * Interface for lists that support lazy deletion
 * @author Robert
 * @version 1.0
 *
 */
public interface LazyDeleteList<T> extends Iterable<T>{
     /**
      * Checks whether this list is empty
      * This should be an O(1) operation
      * @return true if there are no undeleted elements in the list, false otherwise
      */
      boolean isEmpty();
      
      /**
       * Get the number of undeleted elements in the list
       * This should be an O(1) operation
       * @return the count of undeleted elements
       */
      int size();
      
      /**
       * Add a new element to the list.  This element is placed into a deleted node,
       * or if none exists at the end of the list.
       * This should be an O(1) operation for all cases
       * @param data the data element to add to the list
       */
      void add(T data);
      
      /**
       * Remove all the deleted nodes and ensure list contains only undeleted nodes.
       * This is an O(n)
       * @return the number of nodes removed (should count all nodes 
       *              that are removed from the list
       */
      int compress();
      
      /**
       * Remove everything from the list
       * This is an O(1) operation (you may assume the Stack's clear operation is O(1) )
       */
      void clear();
      
      /**
       * Checks whether the list contains a certain value
       * This is an O(n) operation
       * @param data the item to check for
       * @return true if list has this item and it is undeleted, false otherwise
       */
      boolean contains(T data);
      
      /**
       * Removes an item from the list using lazy deletion (the node is marked deleted, but
       * not actually removed.  If duplicate items are present, this removes the first one found.
       * This is an O(n) operation based on having to find the appropriate item
       *
       * @param data the item being deleted
       * @return true if item was in the list and undeleted, false otherwise.
       */
      boolean remove(T data);
      
      /**
       * This is an O(1) operation (You may assume that Stack.size() is O(1) )
       * @return the number of deleted nodes in the list that are available for use
       */
      int deletedNodeCount();
      
      /**
       * This is an O(n) operation due to the compress function.
       * @return the iterator for this collection.  Asking for an iterator causes a compress of the collection.
       */
      Iterator<T> iterator();
}
