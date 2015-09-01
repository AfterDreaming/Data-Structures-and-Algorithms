import java.util.Comparator;
import java.util.Iterator;

/**
 * This represents a container that holds the workorders
 * Students will be implementing three versions of the
 *
 * Container for this simulation:
 * Heap - a standard Max Heap
 * SortedList - an array sorted with quicksort or insertion sort
 * Queue - a standard FIFO structure
 *
 * @author robertwaters
 *
 */
public interface Container extends Iterator<WorkOrder> {
    /**
     * The comparator that the container will use to arrange the container
     * Note that for some containers like Queue and Stack, this
     * would be a nop.
     *
     * @param comp
     */
    void setComparator(Comparator comp);

    /**
     * Add a workorder to the container
     * The actual internal behavior for add would vary by container.
     * For a queue, it just adds at end.
     * For a heap, it does a normal heap insertion
     * For a sorted list, it just adds it to an array which will be sorted later
     */
    void add(WorkOrder wo);

    /**
     * Arranges the workorders in the required order
     * Uses the comparator if necessary
     * Some data structures may not need this method (like Queue)
     * Just make it a no-op for those structures.
     * For a queue, do nothing
     * For a sorted list, run the sorting algorithm
     * For a heap, run make_heap algorithm
     */
    void arrange();

    /**
     * This method will print to stdout the contents of the container
     * Useful for debugging.
     */
    void dumpContainer();

    /*********************************
     * These methods are inherited from  Iterator<WorkOrder>
     */

    /**
     *  like isEmpty() in a normal collection
     *
     * @return true if there are any more items in this container
     */
    @Override
    boolean hasNext();

    /**
     * This method should return the next item according the rules of the container
     * Heap - acts same as removeMax() in a standard heap.
     * SortedList -- returns next item from the sorted order
     * Queue -- returns next FIFO item
     *
     * @return the next item in this container
     */
    @Override
    WorkOrder next();


}
