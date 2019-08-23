/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.util.ArrayList;
import model.Student;

/**
 *
 * @author duytr
 */
public class Controller {
    private ArrayList<Student> dsST;

    public Controller() {
        dsST = new ArrayList<>();
    }
    
    public void addStudent(Student hs){
        dsST.add(hs);
    }
    
    public void changeStudent(String id , float toan, float van, float anh){
        for (Student st: dsST){
            if (st.getId().equals(id)){
                st.setToan(toan);
                st.setToan(van);
                st.setToan(anh);
                break;
            }
        }
    }
    
    public String getBestStudent(){
        Student ans = dsST.get(0);
        for (Student st : dsST){
            if (st.getTB() > ans.getTB() || (st.getTB() == ans.getTB() && st.getId().compareTo(ans.getId())<0)){
                ans = st;
            }
        }
        return ans.getId();
    }
}
