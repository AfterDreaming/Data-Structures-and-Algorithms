import java.util.Comparator;

/**
 * Created by Yufan on 6/29/2015.
 */
public class Heap implements Container {
    private Comparator comparator;
    private WorkOrder[] orderTable = new WorkOrder[10];
    private int size;
    @Override
    public void setComparator(Comparator comp) {
        this.comparator = comp;
    }

    @Override
    public void add(WorkOrder wo) {
        //System.out.println("ADD: "+ wo.getHours());
        if(size == orderTable.length-1){regrow();}
        size++;
        orderTable[size] = wo;
        int current = size;
        //System.out.println("current is "+ current);
        while (current/2 > 0 && comparator.compare(orderTable[current],orderTable[current / 2]) > 0 ){
            //System.out.println("SWAP----- Current is: "+ orderTable[current].getHours() +" Parent is: "+ orderTable[current/2].getHours() + " size " + size);
            swap(current, current / 2);
            //System.out.println("SWAP---Finished-- Current is: "+ orderTable[current].getHours() +" Parent is: "+ orderTable[current/2].getHours());
            current = current/2;
        }
    }

    @Override
    public void arrange() {
        for(int i = (size/2); i >= 1 ; i--){
            maxHeapify(i);
        }
    }

    @Override
    public void dumpContainer() {
        System.out.println("*********** dumpContainer********"+ size);
        for(int i = 1; i <= size; i++){
            System.out.println("WO #. " + i + " name: " + orderTable[i].getName() + " priority: " + orderTable[i].getPriority() + " hour: " + orderTable[i].getHours());
        }
    }

    @Override
    public boolean hasNext() {
        return size != 0;
    }

    /**
     * This method should return the next item according the rules of the container
     * Heap - acts same as removeMax() in a standard heap.
     *
     * @return the next item in this container
     */
    public WorkOrder next() {
        if(size == 0 || !hasNext()){return null;}
        WorkOrder toReturn = orderTable[1];
        orderTable[1] = orderTable[size];
        size--;
        maxHeapify(1);
        return toReturn;
    }

    private void regrow(){
        WorkOrder[] toReturn = new WorkOrder[size + 10];
        for(int i = 0; i <= size; i++){
            toReturn[i] = orderTable[i];
        }
        orderTable = toReturn;
    }

    private void swap(int i, int j){
        WorkOrder temp = orderTable[i];
        orderTable[i] = orderTable[j];
        orderTable[j] = temp;
    }

    private void maxHeapify(int i) {
        if( i <= size/2 && size != 0 && (2*i+1)<= size ){
            //System.out.println("In max heap The i is:" + i+" size is " + size);
            //check does children is bigger than parent
            if(comparator.compare(orderTable[i],orderTable[2 * i]) < 0
                    || comparator.compare(orderTable[i], orderTable[2 * i + 1]) < 0){
                //swap with the bigger one
                if(comparator.compare(orderTable[2*i], orderTable[2 * i + 1]) > 0){
                    //System.out.println("Swap in left");
                    swap(i, 2* i);
                    maxHeapify(2*i);
                } else {
                    //System.out.println("Swap in right");
                    swap(i, 2* i + 1);
                    maxHeapify(2*i + 1);
                }
            }
        }
    }
}
