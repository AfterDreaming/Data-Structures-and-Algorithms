import java.util.Comparator;

/**
 * Created by Yufan on 6/29/2015.
 */
public class HoursComparator implements Comparator<WorkOrder> {
    public int compare(WorkOrder w1, WorkOrder w2) {
        return w1.getHours() - w2.getHours();
    }
}
