/**
 * This class represents a single workorder coming in
 *
 * @author robertwaters
 *
 */
public class WorkOrder {
    /** the work order number */
    private int number;

    /** the amount of time this will take */
    private int hours;

    /** the time left */
    private int hoursLeft;

    /** the name of the customer */
    private String name;

    /** the priority of the job */
    private int priority;

    /** time started */
    private int startTime;

    /** time finished */
    private int finishTime;

    /**
     * Make a new workorder
     * @param n the number
     * @param h the hours it will take
     * @param s the name of the customer
     */
    public WorkOrder(int n, int h, int p, String s) {
        number = n;
        hours = h;
        hoursLeft = h;
        priority = p;
        name = s;
    }

    public String getName() { return name; }

    public int getHours() { return hours; }

    public int getTimeLeft() { return hoursLeft; }

    public int getNumber() { return number; }

    public int getPriority() { return priority; }

    public void setStartTime(int time) { startTime = time; }

    public void setFinishTime(int time) { finishTime = time; }

    public int getWaitTime() { return startTime; }

    public boolean runOneCycle() {
        --hoursLeft;
        return hoursLeft > 0;
    }

    public String toString() {
        return "WO - " + number + " hours: " + hours + " priority: " + priority + " name: " + name + " Hours left: " + hoursLeft;
    }

}

