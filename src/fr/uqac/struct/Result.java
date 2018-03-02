/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uqac.struct;

import java.util.ArrayList;
import javafx.util.Pair;

/**
 *
 * @author Benjamin
 */
public class Result {
    public ArrayList<Pair<Integer, Double>> classement;
    
    public Result() {
        this.classement = new ArrayList();
    }
    
    public void add(int job, double p) {
        Pair<Integer, Double> paire = new Pair(job, p);
        this.classement.add(paire);
    }
    
    public void sort() {
        this.classement.sort((Pair<Integer, Double> p1, Pair<Integer, Double> p2) -> {
            Double a = p1.getValue();
            Double b = p2.getValue();
            return b.compareTo(a);
        });
    }
    public double size() {
        return this.classement.size();
    }
    
    public int getKey(int i) {
        return this.classement.get(i).getKey();
    }
    
    public double getValue(int i) {
        return this.classement.get(i).getValue();
    }
    
    public Result convertToArrayList(int[] classement) {
        this.classement.clear();
        
        for (int j=0; j<classement.length; j++) {
            this.classement.add(new Pair(classement[j], 0.0));
        }
        
        return this;
    }
}
