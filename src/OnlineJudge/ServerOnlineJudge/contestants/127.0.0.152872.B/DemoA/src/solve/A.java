package solve;


import java.math.BigInteger;
import java.util.Scanner;
import model.Student;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author duytr
 */
public class A {
    private BigInteger a;
    private BigInteger b;

    public A(BigInteger a, BigInteger b) {
        this.a = a;
        this.b = b;
    }

    public void setA(BigInteger a) {
        this.a = a;
    }

    public void setB(BigInteger b) {
        this.b = b;
    }

    public BigInteger getA() {
        return a;
    }

    public BigInteger getB() {
        return b;
    }

    public void tong(){
        BigInteger ans = a.add(b);
        System.out.println("Tong :"+ans);
    }
    
    public void hieu(){
        BigInteger ans = a.subtract(b);
        System.out.println("Hieu :"+ans);
    }
    
    
    public void tich(){
        BigInteger ans = a.multiply(b);
        System.out.println("Tich :"+ans);
    }
    
    public static void main(String[] args) {
        Student st = new Student("B15DCCN207", "Hiep");
        System.out.println(st.getCode());
        System.out.println(st.getName());
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        for (int t = 0 ; t < T; t++){
            BigInteger a = sc.nextBigInteger();
            BigInteger b = sc.nextBigInteger();
            System.out.println("Case: "+(t+1) );
            new A(a,b).solve();
        }
    }

    private void solve() {
        tong();
        hieu();
        tich();
    }
}
