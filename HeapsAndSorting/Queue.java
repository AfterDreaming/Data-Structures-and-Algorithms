
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Yufan on 6/29/2015.
 */
public class Queue implements Container {
    int count;
    List<WorkOrder> orderList = new LinkedList<>();
    @Override
    public void setComparator(Comparator comp) {
        return;
    }

    @Override
    public void add(WorkOrder wo) {
        orderList.add(wo);
    }

    @Override
    public void arrange() {
        return;
    }

    @Override
    public void dumpContainer() {
        for(int i = 0; i < orderList.size();i++){
            System.out.println("WO #. " + i + " name: " + orderList.get(i).getName());
            System.out.print("priority: " + orderList.get(i).getPriority() + " hour: " + orderList.get(i).getHours());
        }
    }

    @Override
    public boolean hasNext() {
        return count < orderList.size();
    }

    @Override
    public WorkOrder next() {
        if(orderList.isEmpty()|| !hasNext() ) {return null;}
        count++;
        return orderList.get(count - 1 );
    }
}
