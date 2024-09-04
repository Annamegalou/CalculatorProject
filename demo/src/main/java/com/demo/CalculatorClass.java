package com.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalculatorClass extends Application {

    @Override
    public void start(@SuppressWarnings("exports") Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Calc.fxml")); 
        Scene scene = new Scene(root);
        
        primaryStage.setTitle(" Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        primaryStage.centerOnScreen(); 
    }

    public static void main(String[] args) {
        launch(args);
    }
}