/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demojudge;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.MyPacket;
import viewer.ServerViewer;

/**
 *
 * @author HOANG
 */
public class WorkerThread extends Thread {

    private Socket socket;
    public WorkerThread(Socket socket) {
        this.socket = socket;
    }
    
    private String getListProb(){
        StringBuilder ans = new StringBuilder("");
        File file = new File("./problems");
        String[] directories = file.list(new FilenameFilter() {
          @Override
          public boolean accept(File current, String name) {
            return new File(current, name).isDirectory();
          }
        });
        for (String s: directories){
            ans.append(s+"|");
        }
        ans.deleteCharAt(ans.length()-1);
        return ans.toString();
    }
    
    public void run() {
        DataOutputStream os = null;
        ObjectInputStream is = null;
        try {
            is = new ObjectInputStream(socket.getInputStream());
            os = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(WorkerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while (true){
            try {
                String response ="";
                
                ServerViewer.instance.Log("Processing: " + socket);
                MyPacket ps = (MyPacket) is.readObject();
                if (ps.getMessage().equals("first_connect")){
                    response = getListProb();
                    ServerViewer.instance.Log("First connect successfull!");
                }
                else{
                    String nameFile = ps.getNameFile();
                    String nameProb = ps.getNameProb();
                    String path = "contestants/" +socket.getInetAddress()+socket.getPort()+"."+nameProb;
                    new File(path).mkdirs();
                    File zipFile = new File(path+"/"+nameFile+".zip");
                    ps.copyToFile(zipFile); 
                    zip.UnZipDirectory.unzip(zipFile.getAbsolutePath(), path+"/"+nameFile);
                    //Files.delete(Paths.get(zipFile.getAbsolutePath()));
                    File file = new File(path+"/"+nameFile);
                    response = DemoJudge.Judge(path+"/",nameProb , file);
                }
                os.writeUTF(response);
                os.flush();
                ServerViewer.instance.Log("Complete processing: " + socket);
                //is.close();
                //os.close();
                //socket.close();
            } catch (Exception ex) {
                ServerViewer.instance.Log(ex.toString());
                return;
            }
        }

    }
}
