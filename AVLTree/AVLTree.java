import java.util.*;

/**Here is the AVL tree java program which implemnets BSTree interface
 * @author   Yufan Zhuang
 * @version 1.0
 */
public class AVLTree<E extends Comparable<E>> implements BSTree<E> {

    private class BNode {
        E data;
        BNode left, right;
        private BNode(E item) { data = item;}
        private int getBF(){
            if(right == null & left == null){
                return 0;
            } else if (left == null) {
                return (-1-right.getHeight());
            } else if (right == null) {
                return left.getHeight()+1;
            } else{
                return (left.getHeight() - right.getHeight());
            }
        }
        private int getHeight(){
            if(right == null && left == null){
                return 0;
            } else if (left == null) {
                return right.getHeight() + 1;
            } else if (right == null) {
                return left.getHeight() + 1;
            } else{
                if(left.getHeight() > right.getHeight()){
                    return left.getHeight() + 1;
                }else{
                    return right.getHeight() + 1;
                }
            }
        }
    }
    private BNode root;
    private int size;
    /**
     * Adds the item to the tree.  Duplicate items and null items should not be added. O(log n)
     *
     * @param item the item to add
     * @return true if item added, false if it was not
     */
    public boolean add(E item){
        if(item == null || containsChild(item, root)) return false;
        if(isEmpty()){
            root = new BNode(item);
            size++;
            return true;
        }
        addEntry(root, item);
        root = rebalance(root);
        size++;
        return true;
    }

    private boolean addEntry(BNode node, E item){
        boolean result;
        if(item.compareTo(node.data) < 0){
            if(node.left != null){
                result = addEntry(node.left,item);
                node.left = rebalance(node.left);
                return result;
            }else {
                node.left = new BNode(item);//System.out.println(node.left.data + " left height:" + node.left.height+ " size:" + size );
                return true;
            }
        }else if(item.compareTo(node.data) > 0){
            if(node.right != null){
                result = addEntry(node.right, item);
                node.right = rebalance(node.right);
                return result;
            }else{
                node.right = new BNode(item);//System.out.println(node.right.data + " right height:" + node.right.height+ " size:" + size );
                return true;
            }
        }else {//if equal
            return false;
        }
    }

    private BNode rebalance(BNode oob){
        if(oob == null) return null;
        if(oob.getBF() > 1){ //if heavy left
            if(oob.left.getBF() > 0){
                oob = rotateRight(oob);
            } else{//if oob.left is heavy right
                oob = rotateLeftRight(oob);
            }
        } else if (oob.getBF() < -1){ //if heavy right
            if (oob.right.getBF() < 0){
                oob = rotateLeft(oob);
            } else {//if oob.right is heavy left
                oob = rotateRightLeft(oob);
            }
        }
        return oob;
    }

    private BNode rotateLeft(BNode oob){
        BNode newRoot = oob.right;
        oob.right = newRoot.left;
        newRoot.left = oob;
        return newRoot;
    }

    private BNode rotateRight(BNode oob){
        BNode newRoot = oob.left;
        oob.left = newRoot.right;
        newRoot.right = oob;

        return newRoot;
    }

    //heavy left ----heavy right
    private  BNode rotateLeftRight(BNode oob){
        oob.left = rotateLeft(oob.left);
        return rotateRight(oob);
    }

    //heavy right ----heavy left
    private BNode rotateRightLeft(BNode oob){
        oob.right = rotateRight(oob.right);
        return rotateLeft(oob);
    }

    /**
     * returns the maximum element held in the tree.  null if tree is empty. O(log n)
     * @return maximum item or null if empty
     */
    public E max() {
        if (root == null){return  null;}
        return max(root);
    }

    private  E max(BNode current){
        if (current.right == null){return  current.data;}
        return max(current.right);
    }


    /**
     * returns the number of items in the tree O(1) with variable
     *
     * @return size of the tree
     */
    public int size() {
        return size;
    }

    /**
     * O(1)
     * @return true if tree has no elements, false if tree has anything in it.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * O(log n)
     * @return the minimum element in the tree or null if empty
     */
    public E min(){
        if (root == null){return  null;}
        return min(root);
    }

    private  E min(BNode current){
        if (current.left == null){return  current.data;}
        return min(current.left);
    }


    /**
     * Checks for the given item in the tree. O(log n)
     *
     * @param item the item to look for
     * @return true if item is in tree, false otherwise
     */
    public boolean contains(E item) {
        if (size == 0 ){return  false;}
        return containsChild(item, root);
    }

    private boolean containsChild(E item, BNode current){
        if(item == null|| current == null) return false;
        if (item.compareTo(current.data) == 0){return true;}
        if (item.compareTo(current.data) < 0){
            if(current.left == null){
                return false;
            } else {
                return containsChild(item, current.left);
            }
        }else {
            if(current.right == null){
                return false;
            } else {
                return containsChild(item, current.right);
            }
        }
    }

    /**
     * removes the given item from the tree O(log n)
     *
     * @param item the item to remove
     * @return true if item removed, false if item not found
     */
    public boolean remove(E item){
        if(item == null) return false;
        boolean result = contains(item);
        root = removeRecurse(root, item);
        if(result)size--;
        return result;
    }

    private BNode removeRecurse( BNode node, E item){
        if(node == null) return null;
        if(item.compareTo(node.data) < 0){
            node.left = removeRecurse(node.left, item);
        } else if (item.compareTo(node.data) > 0){
            node.right = removeRecurse(node.right,item);
        }else if(node.right != null && node.left != null){//two subtrees
            node.data = min(node.right);
            node.right = removeRecurse(node.right, node.data);//remove the succ node
        } else{//one subtree or leaf node
            node = (node.left != null) ? node.left : node.right;
        }
        return rebalance(node);
    }

    /**
     * returns an iterator over this collection
     * iterator is based on an in-order traversal
     */
    public Iterator<E> iterator() {
        List<E> inorderTraversal = getInOrder();
        return  inorderTraversal.iterator();
    }

    /**
     * O(n)
     * @return a list of the data in post-order traversal order
     */
    public List<E> getPostOrder() {
        ArrayList<E> postOrder = new ArrayList<>();
        getPostOrder(root, postOrder);
        return  postOrder;
    }

    private void getPostOrder(BNode parent, List<E> list){
        if(parent == null){return;}
        getPostOrder(parent.left,list);
        getPostOrder(parent.right,list);
        list.add(parent.data);
    }

    /**
     * O(n)
     * @return a list of the data in level-order traversal order
     */
    public List<E> getLevelOrder(){
        ArrayList<E> levelOrder = new ArrayList<>();
        if(root == null){return levelOrder;}
        Queue<BNode> visited = new ArrayDeque<>();
        visited.add(root);
        BNode current;
        while(!visited.isEmpty()){
            current = visited.poll();
            if(current.left != null) {visited.add(current.left);}
            if(current.right != null) {visited.add(current.right);}
            levelOrder.add(current.data);
        }
        return levelOrder;
    }

    /**
     * O(n)
     * @return a list of the data in pre-order traversal order
     */
    public List<E> getPreOrder(){
        ArrayList<E> preOrder = new ArrayList<>();
        getPreOrder(root, preOrder);
        return  preOrder;
    }
    private void getPreOrder(BNode parent, List<E> list){
        if(parent == null){return;}
        list.add(parent.data);
        getPreOrder(parent.left, list);
        getPreOrder(parent.right,list);

    }

    /**
     * O(1) [ignore garbage collection costs]
     *
     * Removes all the elements from this tree
     */
    public void clear() {
        size = 0;
        root = null;
    }
    /**
     * Runs in linear time O(n) or 0(1) for the best case
     * @return a list of the data in in-order traversal order
     */
    public List<E> getInOrder() {
        ArrayList<E> inOrder = new ArrayList<>();
        getInOrder(root, inOrder);
        return  inOrder;
    }
    private void getInOrder(BNode parent, List<E> list){
        if(parent == null){return;}
        getInOrder(parent.left, list);
        list.add(parent.data);
        getInOrder(parent.right,list);
    }


//for testing
    private ArrayList<Integer> testGetLevelOrderHeight(){
        ArrayList<Integer> levelOrder = new ArrayList<>();
        if(root == null){return levelOrder;}
        Queue<BNode> visited = new ArrayDeque<>();
        visited.add(root);
        BNode current;
        while(!visited.isEmpty()){
            current = visited.poll();
            if(current.left != null) {visited.add(current.left);}
            if(current.right != null) {visited.add(current.right);}
            levelOrder.add(current.getHeight());
        }
        return levelOrder;
    }
}
