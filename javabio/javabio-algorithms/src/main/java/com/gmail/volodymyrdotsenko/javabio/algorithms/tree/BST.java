package com.gmail.volodymyrdotsenko.javabio.algorithms.tree;

import com.gmail.volodymyrdotsenko.javabio.algorithms.collections.LinkedQueue;
import com.gmail.volodymyrdotsenko.javabio.algorithms.collections.ResizingArrayStack;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@code BST} class represents an ordered symbol table of generic key-value pairs. It supports the usual
 * <em>put</em>, <em>get</em>, <em>contains</em>, <em>delete</em>, <em>size</em>, and <em>is-empty</em> methods. It also
 * provides ordered methods for finding the <em>minimum</em>, <em>maximum</em>, <em>floor</em>, <em>select</em>,
 * <em>ceiling</em>. It also provides a <em>keys</em> method for iterating over all of the keys. A symbol table
 * implements the <em>associative array</em> abstraction: when associating a value with a key that is already in the
 * symbol table, the convention is to replace the old value with the new value. Unlike {@link java.util.Map}, this class
 * uses the convention that values cannot be {@code null} setting the value associated with a key to {@code null} is
 * equivalent to deleting the key from the symbol table.
 * <p>
 * This implementation uses an (unbalanced) binary search tree. It requires that the key type implements the
 * {@code Comparable} interface and calls the {@code compareTo()} and method to compare two keys. It does not call
 * either {@code equals()} or {@code hashCode()}. The <em>put</em>, <em>contains</em>, <em>remove</em>,
 * <em>minimum</em>, <em>maximum</em>, <em>ceiling</em>, <em>floor</em>, <em>select</em>, and <em>rank</em> operations
 * each take linear time in the worst case, if the tree becomes unbalanced. The <em>size</em>, and <em>is-empty</em>
 * operations take constant time. Construction takes constant time.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class BST<Key extends Comparable<Key>, Value> extends AbstractTree<Key, Value> {

    private Node root; // root of BST

    private class Node implements INode {

        private final Key key; // sorted by key
        private Value val; // associated data
        private Node left, right; // left and right subtrees
        private int size; // number of nodes in subtree
        private boolean visited;//flag for marking a visited node while traversal the tree
        private int level;//for root node level 0, children of root - level 1, etc.

        public Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Node node = (Node) o;

            return key.equals(node.key);
        }

        @Override
        public int hashCode() {
            return key.hashCode();
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
            builder.append(val);
            return "{" + builder.toString() + '}';
        }
    }

    /**
     * Initializes an empty symbol table.
     */
    public BST() {
    }

    /**
     * Returns true if this symbol table is empty.
     *
     * @return {@code true} if this symbol table is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return size(root);
    }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null) {
            return 0;
        } else {
            return x.size;
        }
    }

    /**
     * Does this symbol table contain the given key?
     *
     * @param key the key
     * @return {@code true} if this symbol table contains {@code key} and {@code false} otherwise
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public boolean contains(Key key) {
        if (key == null) {
            throw new NullPointerException("argument to contains() is null");
        }
        return get(key) != null;
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table and {@code null} if the key is
     * not in the symbol table
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return get(x.left, key);
        } else if (cmp > 0) {
            return get(x.right, key);
        } else {
            return x.val;
        }
    }


    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param key the key
     * @param val the value
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {
        if (key == null) {
            throw new NullPointerException("first argument to put() is null");
        }
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) {
            return new Node(key, val, 1);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = put(x.left, key, val);
        } else if (cmp > 0) {
            x.right = put(x.right, key, val);
        } else {
            x.val = val;
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    /**
     * Removes the specified key and its associated value from this symbol table
     * (if the key is in this symbol table).
     *
     * @param key the key
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public void delete(Key key) {
        if (key == null) {
            throw new NullPointerException("argument to delete() is null");
        }
        root = delete(root, key);
    }

    private Node delete(Node x, Key key) {
        if (x == null) {
            return null;
        }

        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = delete(x.left, key);
        } else if (cmp > 0) {
            x.right = delete(x.right, key);
        } else {
            if (x.right == null) {
                return x.left;
            }
            if (x.left == null) {
                return x.right;
            }
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * Returns the smallest key in the symbol table.
     *
     * @return the smallest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key min() {
        if (isEmpty()) {
            throw new NoSuchElementException("called min() with empty symbol table");
        }
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) {
            return x;
        } else {
            return min(x.left);
        }
    }

    /**
     * Removes the smallest key and associated value from the symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Symbol table underflow");
        }
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) {
            return x.right;
        }
        x.left = deleteMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
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
            int currentLevel = next.level;
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

    private class PostOrderIterator implements Iterator<Node> {

        private ResizingArrayStack<Node> stack = new ResizingArrayStack<>(size());
        private Iterator<Node> iterator = stack.iterator();

        public PostOrderIterator() {
            stack.push(root);
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public Node next() {
            return iterator.next();
        }
    }

}