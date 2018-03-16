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

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5})
    public void testClearAllWorksCorrectly(int key) {
        testMap.add(1, "Test 1");
        testMap.add(2, "Test 2");
        testMap.add(3, "Test 3");
        testMap.add(4, "Test 4");
        testMap.add(5, "Test 5");
        testMap.clearAll();
        assertThrows(IllegalArgumentException.class, ()-> testMap.getValue(key));
    }

    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19})
    public void testDynamicResizeWontLoseDataWithUpSize(int key) {
        // Test written with a default bucket size of 8 in mind!
        for (int i = 0; i < 20; i++) {
            testMap.add(i, "Test "+i);
        }
        assertEquals("Test " +key, testMap.getValue(key));
    }

    @Test
    public void testBucketSizeUpScales() {
        // Test written with a default bucket size of 8 in mind!
        for (int i = 0; i < 20; i++) {
            testMap.add(i, "Test "+i);
        }
        assertEquals(16, testMap.getBucketSize());
    }

    @ParameterizedTest
    @ValueSource(ints = {6,7,8,9,10,11,12,13,14,15,16,17,18,19})
    public void testDynamicResizeWontLoseDataWithDownSize(int key) {
        // Test written with a default bucket size of 8 in mind!
        for (int i = 0; i < 20; i++) {
            testMap.add(i, "Test "+i);
        }
        for (int i = 0; i < 6; i++) {
            testMap.remove(i);
        }
        assertEquals("Test " +key, testMap.getValue(key));
    }

    @Test
    public void testBucketSizeDownScales() {
        // Test written with a default bucket size of 8 in mind!
        testMap.add(1, "Test");
        assertEquals(4, testMap.getBucketSize());
    }



}