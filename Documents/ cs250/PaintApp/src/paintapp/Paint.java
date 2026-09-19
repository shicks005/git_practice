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
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


/**
 *
 * @author shhic
 */
public class Paint {
   private Canvas canvas;
   private MenuBar menuBar;
   private BorderPane pane;
   private ColorPicker colorPicker;
   private TextField brushSize;
   private File currentFile;
   private SmartSave smartSave;
   
   
   private double lastX;
   private double lastY;
    
   //creates the menu bar and canvas
   public Paint(){
       canvas = new Canvas(600,600);
       smartSave = new SmartSave(this);
       
       menuBar = new MenuBar();
       Menu file = new Menu("File");
       Menu help = new Menu("Help");
       
       MenuItem save = new MenuItem("Save");
       MenuItem saveAs = new MenuItem("Save As");
       MenuItem open = new MenuItem("Open");
       MenuItem exit = new MenuItem("Exit");
       
       //sprint 2
       MenuItem helpItem = new MenuItem("Help");
       MenuItem about = new MenuItem("About");

       
       file.getItems().add(open);
       file.getItems().add(save);
       file.getItems().add(saveAs);
       file.getItems().add(exit);
       
       //Sprint 2//
       help.getItems().add(helpItem);
       help.getItems().add(about);
       /////////////
      
       menuBar.getMenus().add(file);
       menuBar.getMenus().add(help);
       //pane = new BorderPane();
       //pane.setTop(menuBar);
       //pane.setCenter(canvas);
       
       Label sizeLabel = new Label("Brush Size:");
       
       brushSize = new TextField("5");
       brushSize.setPrefWidth(50);
       
       colorPicker = new ColorPicker(Color.BLACK);
       
       ToolBar toolBar = new ToolBar();
       Button resizeButton = new Button("Resize Canvas");
       
       toolBar.getItems().add(sizeLabel);
       toolBar.getItems().add(brushSize);
       toolBar.getItems().add(colorPicker);
       toolBar.getItems().add(resizeButton);
       pane = new BorderPane();
       
       HBox top = new HBox();
       top.getChildren().add(menuBar);
       //pane.setTop(new HBox(menuBar, toolBar));
       //pane.setCenter(canvas);
       ScrollPane scrollPane = new ScrollPane(canvas);
       scrollPane.setPannable(true);
       pane.setTop(new HBox(menuBar, toolBar));
       pane.setCenter(scrollPane);
       
       //User actions
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
       
       //For help menu(sprint 2)
       helpItem.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent e){
           showHelpWindow();
       }
    });
       
        about.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent e){
           showAboutWindow();
       }
    });
        
        resizeButton.setOnAction(new EventHandler<ActionEvent>() {
    @Override
    public void handle(ActionEvent e) {

        double newWidth = canvas.getWidth() + 200;
        double newHeight = canvas.getHeight() + 200;

        Image oldImage = canvas.snapshot(null, null);

        canvas.setWidth(newWidth);
        canvas.setHeight(newHeight);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.drawImage(oldImage, 0, 0);

    }
});
        
        canvas.setOnMousePressed(event -> {
            lastX = event.getX();
            lastY = event.getY();
        });

        canvas.setOnMouseDragged(event -> {

            GraphicsContext gc = canvas.getGraphicsContext2D();

            try {
                double size = Double.parseDouble(brushSize.getText());

                gc.setLineWidth(size);
                gc.setStroke(colorPicker.getValue());

                gc.strokeLine(
                        lastX,
                        lastY,
                        event.getX(),
                        event.getY()
                );
                
                smartSave.changesMade();

                lastX = event.getX();
                lastY = event.getY();

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid brush size.");
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
       fileChooser.setTitle("Open Image");
       fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp"));
       
       
       File file = fileChooser.showOpenDialog(null);
       
       if(file != null){
           
           Image image = new Image(file.toURI().toString());
           
           canvas.setWidth(image.getWidth());
           canvas.setHeight(image.getHeight());
           GraphicsContext grcon = canvas.getGraphicsContext2D();
           
           grcon.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
           grcon.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight());
           
             
       }
   }
   public void onSave(){

       
       Image snapshot = canvas.snapshot(null,null);
       try {
           ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", new File("paint.png"));
           
           smartSave.changesSaved();
           System.out.println("Image Saved");
           
       } catch (Exception e){
           System.out.println("Failed to save image: " + e);
       }
       
   }
   
   public void saveAs(){
       FileChooser fileChooser = new FileChooser();
       fileChooser.setTitle("Save Image");
       fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
       fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG Files", "*.jpg"));
       fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("BMP Files", "*.bmp"));
       
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


public void showHelpWindow(){
    Stage helpStage = new Stage();
    helpStage.setTitle("Help");
    
    Label helpwind = new Label("Paint Help\n\n" + "Click,hold, and drag to draw on the canvas.\n"
    + "Use the text box to change the size of the brush\n" + "Choose a color using the color picker.\n" + "Click file to use open an image and or use save options");
    
    Button closeButton = new Button("Close");
    
    closeButton.setOnAction(new EventHandler<ActionEvent>(){
       @Override
       public void handle(ActionEvent e){
           helpStage.close();
       }
   });
    
    VBox layout = new VBox(10);
    layout.getChildren().add(helpwind);
    layout.getChildren().add(closeButton);
    
    Scene scene = new Scene(layout, 400, 250);
    helpStage.setScene(scene);
    helpStage.show();
    
}

public void showAboutWindow(){
    Stage aboutStage = new Stage();
    aboutStage.setTitle("About");
    
    Label about = new Label("Paint created by me");
    
     Button closeButton = new Button("Close");
    
    closeButton.setOnAction(new EventHandler<ActionEvent>(){
       @Override
       public void handle(ActionEvent e){
           aboutStage.close();
       }
   });
    
    VBox layout = new VBox(10);
    layout.getChildren().add(about);
    layout.getChildren().add(closeButton);
    
    Scene scene = new Scene(layout, 400, 250);
    aboutStage.setScene(scene);
    aboutStage.show();
    
}
public boolean confirmClose(Stage stage){
    return smartSave.confirmClose(stage);
}
}    


