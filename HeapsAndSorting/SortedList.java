import java.util.Comparator;

/**
 * Created by Yufan on 6/29/2015.
 */
public class SortedList implements Container {
    private Comparator comparator;
    private WorkOrder[] orderTable = new WorkOrder[15];
    private int size, count;
    @Override
    public void setComparator(Comparator comp) {
        this.comparator = comp;
    }

    @Override
    public void add(WorkOrder wo) {
        if(size == orderTable.length - 1){
            //System.out.println("run regrow");
            regrow();
        }
        orderTable[size] = wo;
        size++;
    }

    @Override
    public void arrange() {
        if(size <= 10){
            insertionSort();
        } else {
            quickSort(orderTable,0, size-1);
        }
    }

    @Override
    public void dumpContainer() {
        System.out.println("---------dumpContainer--------------");
        for(int i = 0; i < size; i++){
            System.out.println("WO #. " + i + " name: " + orderTable[i].getName()+"priority: " + orderTable[i].getPriority() + " hour: " + orderTable[i].getHours());
        }
    }

    @Override
    public boolean hasNext() {
        return count < size;
    }

    @Override
    public WorkOrder next() {
        if(size == 0 || !hasNext() ) {return null;}
        count++;
        return orderTable[count - 1];
    }

    private void regrow(){
        WorkOrder[] toReturn = new WorkOrder[size * 2];
        for(int i = 0; i < size; i++){
            toReturn[i] = orderTable[i];
        }
        orderTable = toReturn;
    }
    private void insertionSort(){
        for(int i = 0; i < size; i++ ){
            int j = i;
            while( j != 0){
                if(comparator.compare(orderTable[j],orderTable[j-1]) < 0){
                    swap(orderTable,j,j-1);
                }
                j--;
            }
        }
    }
    private void quickSort(WorkOrder[] arr,int lowIndex, int highIndex){
        //if(size == 0) return;
        if((highIndex - lowIndex) > 0){
            int partitionIndex = findPartition(arr,lowIndex,highIndex);
            quickSort(arr,lowIndex,partitionIndex-1);
            quickSort(arr,partitionIndex + 1,highIndex);
        }
    }

    private int findPartition(WorkOrder[] arr,int min,int max){
        int left = min;
        int right = max;
        int middle = (left+right)/2;
        int median;
        if(comparator.compare(arr[left],arr[middle]) > 0){
            if(comparator.compare(arr[middle],arr[right]) > 0){
                median = middle;
            }else if(comparator.compare(arr[left],arr[right]) > 0){
                median = right;
            }else{
                median = left;
            }
        } else {
            if(comparator.compare(arr[left],arr[right]) > 0){
                median = left;
            }else if(comparator.compare(arr[left],arr[right]) > 0){
                median = right;
            }else{
                median = middle;
            }
        }

        WorkOrder partitionElement = arr[median];
        while(left < right){
            while(comparator.compare(arr[left],partitionElement) <= 0 && left < right){
                left++;
            }
            while(comparator.compare(arr[right],partitionElement) > 0){
                right--;
            }
            if(left < right){
                swap(arr,left,right);
            }
        }
        swap(arr,min,right);
        return right;
    }

    private void swap(WorkOrder[] arr,int i, int j){
        WorkOrder temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
