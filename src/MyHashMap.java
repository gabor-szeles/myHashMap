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
            throw new IllegalArgumentException("Key already exists");
        }
        list.add(new KeyValueNode<>(key, value));
        resizeIfNeeded();
    }

    private int getHash(K key) {
        return key.hashCode() % bucketSize;
    }

    private void resizeIfNeeded() {
        //TODO
    }

}
