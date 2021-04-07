/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.cvven.java;

import vue.Connection;

/**
 * Ouvre la première fenêtre de notre application
 * @author senpai
 */
public class CvvenJava {
       
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Connection fen = new Connection();
        fen.setVisible(true);
    }
}
