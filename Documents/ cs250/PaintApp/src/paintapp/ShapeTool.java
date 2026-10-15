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
    
    public void drawPentagon(double x1, double y1, double x2, double y2,double size, Color borderColor, Color fillColor, String lineStyle){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        double centerX = (x1 + x2) / 2;
        double centerY = (y1 + y2) / 2;
        
        double radiusX = Math.abs(x2 - x1) / 2;
        double radiusY = Math.abs(y2 - y1) / 2;

        double[] xPoints = new double[5];
        double[] yPoints = new double[5];

        for (int i = 0; i < 5; i++) {
            double angle = Math.toRadians(-90 + (i * 72));

            xPoints[i] = centerX + radiusX * Math.cos(angle);
            yPoints[i] = centerY + radiusY * Math.sin(angle);
        }

        gc.setLineWidth(size);
        gc.setStroke(borderColor);
        gc.setFill(fillColor);

        if (lineStyle.equals("dashed")) {
            gc.setLineDashes(10, 10);
        } else {
            gc.setLineDashes(null);
        }

        gc.fillPolygon(xPoints, yPoints, 5);
        gc.strokePolygon(xPoints, yPoints, 5);

        
    }
    
    public void drawPolygon(double x1, double y1, double x2, double y2,int sides, double size, Color borderColor, Color fillColor,String lineStyle){
        GraphicsContext gc = canvas.getGraphicsContext2D();

        double centerX = (x1 + x2) / 2;
        double centerY = (y1 + y2) / 2;

        double radiusX = Math.abs(x2 - x1) / 2;
        double radiusY = Math.abs(y2 - y1) / 2;

        double[] xPoints = new double[sides];
        double[] yPoints = new double[sides];

        for (int i = 0; i < sides; i++) {
            double angle = Math.toRadians(-90 + (i * 360.0 / sides));
            xPoints[i] = centerX + radiusX * Math.cos(angle);
            yPoints[i] = centerY + radiusY * Math.sin(angle);
        }

        gc.setLineWidth(size);
        gc.setStroke(borderColor);
        gc.setFill(fillColor);

        if (lineStyle.equals("dashed")) {
            gc.setLineDashes(10, 10);
        } else {
            gc.setLineDashes(null);
        }

        gc.fillPolygon(xPoints, yPoints, sides);
        gc.strokePolygon(xPoints, yPoints, sides);
    }
    
    public void drawRightTriangle(double x1, double y1, double x2, double y2,double size, Color borderColor, Color fillColor, String lineStyle){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double[] xPoints = {x1, x1, x2};
        double[] yPoints = {y1, y2, y2};
        
        gc.setLineWidth(size);
        gc.setStroke(borderColor);
        gc.setFill(fillColor);
        
        if (lineStyle.equals("dashed")) {
            gc.setLineDashes(10, 10);
        } else {
            gc.setLineDashes(null);
        }
        gc.fillPolygon(xPoints, yPoints, 3);
        gc.strokePolygon(xPoints, yPoints, 3);
    }
    
    public void drawIsoscelesTriangle(double x1, double y1, double x2, double y2,double size, Color borderColor, Color fillColor, String lineStyle){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        double middleX = (x1 + x2) / 2;
        
        double[] xPoints = {middleX, x1, x2};
        double[] yPoints = {y1, y2, y2};
        
        gc.setLineWidth(size);
        gc.setStroke(borderColor);
        gc.setFill(fillColor);
        
        if (lineStyle.equals("dashed")) {
            gc.setLineDashes(10, 10);
        } else {
            gc.setLineDashes(null);
        }   
        gc.fillPolygon(xPoints, yPoints, 3);
        gc.strokePolygon(xPoints, yPoints, 3);
    }
    
    public void drawStar(double x1, double y1, double x2, double y2,int points, double size, Color borderColor, Color fillColor, String lineStyle){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        gc.save();
        
        double centerX = x1;
        double centerY = y1;
        
        double radiusX = Math.abs(x2 - x1) / 2;
        double radiusY = Math.abs(y2 - y1) / 2;
        
        double[] xPoints = new double[points * 2];
        double[] yPoints = new double[points * 2];
        
        double radiusIn = 0.45;
        
        if(points == 4){
            radiusIn = 0.7;
        }else if (points == 6){
            radiusIn = 0.35;
        }
        
        for (int i = 0; i< points * 2; i++){
            
            double angle = Math.toRadians(-90 + i * 180/ points);
            double radius;
            if (i % 2 == 0){
                radius = 1.0;
            }else{
                radius = radiusIn;
            }
            xPoints[i] = centerX + radiusX * radius * Math.cos(angle);
            yPoints[i] = centerY + radiusY * radius * Math.sin(angle);
        }
        
        gc.setLineWidth(size);
        gc.setStroke(borderColor);
        gc.setFill(fillColor);
        
        if (lineStyle.equals("dashed")) {
            gc.setLineDashes(10, 10);
        } else {
            gc.setLineDashes(null);
        }
        
        
        gc.fillPolygon(xPoints, yPoints,points * 2);
        gc.strokePolygon(xPoints, yPoints, points * 2);
        
        gc.restore();
    }
 
}