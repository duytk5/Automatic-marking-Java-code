/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reflection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import reflection.entity.Class;
import reflection.entity.Field;
import reflection.entity.Method;
import reflection.entity.Parameter;
public class SourceParser {
    protected int name1;//4
    public float name2;//1
    double name3;//0
    private String path;//2
    
    ArrayList<java.lang.Class> classesTmp ;
    
    private static java.lang.Class getClassFromFile(File file, String name) throws Exception {
        StringBuilder path = new StringBuilder(file.getParentFile().getAbsolutePath());
        StringBuilder pathClasses = new StringBuilder("");
        int vt = 0;
        path.append("\\");
        for (int i = 0 ; i<path.length(); i++){
            pathClasses.append(path.charAt(i));
            if (pathClasses.toString().endsWith("classes")){
                vt = i+1;
                break;
            }
        }
        pathClasses.append("\\");
        for (int i = 0 ; i<pathClasses.length(); i++){
            if (pathClasses.charAt(i) == '\\'){
                pathClasses.setCharAt(i, '/');
            }
        }
        
        StringBuilder preName = new StringBuilder();
        for (int i = vt ; i<path.length(); i++){
            preName.append(path.charAt(i));
        }
        for (int i = 0 ; i<preName.length(); i++){
            if (preName.charAt(i) == '\\'){
                preName.setCharAt(i, '/');
            }
            if (preName.charAt(i) == '/'){
                preName.setCharAt(i, '.');
            }
        }
        while (preName.charAt(0)=='.') preName.deleteCharAt(0);
        StringBuilder sTmp = new StringBuilder(name);
        sTmp.delete(name.length()-6, name.length());
        name = preName.toString() + sTmp.toString();
        System.out.println("" + name);
        
        System.out.println("PATHHHHHH"+pathClasses);
        //pathClasses = new StringBuilder("C:/Users/duytr/OneDrive/Documents/NetBeansProjects/ltm/Desktop/ServerOnlineJudge/contestants/127.0.0.154442/DemoA/build/classes/");
        URLClassLoader loader = new URLClassLoader(new URL[] {
                new URL("file:///" + pathClasses.toString())
        });
        return loader.loadClass(name);
    }

    public void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                System.out.println(fileEntry.getName());
                if (fileEntry.getName().endsWith(".class")){
                    try {
                        classesTmp.add(getClassFromFile(fileEntry , fileEntry.getName()));
                    } catch (Exception ex) {
                        Logger.getLogger(SourceParser.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            }
        }
    }

    
    public SourceParser(String path) {
        this.path = path;
        File folder= new File(path);
        classesTmp = new ArrayList<>();
        listFilesForFolder(folder);
    }

    public ArrayList<Class> getAllClass(){
        ArrayList<Class> classes = new ArrayList<>();
        System.out.println("" + classesTmp.size());
        //Set<java.lang.Class<? extends Object>> classesTmp = reflections.getSubTypesOf(Object.class);
        for(java.lang.Class t: classesTmp){
            Class classN = new Class();
            //package
            classN.setPackage(t.getPackage().getName());
            //name
            classN.setName(t.getName());
            //fields
            ArrayList<Field> fields = new ArrayList<>();
            for(java.lang.reflect.Field field: t.getDeclaredFields()){
                Field f = new Field(field.getName(), field.getType().getName(), 
                        convertModifier(field.getModifiers()));
                fields.add(f);
            }
            classN.setFields(fields);
            //methods
            ArrayList<Method> methods = new ArrayList<>();
            for(java.lang.reflect.Method method: t.getDeclaredMethods()){
                ArrayList<Parameter> parameters = new ArrayList<>();
                for(java.lang.reflect.Parameter p : method.getParameters()){
                    Parameter ptmp = new Parameter(p.getName(), p.getType().getName());
                    parameters.add(ptmp);
                }
                Method m = new Method(convertModifier(method.getModifiers()), method.getName(), 
                        method.getReturnType().getName(), parameters);
                methods.add(m);
            }
            classN.setMethods(methods);
            
           //
           classes.add(classN);
        }
        return classes;
    }
    
    public String parserAllClassToJson(){
        ArrayList<Class> classes = getAllClass();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(classes);
    }
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    public String convertModifier(int modifier){
        if(modifier == 0){
            return "";
        }
        if(modifier == 1){
            return "public";
        }
        if(modifier == 2){
            return "private";
        }
        if(modifier == 4){
            return "protected";
        }
        return "";
    }
    
    public void writeToFileJson(File filejson) throws FileNotFoundException{
        String jsonAns = this.parserAllClassToJson();
        PrintWriter pwj = new PrintWriter(filejson);
        System.out.println(jsonAns);
        pwj.println(jsonAns);
        pwj.close();
    }
}
