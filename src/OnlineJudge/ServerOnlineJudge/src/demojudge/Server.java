/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demojudge;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import viewer.ServerViewer;

/**
 *
 * @author HOANG
 */
public class Server {
    public static final int port = 1107;
    public static final int NUM_OF_THREAD = 4;
    public static boolean stop = false;
    public static Thread threadMain;
    
    public static ServerSocket server ;
    public static void runServer(){
        try {
            server = new ServerSocket(port);
            ServerViewer.instance.Log("Server started: " + server);
            ServerViewer.instance.Log("Waiting for a client ...");
            ExecutorService executor = Executors.newFixedThreadPool(NUM_OF_THREAD);
            while (true){
                Socket client = server.accept();
                ServerViewer.instance.Log("Server accept "+client);
                WorkerThread handler = new WorkerThread(client);
                executor.execute(handler);
            }
        } catch (Exception ex) {
            ServerViewer.instance.Log(ex.toString());
        }
    }
    public static void stopServer(){
        try {
            threadMain.stop();
            ServerViewer.instance.Log("Server closed!");
            if (server!=null) server.close();
        } catch (IOException ex) {
            ServerViewer.instance.Log(ex.toString());
        }
    }
    
    public static void startServer(){
        threadMain = new Thread(new Runnable() {
            @Override
            public void run() {
                Server.runServer();
            }
        });
        threadMain.start();
    }
}
