/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uqac.main;

import fr.uqac.algo.CDS;
import fr.uqac.algo.Gupta;
import fr.uqac.algo.Palmer;
import fr.uqac.algo.sep.SEP;
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
        SEP sep = new SEP();
        
        try {
            //palmer.compute();
            //gupta.compute();
            //cds.compute();
            sep.compute();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
