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
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;
import java.util.Stack;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonBar;
import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import javafx.scene.control.CheckBox;

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
   private int polygonSides = 5;
   private Stack<WritableImage> undoStack;
   private Stack<WritableImage> redoStack;
   private TextTool textTool;
   private boolean textToolSelected = false;
   private SelectionTool selectionTool;
   private boolean selectionToolSelected = false;
   private double selectionStartX;
   private double selectionStartY;
   private double selectionEndX;
   private double selectionEndY;
   private Rectangle selectionRectangle;
   private ImageView pastedImageView;
   private boolean movingPastedImage = false;
   private double pasteOffsetX;
   private double pasteOffsetY;
   private Pane canvasPane;
   private Label autoSaveLabel;
   private int starPoints = 5;
   private Canvas previewCanvas;
   private GraphicsContext preview;
   private ShapeTool previewShape;
   private PaintWebServer webServer = new PaintWebServer();
  
   
   
    
   //creates the menu bar and canvas
   public Paint(){
       canvas = new Canvas(600,600);
       smartSave = new SmartSave(this);
       linetool = new LineTool(canvas);
       curvedLineTool = new CurvedLineTool(canvas);
       shapeTool = new ShapeTool(canvas);
       textTool = new TextTool(canvas);
       selectionTool = new SelectionTool(canvas);
       selectionRectangle = new Rectangle();
       autoSaveLabel = new Label("Next AutoSave: 05:00");
       
       
       autoSaveLabel.setVisible(true);
       selectionRectangle.setFill(Color.TRANSPARENT);
       selectionRectangle.setStroke(Color.BLACK);
       selectionRectangle.setStrokeWidth(1);
       selectionRectangle.getStrokeDashArray().addAll(5.0, 5.0);
       selectionRectangle.setVisible(false);
       
       undoStack = new Stack<WritableImage>();
       redoStack = new Stack<WritableImage>();
       
       menuBar = new MenuBar();
       Menu file = new Menu("File");
       Menu edit = new Menu("Edit");
       Menu help = new Menu("Help");
       Menu view = new Menu("View");
       Menu webMenu = new Menu("Web Server");
       
       
       MenuItem save = new MenuItem("Save");
       MenuItem saveAs = new MenuItem("Save As");
       MenuItem open = new MenuItem("Open");
       MenuItem exit = new MenuItem("Exit");
       MenuItem newTab = new MenuItem("New");
       MenuItem clearCanvas = new MenuItem("Clear Canvas");
       MenuItem undo = new MenuItem("Undo");
       MenuItem redo = new MenuItem("Redo");
       MenuItem toggleTimer = new MenuItem("Show/Hide Timer");
       MenuItem shareImages = new MenuItem("Share Images");
       MenuItem stopServer = new MenuItem("Stop Server");
       
       //sprint 2
       MenuItem helpItem = new MenuItem("Help");
       MenuItem about = new MenuItem("About");
       MenuItem paste = new MenuItem("Paste");
       
       open.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
       save.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
       saveAs.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+S"));
       exit.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
       newTab.setAccelerator(KeyCombination.keyCombination("Ctrl+T"));
       undo.setAccelerator(KeyCombination.keyCombination("Ctrl+Z"));
       redo.setAccelerator(KeyCombination.keyCombination("Ctrl+Y"));
       paste.setAccelerator(KeyCombination.keyCombination("Ctrl+V"));
       

       file.getItems().add(newTab);
       file.getItems().add(clearCanvas);
       file.getItems().add(open);
       file.getItems().add(save);
       file.getItems().add(saveAs);
       file.getItems().add(exit);
       
       edit.getItems().add(undo);
       edit.getItems().add(redo);
       edit.getItems().add(paste);
       
       
       //Sprint 2//
       help.getItems().add(helpItem);
       help.getItems().add(about);
       /////////////
      
       menuBar.getMenus().add(file);
       menuBar.getMenus().add(edit);
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
       
       Button textButton = new Button("Text Box");
       Button selectButton = new Button("Select");
       
       
       
       toolBar.getItems().add(sizeLabel);
       toolBar.getItems().add(selectButton);
       toolBar.getItems().add(brushSize);
       toolBar.getItems().add(colorPicker);
       toolBar.getItems().add(fillColorPicker);
       
       Label borderLabel = new Label("Border:");
       Label fillLabel = new Label("Fill:");
       
     
       pane = new BorderPane();
       canvasPane = new Pane();
      
       previewCanvas = new Canvas(canvas.getWidth(), canvas.getHeight());
       preview = previewCanvas.getGraphicsContext2D();
       previewShape = new ShapeTool(previewCanvas);
       previewCanvas.setMouseTransparent(true);
       
       canvasPane.getChildren().add(canvas);
       canvasPane.getChildren().add(previewCanvas);
       canvasPane.getChildren().add(selectionRectangle);
       
       
       
       
       HBox top = new HBox();
       top.getChildren().add(menuBar);
       //pane.setTop(new HBox(menuBar, toolBar));
       //pane.setCenter(canvas);
       tabPane = new TabPane();

       Tab firstTab = new Tab("Tab 1");
       ScrollPane scrollPane = new ScrollPane(canvasPane);
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
       
        
       HBox menuRow = new HBox();
       menuRow.getChildren().add(menuBar);
       HBox.setHgrow(menuBar, Priority.ALWAYS);
       autoSaveLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;");
       autoSaveLabel.setMinWidth(55);
       autoSaveLabel.setAlignment(Pos.CENTER);
       menuRow.getChildren().add(autoSaveLabel);
        
       VBox top2 = new VBox(menuRow, toolBar);
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
                textToolSelected = false;
                selectionToolSelected = false;
       }
    });
         
        Button colorGrabber = new Button("Color Grabber");
        
        colorGrabber.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                colorGrabberSelected = true;
                textToolSelected = false;
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
                textToolSelected = false;
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
                textToolSelected = false;
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
        MenuItem pentagonItem = new MenuItem("Pentagon");
        MenuItem polygonItem = new MenuItem("Polygon");
        MenuItem rightTriangle = new MenuItem("Right Triangle");
        MenuItem isoscelesTriangle = new MenuItem("Isosceles Triangle");
        MenuItem starItem = new MenuItem("Star");
        
        shapesButton.getItems().add(squareItem);
        shapesButton.getItems().add(rectangleItem);
        shapesButton.getItems().add(circleItem);
        shapesButton.getItems().add(ellipseItem);
        shapesButton.getItems().add(triangleItem);
        shapesButton.getItems().add(pentagonItem);
        shapesButton.getItems().add(polygonItem);
        shapesButton.getItems().add(rightTriangle);
        shapesButton.getItems().add(isoscelesTriangle);
        shapesButton.getItems().add(starItem);
        
        toolBar.getItems().add(shapesButton);
        
        toolBar.getItems().add(textButton);
        
        squareItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                selectedShape = "square";
                lineToolSelected = false;
                curvedLineSelected = false;
                colorGrabberSelected = false;
                textToolSelected = false;
            }
        });
        
        rectangleItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                selectedShape = "rectangle";
                lineToolSelected = false;
                curvedLineSelected = false;
                colorGrabberSelected = false;
                textToolSelected = false;
            }
        });
        
        circleItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                selectedShape = "circle";
                lineToolSelected = false;
                curvedLineSelected = false;
                colorGrabberSelected = false;
                textToolSelected = false;
            }
        });
        
        ellipseItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                selectedShape = "ellipse";
                lineToolSelected = false;
                curvedLineSelected = false;
                colorGrabberSelected = false;
                textToolSelected = false;
            }
        });
        
        triangleItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                selectedShape = "triangle";
                lineToolSelected = false;
                curvedLineSelected = false;
                colorGrabberSelected = false;
                textToolSelected = false;
            }
        });
        
        rightTriangle.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                selectedShape = "rightTriangle";
                lineToolSelected = false;
                curvedLineSelected = false;
                colorGrabberSelected = false;
                textToolSelected = false;
            }
        });
        
        isoscelesTriangle.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                selectedShape = "isoscelesTriangle";
                lineToolSelected = false;
                curvedLineSelected = false;
                colorGrabberSelected = false;
                textToolSelected = false;
            }
        });
        
        pentagonItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                selectedShape = "pentagon";
                lineToolSelected = false;
                curvedLineSelected = false;
                colorGrabberSelected = false;
                textToolSelected = false;
            }
        });
        
        undo.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent event){
               undo();
           }
        });
        
        redo.setOnAction(new EventHandler<ActionEvent>(){
           @Override
           public void handle(ActionEvent event){
               redo();
           }
        });
        
        polygonItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Polygon");
                dialog.setHeaderText("Choose the number of sides");
                dialog.setContentText("Number of sides:");
                Optional<String> result = dialog.showAndWait();
                
                if (result.isPresent()){
                    try{
                        int sides = Integer.parseInt(result.get());
                        if (sides >= 3){
                            polygonSides = sides;
                            selectedShape = "polygon";
                            lineToolSelected = false;
                            curvedLineSelected = false;
                            colorGrabberSelected = false;
                            textToolSelected = false;
                        }
                    } catch (NumberFormatException e){
                        System.out.println("Please enter a number.");
                    }
                }
                
            }  
        });
        
        starItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
               TextInputDialog dialog = new TextInputDialog("5"); 
               dialog.setTitle("Star");
               dialog.setHeaderText("Choose the number of points");
               dialog.setContentText("Number of points:");
               Optional<String> result = dialog.showAndWait();
               
               if (result.isPresent()){
                    try{
                        int points = Integer.parseInt(result.get());
                        if (points >= 4){
                            starPoints = points;
                            selectedShape = "star";
                            lineToolSelected = false;
                            curvedLineSelected = false;
                            colorGrabberSelected = false;
                            textToolSelected = false;
                            
                        }else{
                            System.out.println("Must have at least 4 points.");
                        }
                    } catch (NumberFormatException e){
                        System.out.println("Please enter a valid number of sides.");
                    }
                }
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
        
        clearCanvas.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Clear Canvas");
                alert.setHeaderText("Clear the canvas?");
                alert.setContentText("Are you sure you want to clear the canvas?");
                Optional<ButtonType> result = alert.showAndWait();
                
                if (result.isPresent() && result.get() == ButtonType.OK){
                    GraphicsContext gc = canvas.getGraphicsContext2D();
                    gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
                    smartSave.changesMade();
                }
            }
        });
        
        textButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                textToolSelected = true;
                lineToolSelected = false;
                curvedLineSelected = false;
                selectedShape = "";
                colorGrabberSelected = false;
            }
        });
        
        selectButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                selectionToolSelected = true;
                
                lineToolSelected = false;
                curvedLineSelected = false;
                selectedShape = "";
                colorGrabberSelected = false;
                textToolSelected = false;
            }
        });
        
        paste.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                
                WritableImage copiedImage = selectionTool.getCopiedImage();
                if (copiedImage != null){
                    pastedImageView = new ImageView(copiedImage);
                    pastedImageView.setX(50);
                    pastedImageView.setY(50);
                    canvasPane.getChildren().add(pastedImageView);
                    movingPastedImage = true;
                    
                    pastedImageView.setOnMousePressed(e -> {

                    pasteOffsetX = e.getX();
                    pasteOffsetY = e.getY();
                });
                    
                    pastedImageView.setOnMouseDragged(e -> {
                        
                    double newX = pastedImageView.getX()+ e.getX() - pasteOffsetX;

                    double newY = pastedImageView.getY() + e.getY() - pasteOffsetY;

                    pastedImageView.setX(newX);
                    pastedImageView.setY(newY);
                });
                    
                    pastedImageView.setOnMouseClicked(e -> {

                if (e.getClickCount() == 2) {
                        saveCanvasState();
                        GraphicsContext gc = canvas.getGraphicsContext2D();
                        gc.drawImage( pastedImageView.getImage(),pastedImageView.getX(), pastedImageView.getY());
                        canvasPane.getChildren().remove(pastedImageView);
                        pastedImageView = null;
                        movingPastedImage = false;
                        smartSave.changesMade();
                    }
                });
               }
            }
        });
        
        toggleTimer.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                autoSaveLabel.setVisible(!autoSaveLabel.isVisible());
                autoSaveLabel.setManaged(autoSaveLabel.isVisible());
            }
        });
        
        view.getItems().add(toggleTimer);
        menuBar.getMenus().add(view);
        
        
        shareImages.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                shareOnWeb();
            }
        });
        
        stopServer.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                webServer.stop();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Web Server");
                alert.setHeaderText("Server Stopped");
                alert.setContentText("The web server is now offline.");
                alert.showAndWait();
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
        previewCanvas.setWidth(newWidth);
        previewCanvas.setHeight(newHeight);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.drawImage(oldImage, 0, 0);

    }
});
   
   webMenu.getItems().add(shareImages);
   webMenu.getItems().add(stopServer);
   
   menuBar.getMenus().add(webMenu);
        
   setupCanvasEvents();
   
   System.out.println("autosave test");
   new AutoSave(this);
   }
   
   
   
   //////////////////////////////////////////////////////////////////////////////////////////////////////
   
   
   
 /**
 * Sets up the mouse events for the current canvas.
 */
   private void setupCanvasEvents(){
       canvas.setOnMousePressed(event -> {
           
           if (!colorGrabberSelected){
               saveCanvasState();
           }
           
           if(selectionToolSelected){
               selectionStartX = event.getX();
               selectionStartY = event.getY();
               selectionRectangle.setX(selectionStartX);
               selectionRectangle.setY(selectionStartY);
               selectionRectangle.setWidth(0);
               selectionRectangle.setHeight(0);
               
               selectionRectangle.setVisible(true);
           }
           
           if (textToolSelected){
               TextInputDialog dialog = new TextInputDialog();
               dialog.setTitle("Add Text");
               dialog.setHeaderText("Enter your text");
               dialog.setContentText("Text:");
               Optional<String> result = dialog.showAndWait();
               
               if (result.isPresent()){
                   textTool.drawText(result.get(), event.getX(), event.getY(), colorPicker.getValue());
                   smartSave.changesMade();
               }
               textToolSelected = false;
           }
           
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
            clearPreview();
            preview.setStroke(colorPicker.getValue());
            try{
                double size = Double.parseDouble(brushSize.getText());
                preview.setLineWidth(size);
                
                if(lineStyle.equals("dashed")){
                    preview.setLineDashes(10, 10);
                }else{
                    preview.setLineDashes(null);
                }
                preview.strokeLine(lastX,lastY,event.getX(), event.getY());
            } catch(NumberFormatException ex){
                System.out.println("Please enter a valid brush size.");
            }
            
            if (!selectedShape.isEmpty()){
                clearPreview();
                try{
                    double size = Double.parseDouble(brushSize.getText());
                    double x1 = lastX;
                    double y1 = lastY;
                    double x2 = event.getX();
                    double y2 = event.getY();
                    Color border = colorPicker.getValue();
                    Color fill = fillColorPicker.getValue();
                    
                    if (selectedShape.equals("square")){
                        previewShape.drawSquare(x1,y1,x2,y2,size,border,fill,lineStyle);
                    } else if (selectedShape.equals("rectangle")){
                        previewShape.drawRectangle(x1,y1,x2,y2,size,border,fill,lineStyle);
                    } else if (selectedShape.equals("circle")){
                        previewShape.drawCircle(x1,y1,x2,y2,size,border,fill,lineStyle);
                    } else if (selectedShape.equals("ellipse")){
                        previewShape.drawEllipse(x1,y1,x2,y2,size,border,fill,lineStyle);
                    } else if (selectedShape.equals("triangle")){
                        previewShape.drawTriangle(x1,y1,x2,y2,size,border,fill,lineStyle);
                    } else if (selectedShape.equals("rightTriangle")){
                        previewShape.drawRightTriangle(x1,y1,x2,y2,size,border,fill,lineStyle);
                    } else if (selectedShape.equals("isoscelesTriangle")){
                        previewShape.drawIsoscelesTriangle(x1,y1,x2,y2,size,border,fill,lineStyle);
                    } else if (selectedShape.equals("pentagon")){
                        previewShape.drawPentagon(x1,y1,x2,y2,size,border,fill,lineStyle);
                    } else if (selectedShape.equals("polygon")){
                        previewShape.drawPolygon(x1,y1,x2,y2,polygonSides,size,border,fill,lineStyle);
                    } else if (selectedShape.equals("star")){
                        previewShape.drawStar(x1,y1,x2,y2,starPoints,size,border,fill,lineStyle);
                    } 
                    }catch (NumberFormatException ex){
                    System.out.println("Please enter a valid brush size.");
                
               }
            }
            
            if (!lineToolSelected && !curvedLineSelected && selectedShape.isEmpty() && !selectionToolSelected&& !textToolSelected&& !colorGrabberSelected){
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
                    double size = Double.parseDouble(brushSize.getText());
                    curvedLineTool.drawCurve(lastX,lastY,event.getX(),event.getY(),size,colorPicker.getValue());
                    smartSave.changesMade();
                    lastX = event.getX();
                    lastY = event.getY();

                } catch (NumberFormatException e) {
                    System.out.println( "Please enter a valid brush size.");
                }
            }
            
            if (selectionToolSelected){
                double x = Math.min(selectionStartX, event.getX());
                double y = Math.min(selectionStartY, event.getY());
                
                double width = Math.abs(event.getX() - selectionStartX);
                double height = Math.abs(event.getY() - selectionStartY);
                
                selectionRectangle.setX(x);
                selectionRectangle.setY(y);
                
                selectionRectangle.setWidth(width);
                selectionRectangle.setHeight(height);
                selectionRectangle.setVisible(true);
                
            }

        });
        
        
        
        
        canvas.setOnMouseReleased(e -> {
            clearPreview();

            if (lineToolSelected) {
                try {
                    double size = Double.parseDouble(brushSize.getText());
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
            
            if(selectedShape.equals("pentagon")){
                try{
                    double size = Double.parseDouble(brushSize.getText());
                    shapeTool.drawPentagon(lastX, lastY, e.getX(), e.getY(), size, colorPicker.getValue(), fillColorPicker.getValue(), lineStyle);
                    smartSave.changesMade();
                } catch (NumberFormatException ex){
                    System.out.println("Invalid brush size.");
                }
            }
            
            if(selectedShape.equals("polygon")){
                double size = Double.parseDouble(brushSize.getText());
                shapeTool.drawPolygon(lastX,lastY,e.getX(),e.getY(),polygonSides,size,colorPicker.getValue(),fillColorPicker.getValue(),lineStyle);
                smartSave.changesMade();
            }
        
        
        if(selectedShape.equals("rightTriangle")){
                try{
                    double size = Double.parseDouble(brushSize.getText());
                    shapeTool.drawRightTriangle(lastX, lastY, e.getX(), e.getY(), size, colorPicker.getValue(), fillColorPicker.getValue(), lineStyle);
                    smartSave.changesMade();
                } catch (NumberFormatException ex){
                    System.out.println("Invalid brush size.");
                }
            }
        
        if(selectedShape.equals("isoscelesTriangle")){
                try{
                    double size = Double.parseDouble(brushSize.getText());
                    shapeTool.drawIsoscelesTriangle(lastX, lastY, e.getX(), e.getY(), size, colorPicker.getValue(), fillColorPicker.getValue(), lineStyle);
                    smartSave.changesMade();
                } catch (NumberFormatException ex){
                    System.out.println("Invalid brush size.");
                }
            }
        
        if(selectedShape.equals("star")){
                try{
                    double size = Double.parseDouble(brushSize.getText());
                    shapeTool.drawStar(lastX, lastY, e.getX(), e.getY(),starPoints, size, colorPicker.getValue(), fillColorPicker.getValue(), lineStyle);
                    smartSave.changesMade();
                } catch (NumberFormatException ex){
                    System.out.println("Invalid brush size.");
                }
            }
        
        if(selectionToolSelected){
            selectionEndX = e.getX();
            selectionEndY = e.getY();
            
            selectionTool.copy(selectionStartX,selectionStartY,selectionEndX,selectionEndY);
            selectionRectangle.setVisible(false);
            selectionToolSelected = false;
            
        }
        
        });
   }
   
   
   
   
   ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   
   
   
   
   private void saveCanvasState() {
       WritableImage image = new WritableImage((int) canvas.getWidth(),(int) canvas.getHeight());
       canvas.snapshot(null, image);
       undoStack.push(image);
       redoStack.clear();
   }
   
   private void undo(){
       if (!undoStack.empty()){
           WritableImage currentImage = new WritableImage((int) canvas.getWidth(),(int) canvas.getHeight());
           canvas.snapshot(null, currentImage);
           redoStack.push(currentImage);
           WritableImage previousImage = undoStack.pop();
           GraphicsContext gc = canvas.getGraphicsContext2D();
           
           gc.clearRect(0,0,canvas.getWidth(),(int) canvas.getHeight());
           gc.drawImage(previousImage, 0, 0);
       }
   }
   
   private void redo(){
       if (!redoStack.empty()){
           WritableImage currentImage = new WritableImage((int) canvas.getWidth(),(int) canvas.getHeight());
           canvas.snapshot(null, currentImage);
           undoStack.push(currentImage);
           WritableImage nextImage = redoStack.pop();
           GraphicsContext gc = canvas.getGraphicsContext2D();
           
           gc.clearRect(0,0,canvas.getWidth(),(int) canvas.getHeight());
           gc.drawImage(nextImage, 0, 0);
       }
   }
   
   private void clearPreview(){
       preview.clearRect(0,0, previewCanvas.getWidth(), previewCanvas.getHeight());
   }
  
   private void shareOnWeb(){
       Dialog<ButtonType> dialog = new Dialog<ButtonType>();
       dialog.setTitle("Share Images");
       dialog.setHeaderText("Select the tabs to display on the web server");
       
       VBox layout = new VBox(10);
       ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();
       
       for(int i = 0; i< tabPane.getTabs().size(); i++){
           Tab tab = tabPane.getTabs().get(i);
           
           CheckBox checkBox = new CheckBox(tab.getText());
           checkBoxes.add(checkBox);
           layout.getChildren().add(checkBox);
       }
       
       dialog.getDialogPane().setContent(layout);
       dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
       Optional<ButtonType> result = dialog.showAndWait();
       
       if (!result.isPresent() || result.get() != ButtonType.OK){
           return;
       }
       
       ArrayList<byte[]> selectedImages = new ArrayList<byte[]>();
       
       for (int i = 0; i < checkBoxes.size(); i++) {
           if (checkBoxes.get(i).isSelected()){
               Tab tab = tabPane.getTabs().get(i);
               Canvas selectedCanvas = (Canvas) tab.getUserData();
               
               try{
                   WritableImage image = selectedCanvas.snapshot(null, null);
                   ByteArrayOutputStream output = new ByteArrayOutputStream();
                   
                   ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", output);
                   selectedImages.add(output.toByteArray());
                   
               } catch (Exception e){
                   System.out.println("Failed to capture image: " + e);
               }
           }
       }
       
       if (selectedImages.isEmpty()){
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Web Server");
           alert.setHeaderText("No Images Selected");
           alert.setContentText("Please select at least one image tab.");
           alert.showAndWait();
           return;
                   
       }
       try{
           webServer.start(selectedImages);
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Web Server Started");
           alert.setHeaderText("Images are ready!");
           alert.setContentText("Open http://localhost:8080 in your browser.");
           alert.showAndWait();
       } catch (Exception e){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Web Server Error");
           alert.setHeaderText("Could not start the server");
           alert.setContentText(e.getMessage());
           alert.showAndWait();
       }
   }
   
   
   /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   
   
   
   public Canvas getCanvas(){
       return canvas;
   }
   
   public MenuBar getMenuBar(){
       return menuBar;
   }
   
   public BorderPane getPane(){
       return pane;
   }
    
   
   
   ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   
   
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

       
       Image snapshot = canvasPane.snapshot(null,null);
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
           String fileName = file.getName().toLowerCase();
           String format = "png";
           
           if (fileName.endsWith(".jpg")){
               format = "jpg";
           } else if (fileName.endsWith(".bmp")){
               format = "bmp";
           }
           
           if (!format.equals("png")){
               Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
               warning.setTitle("Image Format Warning");
               warning.setHeaderText("Possible Image Data Loss");
               warning.setContentText("Saving in a different image format may cause " + "some image features or data to be lost.\n\n" + "JPG may reduce image quality and does not " + "support transparency.\n\n" + "Do you wish to continue?");
               Optional<ButtonType> result = warning.showAndWait();
               if (!result.isPresent() || result.get() != ButtonType.OK){
                   return;
               }
           }
           try{
               Image snapshot = canvasPane.snapshot(null,null);
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

  
    public void autoSave(){
       try{
           boolean wasVisible = selectionRectangle.isVisible();
           selectionRectangle.setVisible(false);
           WritableImage image = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
           canvasPane.snapshot(null, image);
           selectionRectangle.setVisible(wasVisible);
           File file = new File("autosave.png");
           ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
           System.out.println("AutoSaved.");
           System.out.println("Saved to " + file.getAbsolutePath());
       } catch (Exception e){
           System.out.println("AutoSave failed.");
       }
    }
   
    public void autoSaveTimer(int seconds){
       int minutes = seconds/60;
       int sec = seconds % 60;
       String time = String.format("%02d:%02d", minutes, sec);
       autoSaveLabel.setText(time);
    }

}    


