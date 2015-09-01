import java.util.*;

/**
 * The CuckooMap class implements the map interface by Cuckoo Hashing
 *
 * @author Yufan  on 6/20/2015.
 * @version 1.0
 */
public class CuckooMap<K extends Hashable, V> implements Map<K, V> {

    private class Bucket<K extends Comparable, V> implements Map.Entry<K, V>, Comparable {
        K key;
        V value;

        public Bucket(K k, V v) {
            key = k;
            value = v;
        }

        public void setKey(K k) {
            if (k == null) return;
            this.key = k;
        }

        public V setValue(V v) {
            V toReturn = value;
            value = v;
            return toReturn;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) return false;
            Map.Entry e = (Map.Entry) o;
            return key.equals(e.getKey());
        }

        public int compareTo(Object o) {
            Bucket e = (Bucket) o;
            return this.key.compareTo(e.getKey());
        }
    }

    private static final float LOAD_FACOTOR = 0.5f;
    private int size;
    private Bucket[] table1;
    private Bucket[] table2;
    private int p1;
    private int p2;
    private ArrayList<V> valueList = new ArrayList<>();

    public CuckooMap() {
        size = 0;
        do {
            p1 = (int) ((Math.random() * 10) + 1);
            p2 = (int) ((Math.random() * 10) + 1);
        } while (p1 == p2);
        table1 = new Bucket[11];
        table2 = new Bucket[11];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object k) {
        return (k != null) && keySet().contains((K) k);
    }

    @Override
    public boolean containsValue(Object v) {
        return  values().contains((V) v);
    }

    @Override
    public V get(Object k) {
        if(k == null) return null;
        K inputKey = (K) k;

        int index1 = hashFunc1(inputKey);
        int index2 = hashFunc2(inputKey);
        if (table1[index1] != null) {
            if (table1[index1].getKey().equals(inputKey)) {
                return (V) table1[index1].getValue();
            }
        }
        if (table2[index2] != null) {
            if (table2[index2].getKey().equals(inputKey)) {
                return (V) table2[index2].getValue();
            }
        }
        return null;
    }

    @Override
    public V remove(Object k) {
        if(k == null) return null;
        K inputKey = (K) k;
        V toReturn = null;
        int index1 = hashFunc1(inputKey);
        int index2 = hashFunc2(inputKey);
        if (table1[index1] != null) {
            if (table1[index1].getKey().equals(inputKey)) {
                toReturn = (V) table1[index1].getValue();

                table1[index1] = null;
                size--;
            }
        }
        if (table2[index2] != null) {
            if (table2[index2].getKey().equals(inputKey)) {
                toReturn = (V) table2[index2].getValue();

                table2[index2] = null;
                size--;
            }
        }
        return toReturn;
    }

    @Override
    public V put(K k, V v) {
        //System.out.println("k hashcode is "  + k.hash1());
        if (k == null) return null;
        V toReturn = null;
        if (loadFactor() >= LOAD_FACOTOR) {
            //System.out.println("regrow because of lf");
            regrow(k);
        }
        if (containsKey(k)) {
            toReturn = get(k);
            if (table1[hashFunc1(k)] != null) {
                if (k.equals(table1[hashFunc1(k)].getKey())) {
                    table1[hashFunc1(k)].setValue(v); //replace the old one
                }
            } else {
                table2[hashFunc2(k)].setValue(v); //replace the old one
            }
        } else {
            Bucket<K, V> toAdd = new Bucket<K, V>(k, v);
            int pullOutCount = 0;
            boolean put = false;
            int index1 = hashFunc1(k);
            int index2;
            while (!put) {
                if (table1[index1] == null) {
                    //System.out.println("empty slot"+index1);
                    table1[index1] = toAdd;
                    put = true;
                    size++;
                } else {
                    Bucket<K, V> toReplaced = table1[index1];
                    //System.out.println("table1 slot is filled");
                    table1[index1] = toAdd;
                    index2 = hashFunc2(toReplaced.getKey());
                    if (table2[index2] == null) {
                        table2[index2] = toReplaced;
                        put = true;
                        size++;
                    } else {
                        pullOutCount++;
                        toAdd = table2[index2];
                        index1 = hashFunc1(toAdd.getKey());
                        //System.out.println("index is: "+ index1);
                        table2[index2] = toReplaced;
                        if (pullOutCount > size) {
                            //System.out.println("regrow because of size");
                            regrow(toAdd.getKey());
                        }
                    }
                }
            }
        }
        return toReturn;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        Set<?> entrySet = m.entrySet();
        for (Object bucket : entrySet) {
            Map.Entry<K, V> toPut = (Map.Entry<K, V>) bucket;
            this.put(toPut.getKey(), (V) toPut.getValue());
        }
    }

    @Override
    public void clear() {
        size = 0;
        do {
            p1 = (int) ((Math.random() * 10) + 1);
            p2 = (int) ((Math.random() * 10) + 1);
        } while (p1 == p2);
        table1 = new Bucket[11];
        table2 = new Bucket[11];
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new TreeSet<>();
        for (int i = 0; i < table1.length; i++) {
            if (table1[i] != null) {
                set.add((K) table1[i].getKey());
            }
        }
        for (int i = 0; i < table2.length; i++) {
            if (table2[i] != null) {
                set.add((K) table2[i].getKey());
            }
        }
        return set;
    }

    @Override
    public Collection<V> values() {
        for (int i = 0; i < table1.length; i++) {
            if (table1[i] != null) {
                valueList.add((V) table1[i].getValue());
            }

        }
        for (int i = 0; i < table2.length; i++) {
            if (table2[i] != null) {
                valueList.add((V) table2[i].getValue());
            }
        }
        return valueList;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = new TreeSet<>();
        for (int i = 0; i < table1.length; i++) {
            if (table1[i] != null) {
                set.add(table1[i]);
            }
        }
        for (int i = 0; i < table2.length; i++) {
            if (table2[i] != null) {
                set.add(table2[i]);
            }
        }
        return set;
    }

    private int hashFunc1(K k) {
        return ((p1 * k.hash1()) % table1.length)/2;
    }

    private int hashFunc2(K k) {
        return ((p2 * k.hash2()) % table2.length)/2;
    }

    private void regrow(K k) {
        //System.out.println("regrow is called");
        //Random rand = new Random();
        do {
            p1 = p1 ^ hashFunc1(k);
            p2 = p2 ^ hashFunc2(k);
        } while (p1 == p2);

        Bucket[] tableOld1 = table1;
        Bucket[] tableOld2 = table2;
        table1 = new Bucket[size * 2 + 1];
        table2 = new Bucket[size * 2 + 1];
        size = 0;
        for (int i = 0; i < tableOld1.length; i++) {
            if (tableOld1[i] != null) {
                put((K) tableOld1[i].getKey(), (V) tableOld1[i].getValue());
            }
        }
        for (int i = 0; i < tableOld2.length; i++) {
            if (tableOld2[i] != null) {
                put((K) tableOld2[i].getKey(), (V) tableOld2[i].getValue());
            }
        }
    }

    private double loadFactor() {
        return ((double)size / (table1.length + table2.length));
    }

    public void showInside() {
        System.out.println("Size: " + size() + " TABLE1: " + table1.length + " TABLE2: " + table2.length);
        System.out.println("Table1: ");
        for (int i = 0; i < table1.length; i++) {
            if (table1[i] != null) {
                System.out.println(" The key is:  " + table1[i].getKey() + " value is:  " + table1[i].getValue() + " index is:  " + i);
            }
        }
        System.out.println("Table2: ");
        for (int i = 0; i < table2.length; i++) {
            if (table2[i] != null) {
                System.out.println(" the key is:  " + table2[i].getKey() + " value is:  " + table2[i].getValue() + " the index is: " + i);
            }
        }
    }
}
