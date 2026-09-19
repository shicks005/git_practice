/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paintapp;

/**
 *
 * @author shhic
 */
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;

public class SmartSave {
    
    private boolean unsavedChanges = false;
    private Paint paint;
    
    public SmartSave(Paint paint){
        this.paint = paint;
    }
    
    public void changesMade(){
        unsavedChanges = true;
    }
    
    public void changesSaved(){
        unsavedChanges = false;
    }
    
    public boolean confirmClose(Window window){
        if (!unsavedChanges){
            return true;
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Unsaved Changes");
        alert.setHeaderText("You have unsaved changes.");
        alert.setContentText("Would you like to save before closing?");
        
        ButtonType saveButton = new ButtonType("Save");
        ButtonType dontSaveButton = new ButtonType("Don't Save");
        ButtonType cancelButton = new ButtonType("Cancel");
        
        alert.getButtonTypes().setAll(saveButton, dontSaveButton, cancelButton);
        
        var result = alert.showAndWait();
        if (result.isPresent()){
            if (result.get() == saveButton){
                paint.onSave();
                return true;
            }
            if (result.get() == dontSaveButton){
                return true;
            }
            if (result.get() == cancelButton){
                paint.onSave();
                return false;
            }
        }
        return false;
    }
}
