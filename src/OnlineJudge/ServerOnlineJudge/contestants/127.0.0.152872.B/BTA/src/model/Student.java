/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author duytr
 */
public class Student {
    private String id;
    private String name;
    private String grade;
    private float toan;
    private float van;
    private float anh;

    public Student(String id,String name, String grade) {
        this.name = name;
        this.grade = grade;
        this.id = id;
        this.toan = this.anh = this.van = -1;
    }

    public String getId() {
        return id;
    }

    public void setToan(float toan) {
        this.toan = toan;
    }

    public void setVan(float van) {
        this.van = van;
    }

    public void setAnh(float anh) {
        this.anh = anh;
    }
    
    public float getTB(){
        return (toan + van + anh) / 3;
    }
}
