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
public class LineTool {
    private Canvas canvas;
    
    public LineTool(Canvas canvas){
        this.canvas = canvas;
    }
    
    public void drawLine(double x1, double y1, double x2, double y2, double size, Color color, String lineStyle){
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setLineWidth(size);
        gc.setStroke(color);

        if (lineStyle.equals("dashed")) {
            gc.setLineDashes(10, 10);
        } else {
            gc.setLineDashes(null);
        }

        gc.strokeLine(x1, y1, x2, y2);
    }
    
}
