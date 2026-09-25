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
public class CurvedLineTool {
    
    private Canvas canvas;
    
    public CurvedLineTool(Canvas canvas){
        this.canvas = canvas;
    }
    
    public void drawCurve(double x1, double y1, double x2, double y2, double size, Color color){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        gc.setLineWidth(size);
        gc.setStroke(color);
        
         double controlX = (x1 + x2) / 2;
        double controlY = y1 - 50;

        gc.beginPath();
        gc.moveTo(x1, y1);
        gc.quadraticCurveTo(controlX, controlY, x2, y2);
        gc.stroke();
    }
    
}

