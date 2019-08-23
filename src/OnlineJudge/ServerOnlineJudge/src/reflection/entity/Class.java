/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reflection.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Class implements Serializable{
    private String name;
    private String _package;
    private ArrayList<Field> fields;
    private ArrayList<Method> methods;
    
    public Class(){
        
    }

    public Class(String name, String _package, ArrayList<Field> fields, ArrayList<Method> methods) {
        this.name = name;
        this._package = _package;
        this.fields = fields;
        this.methods = methods;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackage() {
        return _package;
    }

    public void setPackage(String _package) {
        this._package = _package;
    }

    public ArrayList<Field> getFields() {
        return fields;
    }

    public void setFields(ArrayList<Field> fields) {
        this.fields = fields;
    }

    public ArrayList<Method> getMethods() {
        return methods;
    }

    public void setMethods(ArrayList<Method> methods) {
        this.methods = methods;
    }
    
}
