package com.gmail.volodymyrdotsenko.javabio.algorithms.tree;

import java.util.Iterator;

/**
 * Created by Volodymyr_Dotsenko on 10/14/2016.
 */
public abstract class AbstractTree<K extends Comparable<K>, V> {

    interface INode {

        int getLevel();

        void setLevel(int level);

        void setVisited(boolean flag);
    }

    protected abstract Iterator<INode> breadthFirstIterator();

    protected abstract int size();

    public String print() {
        StringBuilder builder = new StringBuilder();
        builder.append("Tree size: " + size());
        Iterator<INode> iterator = breadthFirstIterator();
        int level = 0;
        StringBuilder levelBuilder = new StringBuilder();
        while (iterator.hasNext()) {
            INode node = iterator.next();
            if (node.getLevel() == level) {
                levelBuilder.append(" " + node);
            } else {
                level = node.getLevel();
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

    protected INode setLevelAndVisited(INode node, int level) {
        node.setLevel(level);
        node.setVisited(true);
        return node;
    }
}