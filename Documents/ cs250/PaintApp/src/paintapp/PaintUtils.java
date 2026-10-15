/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paintapp;

/**
 *
 * @author shhic
 */
public class PaintUtils {
    
    public static boolean validBrushSize(double size){
        return size > 0;
    }
    
    public static boolean validCanvasSize(double width, double height){
        return width > 0 && height > 0;
    }
    
    public static boolean validPolygonSides(int sides){
        return sides >= 3;
    }
}
