import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class OrderProcessing {
    private static final int PROCESSORS = 5;

    private static List<WorkOrder> orders = new ArrayList<>();
    private WorkOrder[] processors = new WorkOrder[PROCESSORS];

    /**
     * make a new simulation with a number of work orders
     *
     * @param count the number of work orders to use for the simulation.
     */
    public OrderProcessing(int count) {
        OrderProcessing.getOrders(count);
    }

    /**
     * main utility method, run the simulation
     *
     * @param cont the container to use for the simulation
     */
    public void runSimulation(Container cont) {
        //first we setup the workorders
        for (WorkOrder wo : orders)
            cont.add(wo);

        //get container in right order
        cont.arrange();

        //set up a simple clock
        int cycles = 1;
        int done = 0;

        //fill the processors for round 1
        for (int i = 0; i < PROCESSORS; ++i) {
            processors[i] = cont.next();
            processors[i].setStartTime(cycles - 1);
        }

        //Now loop till done
        while (true) {
            //increment the clock
            ++cycles;
            //go through each processor
            for (int i = 0; i < PROCESSORS; ++i) {
                //check that it is busy, if so run the job for one tick
                if (processors[i] != null && !processors[i].runOneCycle()) {
                    //our timer has expired since run returned true
                    System.out.println("WorkOrder: " + processors[i] + " completed at cycle: " + cycles);
                    //log our finish time
                    processors[i].setFinishTime(cycles);
                    //check if there are more jobs waiting
                    if (!cont.hasNext()) {
                        //if no more jobs, mark the processor done and increment count of idle processors
                        processors[i] = null ;
                        ++done;
                    }
                    else {
                        //otherwise startup a new job
                        processors[i] = cont.next();
                        processors[i].setStartTime(cycles - 1);
                    }
                }
            }
            //if all processors are idle, we are done.
            if (done >= PROCESSORS) break;

            //System.out.println("Cycle: " + cycles);

        }
        //Print some statistics
        System.out.println("Total Cycles: " + cycles);
        int totalWait = 0;
        for (WorkOrder wo : orders) {
            totalWait += wo.getWaitTime() - 1;
        }
        System.out.println("Total Wait Time: " + totalWait + "  Average Wait Time: " + (totalWait / orders.size()));
    }

    /**
     * Create the workorders and randomize their order.
     *
     * @param count number of workorders to make
     */
    private static void initialize(int count) {
        orders.clear();
        Random rand = new Random(1234);
        for (int i = 0; i < count; ++i) {
            String name =  null;
            if (i < 10 ) name = "00" + i;
            else if (i < 100) name = "0" + i;
            else name = "" + i;
            orders.add(new WorkOrder(i, rand.nextInt(100), rand.nextInt(25), "WO-" + name));
        }
        Collections.shuffle(orders);
    }

    /**
     * return a random collection of workorders
     * @param count the number of orders to make
     * @return a random collection of workorders of size count
     */
    public static List<WorkOrder> getOrders(int count) {
        initialize(count);
        return orders;
    }

    /**
     * For debug, print all the workorders to stdout
     */
    public void printWorkOrders() {
        System.out.println("**Dump Workorders **");
        for (WorkOrder wo : orders)
            System.out.println(wo);
        System.out.println("*********************");
    }

}

