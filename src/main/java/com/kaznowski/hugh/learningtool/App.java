package com.kaznowski.hugh.learningtool;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        terminal();
    }

    private static void terminal() {
        Scanner scanner = new Scanner(System.in);
        LearningResource learningResource = new ResourceSelector(scanner, System.out).selectResource();
        new Learner(scanner, System.out).learn(learningResource);
    }
}
