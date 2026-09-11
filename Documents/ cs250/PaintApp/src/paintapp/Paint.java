/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paintapp;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import java.io.File;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 *
 * @author shhic
 */
public class Paint {
   private Canvas canvas;
   private MenuBar menuBar;
   private BorderPane pane;
    
   
   public Paint(){
       canvas = new Canvas(600,600);
       
       MenuBar menuBar = new MenuBar();
       Menu file = new Menu("File");
       MenuItem save = new MenuItem("Save");
       MenuItem saveAs = new MenuItem("Save As");
       
       file.getItems().add(save);
       file.getItems().add(saveAs);
       
       menuBar.getMenus().add(file);
       pane = new BorderPane();
       pane.setTop(menuBar);
       pane.setCenter(canvas);
       
       save.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent e){
           onSave();
       }   
   });
       saveAs.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent e){
           onExit();
       }   
   });
   }
   
   public Canvas getCanvas(){
       return canvas;
   }
   
   public MenuBar getMenuBar(){
       return menuBar;
   }
   
   public BorderPane getPane(){
       return pane;
   }
       
   
   public void onSave(){
       try {
           Image snapshot = canvas.snapshot(null,null);
           ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", new File("paint.png"));
           
       } catch (Exception e){
           System.out.println("Failed to save image: " + e);
       }
   }
    
   public void onExit(){
       Platform.exit();
   }
}

