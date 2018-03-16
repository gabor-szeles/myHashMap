import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class MyHashMapTest {

    private MyHashMap<Integer, String> testMap;

    @BeforeEach
    public void setUp() {
        testMap = new MyHashMap<>();
    }

    @Test
    public void testAddThrowsExceptionIfKeyExists() {
        testMap.add(1, "Test");
        assertThrows(IllegalArgumentException.class, ()-> testMap.add(1, "duplicatekey"));
    }

    @Test
    public void testGetValueThrowsExceptionsIfKeyNotFound() {
        testMap.add(1, "Test");
        assertThrows(IllegalArgumentException.class, ()-> testMap.getValue(3));
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5})
    public void testGetValueWorksCorrectly(int key) {
        HashMap<Integer, String> compareMap = new HashMap<>();
        testMap.add(key, "Test"+key);
        compareMap.put(key, "Test"+key);
        assertEquals(compareMap.get(key), testMap.getValue(key));
    }

    @Test
    public void testValueCanBeNull() {
        testMap.add(1, null);
        assertEquals(null, testMap.getValue(1));
    }

    @Test
    public void testKeyCannotBeNull() {
        assertThrows(IllegalArgumentException.class,()-> testMap.add(null, "Test"));
    }

    @Test
    public void testRemoveWorksCorrectly() {
        testMap.add(1, "Test");
        testMap.remove(1);
        assertThrows(IllegalArgumentException.class, ()-> testMap.getValue(1));
    }

    @Test
    public void testRemoveThrowsExceptionForNotExistingKey() {
        testMap.add(1, "Test");
        assertThrows(IllegalArgumentException.class, ()-> testMap.getValue(2));
    }



}