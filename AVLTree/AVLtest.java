import java.util.ArrayList;
import java.util.List;


/**
 * Created by Yufan on 6/11/2015.
 */
public class AVLtest {
    public static void main(String[] args){
        AVLTree<String> tree = new AVLTree<String>();
        String[] singleRotateLevelTraversalResults = {"M", "D", "X", "B", "H", "T", "Z"};
        String[] testStringsSingleRotate = {"M", "H", "T", "D", "B", "X", "Z"} ;
        for (int i = 0; i < testStringsSingleRotate.length-3; ++i) {
            boolean flag = tree.add(testStringsSingleRotate[i]);
        }
        List<String> list = tree.getLevelOrder();
        //ArrayList<Integer> list2 = tree.testGetLevelOrderHeight();
        for (int i = 0; i < list.size(); ++ i) {
            //System.out.println(list.get(i) +" height is: " + list2.get(i) );
        }
    }

}
