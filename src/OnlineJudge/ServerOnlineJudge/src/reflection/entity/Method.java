/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reflection.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Method implements Serializable{
    private String modifier;
    private String name;
    private String returnType;
    private ArrayList<Parameter> parameters;

    public Method() {
    }

    public Method(String modifier, String name, String returnType, ArrayList<Parameter> parameters) {
        this.modifier = modifier;
        this.name = name;
        this.returnType = returnType;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public ArrayList<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<Parameter> parameters) {
        this.parameters = parameters;
    }
}
