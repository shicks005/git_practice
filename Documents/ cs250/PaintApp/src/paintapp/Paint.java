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
import javafx.scene.control.MenuButton;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyCombination;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;


/**
 *
 * @author shhic
 */

/**
 * Creates and manages the Paint application.
 */
public class Paint {
   private Canvas canvas;
   private MenuBar menuBar;
   private BorderPane pane;
   private ColorPicker colorPicker;
   private TextField brushSize;
   private File currentFile;
   private SmartSave smartSave;
   private LineTool linetool;
   private CurvedLineTool curvedLineTool;
   private boolean lineToolSelected = false;
   private boolean curvedLineSelected = false;
   private ShapeTool shapeTool;
   private ColorPicker fillColorPicker;
   private boolean colorGrabberSelected = false;
   private TabPane tabPane;
   
   private String selectedShape = "";
   private String lineStyle = "solid";
   
   private double lastX;
   private double lastY;
    
   //creates the menu bar and canvas
   public Paint(){
       canvas = new Canvas(600,600);
       smartSave = new SmartSave(this);
       linetool = new LineTool(canvas);
       curvedLineTool = new CurvedLineTool(canvas);
       shapeTool = new ShapeTool(canvas);
       
       menuBar = new MenuBar();
       Menu file = new Menu("File");
       Menu help = new Menu("Help");
       
       
       MenuItem save = new MenuItem("Save");
       MenuItem saveAs = new MenuItem("Save As");
       MenuItem open = new MenuItem("Open");
       MenuItem exit = new MenuItem("Exit");
       MenuItem newTab = new MenuItem("New");
       
       //sprint 2
       MenuItem helpItem = new MenuItem("Help");
       MenuItem about = new MenuItem("About");
       
       open.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
       save.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
       saveAs.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+S"));
       exit.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
       newTab.setAccelerator(KeyCombination.keyCombination("Ctrl+T"));

       file.getItems().add(newTab);
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
       
       Label sizeLabel = new Label("Brush Size (pixels):");
       
       brushSize = new TextField();
       brushSize.setPromptText("Pixels");
       brushSize.setPrefWidth(50);
       
       colorPicker = new ColorPicker(Color.BLACK);
       fillColorPicker = new ColorPicker();
       fillColorPicker.setValue(Color.WHITE);
       
       ToolBar toolBar = new ToolBar();
       Button resizeButton = new Button("Resize Canvas");
       
       
       toolBar.getItems().add(sizeLabel);
       toolBar.getItems().add(brushSize);
       toolBar.getItems().add(colorPicker);
       toolBar.getItems().add(fillColorPicker);
       
       Label borderLabel = new Label("Border:");
       Label fillLabel = new Label("Fill:");
       
     
       pane = new BorderPane();
       
       
       
       HBox top = new HBox();
       top.getChildren().add(menuBar);
       //pane.setTop(new HBox(menuBar, toolBar));
       //pane.setCenter(canvas);
       tabPane = new TabPane();

       Tab firstTab = new Tab("Tab 1");
       ScrollPane scrollPane = new ScrollPane(canvas);
       scrollPane.setPannable(true);

       firstTab.setContent(scrollPane);
       firstTab.setClosable(false);
       firstTab.setUserData(canvas);
       tabPane.getTabs().add(firstTab);

        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTabPage) -> {
            if (newTabPage != null) {
                canvas = (Canvas) newTabPage.getUserData();

                linetool = new LineTool(canvas);
                curvedLineTool = new CurvedLineTool(canvas);
                shapeTool = new ShapeTool(canvas);

                setupCanvasEvents();
            }
        }
    );
       
       VBox top2 = new VBox(menuBar, toolBar);
       pane.setTop(top2);
       pane.setCenter(tabPane);
       
       
       
       //////////////////////////////////////////////////////////////////////////////////////
         
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
        

        Button brushButton = new Button("Brush");
         brushButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                lineToolSelected = false;
                curvedLineSelected = false;
                selectedShape = "";
                colorGrabberSelected = false;
       }
    });
         
        Button colorGrabber = new Button("Color Grabber");
        
        colorGrabber.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                colorGrabberSelected = true;
            }
        });
        
         
         
         MenuButton pencilButton = new MenuButton("Pencil");
        
        MenuItem lineItem = new MenuItem("Line");
        

        lineItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                lineToolSelected = true;
                curvedLineSelected = false;
                selectedShape = "";
                colorGrabberSelected = false;
       }
    });
        MenuItem curvedLineItem = new MenuItem("Curved Line");
        curvedLineItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                lineToolSelected = false;
                curvedLineSelected = true;
                selectedShape = "";
                colorGrabberSelected = false;
       }
    });
        
        toolBar.getItems().add(brushButton);
        toolBar.getItems().add(pencilButton);
        toolBar.getItems().add(resizeButton);
        pencilButton.getItems().add(lineItem);
        pencilButton.getItems().add(curvedLineItem);
        
        
        MenuButton shapesButton = new MenuButton("Shapes");
        
        MenuItem squareItem = new MenuItem("Square");
        MenuItem rectangleItem = new MenuItem("Rectangle");
        MenuItem circleItem = new MenuItem("Circle");
        MenuItem ellipseItem = new MenuItem("Ellipse");
        MenuItem triangleItem = new MenuItem("Triangle");
        
        shapesButton.getItems().add(squareItem);
        shapesButton.getItems().add(rectangleItem);
        shapesButton.getItems().add(circleItem);
        shapesButton.getItems().add(ellipseItem);
        shapesButton.getItems().add(triangleItem);
        
        toolBar.getItems().add(shapesButton);
        
        squareItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                selectedShape = "square";
                lineToolSelected = false;
                curvedLineSelected = false;
                colorGrabberSelected = false;
            }
        });
        
        rectangleItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                selectedShape = "rectangle";
                lineToolSelected = false;
                curvedLineSelected = false;
                colorGrabberSelected = false;
            }
        });
        
        circleItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                selectedShape = "circle";
                lineToolSelected = false;
                curvedLineSelected = false;
                colorGrabberSelected = false;
            }
        });
        
        ellipseItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                selectedShape = "ellipse";
                lineToolSelected = false;
                curvedLineSelected = false;
                colorGrabberSelected = false;
            }
        });
        
        triangleItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                selectedShape = "triangle";
                lineToolSelected = false;
                curvedLineSelected = false;
                colorGrabberSelected = false;
            }
        });
        
        
        MenuButton lineStyleButton = new MenuButton("Line Style");
        MenuItem solidItem = new MenuItem("Solid");
        MenuItem dashedItem = new MenuItem("Dashed");
        
        solidItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                lineStyle = "solid";
            }
        });
        
        dashedItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                lineStyle = "dashed";
            }
        });
        lineStyleButton.getItems().add(solidItem);
        lineStyleButton.getItems().add(dashedItem);
        toolBar.getItems().add(lineStyleButton);
        toolBar.getItems().add(colorGrabber);
        
        newTab.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                Canvas newCanvas = new Canvas(600,600);
                ScrollPane newScrollPane = new ScrollPane(newCanvas);
                
                newScrollPane.setPannable(true);
                
                String tabName = ("Tab " + (tabPane.getTabs().size() + 1));
                Tab newTabPage = new Tab(tabName);
                newTabPage.setContent(newScrollPane);
                newTabPage.setUserData(newCanvas);
                tabPane.getTabs().add(newTabPage);
                tabPane.getSelectionModel().select(newTabPage);
                
                canvas = newCanvas;
                
                linetool = new LineTool(canvas);
                curvedLineTool = new CurvedLineTool(canvas);
                shapeTool = new ShapeTool(canvas);
                setupCanvasEvents();
                
                 
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
        
   setupCanvasEvents();
   }
   
   //////////////////////////////////////////////////////////////////////////////////////////////////////
   
 /**
 * Sets up the mouse events for the current canvas.
 */
   private void setupCanvasEvents(){
       canvas.setOnMousePressed(event -> {
            if (colorGrabberSelected){
                WritableImage image = new WritableImage((int) canvas.getWidth(),(int) canvas.getHeight());
                 canvas.snapshot(null, image);
                 PixelReader pixelReader = image.getPixelReader();
                 Color grabbedColor = pixelReader.getColor((int) event.getX(),(int) event.getY());
                 colorPicker.setValue(grabbedColor);
                 
                 colorGrabberSelected = false;
            }
            
            lastX = event.getX();
            lastY = event.getY();
        });

        canvas.setOnMouseDragged(event -> {
            if (!lineToolSelected && !curvedLineSelected && selectedShape.isEmpty()){
                GraphicsContext gc = canvas.getGraphicsContext2D();
                
                try{
                    double size = Double.parseDouble(brushSize.getText());
                    gc.setLineWidth(size);
                    gc.setStroke(colorPicker.getValue());
                    gc.strokeLine(lastX,lastY, event.getX(),event.getY());
                    
                    smartSave.changesMade();
                    lastX = event.getX();
                    lastY = event.getY();
                } catch (NumberFormatException e){
                    System.out.println("Please enter a valid brush size.");
                }
            }
            
            if (curvedLineSelected) {

                try {
                    double size =
                            Double.parseDouble(brushSize.getText());
                    curvedLineTool.drawCurve(
                            lastX,
                            lastY,
                            event.getX(),
                            event.getY(),
                            size,
                            colorPicker.getValue()
                    );
                    smartSave.changesMade();
                    lastX = event.getX();
                    lastY = event.getY();

                } catch (NumberFormatException e) {
                    System.out.println( "Please enter a valid brush size.");
                }
            }

        });
        
        canvas.setOnMouseReleased(e -> {

            if (lineToolSelected) {
                try {
                    double size =
                            Double.parseDouble(brushSize.getText());
                    linetool.drawLine(lastX,lastY,e.getX(),e.getY(),size,colorPicker.getValue(), lineStyle);
                    smartSave.changesMade();
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid brush size.");

                }
            }
            if(selectedShape.equals("square")){
                try{
                    double size = Double.parseDouble(brushSize.getText());
                    shapeTool.drawSquare(lastX, lastY, e.getX(), e.getY(), size, colorPicker.getValue(), fillColorPicker.getValue(), lineStyle);
                    smartSave.changesMade();
                } catch (NumberFormatException ex){
                    System.out.println("Invalid brush size.");
                }
            }
            
            if(selectedShape.equals("rectangle")){
                try{
                    double size = Double.parseDouble(brushSize.getText());
                    shapeTool.drawRectangle(lastX, lastY, e.getX(), e.getY(), size, colorPicker.getValue(), fillColorPicker.getValue(), lineStyle);
                    smartSave.changesMade();
                } catch (NumberFormatException ex){
                    System.out.println("Invalid brush size.");
                }
            }
            
            if(selectedShape.equals("circle")){
                try{
                    double size = Double.parseDouble(brushSize.getText());
                    shapeTool.drawCircle(lastX, lastY, e.getX(), e.getY(), size, colorPicker.getValue(), fillColorPicker.getValue(), lineStyle);
                    smartSave.changesMade();
                } catch (NumberFormatException ex){
                    System.out.println("Invalid brush size.");
                }
            }
            
            if(selectedShape.equals("ellipse")){
                try{
                    double size = Double.parseDouble(brushSize.getText());
                    shapeTool.drawEllipse(lastX, lastY, e.getX(), e.getY(), size, colorPicker.getValue(),fillColorPicker.getValue(), lineStyle);
                    smartSave.changesMade();
                } catch (NumberFormatException ex){
                    System.out.println("Invalid brush size.");
                }
            }
            
            if(selectedShape.equals("triangle")){
                try{
                    double size = Double.parseDouble(brushSize.getText());
                    shapeTool.drawTriangle(lastX, lastY, e.getX(), e.getY(), size, colorPicker.getValue(), fillColorPicker.getValue(), lineStyle);
                    smartSave.changesMade();
                } catch (NumberFormatException ex){
                    System.out.println("Invalid brush size.");
                }
            }
        });
   }
   //////////////////////////////////////////////////////////////////////////////////////////////
   
   
   public Canvas getCanvas(){
       return canvas;
   }
   
   public MenuBar getMenuBar(){
       return menuBar;
   }
   
   public BorderPane getPane(){
       return pane;
   }
    
   ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   
   
//The methods that makes everything interactable 
   
 /**
 * Opens an image file and displays it on the canvas.
 */
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


