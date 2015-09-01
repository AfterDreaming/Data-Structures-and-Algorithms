import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;


public class OrderProcessingTest {

    @Test
    public void testQueue() {
        List<WorkOrder> list = OrderProcessing.getOrders(10);
        Queue q = new Queue();
        for (int i=0; i<list.size();++i) {
            q.add(list.get(i));
        }
        q.dumpContainer();
        System.out.println("*********** Testing the Queue ************");
        for (int i = 0; i < list.size(); ++i) {
            System.out.println(q.next());
        }
    }

    @Test
    public void testHeap() {
        System.out.println("*********** Test the Heap********");

        List<WorkOrder> list = OrderProcessing.getOrders(100);
        Heap h = new Heap();
        h.setComparator(new HoursComparator());
        for (int i = 0; i < list.size(); ++i) {
            h.add(list.get(i));
        }
        for (int i = 0; i < list.size(); ++i) {
            System.out.println(h.next());
        }
    }

    @Test
    public void testQsortByNameSmall() {
        System.out.println("**************** Test Small SortedList ***************************");
        SortedList sl = new SortedList();
        //sl.setComparator(new NameComparator());
        sl.setComparator(new HoursComparator());
        List<WorkOrder> list = OrderProcessing.getOrders(10);

        for (int i = 0; i< list.size(); ++i) {
            sl.add(list.get(i));
            //System.out.println("Add from test");
        }
        sl.arrange();
        //sl.dumpContainer();
        //System.out.println("*********** Results **********");
        for (int i = 0; i < list.size(); ++i) {
            System.out.println(sl.next());
        }
    }

    @Test
    public void testQsortBigByName() {
        System.out.println("************ Test Big SortedList ******************");
        SortedList sl = new SortedList();
        sl.setComparator(new HoursComparator());
        List<WorkOrder> list = OrderProcessing.getOrders(100);
        Collections.shuffle(list);
        for (int i = 0; i< list.size(); ++i) {
            sl.add(list.get(i));
            //System.out.println("Add from test");
        }
        sl.arrange();
        //sl.dumpContainer();
        for (int i = 0; i < list.size(); ++i) {
            System.out.println(sl.next());
        }
    }

    @Test
    public void testQsortMedByPriority() {
        System.out.println("************ Test Med SortedList by Priority ******************");
        SortedList sl = new SortedList();
        sl.setComparator(new PriorityComparator());
        List<WorkOrder> list = OrderProcessing.getOrders(40);
        Collections.shuffle(list);
        for (int i = 0; i< list.size(); ++i) {
            sl.add(list.get(i));
            //System.out.println("Add from test");
        }
        sl.arrange();
       // sl.dumpContainer();
        for (int i = 0; i < list.size(); ++i) {
            System.out.println(sl.next());
        }
    }


    @Test
    public void testShortestJobFirstSchedule() {
        OrderProcessing opt = new OrderProcessing(100);
        SortedList sl = new SortedList();
        sl.setComparator(new HoursComparator());
        //sl.arrange();
        //sl.dumpContainer();
        System.out.println(" ********** Shortest Job First ************");
        opt.runSimulation(sl);
    }

    @Test
    public void testFirstComeFirstServedSchedule() {
        OrderProcessing opt = new OrderProcessing(100);
        System.out.println("*********  First Come First Serve **********");
        opt.runSimulation(new Queue());
    }

    @Test
    public void testLongestJobFirstSchedule() {
        OrderProcessing opt = new OrderProcessing(100);
        opt.printWorkOrders();
        Heap h = new Heap();
        h.setComparator(new HoursComparator());
        System.out.println("******************** Test longest job first *******************");
        opt.runSimulation(h);
    }

    @Test
    public void testHighestPriorityJobFirstSchedule() {
        OrderProcessing opt = new OrderProcessing(100);
        opt.printWorkOrders();
        SortedList h = new SortedList();
        h.setComparator(new PriorityComparator());
        System.out.println("******************** Test highest priority job first *******************");
        opt.runSimulation(h);
    }


}

