/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demojudge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;
import reflection.SourceParser;
import solve.CompareAnswer;
import viewer.ServerViewer;

/**
 *
 * @author HOANG
 */
public class DemoJudge {

    /**
     * @param args the command line arguments
     */
    private static final String PATH_PROBLEMS = "problems/";
    public static String Judge(String pathClient, String nameProb, File fileRun) {
        try {
            System.out.println(""+nameProb);
            String path = fileRun.getAbsolutePath();
            System.out.println(path);
            String mainClassPath = new String(new Scanner(new File(PATH_PROBLEMS+nameProb+"/mainclasspath.txt")).nextLine());
            System.out.println(mainClassPath);
            final String[] cmd1 = {
                "CMD", "/C",
                "cd " + path
                + "&& ant compile jar"
            };
            
            ProcessBuilder buider1 = new ProcessBuilder(cmd1);
            Process proc1 = buider1.start();
            BufferedReader stdInput1 = new BufferedReader(new InputStreamReader(proc1.getInputStream()));
            BufferedReader stdError1 = new BufferedReader(new InputStreamReader(proc1.getErrorStream()));
            System.out.println("Here is the standard output of the command:\n");

            String s1 = null;
            while ((s1 = stdInput1.readLine()) != null) {
                System.out.println(s1);
            }
            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s1 = stdError1.readLine()) != null) {
                System.out.println(s1);
                return "Compile Error";
            }
            while (proc1.isAlive()){
            }
            System.out.println("done1");
            final String[] cmd = {
                "CMD", "/C",
                "cd " + path + "\\dist"
                + "&&java -cp "+fileRun.getName()+".jar "+mainClassPath
            };
            ProcessBuilder buider = new ProcessBuilder(cmd);
            buider.redirectInput(new File(PATH_PROBLEMS + nameProb +"/"+"input.txt"));
            Process proc = buider.start();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            ServerViewer.instance.Log("Run CMD done!");
            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");

            File file = new File(pathClient+"output.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter pw = new PrintWriter(file);
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
                pw.println(s);
            }
            pw.close();
            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
                return "Compile Error";
            }
            System.out.println("done2");
            
            String path2 = pathClient + fileRun.getName() + "/build/classes";
            System.out.println(""+path2);
            SourceParser sp= new SourceParser(path2);
            File f1 = new File(pathClient +"/" + "output.json");
            sp.writeToFileJson(f1);
            
            File f2= new File(PATH_PROBLEMS + nameProb +"/"+"output.json");
            System.out.println("" + f2.getAbsolutePath());
            String ans2 = CompareAnswer.compareFileJson(f1, f2);
            System.out.println("" + ans2);
            return checkOutPut(pathClient,nameProb) +"|"+ ans2 ;
        } catch (Exception ex) {
            ServerViewer.instance.Log(ex.toString());
            return "Run Error";
        }
    }

    public static String checkOutPut(String pathClient,String nameProb) {
        try {
            File f1 = new File(pathClient+"output.txt");// client answer
            File f2 = new File(PATH_PROBLEMS + nameProb +"/"+"output.txt");// correct answer

            return CompareAnswer.compareFileTxT(f1, f2);
        } catch (Exception ex) {
            ServerViewer.instance.Log(ex.toString());
            return "Error";
        }
    }
}
