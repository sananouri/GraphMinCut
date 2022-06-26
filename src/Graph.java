import java.util.ArrayList;
import java.util.Random;

public class Graph {
    private int size;
    private ArrayList<SuperVertex> vertices;
    private ArrayList<SuperEdge> edges;
    private ArrayList<SuperEdge> minCutEdges;
    private static ArrayList<SuperEdge> minCutSaver = new ArrayList<>();

    public Graph(ArrayList<SuperEdge> edges, ArrayList<SuperVertex> vertices) {
        this.size = vertices.size();
        this.edges = edges;
        this.vertices = vertices;
        minCutEdges = new ArrayList<>();
    }

    public static Graph getGraph(String matrix) {
        String[] lines = matrix.split("\n"), nums;
        int size = lines.length;
        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<SuperVertex> superVertices = new ArrayList<>();
        ArrayList<SuperEdge> edges = new ArrayList<>();
        Vertex vertex;
        SuperVertex superVertex;
        for (int j = 1; j <= size; j++) {
            vertex = new Vertex();
            vertices.add(vertex);
            superVertex = new SuperVertex(vertex);
            superVertices.add(superVertex);
        }
        for (int i = 0; i < size; i++) {
            nums = lines[i].split(" ");
            for (int j = 0; j < size; j++) {
                if (Integer.valueOf(nums[j]) != 0 && j >= i) {
                    edges.add(new SuperEdge(vertices.get(i), vertices.get(j),
                            superVertices.get(i), superVertices.get(j)));
                }
            }
        }
        return new Graph(edges, superVertices);
    }

    public int getSize() {
        return size;
    }

    public ArrayList<SuperEdge> getEdges() {
        return edges;
    }

    public ArrayList<SuperEdge> getMinCutEdges() {
        if (minCutEdges.size() == 0) {
            minCutSaver = new ArrayList<>();
            minCutEdges = applyKarger_Stein();
        }
        return minCutEdges;
    }

    private ArrayList<SuperEdge> applyKarger_Stein() {
        final double radix = Math.sqrt(2);
        Graph newGraph;
        if (size <= 4) {
            newGraph = applyKarger(2);
            if (minCutSaver.size() < newGraph.minCutEdges.size()) {
                minCutSaver = newGraph.minCutEdges;
            }
        } else {
            newGraph = applyKarger(size / radix);
            newGraph.applyKarger_Stein();
            newGraph.applyKarger_Stein();
        }
        return minCutSaver;
    }

    private Graph applyKarger(double bound) {
        ArrayList<SuperVertex> vertices = new ArrayList<>(this.vertices);
        ArrayList<SuperEdge> edges = new ArrayList<>(this.edges);
        ArrayList<SuperEdge> minCutEdges = new ArrayList<>(this.minCutEdges);
        Graph graph = new Graph(edges, vertices);
        graph.minCutEdges = minCutEdges;
        SuperEdge selectedEdge;
        Random random = new Random();
        int r;
        while (vertices.size() > bound) {
            r = random.nextInt(edges.size());
            selectedEdge = edges.get(r);
            minCutEdges.add(selectedEdge);
            edges.remove(selectedEdge);
            graph.merge(selectedEdge.getStartSuperVertex(), selectedEdge.getEndSuperVertex());
        }
        graph.size = vertices.size();
        return graph;
    }

    private void merge(SuperVertex v1, SuperVertex v2) {
        SuperVertex superVertex = new SuperVertex(v1.getIndex());
        vertices.remove(v1);
        vertices.remove(v2);
        vertices.add(superVertex);
        superVertex.getVertices().addAll(v1.getVertices());
        superVertex.getVertices().addAll(v2.getVertices());
        ArrayList<SuperEdge> temp = new ArrayList<>(edges);
        SuperEdge edge;
        boolean v1HasStart, v1HasEnd, v2HasStart, v2HasEnd;
        for (SuperEdge e : temp) {
            v1HasStart = v1.getVertices().contains(e.getStartVertex());
            v1HasEnd = v1.getVertices().contains(e.getEndVertex());
            v2HasStart = v2.getVertices().contains(e.getStartVertex());
            v2HasEnd = v2.getVertices().contains(e.getEndVertex());
            if ((v1HasStart && v2HasEnd) || (v1HasEnd && v2HasStart)) {
                minCutEdges.add(e);
                edges.remove(e);
            } else if (v1HasStart || v2HasStart) {
                edge = new SuperEdge(e.getStartVertex(), e.getEndVertex(), superVertex, e.getEndSuperVertex());
                edges.remove(e);
                edges.add(edge);
            } else if (v1HasEnd || v2HasEnd) {
                edge = new SuperEdge(e.getStartVertex(), e.getEndVertex(), e.getStartSuperVertex(), superVertex);
                edges.remove(e);
                edges.add(edge);
            }
        }
    }
}