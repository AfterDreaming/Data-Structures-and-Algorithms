
/*
 * CircularBuffer.java
 * Homework #1
 * Summer 2015
 */


/**
 * This class represents a basic circular buffer data structure
 * that supports both regrow and overwrite semantics.
 * 
 * The circular buffer acts like a queue, in that elements are added
 * at the end, and removed from the front.  (oldest items are always removed first).
 *  
 * @type T the type of the data element held in the buffer
 * 
 * @author Robert Waters
 *
 */
public interface CircularBuffer<T> {

    /**
     * @return true if buffer is empty (has no elements) false otherwise
     */
    public boolean isEmpty();
    
    /**
     * @return the maximum number of elements that this buffer can hold without 
     *         either a regrow or an overwrite
     */
    public int capacity();
    
    /**
     * @return the number of actual elements in the buffer
     */
    public int size();
    
    /**
     * Sets the correct action for the buffer to take when it is full
     * 
     * @param newMode the mode to use when the buffer gets full
     */
    public void setMode(BufferGrowMode newMode);
    
    /**
     * Adds an item to the buffer.  This may cause either a regrow operation or an overwrite of the information
     * in the buffer.
     * 
     * @param item the item to add to the buffer
     */
    public void add(T item);
    
    /**
     * Removes the oldest item (front) from the buffer.  This also removes that item from the buffer.
     *
     * 
     * @return the oldest item in the buffer,  or null if buffer is empty
     *
     */
    public T remove();

    /**
     * Tests whether this buffer has the requested item
     *
     * @param item the item to check for
     *
     * @return true if buffer has this item stored, false otherwise
     */
    public boolean contains(T item);
}
