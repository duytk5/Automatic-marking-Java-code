/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solve;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author duytr
 */
public class CompareAnswer {
    
    public static float calcCompareJson(Object json1 , Object json2){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        System.out.println("" + json1.getClass());
//        System.out.println("" + json2.getClass());
//        System.out.println("" + json1.toString());
//        System.out.println("" + json2.toString());
        if ((json1 instanceof JsonArray && json2 instanceof JsonArray) || (json1 instanceof ArrayList && json2 instanceof ArrayList)){
            JsonArray js1 = (JsonArray)gson.toJsonTree(json1);
            JsonArray js2 = (JsonArray)gson.toJsonTree(json2);
            float sum = 0 ;
            float ans = 0 ;
            for (JsonElement je1 : js1){
                sum += 1;
                float ma = 0;
                for (JsonElement je2 : js2){
                    float x = calcCompareJson(je1, je2);
                    ma = Math.max (ma , x);
                }
                ans += ma;
            }
            if (sum == 0) return 1;
            System.out.println(ans + " " + sum);
            System.out.println("" + js1);
            System.out.println("" + js2);
            return ans/sum;
        }
        else
        if (json1 instanceof JsonObject && json2 instanceof JsonObject){
            JsonObject js1 = (JsonObject)gson.toJsonTree(json1);  
            JsonObject js2 = (JsonObject)gson.toJsonTree(json2);
            Set<String> sk = js1.keySet();
            float sum = 0 ;
            float ans = 0;
            for (String key : sk){
                sum+=1;
                if (js2.has(key)){
                    float tmp = calcCompareJson(js1.get(key), js2.get(key));
                    if (key.equals("name") && tmp != 1){
                        return 0;
                    }
                    if (key.equals("type") && tmp != 1){
                        return 0;
                    }
                    ans += tmp;
                }
            }
            
            return ans/sum;
        }
        else{
            if (json1.toString().equals(json2.toString())) return 1;
            else return 0;
        }
    }
    
    public static String compareFileJson(File f1, File f2) throws FileNotFoundException{
        String ans = "";
        BufferedReader br1 = new BufferedReader(new FileReader(f1));
        BufferedReader br2 = new BufferedReader(new FileReader(f2));

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Object json1 = gson.fromJson(br1, Object.class);
        Object json2 = gson.fromJson(br2, Object.class);
        
        
        float res = calcCompareJson(json1, json2);
        
        
        ans = "" + res*100;
        //System.out.println("" + ans);
        return ans;
    }
    
    public static String compareFileTxT(File f1, File f2) throws FileNotFoundException, IOException{
        boolean check = true;
        FileReader fR1 = new FileReader(f1);
        FileReader fR2 = new FileReader(f2);

        BufferedReader reader1 = new BufferedReader(fR1);
        BufferedReader reader2 = new BufferedReader(fR2);

        String line1 = reader1.readLine();
        String line2 = reader2.readLine();
        if (line1==null || line2 == null || !line1.equalsIgnoreCase(line2)){
            check = false;
        }
        while ((check) && ((line1 = reader1.readLine()) != null)
                && ((line2 = reader2.readLine()) != null)) {
            if (!line1.equalsIgnoreCase(line2)) {
                check = false;
            }
        }
        reader1.close();
        reader2.close();
        if (check) return "Accepted";
        else return "Wrong Answer";
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        File f1 = new File("C:\\Users\\duytr\\OneDrive\\Documents\\NetBeansProjects\\ltm\\Desktop\\ServerOnlineJudge\\problems\\A\\output.json");
        File f2 = new File("C:\\Users\\duytr\\OneDrive\\Documents\\NetBeansProjects\\ltm\\Desktop\\ServerOnlineJudge\\contestants\\127.0.0.152333.A\\output.json");
        System.out.println("" + compareFileJson(f1,f2));
    }
}
