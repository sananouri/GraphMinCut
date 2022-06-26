import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class FxClass extends Application {
    private static Graph graph;
    private static ArrayList<Point2D> vertexes = new ArrayList<>();
    private  static Group pane = new Group();

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(pane, 1400, 700);
        stage.setScene(scene);
        stage.show();
        Label label = new Label("Graph Matrix: ");
        label.setLayoutX(450);
        label.setLayoutY(125);
        TextArea textField = new TextArea();
        textField.setLayoutX(570);
        textField.setLayoutY(120);
        textField.setPrefWidth(300);
        textField.setPrefHeight(300);
        pane.getChildren().add(label);
        pane.getChildren().add(textField);
        Button button = new Button("Draw Minimum Cut");
        button.setLayoutX(640);
        button.setLayoutY(430);
        button.setOnMouseClicked(event -> {
            pane.getChildren().clear();
            String matrix = textField.getText();
            graph = Graph.getGraph(matrix);
            drawMinCut();
        });
        pane.getChildren().add(button);
    }

    private void drawMinCut() {
        Label label = new Label("Graph");
        label.setLayoutX(350);
        pane.getChildren().add(label);
        setVertexes(350, 50);//graph
        pane.getChildren().addAll(getGraph(graph.getEdges()));
        label = new Label("Minimum Cut");
        label.setLayoutX(1050);
        pane.getChildren().add(label);
        setVertexes(1050, 50);//minimum cut
        pane.getChildren().addAll(getGraph(graph.getMinCutEdges()));
        label = new Label((graph.getEdges().size() - graph.getMinCutEdges().size()) + " edges removed.");
        label.setLayoutX(600);
        label.setLayoutY(650);
        pane.getChildren().add(label);
    }

    private static ArrayList<Node> getGraph(ArrayList<SuperEdge> edges) {
        ArrayList<Node> minimumCut = new ArrayList<>();
        Line line;
        Point2D from, to;
        Circle circle;
        int index;
        Label label;
        for (Point2D point : vertexes) {
            circle = new Circle(point.getX(), point.getY(), 15);
            minimumCut.add(circle);
            label = new Label("V" + (vertexes.indexOf(point) + 1));
            label.setTextFill(Color.WHITE);
            label.setLayoutX(point.getX() - 10);
            label.setLayoutY(point.getY() - 10);
            minimumCut.add(label);
        }
        for (SuperEdge edge : edges) {
            index = (edge.getStartVertex().getIndex() - 1) / 2;
            from = vertexes.get(index);
            index = (edge.getEndVertex().getIndex() - 1) / 2;
            to = vertexes.get(index);
            line = new Line(from.getX(), from.getY(), to.getX(), to.getY());
            minimumCut.add(line);
        }
        return minimumCut;
    }

    private static void setVertexes(double initialX, double initialY) {
        int graphSize = graph.getSize();
        final double alpha = 2 * Math.PI / graphSize, r = 300;
        double beta = alpha, x , y;
        ArrayList<Point2D> points = new ArrayList<>();
        Point2D point2D;
        points.add(new Point2D(initialX, initialY));
        for (int i = 1; i < graphSize; i++) {
            x = Math.abs(Math.sin(beta) * r);
            y = Math.abs(Math.cos(beta) * r);
            if (beta <= Math.PI / 2) {
                point2D = new Point2D(initialX + x, initialY + r - y);
            } else if (beta <= Math.PI) {
                point2D = new Point2D(initialX + x, initialY + r + y);
            } else if (beta <= 3 * Math.PI / 2) {
                point2D = new Point2D(initialX - x, initialY + r + y);
            } else {
                point2D = new Point2D(initialX - x, initialY + r - y);
            }
            points.add(point2D);
            beta += alpha;
        }
        vertexes = points;
    }
}
