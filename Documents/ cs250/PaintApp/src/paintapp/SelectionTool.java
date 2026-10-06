/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paintapp;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;

/**
 *
 * @author shhic
 */
public class SelectionTool {
    private Canvas canvas;
    private WritableImage copiedImage;
    
    public SelectionTool(Canvas canvas){
        this.canvas = canvas;
    }
    
    public void copy(double x1, double y1, double x2, double y2){
        double startX = Math.min(x1, x2);
        double startY = Math.min(y1, y2);
        
        double width = Math.abs(x2 - x1);
        double height = Math.abs(y2 - y1);
        
        WritableImage canvasImage = new WritableImage((int) canvas.getWidth(),(int) canvas.getHeight());
        canvas.snapshot(null, canvasImage);
        copiedImage = new WritableImage(canvasImage.getPixelReader(),(int) startX,(int) startY,(int) width,(int) height);
    }
    
    public void paste(double x, double y){
        if (copiedImage != null){
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.drawImage(copiedImage, x, y);
        }
    }
    
    public WritableImage getCopiedImage() {
        return copiedImage;
    }
 
}
