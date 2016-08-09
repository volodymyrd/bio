package com.gmail.volodymyrdotsenko.javabio.algorithms.graph;

import com.gmail.volodymyrdotsenko.javabio.algorithms.collections.LinkedStack;

import java.util.*;
import java.util.stream.Collectors;

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

    public Set<Path> findAllPaths() {
        return findAllCycles(faindPathInit());
    }

    public Deque<Integer> findPath() {
        return findCycle(faindPathInit());
    }

    private Integer faindPathInit() {
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

        return startPoint;
    }

    private String getUnbalancedVertices() {
        StringBuilder stringBuilder = new StringBuilder();

        unbalancedVertices.forEach(e -> stringBuilder.append(e + " "));

        return stringBuilder.toString();
    }

    public enum PathStatus {
        NEW, SUCCESS, FAILED;
    }

    public class Path {
        private final Map<Integer, Set<Edge>> deletedEdges;
        private final Deque<Integer> vertices;
        private final Deque<Integer> stack;
        private PathStatus status = PathStatus.NEW;

        public Path() {
            deletedEdges = new HashMap<>();
            vertices = new LinkedList<>();
            stack = new LinkedList<>();
        }

        public Path(Path path) {
            deletedEdges = new HashMap<>();
            path.deletedEdges.forEach((k, v) -> {
                deletedEdges.put(k, v.stream().map(e -> {
                    return new Edge(e);
                }).collect(Collectors.toSet()));
            });
            vertices = new LinkedList<>(path.getVertices());
            stack = new LinkedList<>();
            for (Integer i : path.stack)
                stack.push(i);
        }

        public Deque<Integer> getVertices() {
            return vertices;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Path path = (Path) o;

            if (!vertices.equals(path.vertices)) return false;
            if (!stack.equals(path.stack)) return false;
            return status == path.status;

        }

        @Override
        public int hashCode() {
            int result = vertices.hashCode();
            result = 31 * result + stack.hashCode();
            result = 31 * result + status.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "Path{" +
                    "deletedEdges=" + deletedEdges +
                    ", vertices=" + vertices +
                    ", stack=" + stack +
                    ", status=" + status +
                    '}';
        }
    }

    public Set<Path> findAllCycles(Integer startPoint) {
        if (unbalancedVertices.size() > 0) {
            throw new IllegalStateException("Graph is not Eulerian");
        }

        List<Path> paths = new ArrayList<>();

        Path path = new Path();
        paths.add(path);

        BreadthIterator breadthIterator = new BreadthIterator();
        if (!breadthIterator.hasNext())
            throw new IllegalStateException("Graph empty");

        int startVertex = startPoint == null ? breadthIterator.next() : startPoint;

        path.stack.push(startVertex);

        for (; ; ) {
            boolean finished = true;
            ListIterator<Path> iterator = paths.listIterator();
            while (iterator.hasNext()) {
                Path p = iterator.next();

                switch (p.status) {
                    case NEW:
                        finished = false;
                        go(p, iterator, paths);
                        break;
                    case SUCCESS:
                        break;
                    case FAILED:
                        break;
                }
            }
            if (finished)
                break;
        }

        return new HashSet<>(paths);
    }

    private void go(Path path, ListIterator<Path> pathsIterator, List<Path> paths) {
        while (!path.stack.isEmpty()) {
            int v = path.stack.peek();
            Set<Edge> deleted = path.deletedEdges.get(v);
            int deletedNumber = (deleted == null ? 0 : deleted.size());
            if ((outdegree(v) - deletedNumber) == 0) {
                path.vertices.push(path.stack.pop());
            } else {
                if (deleted == null) {
                    deleted = new HashSet<>(degree(v));
                    path.deletedEdges.put(v, deleted);
                }

                DepthIterator depthIterator = new DepthIterator(v);
                Map<Edge, Integer> edges = new HashMap<>();
                boolean deletedFound = false;
                Path altPathTmp = new Path(path);
                while (depthIterator.hasNext()) {
                    boolean dublicatedEdge = false;
                    int w = depthIterator.next();
                    Edge edge = new Edge(v, w);
                    Integer degree = edges.get(edge);
                    if (degree == null)
                        edges.put(edge, 1);
                    else {
                        edges.put(edge, ++degree);
                        edge = new Edge(v, w, degree);
                        dublicatedEdge = true;
                    }
                    if (!deleted.contains(edge)) {
                        if (!deletedFound) {
                            path.stack.push(w);
                            deleted.add(edge);
                            deletedFound = true;
                        } else {
                            if (!dublicatedEdge) {
                                Path altPath = new Path(altPathTmp);
                                altPath.stack.push(w);
                                if (!paths.contains(altPath)) {
                                    altPath.deletedEdges.get(v).add(edge);
                                    pathsIterator.add(altPath);
                                }
                            }
                        }
                    }
                }

                if (!deletedFound) {
                    path.status = PathStatus.FAILED;
                    break;
                }
            }
        }

        path.deletedEdges.clear();

        if (path.status == PathStatus.NEW) {
            path.status = PathStatus.SUCCESS;

            if (fictiveVertex != null && path.vertices.peek().equals(fictiveVertex))
                path.vertices.removeLast();
        }
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
                Map<Edge, Integer> edges = new HashMap<>();
                while (depthIterator.hasNext()) {
                    int w = depthIterator.next();
                    Edge edge = new Edge(v, w);
                    Integer degree = edges.get(edge);
                    if (degree == null)
                        edges.put(edge, 1);
                    else {
                        edges.put(edge, ++degree);
                        edge = new Edge(v, w, degree);
                    }
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