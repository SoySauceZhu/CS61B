package lab9;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class MyTest {

    public BSTMap<String, Integer> map;

    @BeforeEach
    void setUp() {
        // Initialize your BST implementation here
        map = new BSTMap<>();
    }

    @Test
    void testPutAndGet() {
        map.put("key1", 1);
        map.put("key2", 2);
        map.put("key3", 3);

        assertEquals(1, map.get("key1"));
        assertEquals(2, map.get("key2"));
        assertEquals(3, map.get("key3"));
    }

    @Test
    void testContainsKey() {
        map.put("key1", 1);
        map.put("key2", 2);

        assertTrue(map.containsKey("key1"));
        assertTrue(map.containsKey("key2"));
        assertFalse(map.containsKey("key3"));
    }

    @Test
    void testSize() {
        map.put("key1", 1);
        map.put("key2", 2);
        map.put("key3", 3);

        assertEquals(3, map.size());

        map.remove("key2");

        assertEquals(2, map.size());
    }

    @Test
    void testKeySet() {
        map.put("key1", 1);
        map.put("key2", 2);
        map.put("key3", 3);

        Set<String> keys = map.keySet();

        assertEquals(3, keys.size());
        assertTrue(keys.contains("key1"));
        assertTrue(keys.contains("key2"));
        assertTrue(keys.contains("key3"));
    }

    @Test
    void testRemove() {
        map.put("key2", 2);
        map.put("key1", 1);
        map.put("key4", 1);
        map.put("key3", 1);
        map.put("key5", 1);

        assertEquals(5, map.size());

        assertEquals(2, map.remove("key2"));

        assertFalse(map.containsKey("key2"));
        assertEquals(4, map.size());
    }


}
