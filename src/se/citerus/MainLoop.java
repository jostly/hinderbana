package se.citerus;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainLoop extends Application {

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 800, 600, Color.BLACK);
        primaryStage.setScene(scene);

        EventHandler<KeyEvent> eventEventHandler = inputEvent -> {
            System.out.println(inputEvent.toString());
            inputEvent.consume();
        };

        scene.setOnKeyPressed(eventEventHandler);

        Circle circle = new Circle(150, Color.web("white", 0.5));
        circle.setStrokeType(StrokeType.OUTSIDE);
        circle.setStroke(Color.web("white", 0.9));
        circle.setStrokeWidth(4);

        root.getChildren().add(circle);

        Timeline gameLoop = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            System.out.println("TICK!");
        }));
        gameLoop.setCycleCount(Animation.INDEFINITE);

        gameLoop.play();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
