import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MyHashMap<K, V> {

    private int bucketSize = 8;
    private LinkedList<KeyValueNode>[] elements = new LinkedList[bucketSize];
    private int elementsNumber;

    public MyHashMap() {
        for (int i = 0; i < bucketSize; i++) {
            elements[i] = new LinkedList<>();
        }
        elementsNumber = 0;
    }

    public int getBucketSize() {
        return bucketSize;
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
        elementsNumber++;
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
        elementsNumber--;
    }

    public void clearAll() {
        for (int i = 0; i < elements.length; i++) {
            elements[i] = new LinkedList<>();
        }
        elementsNumber = 0;
    }

    public Set<K> getKeySet() {
        Set<K> resultSet = new HashSet<>();
        Set<KeyValueNode> allDataSet = getKeyValueNodes();
        for (KeyValueNode node:allDataSet) {
            resultSet.add((K)node.getKey());
        }
        return resultSet;
    }

    public List<V> getAllValues() {
        List<V> resultList = new ArrayList<>();
        Set<KeyValueNode> allDataSet = getKeyValueNodes();
        for (KeyValueNode node:allDataSet) {
            resultList.add((V)node.getValue());
        }
        return resultList;
    }

    public void update(K key, V value) {
        nullKeyCheck(key);
        int position = getHash(key);
        LinkedList list = elements[position];
        Predicate<KeyValueNode> keyPredicate = e->e.getKey() == key;
        if(!list.stream().anyMatch(keyPredicate)) {
            throw new IllegalArgumentException("Key doesn't exist!");
        }
        KeyValueNode targetNode = (KeyValueNode) list.stream().filter(keyPredicate).findFirst().get();
        targetNode.setValue(value);
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
        if(elementsNumber>bucketSize*2) {
            resize(true);
        } else if(elementsNumber<bucketSize/2) {
            resize(false);
        }
    }

    private void resize(boolean upwards) {
        Set<KeyValueNode> allDataSet = getKeyValueNodes();
        int position;
        if (upwards) {
            bucketSize = bucketSize*2;
        } else {
            bucketSize = bucketSize/2;
        }
        elements = new LinkedList[bucketSize];
        for (int i = 0; i < elements.length; i++) {
            elements[i] = new LinkedList<>();
        }
        for (KeyValueNode node:allDataSet) {
            position = getHash((K)node.getKey());
            elements[position].add(node);
        }
    }

    private Set<KeyValueNode> getKeyValueNodes() {
        Set<KeyValueNode> allDataSet = new HashSet<>();
        for (int i = 0; i < elements.length; i++) {
            if(!elements[i].isEmpty()) {
                allDataSet.addAll(elements[i]);
            }
        }
        return allDataSet;
    }

}
