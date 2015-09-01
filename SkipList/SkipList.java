/**
 * Created by Yufan on 7/10/2015.
 */

import java.util.*;

public class SkipList<K extends Integer, V> implements Map<K, V> {
    private int size,level;
    private static Random rand;
    private static final int MAX_LEVEL = 8;
    private Node head;

    public SkipList(){
        rand = new Random();
        size = 0;
        head = new Node(Integer.MIN_VALUE,null,0);

    }
    private class Node implements Map.Entry<K, V>,Comparable{
        Integer key;
        V data;
        Node up,down,next;
        int level;
        @Override
        public K getKey() {return (K)key;}

        @Override
        public V getValue() {return data;}

        @Override
        public V setValue(V value) {return data = value;}

        private Node(Integer key,V data, int level,Node next,Node up,Node down){
            this.key = key;
            this.data = data;
            this.next = next;
            this.up = up;
            this.down = down;
            this.level = level;
        }
        private Node(Integer key,V data,int level){
            this(key,data,level,null,null,null);
        }
        public String toString(){
            return key.toString();
        }

        @Override
        public int compareTo(Object o) {
            Node e;
            e = (Node)o ;
            return key.compareTo(e.getKey());
        }
    }



    @Override
    public int size() {return size;}


    @Override
    public boolean isEmpty() {return size == 0;}


    @Override
    public boolean containsKey(Object key) {
        if(key == null || size ==0){return false;}
        //check down
        Node current = head;
        int existLevel = head.level;
        while(existLevel >= 0){
            while(current.next != null && current.next.key.compareTo((K)key) < 0){
                current = current.next;
            }
            //if the key is exist
            if(current.next != null && current.next.key.compareTo((K)key)==0){
                return true;
            }
            if(existLevel > 0 ){
                current = current.down;
            }
            existLevel--;
        }
        return false;
    }


    @Override
    public boolean containsValue(Object value) {
        Node current = head;
        //go to the bottom level
        while(current.level > 0){
            current = current.down;
        }
        while (current.next != null){
            if(current.next != null && current.next.data.equals((V)value)){
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public V get(Object key) {
        if(key == null || size ==0){return null;}
        //check down
        Node current = head;
        int existLevel = head.level;
        while(existLevel >= 0){
            while(current.next != null && current.next.key.compareTo((K)key) < 0){
                current = current.next;
            }
            //if the key is exist
            if(current.next != null && current.next.key.compareTo((K)key)==0){
                return current.next.data;
            }
            if(existLevel > 0 ){
                current = current.down;
            }
            existLevel--;
        }
        return null;
    }


    @Override
    public V put(K key, V value) {
        if(key == null){return null;}

        Node current = head;
        while(current != null){
            while(current.next != null && current.next.key.compareTo(key) < 0){
                current = current.next;
            }
            if(current.next != null && current.next.key.compareTo(key)==0){
                //if the key exists
                V toReturn;
                current = current.next;
                toReturn = current.data;
                while(current != null) {
                    current.data = value;
                    current = current.down;
                }
                return toReturn;
            }
            current = current.down;
        }


        int existLevel = 0;
        while(ifUpper()){
            existLevel++;
        }
        //if the exist level is higher than the head, build up
        if(existLevel > head.level){
            for(int i = 0;i < (existLevel - head.level); i++){
                Node old = head;
                head = new Node(Integer.MIN_VALUE,null,old.level+1);
                head.down = old;
                old.up = head;
            }
        }
        current = head;
        int insertLevel = head.level;
        //build down
        Node upper = null;
        while(insertLevel >= 0){
            //skip upper nodes
            while(insertLevel > existLevel){
                while(current.next != null && current.next.key.compareTo(key) < 0){
                    current = current.next;
                }
                insertLevel--;
                current = current.down;
            }
            while(current.next != null && current.next.key.compareTo(key) < 0){
                current = current.next;
            }
                //if the key does not exist
                Node oldNext = current.next;
                current.next = new Node(key,value,insertLevel);
                current.next.next = oldNext;
                current.next.level = insertLevel;
            if(upper != null && insertLevel < existLevel){
                upper.down = current.next;
                current.next.up = upper;
            }
            //current is toAdd's prev
            upper = current.next;
            if(insertLevel > 0 ){current = current.down;}//go down by prev node
            insertLevel--;
        }
        size++;
        return null;
    }


    @Override
    public V remove(Object key) {
        if(key == null || size ==0){return null;}
        //check down
        V toReturn = null;
        //current is target's prev
        Node current = head;
        int existLevel = head.level;
        while(existLevel >= 0){
            while(current.next != null && current.next.key.compareTo((K)key) < 0){
                current = current.next;
            }
            //if the key is exist
            if(current.next != null && current.next.key.compareTo((K)key)==0){
                toReturn = current.next.data;
                current.next = current.next.next;
            }
            //check down row
            if(existLevel > 0 ){
                current = current.down;
            }
            existLevel--;
        }
        if(toReturn != null){size--;}
        return toReturn;
    }


    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for(Entry bucket: m.entrySet()){
            put((K) bucket.getKey(), (V) bucket.getValue());
        }
    }

    /**
     * Removes all of the mappings from this map (optional operation).
     * The map will be empty after this call returns.
     *
     * @throws UnsupportedOperationException if the <tt>clear</tt> operation
     *                                       is not supported by this map
     */
    @Override
    public void clear() {
        size = 0;
        head = new Node(Integer.MIN_VALUE,null,0);
    }


    @Override
    public Set<K> keySet() {
        Set<K> toReturn = new HashSet<>();
        Node current = head;
        while(current.level > 0){
            current = current.down;
        }
        while (current != null){
            if(current.key != Integer.MIN_VALUE){
                toReturn.add((K)current.key);
            }
            current = current.next;
        }
        return toReturn;
    }


    @Override
    public Collection<V> values() {
        Collection<V> toReturn = new ArrayList<>();
        Node current = head;
        while(current.level > 0){
            current = current.down;
        }
        while (current != null){
            if(current.key != Integer.MIN_VALUE){
                toReturn.add(current.data);
            }
            current = current.next;
        }
        return toReturn;
    }


    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> toReturn = new TreeSet<>();
        Node current = head;
        while(current.level > 0){
            current = current.down;
        }
        while (current != null){
            if(current.key != Integer.MIN_VALUE){
                toReturn.add(current);
            }
            current = current.next;
        }
        return toReturn;
    }

    private boolean ifUpper(){
        return rand.nextBoolean();
    }
    public void showInside(){
        Node current = head;
        int currentLevel = head.level;

        while(currentLevel >= 0){
            String levelOutPut = null;
            while(current.next!= null){
                levelOutPut += current.data + " ";
                current = current.next;
            }
            System.out.println("level number: "+ currentLevel +" has "+ levelOutPut);
            currentLevel--;
        }
    }
}
