/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import model.MyPacket;
/**
 *
 * @author HOANG
 */
public class Client {
    private Socket client;
    private String SERVER_IP;
    private int SERVER_PORT;
    private DataInputStream is;
    private ObjectOutputStream os;
    public Client(String SERVER_IP , int SERVER_PORT) throws IOException{
        this.SERVER_IP = SERVER_IP;
        this.SERVER_PORT = SERVER_PORT;
        client = new Socket(SERVER_IP, SERVER_PORT); // Connect to server
        System.out.println("client create");
        is = new DataInputStream(client.getInputStream());
        os = new ObjectOutputStream(client.getOutputStream());
        MyPacket ps = new MyPacket("first_connect", null, null, null);
        os.writeObject(ps);
        String response = is.readUTF();
        System.out.println(""+response);
        controler.Controler.instance.setCBP(response);
        System.out.println("OKOK");
        System.out.println("Connected: " + client);
    }
    public void disconnect() throws IOException{
        os.close();
        is.close();
        client.close();
    }
    public String sendFile(File file, String nameProb, String nameFile){
        try {
            //System.out.println("Connected: " + client);
            MyPacket ps = new MyPacket("judge", nameProb, nameFile, file);
            os.writeObject(ps);
            String response = is.readUTF();
            return response;
        } catch (Exception ex) {
            System.out.println(""+ex.toString());
            return "Error";
        }
    }
}
