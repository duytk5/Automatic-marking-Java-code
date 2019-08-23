/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 *
 * @author duytr
 */
public class MyPacket implements Serializable {

    private static final int idSerializable = 123;
    private String message = "";
    private String nameFile = "";
    private String nameProb = "";
    private byte[] fileSend;

    public MyPacket(String message, String nameProb, String nameFile, File file) {
        this.message = message;
        this.nameProb = nameProb;
        this.nameFile = nameFile;
        try {
            this.fileSend = Files.readAllBytes(file.toPath());
        } catch (Exception ex) {
        }
    }

    public void copyToFile(File file) throws FileNotFoundException, IOException {
        FileOutputStream os = new FileOutputStream(file);
        os.write(fileSend);
    }

    public static int getIdSerializable() {
        return idSerializable;
    }

    public String getMessage() {
        return message;
    }

    public String getNameProb() {
        return nameProb;
    }

    public String getNameFile() {
        return nameFile;
    }
}
