import java.util.LinkedList;
import java.util.function.Predicate;

public class MyHashMap<K, V> {

    private int bucketSize = 16;
    private LinkedList<KeyValueNode>[] elements = new LinkedList[bucketSize];

    public MyHashMap() {
        for (int i = 0; i < bucketSize; i++) {
            elements[i] = new LinkedList<>();
        }
    }


    //Main methods

    public void add(K key, V value) {
        nullKeyCheck(key);
        int position = getHash(key);
        LinkedList list = elements[position];
        Predicate<KeyValueNode> keyExists = e->e.getKey() == key;
        if(list.stream().anyMatch(keyExists)) {
            throw new IllegalArgumentException("Key already exists!");
        }
        list.add(new KeyValueNode<>(key, value));
        resizeIfNeeded();
    }


    public V getValue(K key) {
        nullKeyCheck(key);
        int position = getHash(key);
        LinkedList list = elements[position];
        Predicate<KeyValueNode> keyPredicate = e->e.getKey() == key;
        if(!list.stream().anyMatch(keyPredicate)) {
            throw new IllegalArgumentException("Key doesn't exist!");
        }
        KeyValueNode resultNode = (KeyValueNode) list.stream().filter(keyPredicate).findFirst().get();
        return (V) resultNode.getValue();
    }

    public void remove(K key) {
        nullKeyCheck(key);
        int position = getHash(key);
        LinkedList list = elements[position];
        Predicate<KeyValueNode> keyPredicate = e->e.getKey() == key;
        if(!list.stream().anyMatch(keyPredicate)) {
            throw new IllegalArgumentException("Key doesn't exist!");
        }
        KeyValueNode resultNode = (KeyValueNode) list.stream().filter(keyPredicate).findFirst().get();
        list.remove(resultNode);
    }

    public void clearAll() {
        for (int i = 0; i < elements.length; i++) {
            elements[i] = new LinkedList<>();
        }
    }



    //Auxiliary methods

    private void nullKeyCheck(K key) {
        if(key==null) {
            throw new IllegalArgumentException("Key cannot be null!");
        }
    }

    private int getHash(K key) {
        return key.hashCode() % bucketSize;
    }

    private void resizeIfNeeded() {
        //TODO
    }

}
