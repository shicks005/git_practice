/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paintapp;

import javafx.application.Platform;
/**
 *
 * @author shhic
 */
public class AutoSave {
    
    
    public AutoSave(Paint paint){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run(){
                boolean running = true;
                while(running){
                    try{
                        for (int seconds = 10; seconds >=1; seconds--){
                            int timeLeft = seconds;
                            Platform.runLater(new Runnable(){
                                @Override
                                public void run(){
                                    paint.autoSaveTimer(timeLeft);
                                }
                            });
                            Thread.sleep(1000);
                            
                        }
                        
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run(){
                                paint.autoSave();
                                paint.autoSaveTimer(10);
                            }
                        });
                    }catch (InterruptedException e){
                        running = false;
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
    
   
}
