import java.util.Comparator;

/**
 * Created by Yufan on 6/29/2015.
 */
public class PriorityComparator implements Comparator<WorkOrder> {
    public int compare(WorkOrder w1, WorkOrder w2) {
        return w1.getPriority() - w2.getPriority();
    }
}