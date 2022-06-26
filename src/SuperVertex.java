import java.util.ArrayList;

public class SuperVertex extends Vertex {
    private ArrayList<Vertex> vertices;

    public SuperVertex(Vertex vertex) {
        super();
        vertices = new ArrayList<>();
        vertices.add(vertex);
    }

    public SuperVertex(int index) {
        super(index);
        vertices = new ArrayList<>();
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }
}
