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
public class ShapeTool {

    private Canvas canvas;

    public ShapeTool(Canvas canvas) {
        this.canvas = canvas;
    }

    public void drawRectangle(double x1, double y1, double x2, double y2,double size, Color borderColor, Color fillColor, String lineStyle) {

        GraphicsContext gc = canvas.getGraphicsContext2D();

        double width = Math.abs(x2 - x1);
        double height = Math.abs(y2 - y1);

        double x = Math.min(x1, x2);
        double y = Math.min(y1, y2);

        gc.setFill(fillColor);
        gc.fillRect(x, y, width, height);

        gc.setLineWidth(size);
        gc.setStroke(borderColor);
        if (lineStyle.equals("dashed")) {
            gc.setLineDashes(10, 10);
        } else {
            gc.setLineDashes(null);
        }
        gc.strokeRect(x, y, width, height);
    }

    public void drawSquare(double x1, double y1, double x2, double y2, double size, Color borderColor, Color fillColor, String lineStyle) {

       GraphicsContext gc = canvas.getGraphicsContext2D();

        double side = Math.min(Math.abs(x2 - x1), Math.abs(y2 - y1));

        double x = Math.min(x1, x2);
        double y = Math.min(y1, y2);

        gc.setFill(fillColor);
        gc.fillRect(x, y, side, side);

        gc.setLineWidth(size);
        gc.setStroke(borderColor);
        if (lineStyle.equals("dashed")) {
            gc.setLineDashes(10, 10);
        } else {
            gc.setLineDashes(null);
        }
        
        gc.strokeRect(x, y, side, side);
    }

    public void drawEllipse(double x1, double y1, double x2, double y2,double size, Color borderColor, Color fillColor, String lineStyle) {

        GraphicsContext gc = canvas.getGraphicsContext2D();

        double width = Math.abs(x2 - x1);
        double height = Math.abs(y2 - y1);

        double x = Math.min(x1, x2);
        double y = Math.min(y1, y2);

        gc.setFill(fillColor);
        gc.fillOval(x, y, width, height);

        gc.setLineWidth(size);
        gc.setStroke(borderColor);
        if (lineStyle.equals("dashed")) {
            gc.setLineDashes(10, 10);
        } else {
            gc.setLineDashes(null);
        }
        
        gc.strokeOval(x, y, width, height);
    }

    public void drawCircle(double x1, double y1, double x2, double y2, double size, Color borderColor, Color fillColor, String lineStyle) {

       GraphicsContext gc = canvas.getGraphicsContext2D();

        double side = Math.min(Math.abs(x2 - x1),Math.abs(y2 - y1));

        double x = Math.min(x1, x2);
        double y = Math.min(y1, y2);

        gc.setFill(fillColor);
        gc.fillOval(x, y, side, side);

        gc.setLineWidth(size);
        gc.setStroke(borderColor);
        if (lineStyle.equals("dashed")) {
            gc.setLineDashes(10, 10);
        } else {
            gc.setLineDashes(null);
        }
        gc.strokeOval(x, y, side, side);
    }

    public void drawTriangle(double x1, double y1, double x2, double y2,double size, Color borderColor, Color fillColor, String lineStyle) {

        GraphicsContext gc = canvas.getGraphicsContext2D();

        double width = Math.abs(x2 - x1);
        double height = Math.abs(y2 - y1);

        double x = Math.min(x1, x2);
        double y = Math.min(y1, y2);

        double[] xPoints = {
            x + width / 2,
            x,
            x + width
        };

        double[] yPoints = {
            y,
            y + height,
            y + height
        };

        gc.setFill(fillColor);
        gc.fillPolygon(xPoints, yPoints, 3);
    
        gc.setLineWidth(size);
        gc.setStroke(borderColor);
        if (lineStyle.equals("dashed")) {
            gc.setLineDashes(10, 10);
        } else {
            gc.setLineDashes(null);
        }
        
        gc.strokePolygon(xPoints, yPoints, 3);
    }
}