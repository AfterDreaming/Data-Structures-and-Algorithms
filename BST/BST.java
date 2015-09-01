import java.util.*;

/**
 * The BST class implements BinaryTree interface and it provides us a
 * way to maintaining dynamic sorted data by  encoding the binary search algorithm into
 * its structure.
 *
 * @author Yufan Zhuang
 * @version 1.0
 * @param <E> the type of the elements to store in the BST
 */
public class BST<E extends Comparable<E>> implements BinaryTree<E>{

    private class BNode {
        E data;
        BNode left, right, parent;
        private BNode(E item) { data = item;}
    }
    private BNode root;
    private int size;

    /**
     * Adds the item to the tree.  Duplicate items and null items should not be added.
     * Runs in O(log n) expected time, may be linear time in worst case
     *
     * @param item the item to add
     * @return true if item added, false if it was not
     */
    public boolean add(E item) {
        if(item == null || contains(item)) {return  false;}
        if(root == null) {
            root = new BNode(item);
            size++;
            return true;
        } else {
            return add(item, root);
        }
    }

    private boolean add(E item, BNode root){
        if (item.compareTo(root.data) == 0){return  false;}
        if (item.compareTo(root.data) < 0){
            if(root.left == null){
                root.left = new BNode(item);
                root.left.parent = root;
                size++;
                return  true;
            } else {
                return add(item, root.left);
            }
        }else {
            if(root.right == null){
                root.right = new BNode(item);
                root.right.parent = root;
                size++;
                return  true;
            } else {
                return add(item, root.right);
            }
        }
    }
    /**
     * returns the maximum element held in the tree.  null if tree is empty.
     * runs in O(log n) expected, may be linear in worst case
     *
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
     * returns the number of items in the tree
     * runs in O(1)
     *
     * @return the size of the BST
     */
    public int size() {
        return size;
    }

    /**
     * runs in O(1)
     *
     * @return true if tree has no elements, false if tree has anything in it.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * returns the minimum element held in the tree.  null if tree is empty.
     * runs in O(log n) expected, may be linear in worst case
     *
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
     * Checks for the given item in the tree.
     * runs in O(log n) expected, may be linear in worst case
     *
     * @param item the item to look for
     * @return true if item is in tree, false otherwise
     */
    public boolean contains(E item) {
        if (size == 0 ){return  false;}
        return containsChild(item, root);
    }

    private boolean containsChild(E item, BNode current){
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
     * removes the given item from the tree
     * runs in O(log n) expected, may be linear in worst case
     * use in-order successor if necessary
     *
     * @param item the item to remove
     * @return true if item removed, false if item not found
     */
    public boolean remove(E item){
        if (size == 0 || root == null || item == null ){return  false;}
        return remove(item, root);
    }

    private  boolean remove(E item, BNode current){
        if(item == null || current == null) {return false;}
        if (item.compareTo(current.data) == 0){
            //if leaf node
            if(current.left == null && current.right == null){
                //if this node is in the left subtree
                if(current.parent != null){
                    if(current.parent.left != null && current.parent.left.data.compareTo(item) == 0){
                        current.parent.left = null;
                    } else {
                        current.parent.right = null;
                    }
                } else {
                    root = null;
                }


            } else if (current.left == null || current.right == null) {

            //if toRemove current has only one subtree
                if (current.left == null){//if only has right subtree
                    //determine current in left or right subtree of the parents'
                    if(current.parent.left.data.compareTo(item) == 0){
                        current.parent.left = current.right;
                        current.right.parent = current.parent;
                    } else {
                        current.parent.right = current.right;
                        current.right.parent = current.parent;
                    }
                }else {//if toRemove only has left subtree
                    //determine current in left or right subtree of the parents'
                    if(current.parent.left.data.compareTo(item) == 0){
                        current.parent.left = current.left;
                        current.left.parent = current.parent;
                    } else {
                        current.parent.right = current.left;
                        current.left.parent = current.parent;
                    }
                }
            } else {

            //if the node has two subtree
                current.data = minAndDelete(current.right);
            }
            size--;
            return true;
        }
        if (item.compareTo(current.data) < 0){
            if(current.left == null){
                return false;
            } else {
                return remove(item, current.left);
            }
        }else {
            if(current.right == null){
                return false;
            } else {
                return remove(item, current.right);
            }
        }
    }

    //find the min in the subtree of current
    private  E minAndDelete(BNode current){
        if (current.left == null){
            //delete the node
            current.parent.left = null;
            return  current.data;
        }
        return minAndDelete(current.left);
    }
    /**
     * returns an iterator over this collection
     *
     * iterator is based on an in-order traversal
     */
    public Iterator<E> iterator() {
        List<E> inorderTraversal = getInOrder();
        return  inorderTraversal.iterator();
    }

    /**
     * Runs in linear time
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
     * Runs in linear time
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
     * Runs in linear time
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
    /**
     * Runs in linear time??????????why
     * Removes all the elements from this tree
     */
    public void clear() {
        size = 0;
        root = null;
    }
}
