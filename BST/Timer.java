import java.util.TreeSet;

/**
 * Created by robertwaters on 5/22/15.
 */
public class Timer {
    public static final int TEST_INPUT_SIZE = 5000;
    public static void main(String[] args) {
        System.out.println("Timing Test");
        System.out.println(TEST_INPUT_SIZE + " entries  Built-in tree");
        TreeSet<Integer> javaTree = new TreeSet<>();

        for (int i = 0; i < TEST_INPUT_SIZE; ++i) {
            javaTree.add(i);
        }

        long start = System.nanoTime();
        javaTree.contains(30000);
        long stop = System.nanoTime();

        System.out.println("Time to find not there: " + (stop - start));

        start = System.nanoTime();
        javaTree.contains(5000);
        stop = System.nanoTime();

        System.out.println("Time to find last item: " + (stop - start));

        javaTree = null;

        System.out.println("\nYour Tree: ");

        BST<Integer> myTree = new BST<>();

        for (int i = 0; i < 5000; ++i) {
            myTree.add(i);
        }

        start = System.nanoTime();
        myTree.contains(30000);
        stop = System.nanoTime();

        System.out.println("Time to find not there: " + (stop - start));

        start = System.nanoTime();
        myTree.contains(5000);
        stop = System.nanoTime();

        System.out.println("Time to find last item: " + (stop - start));

    }
}
