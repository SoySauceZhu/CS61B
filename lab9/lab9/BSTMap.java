package lab9;

import java.util.*;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>, Iterable<K> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) return null;
        if (key.compareTo(p.key) > 0) return getHelper(key, p.right);
        if (key.compareTo(p.key) < 0) return getHelper(key, p.left);

        return p.value;
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
     * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            p = new Node(key, value);
            size++;
        } else {
            if (key.compareTo(p.key) > 0) {
                p.right = putHelper(key, value, p.right);
            }
            if (key.compareTo(p.key) < 0) {
                p.left = putHelper(key, value, p.left);
            }
            if (key.compareTo(p.key) == 0) {
                p.value = value;
            }
        }

        return p;       // return p itself to it's parent.left/right
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);

    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

//////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        keySetHelper(root, set);
        return set;
    }

    /** Return the tree rooted at p */
    private void keySetHelper(Node p, Set<K> set) {
        if (p == null) return;
        set.add(p.key);
        keySetHelper(p.left, set);
        keySetHelper(p.right, set);
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        Node p = removeHelper(key, root);
        V value = p.value;
        removeHelper(p);
        size--;
        return value;
    }

    /** Hibbard deletion */
    private void removeHelper(Node p ) {
        if (p.right == null && p.left == null) {
            p = null;
        } else if (p.right == null) {
            p.key = p.left.key;
            p.value = p.left.value;
            p.left = p.left.left;
            p.right = p.left.right;
        } else if (p.left == null) {
            p.key = p.right.key;
            p.value = p.right.value;
            p.left = p.right.left;
            p.right = p.right.right;
        } else {
            Node min = findMinOf(p);
            p.key = min.key;
            p.value = min.value;
            removeHelper(min);
        }
    }
    private Node removeHelper(K key, Node p) {
        if (p == null) return null;
        if (key.compareTo(p.key) > 0) return removeHelper(key, p.right);
        if (key.compareTo(p.key) < 0) return removeHelper(key, p.left);

        return p;
    }

    private Node findMinOf(Node p) {
        if (p.left.left == null) {
            return p.left;
        }

        return findMinOf(p.left);
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTIterator(root);

    }
    private class BSTIterator implements Iterator<K> {
        private Node current;
        private Stack<Node> stack;

        public BSTIterator(Node root) {
            this.current = root;
            this.stack = new Stack<>();
            pushLeft(current);
        }

        private void pushLeft(Node node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in the tree.");
            }
            Node node = stack.pop();
            pushLeft(node.right);
            return node.key;
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
