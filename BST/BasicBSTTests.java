import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


/**
 * Binary Search Tree tests for
 * CS 1332 Homework 05
 *
 * Created February 11, 2014
 *
 * @author Jonthan Jemson
 * @version 1.0
 */
public class BasicBSTTests {
    private BinaryTree<Integer> tree;
    @Before
    public void setUp() throws Exception {
        tree = new BST<Integer>();
    }

    /*
     * Null Argument Tests
     *
     * These tests check to ensure
     * IllegalArgumentException is thrown
     * when null is passed in as a parameter
     * to the various methods.
     */
    @Test(timeout = 200, expected = IllegalArgumentException.class)
    public void testNullAdd() {
        tree.add(null);
    }
    @Test(timeout = 200, expected = IllegalArgumentException.class)
    public void testNullAddAll() {
        tree.add(null);
        assertNull(tree.contains(null));
    }
    @Test(timeout = 200, expected = IllegalArgumentException.class)
    public void testNullRemove() {
        assertNull(tree.remove(null));
    }
    @Test(timeout = 200, expected = IllegalArgumentException.class)
    public void testNullContains() {
        assertFalse(tree.contains(null));
    }
    @Test(timeout = 200, expected = IllegalArgumentException.class)
    public void testNullGet() {
        assertNull(tree.contains(null));
    }

    /*
     * Basic Tree tests
     * A tree consisting of three nodes (25, 50, 75)
     */

    /**
     * Adds a basic tree of three nodes.
     * Used for the basic tests.
     */
    private void addBasicTree() {
        tree.add(50);
        tree.add(25);
        tree.add(75);
        tree.add(15);
        tree.add(20);
        tree.add(80);
        tree.add(70);
        tree.add(55);
        tree.add(40);
        tree.add(35);
        tree.add(30);
        tree.add(10);
        tree.add(60);
        tree.add(65);
        tree.add(85);
        tree.add(45);
    }
    @Test(timeout = 200)
    public void testBasicAdd() throws Exception {
        addBasicTree();
        assertTrue(tree.contains(new Integer(50)));
        assertTrue(tree.contains(new Integer(25)));
        assertTrue(tree.contains(new Integer(75)));
        
        List<Integer> preOrder = tree.getPreOrder();
        List<Integer> inOrder = tree.getInOrder();
        List<Integer> postOrder = tree.getPostOrder();
        List<Integer> levelOrder = tree.getLevelOrder();
        
        assertArrayEquals(new Integer[]{50, 25, 15, 10, 20, 40, 35, 30, 45, 75, 70, 55, 60, 65, 80, 85}, preOrder.toArray());
        assertArrayEquals(new Integer[]{10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85}, inOrder.toArray());
        assertArrayEquals(new Integer[]{10, 20, 15, 30, 35, 45, 40, 25, 65, 60, 55, 70, 85, 80, 75, 50}, postOrder.toArray());
        assertArrayEquals(new Integer[]{50, 25, 75, 15, 40, 70, 80, 10, 20, 35, 45, 55, 85, 30, 60, 65}, levelOrder.toArray());
    }



    @Test(timeout = 200)
    public void testBasicRemove() throws Exception {
        addBasicTree();
        
        assertEquals(new Integer(50), tree.remove(50));
        
        assertFalse(tree.contains(new Integer(50)));
        assertTrue(tree.contains(new Integer(25)));
        assertTrue(tree.contains(new Integer(75)));
        
        List<Integer> preOrder = tree.getPreOrder();
        List<Integer> inOrder = tree.getInOrder();
        List<Integer> postOrder = tree.getPostOrder();
        List<Integer> levelOrder = tree.getLevelOrder();
        
        
        assertArrayEquals(new Integer[]{55, 25, 15, 10, 20, 40, 35, 30, 45, 75, 70, 60, 65, 80, 85}, preOrder.toArray());
        assertArrayEquals(new Integer[]{10, 15, 20, 25, 30, 35, 40, 45, 55, 60, 65, 70, 75, 80, 85}, inOrder.toArray());
        assertArrayEquals(new Integer[]{10, 20, 15, 30, 35, 45, 40, 25, 65, 60, 70, 85, 80, 75, 55}, postOrder.toArray());
        assertArrayEquals(new Integer[]{55, 25, 75, 15, 40, 70, 80, 10, 20, 35, 45, 60, 85, 30, 65}, levelOrder.toArray());
        
        assertEquals(new Integer(75), tree.remove(75));

        assertTrue(tree.contains(new Integer(25)));
        assertFalse(tree.contains(new Integer(75)));
        
        preOrder = tree.getPreOrder();
        inOrder = tree.getInOrder();
        postOrder = tree.getPostOrder();
        levelOrder = tree.getLevelOrder();
        
        assertArrayEquals(new Integer[]{55, 25, 15, 10, 20, 40, 35, 30, 45, 80, 70, 60, 65, 85}, preOrder.toArray());
        assertArrayEquals(new Integer[]{10, 15, 20, 25, 30, 35, 40, 45, 55, 60, 65, 70, 80, 85}, inOrder.toArray());
        assertArrayEquals(new Integer[]{10, 20, 15, 30, 35, 45, 40, 25, 65, 60, 70, 85, 80, 55}, postOrder.toArray());
        assertArrayEquals(new Integer[]{55, 25, 80, 15, 40, 70, 85, 10, 20, 35, 45, 60, 30, 65}, levelOrder.toArray());

        assertEquals(new Integer(25), tree.remove(25));
        assertFalse(tree.isEmpty());        
        
        preOrder = tree.getPreOrder();
        inOrder = tree.getInOrder();
        postOrder = tree.getPostOrder();
        levelOrder = tree.getLevelOrder();
        
        assertArrayEquals(new Integer[]{55, 30, 15, 10, 20, 40, 35, 45, 80, 70, 60, 65, 85}, preOrder.toArray());
        assertArrayEquals(new Integer[]{10, 15, 20, 30, 35, 40, 45, 55, 60, 65, 70, 80, 85}, inOrder.toArray());
        assertArrayEquals(new Integer[]{10, 20, 15, 35, 45, 40, 30, 65, 60, 70, 85, 80, 55}, postOrder.toArray());
        assertArrayEquals(new Integer[]{55, 30, 80, 15, 40, 70, 85, 10, 20, 35, 45, 60, 65}, levelOrder.toArray());
    }
    

    @Test(timeout = 200)
    public void testBasicContains() throws Exception {
        addBasicTree();
        assertTrue(tree.contains(50));
        assertTrue(tree.contains(25));
        assertTrue(tree.contains(75));
        assertFalse(tree.contains(12));
        assertFalse(tree.contains(99));
    }

    @Test(timeout = 200)
    public void testBasicPreOrder() throws Exception {
        addBasicTree();
        List<Integer> preOrder = new ArrayList<Integer>();
        preOrder.add(50);
        preOrder.add(25);
        preOrder.add(15);
        preOrder.add(10);
        preOrder.add(20);
        preOrder.add(40);
        preOrder.add(35);
        preOrder.add(30);
        preOrder.add(45);
        preOrder.add(75);
        preOrder.add(70);
        preOrder.add(55);
        preOrder.add(60);
        preOrder.add(65);
        preOrder.add(80);
        preOrder.add(85);       
        assertEquals(preOrder, tree.getPreOrder());
    }

    @Test(timeout = 200)
    public void testBasicInOrder() throws Exception {
        addBasicTree();
        List<Integer> inOrder = new ArrayList<Integer>();
        inOrder.add(10);
        inOrder.add(15);
        inOrder.add(20);
        inOrder.add(25);
        inOrder.add(30);
        inOrder.add(35);
        inOrder.add(40);
        inOrder.add(45);
        inOrder.add(50);
        inOrder.add(55);
        inOrder.add(60);
        inOrder.add(65);
        inOrder.add(70);
        inOrder.add(75);
        inOrder.add(80);
        inOrder.add(85);
        assertEquals(inOrder, tree.getInOrder());
    }

    @Test(timeout = 200)
    public void testBasicPostOrder() throws Exception {
        addBasicTree();
        List<Integer> postOrder = new ArrayList<Integer>();
        postOrder.add(10);
        postOrder.add(20);
        postOrder.add(15);
        postOrder.add(30);
        postOrder.add(35);
        postOrder.add(45);
        postOrder.add(40);
        postOrder.add(25);
        postOrder.add(65);
        postOrder.add(60);
        postOrder.add(55);
        postOrder.add(70);
        postOrder.add(85);
        postOrder.add(80);
        postOrder.add(75);
        postOrder.add(50);
        assertEquals(postOrder, tree.getPostOrder());
    }

    @Test(timeout = 200)
    public void testBasicLevelOrder() throws Exception {
        addBasicTree();
        List<Integer> levelOrder = new ArrayList<Integer>();
        levelOrder.add(50);
        levelOrder.add(25);
        levelOrder.add(75);
        levelOrder.add(15);
        levelOrder.add(40);
        levelOrder.add(70);
        levelOrder.add(80);
        levelOrder.add(10);
        levelOrder.add(20);
        levelOrder.add(35);
        levelOrder.add(45);
        levelOrder.add(55);
        levelOrder.add(85);
        levelOrder.add(30);
        levelOrder.add(60);
        levelOrder.add(65);
        assertEquals(levelOrder, tree.getLevelOrder());
    }
    
    @Test(timeout = 200)
    public void testBasicIsEmpty() throws Exception {
        assertTrue(tree.isEmpty());
        addBasicTree();
        assertFalse(tree.isEmpty());
    }

    @Test(timeout = 200)
    public void testBasicSize() throws Exception {
        assertEquals(0, tree.size());
        addBasicTree();
        assertEquals(16, tree.size());
    }

    @Test(timeout = 200)
    public void testBasicClear() throws Exception {
        assertTrue(tree.isEmpty());
        testBasicAdd();
        assertFalse(tree.isEmpty());
        tree.clear();
        assertEquals(0, tree.size());
        assertTrue(tree.isEmpty());
    }

    /*
     * Empty tree tests
     * Make sure the tree works when
     * there is no data inside it.
     */

    @Test(timeout = 200)
    public void testEmptyRemove() throws Exception {
        assertNull(tree.remove(50));
        assertNull(tree.remove(25));
        assertNull(tree.remove(75));
    }

    @Test(timeout = 200)
    public void testEmptyContains() throws Exception {
        assertFalse(tree.contains(50));
        assertFalse(tree.contains(25));
        assertFalse(tree.contains(75));
        assertFalse(tree.contains(12));
        assertFalse(tree.contains(99));
    }

    @Test(timeout = 200)
    public void testEmptyGet() throws Exception {
        assertFalse(tree.contains(50));
        assertFalse(tree.contains(25));
        assertFalse(tree.contains(75));
        assertFalse(tree.contains(50));
        assertFalse(tree.contains(99));

    }

    @Test(timeout = 200)
    public void testEmptyPreOrder() throws Exception {
        List<Integer> preOrder = new ArrayList<Integer>();
        assertEquals(preOrder, tree.getPreOrder());
    }

    @Test(timeout = 200)
    public void testEmptyInOrder() throws Exception {
        List<Integer> inOrder = new ArrayList<Integer>();
        assertEquals(inOrder, tree.getInOrder());
    }

    @Test(timeout = 200)
    public void testEmptyPostOrder() throws Exception {
        List<Integer> postOrder = new ArrayList<Integer>();
        assertEquals(postOrder, tree.getPostOrder());
    }

    @Test(timeout = 200)
    public void testEmptyLevelOrder() throws Exception {
        List<Integer> postOrder = new ArrayList<Integer>();
        assertEquals(postOrder, tree.getLevelOrder());
    }

    
    @Test(timeout = 200)
    public void testEmptyIsEmpty() throws Exception {
        assertTrue(tree.isEmpty());
    }

    @Test(timeout = 200)
    public void testEmptySize() throws Exception {
        assertEquals(0, tree.size());
    }

    @Test(timeout = 200)
    public void testEmptyClear() throws Exception {
        assertTrue(tree.isEmpty());
        tree.clear();
        assertEquals(0, tree.size());
        assertTrue(tree.isEmpty());
        assertTrue(tree.isEmpty());
    }

    @Test(timeout = 200)
    public void testToString() throws Exception {
        assertEquals(tree.toString(), "()");
        tree.add(25);
        tree.add(12);
        assertEquals(tree.toString(), "(25(12()())())");
        tree.add(37);
        tree.add(38);
        assertEquals(tree.toString(), "(25(12()())(37()(38()())))");
    }

}