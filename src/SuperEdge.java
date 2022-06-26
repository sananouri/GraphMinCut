public class SuperEdge{
    private Vertex startVertex;
    private Vertex endVertex;
    private SuperVertex startSuperVertex;
    private SuperVertex endSuperVertex;

    public SuperEdge(Vertex startVertex, Vertex endVertex, SuperVertex startSuperVertex, SuperVertex endSuperVertex) {
        this.startVertex = startVertex;
        this.endVertex = endVertex;
        this.startSuperVertex = startSuperVertex;
        this.endSuperVertex = endSuperVertex;
    }

    public Vertex getStartVertex() {
        return startVertex;
    }

    public Vertex getEndVertex() {
        return endVertex;
    }

    public SuperVertex getStartSuperVertex() {
        return startSuperVertex;
    }

    public SuperVertex getEndSuperVertex() {
        return endSuperVertex;
    }
}
