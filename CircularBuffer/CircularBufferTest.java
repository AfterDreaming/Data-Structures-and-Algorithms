import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

//170 points total
public class CircularBufferTest {
    CircularBuffer<String> buff;
    @Before
    public void setUp() throws Exception {
        //This statement will give error until you implement the class CircularArrayBuffer<T>
        buff = new CircularArrayBuffer<String>();
    }

    @Test
    public void testIsEmptyAtFirst() {
        assertTrue("Initial buffer not empty  -5 points", buff.isEmpty());
    
    }
    
    @Test
    public void testIsEmptyAfterAdd() {
        buff.add("A");
        assertTrue("Thinks its empty after an add -5 points", !buff.isEmpty());
    }
    
    @Test
    public void testIsEmptyAfterLastElementDeleted() {
        buff.add("A");
        buff.remove();
        assertTrue("Thinks it has something after remove of last item -5 points", buff.isEmpty());
    }

    @Test
    public void testInitialCapacity() {
        assertEquals("Initial Capacity not correct -5 points", 10, buff.capacity());
    }
    
    @Test
    public void testCapacityAfterRegrow() {
        addItems(14);
        assertEquals("Capacity after regrow incorrect -5 points", 20, buff.capacity());
    }
    
    @Test
    public void testCapacityAfterOverwrite() {
        buff.setMode(BufferGrowMode.OVERWRITE);
        addItems(14);
        assertEquals("Capacity after regrow incorrect -5 points", 10, buff.capacity());
    }
    
    private void addItems(int count) {
        for (int i = 0; i < count; i++) {
            buff.add("A" + i);
        }
    }

    @Test
    public void testInitialSize() {
        assertEquals("Initial Size not correct -5 points", 0, buff.size());
    }
    
    @Test
    public void testSizeAfterOneInsert() {
        buff.add("C");
        assertEquals("Size after one addition wrong -5 points", 1, buff.size());
    }
    
    @Test
    public void testSizeAfterRemoveLastOne() {
        buff.add("C");
        buff.remove();
        assertEquals("Size after removing last item wrong -5 points", 0, buff.size());
    }
    
    @Test 
    public void testSizeAfterRemoveMultiple() {
        addItems(14);
        buff.remove();
        buff.remove();
        buff.remove();
        buff.remove();
        buff.remove();
        assertEquals("Size after removing multiple things wrong -5 points", 9, buff.size());
        
    }
    
    @Test
    public void testSizeWithRegrow() {
        addItems(15);
        assertEquals("Size after regrow wrong -5 points", 15, buff.size());
    }
    
    @Test
    public void testSizeWithOverwrite() {
        buff.setMode(BufferGrowMode.OVERWRITE);
        addItems(15);
        assertEquals("Size with overwrite wrong -5 points", 10, buff.size());
    }

    @Test
    public void testAddOneItem() {
       buff.add("C");
       assertTrue("Actually added the item -5 points", buff.contains("C"));
    }
    
    @Test
    public void testAddMultipleItemsRegrow() {
        addItems(15);
        assertTrue("Missing item after added into regrown container -5 points", buff.contains("A14"));
        assertTrue("Missing item after added into regrown container -5 points", buff.contains("A10"));
        assertTrue("Missing item afer added into regrown container -5 points", buff.contains("A9"));
        assertTrue("Missing item after added into regrown container -5 points", buff.contains("A0"));
        assertEquals("Didn't add all items -5 points", 15, buff.size());
        assertEquals("Didn't reset head -5 points", "A0", buff.remove());
    }
    
    @Test
    public void testAddMultipleItemsOverwrite() {
        buff.setMode(BufferGrowMode.OVERWRITE);
        addItems(15);
        assertTrue("Missing item after added into regrown container -5 points", buff.contains("A14"));
        assertTrue("Missing item after added into regrown container -5 points", buff.contains("A5"));
        assertEquals("Didn't add all items -5 points", 10, buff.size());
        assertEquals("Didn't set front correctly -5 points", "A5", buff.remove());
    }
    
    @Test
    public void testRegrowWithWrap() {
        addItems(9);
        buff.remove();
        buff.remove();
        buff.remove();
        buff.add("A10");
        buff.add("A11");
        buff.add("A12");
        buff.add("A13");
        buff.add("A14");
        assertFalse("Item should not be present -5 points", buff.contains("A0"));
        assertFalse("Item should not be present -5 points", buff.contains("A2"));
        assertTrue("Item not found -5 points", buff.contains("A3"));
        assertTrue("Item not found -5 points", buff.contains("A14"));
    }
    

    @Test
    public void testRemoveOne() {
        buff.add("C");
        assertEquals("Did not get back the one item in buffer -5 points", "C", buff.remove());
    }
    
    @Test
    public void testRemoveWhenFull() {
        addItems(9);
        assertEquals("Remove from full buffer wrong -5 points", "A0", buff.remove());
        assertEquals("Remove from full buffer wrong -5 points", "A1", buff.remove());
    }
  
    @Test
    public void testRemoveWhenEmpty() {
        assertNull("Remove when empty did not return null -5 points", buff.remove());
    }
    
    @Test
    public void testRemoveWhenEmptyAfterDeletesRegrow() {
    	addItems(15);
    	for (int i = 0; i < 15; i++) {
    		buff.remove();
    	}
    	assertNull("Remove when empty after deletes not null -5 points", buff.remove());
    }
    
    @Test
    public void testRemoveWhenEmptyAfterDeletesOverwrite() {
    	buff.setMode(BufferGrowMode.OVERWRITE);
    	addItems(15);
    	for (int i = 0; i < 15; i++) {
    		buff.remove();
    	}
    	assertNull("Remove when empty after deletes not null -5 points", buff.remove());
    }
    
    @Test
    public void testRemoveWrappedAfterRegrow() {
        addItems(10);
        buff.remove();
        buff.remove();
        buff.remove();
        buff.add("A99");
        buff.add("A100");
        buff.add("A101");
        buff.add("A102");
        buff.add("A103");
        assertEquals("Remove from wrapped after regrow wrong -5 points", "A3", buff.remove());
    }


}
