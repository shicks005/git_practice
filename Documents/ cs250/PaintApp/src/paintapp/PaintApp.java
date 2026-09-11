/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package paintapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.layout.BorderPane;
/**
 *
 * @author shhic
 */
public class PaintApp extends Application {
    
    @Override
    public void start(Stage stage){
        Paint paint = new Paint();
        //pane = new BorderPane();
        
 
        Scene scene = new Scene(paint.getPane(),700,630);
        
        stage.setTitle("Paint App");
        stage.setScene(scene);
        stage.show();
      
    }

    public static void main(String[] args) {
        launch(args);
        
    }
}
