public class Vertex implements Comparable {
    private int index;
    private static int counter = 1;

    public Vertex() {
        this.index = counter;
        counter++;
    }

    public Vertex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Vertex) {
            return ((Vertex) o).index - index;
        }
        return 0;
    }
}
