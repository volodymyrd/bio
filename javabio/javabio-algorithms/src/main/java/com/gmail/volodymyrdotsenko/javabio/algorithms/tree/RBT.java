package com.gmail.volodymyrdotsenko.javabio.algorithms.tree;

import com.gmail.volodymyrdotsenko.javabio.algorithms.collections.LinkedQueue;

import java.util.Iterator;

/**
 * A Red-Black tree based implementation
 *
 * Created by Volodymyr_Dotsenko on 10/14/2016.
 */
public class RBT<K extends Comparable<K>, V> extends AbstractTree<K, V> {

    /**
     * The number of entries in the tree
     */
    private int size = 0;

    /**
     * The number of structural modifications to the tree.
     */
    private int modCount = 0;

    // Red-black mechanics
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    private transient Node root;

    private final class Node implements INode {

        K key;
        V value;
        Node left;
        Node right;
        Node parent;
        boolean color = BLACK;

        boolean visited;//flag for marking a visited node while traversal the tree
        int level;//for root node level 0, children of root - level 1, etc.

        /**
         * Make a new cell with given key, value, and parent, and with
         * {@code null} child links, and BLACK color.
         */
        Node(K key, V value, Node parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        /**
         * Replaces the value currently associated with the key with the given
         * value.
         *
         * @return the value associated with the key before this method was called
         */
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override
        public int getLevel() {
            return level;
        }

        @Override
        public void setLevel(int level) {
            this.level = level;
        }

        @Override
        public void setVisited(boolean flag) {
            this.visited = flag;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(value);
            if(parent != null)
                builder.append(parent);
            else
                builder.append("");

            return "{" + builder.toString() + '}';
        }
    }

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings in this map
     */
    public int size() {
        return size;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old
     * value is replaced.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with {@code key}, or {@code null} if there was no mapping for {@code key}.
     * (A {@code null} return can also indicate that the map previously associated {@code null} with {@code key}.)
     * @throws ClassCastException   if the specified key cannot be compared with the keys currently in the map
     * @throws NullPointerException if the specified key is null and this map uses natural ordering, or its comparator
     *                              does not permit null keys
     */
    public V put(K key, V value) {
        Node r = root;
        if (r == null) {
            compare(key, key); // type (and possibly null) check

            root = new Node(key, value, null);
            size = 1;
            modCount++;
            return null;
        }
        int cmp;
        Node parent;

        if (key == null) {
            throw new NullPointerException();
        }
        K k = key;
        do {
            parent = r;
            cmp = k.compareTo(r.key);
            if (cmp < 0) {
                r = r.left;
            } else if (cmp > 0) {
                r = r.right;
            } else {
                return r.setValue(value);
            }
        } while (r != null);

        Node e = new Node(key, value, parent);
        if (cmp < 0) {
            parent.left = e;
        } else {
            parent.right = e;
        }
        fixAfterInsertion(e);
        size++;
        modCount++;
        return null;
    }

    /**
     * From CLR
     */
    private void fixAfterInsertion(Node x) {
        x.color = RED;

        while (x != null && x != root && x.parent.color == RED) {
            if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
                Node y = rightOf(parentOf(parentOf(x)));
                if (colorOf(y) == RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    if (x == rightOf(parentOf(x))) {
                        x = parentOf(x);
                        rotateLeft(x);
                    }
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    rotateRight(parentOf(parentOf(x)));
                }
            } else {
                Node y = leftOf(parentOf(parentOf(x)));
                if (colorOf(y) == RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    if (x == leftOf(parentOf(x))) {
                        x = parentOf(x);
                        rotateRight(x);
                    }
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    rotateLeft(parentOf(parentOf(x)));
                }
            }
        }
        root.color = BLACK;
    }

    /**
     * From CLR
     */
    private void rotateLeft(Node p) {
        if (p != null) {
            Node r = p.right;
            p.right = r.left;
            if (r.left != null) {
                r.left.parent = p;
            }
            r.parent = p.parent;
            if (p.parent == null) {
                root = r;
            } else if (p.parent.left == p) {
                p.parent.left = r;
            } else {
                p.parent.right = r;
            }
            r.left = p;
            p.parent = r;
        }
    }

    /**
     * From CLR
     */
    private void rotateRight(Node p) {
        if (p != null) {
            Node l = p.left;
            p.left = l.right;
            if (l.right != null) {
                l.right.parent = p;
            }
            l.parent = p.parent;
            if (p.parent == null) {
                root = l;
            } else if (p.parent.right == p) {
                p.parent.right = l;
            } else {
                p.parent.left = l;
            }
            l.right = p;
            p.parent = l;
        }
    }

    private Node parentOf(Node p) {
        return (p == null ? null : p.parent);
    }

    private Node leftOf(Node p) {
        return (p == null) ? null : p.left;
    }

    private Node rightOf(Node p) {
        return (p == null) ? null : p.right;
    }

    private boolean colorOf(Node p) {
        return (p == null ? BLACK : p.color);
    }

    private void setColor(Node p, boolean c) {
        if (p != null) {
            p.color = c;
        }
    }

    /**
     * Compares two keys using the correct comparison method for this TreeMap.
     */
    final int compare(K k1, K k2) {
        return k1.compareTo(k2);
    }

    @Override
    protected Iterator<INode> breadthFirstIterator() {
        return new BreadthFirstIterator();
    }

    private class BreadthFirstIterator implements Iterator<INode> {

        private LinkedQueue<INode> queue = new LinkedQueue<>();

        public BreadthFirstIterator() {
            root.level = 0;
            root.visited = true;
            queue.enqueue(root);
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public Node next() {
            Node next = (Node) queue.dequeue();
            int currentLevel = next.getLevel();
            int nextLevel = ++currentLevel;
            if (next.left != null && !next.left.visited) {
                queue.enqueue(setLevelAndVisited(next.left, nextLevel));
            }
            if (next.right != null && !next.right.visited) {
                queue.enqueue(setLevelAndVisited(next.right, nextLevel));
            }
            return next;
        }
    }
}