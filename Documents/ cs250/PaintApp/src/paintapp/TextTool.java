/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paintapp;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
/**
 *
 * @author shhic
 */
public class TextTool {
    private Canvas canvas;
    
    public TextTool(Canvas canvas){
        this.canvas = canvas;
    }
    
    
    public void drawText(String text, double x, double y, Color color){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(color);
        gc.fillText(text, x, y);
    }
}
