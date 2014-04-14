package se.citerus;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.random;

public class MainLoop extends Application {
    public static final int MOVE_VEL = 10;
    boolean left = false;
    boolean right = false;
    boolean up = false;
    boolean down = false;

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 800, 600, Color.BLACK);
        primaryStage.setScene(scene);

        EventHandler<KeyEvent> eventEventHandlerPressed = inputEvent -> {
            KeyCode keyCode = inputEvent.getCode();
            if (keyCode == KeyCode.UP) up = true;
            else if (keyCode == KeyCode.DOWN) down = true;
            else if (keyCode == KeyCode.LEFT) left = true;
            else if (keyCode == KeyCode.RIGHT) right = true;
            else if (keyCode == KeyCode.ESCAPE) System.exit(0);

            inputEvent.consume();
        };

        EventHandler<KeyEvent> eventEventHandlerRelease = inputEvent -> {
            KeyCode keyCode = inputEvent.getCode();
            if (keyCode == KeyCode.UP) up = false;
            else if (keyCode == KeyCode.DOWN) down = false;
            else if (keyCode == KeyCode.LEFT) left = false;
            else if (keyCode == KeyCode.RIGHT) right = false;
            else if (keyCode == KeyCode.ESCAPE) System.exit(0);

            inputEvent.consume();
        };

        scene.setOnKeyPressed(eventEventHandlerPressed);
        scene.setOnKeyReleased(eventEventHandlerRelease);

        final List<Circle> obstacles = new LinkedList<>();

        Circle circle = createObstacle();

        obstacles.add(circle);

        root.getChildren().add(circle);

        Polygon player = new Polygon(
                0.0, 0.0,
                20.0, 40.0,
                -20.0, 40.0
        );
        player.setFill(Color.AQUA);
        player.setTranslateY(500);
        player.setTranslateX(400);

        root.getChildren().add(player);

        Timeline gameLoop = new Timeline(new KeyFrame(Duration.millis(10), event -> {
//            System.out.println("TICK!");

            for (Circle c : obstacles) {
                c.setCenterY(c.getCenterY() + 1);
            }
            if (random() < 0.01) {
                Circle c = createObstacle();
                obstacles.add(c);
                root.getChildren().add(c);
            }

//            System.out.println("pre: " + player.translateYProperty().getValue() + " (up = " + up + ")");
            Double curX = player.getTranslateX();
            Double curY = player.getTranslateY();
            player.setTranslateX(left ? curX - MOVE_VEL : right ? curX + MOVE_VEL : curX);
            player.setTranslateY(up ? curY - MOVE_VEL : down ? curY + MOVE_VEL : curY);
//            System.out.println("post: " + player.translateYProperty().getValue() + " (up = " + up + ")");
            for (Circle obstacle : obstacles) {
                if (obstacle.getCenterX() + obstacle.getRadius() > player.getTranslateX() &&
                        obstacle.getCenterX() - obstacle.getRadius() < player.getTranslateX() &&
                        obstacle.getCenterY() + obstacle.getRadius() > player.getTranslateY() &&
                        obstacle.getCenterY() - obstacle.getRadius() < player.getTranslateY()
                        ) System.exit(0);
            }
        }));

        gameLoop.setCycleCount(Animation.INDEFINITE);

        gameLoop.play();

        primaryStage.show();
    }

    private Circle createObstacle() {
        Circle circle = new Circle(40, Color.web("red", 0.5));
        circle.setStrokeType(StrokeType.OUTSIDE);
        circle.setStroke(Color.web("green", 0.9));
        circle.setStrokeWidth(4);
        circle.setCenterY(-40);
        circle.setCenterX(random() * 800);
        return circle;
    }

    public static void main(String[] args) {
        launch(args);
    }


}
