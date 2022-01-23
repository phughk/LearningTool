package com.kaznowski.hugh.learningtool;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Button button = new Button("This is a new button");
        button.setOnAction(actionEvent -> System.out.printf("Button was pressed\n"));
        StackPane root = new StackPane();
        root.getChildren().add(button);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Primary stage");
        stage.show();
    }
}
