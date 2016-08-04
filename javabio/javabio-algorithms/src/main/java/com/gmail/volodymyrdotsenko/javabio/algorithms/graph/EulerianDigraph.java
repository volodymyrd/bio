package com.gmail.volodymyrdotsenko.javabio.algorithms.graph;

import com.gmail.volodymyrdotsenko.javabio.algorithms.collections.LinkedStack;

import java.util.*;

/**
 * Eulerian oriented graph
 * <p>
 * <p>
 * Created by Volodymyr Dotsenko on 22.07.16.
 */
public class EulerianDigraph<T> extends SymbolDigraph<T> {

    private Map<Integer, Set<Edge>> deletedEdges;

    public EulerianDigraph() {
    }

    public EulerianDigraph(int V) {
        super(V);
    }

    @Override
    public EulerianDigraph addEdge(int v, int w) {
        Edge edge = new Edge(v, w);
        edges.add(edge);

        EulerianDigraph digraph = (EulerianDigraph) super.addEdge(v, w);

        int indegree = indegree(v);
        int outdegree = outdegree(v);

        if (indegree != outdegree)
            unbalancedVertices.add(new UnbalancedVertex(v, indegree, outdegree));
        else
            unbalancedVertices.remove(new UnbalancedVertex(v, indegree, outdegree));

        indegree = indegree(w);
        outdegree = outdegree(w);

        if (indegree != outdegree)
            unbalancedVertices.add(new UnbalancedVertex(w, indegree, outdegree));
        else
            unbalancedVertices.remove(new UnbalancedVertex(w, indegree, outdegree));

        return digraph;
    }

    /**
     * Adds the directed edge v->w to this digraph.
     *
     * @param v the tail vertex
     * @param w the head vertex
     * @throws IndexOutOfBoundsException unless both 0 <= v < V and 0 <= w < V
     */
    public EulerianDigraph addEdge(T v, T w) {
        Edge edge = addSymbolEdge(v, w);

        return this.addEdge(edge.getV(), edge.getW());
    }

    public List<UnbalancedVertex> findListUnbalancedVertex() {
        List<UnbalancedVertex> list = new ArrayList<>();

        BreadthIterator breadthIterator = new BreadthIterator();
        while (breadthIterator.hasNext()) {
            int v = breadthIterator.next();
            if (indegree(v) != outdegree(v)) {
                list.add(new UnbalancedVertex(v, indegree(v), outdegree(v)));
            }
        }

        return list;
    }

    public boolean isCycle() {
        if (unbalancedVertices.size() == 0)
            return true;
        else
            return false;
    }

    public Set<Edge> findCycleAsEdge() {
        Set<Edge> path = new LinkedHashSet<>();

        Iterator<Integer> it = findCycle(null).iterator();
        Integer v = null;
        while (it.hasNext()) {
            if (v == null)
                v = it.next();
            Integer w = it.next();
            if (w == null)
                break;

            path.add(new Edge(v, w));
            v = w;
        }

        return path;
    }

    private Integer fictiveVertex;

    public Deque<Integer> findPath() {
        fictiveVertex = null;
        Integer startPoint = null;
        if (unbalancedVertices.size() == 2) {
            Iterator<UnbalancedVertex> iterator = unbalancedVertices.iterator();
            UnbalancedVertex uv = iterator.next();
            UnbalancedVertex uw = iterator.next();

            int uvDiff = uv.indegree - uv.outdegree;
            int uwDiff = uw.indegree - uw.outdegree;
            if (uvDiff + uwDiff == 0) {
                if (uvDiff > 0) {
                    addEdge(uv.v, uw.v);
                    startPoint = uw.v;
                } else {
                    addEdge(uw.v, uv.v);
                    startPoint = uv.v;
                }

                fictiveVertex = startPoint;

                if (unbalancedVertices.size() != 0)
                    throw new IllegalStateException("Graph is not Eulerian, exist unbalanced vertices: " + getUnbalancedVertices());
            } else
                throw new IllegalStateException("Graph is not Eulerian, exist unbalanced vertices: " + getUnbalancedVertices());

        } else if (unbalancedVertices.size() != 0) {
            throw new IllegalStateException("Graph is not Eulerian, exist unbalanced vertices: " + getUnbalancedVertices());
        }


        return findCycle(startPoint);
    }

    private String getUnbalancedVertices() {
        StringBuilder stringBuilder = new StringBuilder();

        unbalancedVertices.forEach(e -> stringBuilder.append(e + " "));

        return stringBuilder.toString();
    }

    public Deque<Integer> findCycle(Integer startPoint) {
        if (unbalancedVertices.size() > 0) {
            throw new IllegalStateException("Graph is not Eulerian");
        }

        deletedEdges = new HashMap<>();

        Deque<Integer> vertices = new LinkedList<>();

        LinkedStack<Integer> stack = new LinkedStack<>();
        BreadthIterator breadthIterator = new BreadthIterator();
        if (!breadthIterator.hasNext())
            throw new IllegalStateException("Graph empty");

        int startVertex = startPoint == null ? breadthIterator.next() : startPoint;

        stack.push(startVertex);

        while (!stack.isEmpty()) {
            int v = stack.peek();
            Set<Edge> deleted = deletedEdges.get(v);
            int deletedNumber = (deleted == null ? 0 : deleted.size());
            if ((outdegree(v) - deletedNumber) == 0) {
                vertices.push(stack.pop());
            } else {
                if (deleted == null) {
                    deleted = new HashSet<>(degree(v));
                    deletedEdges.put(v, deleted);
                }

                DepthIterator depthIterator = new DepthIterator(v);
                while (depthIterator.hasNext()) {
                    int w = depthIterator.next();
                    Edge edge = new Edge(v, w);
                    if (!deleted.contains(edge)) {
                        stack.push(w);
                        deleted.add(edge);
                        break;
                    }
                }
            }
        }

        deletedEdges.clear();
        deletedEdges = null;

        if (fictiveVertex != null && vertices.peek().equals(fictiveVertex))
            vertices.removeLast();

        return vertices;
    }
}