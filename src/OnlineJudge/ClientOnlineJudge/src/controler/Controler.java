/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;
import viewer.ClientViewer;
import static zip.ZipDirectory.zipDirectory;

/**
 *
 * @author duytr
 */
public class Controler {
    public static Controler instance = new Controler();
    public static File runFile;
    public static File zipFile;
    private Client client = null;
    public Controler(){
        instance = this;
    }
    public String Submit(String nameProb) throws IOException{
        String ans = "Error";
        String nameFile = getNameRunFile();
        zipFile(runFile);
        ans = client.sendFile(zipFile, nameProb, nameFile);
        deleteZip(zipFile.getAbsolutePath());
        return ans ;
    }
    
    public void zipFile(File file){
        String zipPath = "clientsource\\"+file.getName()+".zip";
        System.out.println(zipPath);
        zipDirectory(file,zipPath);
        this.zipFile = new File(zipPath);
    }
    
    public void deleteZip(String zipPath) throws IOException{
        Files.delete(Paths.get(zipPath));
    }
    
    public void SelectFile(File file){
        this.runFile = file;
    }
    
    public String getNameRunFile(){
        return runFile.getName();
    }
    public String getNameZipFile(){
        return zipFile.getName();
    }
    public boolean notConnected() {
        return client==null;
    }
    
    public void connect(String ipa, int port) throws IOException{
        if (client!= null) return;
        client = new Client(ipa, port);
    }
    
    public void disconnect() throws IOException{
        client.disconnect();
        client = null;
        ClientViewer.instance.removeProb();
    }


    public void setCBP(String response) {
        StringTokenizer stk = new StringTokenizer(response,"|");
        ClientViewer.instance.removeProb();
        ClientViewer.instance.addProb("Select Problem");
        while(stk.hasMoreElements()){
            String tmp = stk.nextToken();
            ClientViewer.instance.addProb(tmp);
            System.out.println(""+tmp);
        }
    }
}
