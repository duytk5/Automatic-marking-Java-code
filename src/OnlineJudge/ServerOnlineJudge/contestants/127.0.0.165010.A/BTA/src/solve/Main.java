/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solve;

import control.Controller;
import java.util.Scanner;
import model.Student;

/**
 *
 * @author duytr
 */
public class Main {
    public static void main(String[] args) {
        control.Controller ct = new Controller();
        Scanner sc = new Scanner(System.in);
        int Q = Integer.parseInt(sc.next());
        while (Q-->0){
            int type = Integer.parseInt(sc.next());
            if (type == 0){
                String id = sc.next();
                String name = sc.next();
                String grade = sc.next();
                Student st = new Student(id, name, grade);
                ct.addStudent(st);
            }
            if (type == 1){
                System.out.println("" + ct.getBestStudent());
            }
            if (type == 2){
                String id = sc.next();
                float toan = Float.parseFloat(sc.next());
                float van = Float.parseFloat(sc.next());
                float anh = Float.parseFloat(sc.next());
                ct.changeStudent(id, toan, van, anh);
            }
        }
    }
}
