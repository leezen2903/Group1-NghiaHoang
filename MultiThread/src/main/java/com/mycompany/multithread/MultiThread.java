/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.multithread;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class MultiThread {

//Dùng multi thread, mỗi lúc sẽ chạy khác nhau và nó chạy đồng thời.    
    public static void main(String[] args) {
       List<Thread> threads = new ArrayList<>();
        //Tạo 10 task:
       for (int i = 0; i < 10; i++) {
           //DÙng cú pháp Runnable.
           //1. Tạo Constructor thread trc bỏ RUnnable bên trong
           String name = "Cong viec thu:" + (i + 1);
           Thread thread = new Thread(new Runnable() {
               @Override
               public void run() {
                   for (int j = 0; j < 5; j++) {
                       System.out.println(name + " thuc hien lan " + (j + 1));
                   }
               }
           });
           threads.add(thread);
           
       }
       for (Thread thread : threads) {
           thread.start();
       }
    }
}
