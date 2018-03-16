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

    public void add(K key, V value) {
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
        int position = getHash(key);
        LinkedList list = elements[position];
        Predicate<KeyValueNode> keyPredicate = e->e.getKey() == key;
        if(!list.stream().anyMatch(keyPredicate)) {
            throw new IllegalArgumentException("Key doesn't exist!");
        }
        KeyValueNode resultNode = (KeyValueNode) list.stream().filter(keyPredicate).findFirst().get();
        return (V) resultNode.getValue();
    }

    private int getHash(K key) {
        return key.hashCode() % bucketSize;
    }

    private void resizeIfNeeded() {
        //TODO
    }

}
