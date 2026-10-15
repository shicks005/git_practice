/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paintapp;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;



/**
 *
 * @author shhic
 */
public class PaintWebServer {
    
    private HttpServer server;
    
    public void start(ArrayList<byte[]> images) throws IOException{
        stop();
        
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 8080), 0 );
        String html = "<html><body><h1>My Paint Images</h1>";
        
        for (int i =0; i < images.size(); i++){
            byte[] image = images.get(i);
            String encoded = Base64.getEncoder().encodeToString(image);
            html += "<img src='data:image/png;base64," + encoded + "' style='max-width:90%;'><hr>";
        }
        
        if(images.isEmpty()){
            html += "<p>No images selected.</p>";
        }
        html += "</body></html>";
        byte[] page = html.getBytes("UTF-8");
        
        server.createContext("/", new HttpHandler(){
           @Override
           public void handle(HttpExchange exchange) throws IOException{
               exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
               exchange.sendResponseHeaders(200, page.length);
               OutputStream output = exchange.getResponseBody();
               output.write(page);
               output.close();
           }
        });
        server.start();
    }
        
        public void stop(){
            if (server != null){
                server.stop(0);
                server = null;
            }
        }
    
}
