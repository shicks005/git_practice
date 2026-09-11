/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javafx.embed.swing.SwingFXUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ColorPicker;
import javax.imageio.ImageIO;
import java.io.File;

/**
 *
 * @author shhic
 */
public class Paint {
    
    @FXML
    private Canvas canvas;
    
    @FXML
    private ColorPicker colorPicker;
    
    @FXML
    private TextField brushSize;
    
    @FXML
    private CheckBox eraser;
    
   public void onsave(){
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
