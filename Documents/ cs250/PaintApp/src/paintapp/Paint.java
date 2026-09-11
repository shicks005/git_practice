/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paintapp;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import javafx.scene.image.ImageView;
import java.io.File;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


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
       MenuItem open = new MenuItem("Open");
       MenuItem exit = new MenuItem("Exit");

       
       file.getItems().add(open);
       file.getItems().add(save);
       file.getItems().add(saveAs);
       file.getItems().add(exit);
               
       menuBar.getMenus().add(file);
       pane = new BorderPane();
       pane.setTop(menuBar);
       pane.setCenter(canvas);
       
       
       open.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent e){
           onOpen();
           }
   });   
       save.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent e){
           onSave();
       }   
   });
       saveAs.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent e){
           saveAs();
       }   
   });
       
       exit.setOnAction(new EventHandler<ActionEvent>(){
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
    

//The methods that makes everything interactable   
   public void onOpen(){
       FileChooser fileChooser = new FileChooser();
       File file = fileChooser.showOpenDialog(null);
       
       if(file != null){
           Image image = new Image(file.toURI().toString());
           GraphicsContext grcon = canvas.getGraphicsContext2D();
           
           grcon.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
           grcon.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight());
           
                
       }
   }
   public void onSave(){
       Image snapshot = canvas.snapshot(null,null);
       try {
           ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", new File("paint.png"));
           System.out.println("Image Saved");
           
       } catch (Exception e){
           System.out.println("Failed to save image: " + e);
       }
   }
   
   public void saveAs(){
       FileChooser fileChooser = new FileChooser();
       fileChooser.setTitle("Save Image");
       fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*png"));
       
       File file = fileChooser.showSaveDialog(null);
       if(file != null){
           try{
               Image snapshot = canvas.snapshot(null,null);
               ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
               System.out.println("Image Saved");
           } catch(Exception e){
               System.out.println("Failed to save image: " + e);
           }
       }
   }
    
   public void onExit(){
       Platform.exit();
   }
}

