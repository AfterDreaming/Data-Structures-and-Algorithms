/**
 * Created by Yufan on 5/16/2015.
 */
public class CircularArrayBuffer<T> implements CircularBuffer<T> {

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
 */
    private int size;
    private int front;
    private int rear = -1;
    private T[] data =(T[]) new Object[10];
    private BufferGrowMode mode = BufferGrowMode.REGROW;

    /**
     * @return true if buffer is empty (has no elements) false otherwise
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * @return the maximum number of elements that this buffer can hold without
     *         either a regrow or an overwrite
     */
    public int capacity(){
        return data.length;
    }

    /**
     * @return the number of actual elements in the buffer
     */
    public int size(){
        return size;
    }

    /**
     * Sets the correct action for the buffer to take when it is full
     *
     * @param newMode the mode to use when the buffer gets full
     */
    public void setMode(BufferGrowMode newMode){
        this.mode = newMode;
    }

    /**
     * Adds an item to the buffer.  This may cause either a regrow operation or an overwrite of the information
     * in the buffer.
     *
     * @param item the item to add to the buffer
     */
    public void add(T item){
        if (mode == BufferGrowMode.REGROW ){
            if (size == capacity()){
                regrow(); //data capacity +10
            }
            rear++;
            size++;
        }else {
            if (size == capacity()){  //if OverWrite
                rear++; //data capacity + 10
                front++;
            } else{
                rear++;
                size++;
            }
        }
        front = front % data.length;
        rear = rear % data.length;
        data[rear] = item;
    }

    /**
     * Removes the oldest item (front) from the buffer.  This also removes that item from the buffer.
     *
     *
     * @return the oldest item in the buffer,  or null if buffer is empty
     *
     */
    public T remove() {
        if(size==0) return null;
        T toReturn = data[front];
        data[front] = null;
        front++;
        front = front % data.length;
        size--;
        return toReturn;
    }

    /**
     * Tests whether this buffer has the requested item
     *
     * @param item the item to check for
     *
     * @return true if buffer has this item stored, false otherwise
     */
    public boolean contains(T item){
        if(isEmpty()){
            return false;
        }
        for (T ele : data){
            if (ele != null){
                if(ele.equals(item)) return true;
            }
        }
        return false;
    }

    private void regrow(){
        T[] oldData = data;
        data = (T[]) new Object[oldData.length + 10];
        if(rear < front) front += 10;
        for (int i = front; i < front + size; i++){
            data[i % data.length] = oldData[i % oldData.length];
        }
    }
}

