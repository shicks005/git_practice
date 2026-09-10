/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package paintapp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.text.Text;
/**
 *
 * @author shhic
 */
public class PaintApp extends Application {
    
    @Override
    public void start(Stage stage)throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("paint.fxml"))));
        stage.setTitle("PaintApp");
        stage.show();
      

    }

   /* private void initUI(Stage stage){
        Text text = new Text();
        text.setText("POKEMON");
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 400, 300);
        root.getChildren().add(text);
        stage.setScene(scene);
        stage.setTitle("Catch em all"); 
        stage.show();
    } 
   */
    public static void main(String[] args) {
        //launch(args);
        System.out.print("Hello");
    }
    
}
