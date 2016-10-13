package com.gmail.volodymyrdotsenko.javabio.algorithms.tree;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by Volodymyr_Dotsenko on 10/13/2016.
 */
public class B23T<Key extends Comparable<Key>, Value> {

    private final static int ORDER = 3;

    private Node root; // root

    private class Node {

        private final Object[] keys = new Object[ORDER]; // sorted by key
        private final Object[] vals = new Object[ORDER];
        ; // associated data
        private Node first, second, third, fourth;
        private int size; // number of taken keys
        private boolean visited;//flag for marking a visited node while traversal the tree
        private Node parent;

        public Node(Key key, Value val) {
            this.keys[size] = key;
            this.vals[size] = val;
            size++;
        }

        public Node(Key key, Value val, Node first, Node second, Node third,
                    Node fourth, Node parent) {
            this.keys[size] = key;
            this.vals[size] = val;
            this.first = first;
            this.second = second;
            this.third = third;
            this.fourth = fourth;
            this.parent = parent;
            size++;
        }

        public Node put(Key key, Value val) {
            if (size == ORDER) {
                throw new IllegalStateException();
            }

            for (int i = size - 1; i >= 0; i--) {
                if (key.compareTo((Key) keys[i]) >= 0) {
                    this.keys[i + 1] = key;
                    this.vals[i + 1] = val;
                    break;
                } else {
                    this.keys[i + 1] = this.keys[i];
                    this.vals[i + 1] = this.vals[i];
                    this.keys[i] = key;
                    this.vals[i] = val;
                }
            }

            size++;

            return this;
        }

        public boolean isLeaf() {
            return (first == null) && (second == null) && (third == null);
        }

        public void toNode2(Key key, Value val, Node first, Node second) {
            size = 1;
            keys[0] = key;
            vals[0] = val;
            for (int i = 1; i < ORDER; i++) {
                keys[i] = null;
                vals[i] = null;
            }
            this.first = first;
            this.second = second;
            this.third = null;
            this.fourth = null;
            this.parent = null;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < ORDER; i++) {
                if (vals[i] != null) {
                    builder.append(vals[i]);
                    builder.append(",");
                }
            }
            int i = builder.lastIndexOf(",");
            if (i > 0) {
                builder.delete(i, i + 1);
            }
            return "{" + builder.toString() + '}';
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
            //delete(key);
            return;
        }
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) {
            return new Node(key, val);
        }

        if (x.isLeaf()) {
            x.put(key, val);
        } else if (key.compareTo((Key) x.keys[0]) <= 0) {
            put(x.first, key, val);
        } else if ((x.size == 1) || ((x.size == 2) && key.compareTo((Key) x.keys[1]) <= 0)) {
            put(x.second, key, val);
        } else {
            put(x.third, key, val);
        }

        return split(x);
    }

    private Node split(Node n) {
        if (n.size < 3) {
            return n;
        }

        Node x = new Node((Key) n.keys[0], (Value) n.vals[0], n.first, n.second, null, null, n.parent);
        Node y = new Node((Key) n.keys[2], (Value) n.vals[2], n.third, n.fourth, null, null, n.parent);

        if (x.first != null) {
            x.first.parent = x;
        }

        if (x.second != null) {
            x.second.parent = x;
        }

        if (y.first != null) {
            y.first.parent = y;
        }

        if (y.second != null) {
            y.second.parent = y;
        }

        if (n.parent != null) {
            n.parent.put((Key) n.keys[1], (Value) n.vals[1]);

            if (n.parent.first == n) {
                n.parent.first = null;
            } else if (n.parent.second == n) {
                n.parent.second = null;
            } else if (n.parent.third == n) {
                n.parent.third = null;
            }

            if (n.parent.first == null) {
                n.parent.fourth = n.parent.third;
                n.parent.third = n.parent.second;
                n.parent.second = y;
                n.parent.first = x;
            } else if (n.parent.second == null) {
                n.parent.fourth = n.parent.third;
                n.parent.third = y;
                n.parent.second = x;
            } else {
                n.parent.fourth = y;
                n.parent.third = x;
            }

            return n.parent;
        } else {
            x.parent = n;
            y.parent = n;
            n.toNode2((Key) n.keys[1], (Value) n.vals[1], x, y);

            return n;
        }
    }

    private class BreadthFirstIterator implements Iterator<Node> {

        private Node next = root;
        private int currenLevel = 0;
        private int nextLevel = 0;

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Node next() {
            Node currentNode = next;
            currentNode.visited = true;
            currenLevel = nextLevel;

            if (currentNode.parent != null) {
                if (allVisited(currentNode.parent)) {
                    next = getNext(currentNode);
                    nextLevel++;
                } else {
                    next = getNext(currentNode.parent);
                }
            } else {
                next = getNext(currentNode);
                nextLevel++;
            }

            return currentNode;
        }

        private boolean allVisited(Node node) {
            return
                (node.first == null || node.first.visited) &&
                (node.second == null || node.second.visited) &&
                (node.third == null || node.third.visited) &&
                (node.fourth == null || node.fourth.visited);
        }

        private Node getNext(Node current) {
            if (current.first != null && !current.first.visited) {
                return current.first;
            } else if (current.second != null && !current.second.visited) {
                return current.second;
            } else if (current.third != null && !current.third.visited) {
                return current.third;
            } else if (current.fourth != null && !current.fourth.visited) {
                return current.fourth;
            } else {
                return null;
            }
        }
    }

    public String print() {
        StringBuilder builder = new StringBuilder();
        BreadthFirstIterator iterator = new BreadthFirstIterator();
        int level = 0;
        StringBuilder levelBuilder = new StringBuilder();
        while (iterator.hasNext()) {
            Node node = iterator.next();
            if (iterator.currenLevel == level) {
                levelBuilder.append(" " + node);
            } else {
                level = iterator.currenLevel;
                builder.append("\r\n");
                builder.append(levelBuilder);

                levelBuilder = new StringBuilder();
                levelBuilder.append(" " + node);
            }
        }
        builder.append("\r\n");
        builder.append(levelBuilder);
        return builder.toString();
    }
}