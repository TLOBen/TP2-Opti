/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uqac;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Benjamin
 */
public class Main {
    public static void main(String[] args) {
        Gupta gupta = new Gupta();
        Palmer palmer = new Palmer();
        CDS cds = new CDS();
        
        try {
            //gupta.compute();
            //palmer.compute();
            cds.compute();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
