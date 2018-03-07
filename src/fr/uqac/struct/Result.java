package fr.uqac.struct;

import java.util.ArrayList;
import javafx.util.Pair;

/**
 * Classe de résultat contenant l'ordonnancement des jobs
 * 
 * @author Julien CUSSET, Benjamin DAGOURET
 */
public class Result {
    /**
     * L'ordonnancement des jobs
     */
    public ArrayList<Pair<Integer, Double>> classement;
    
    /**
     * Constructeur
     */
    public Result() {
        this.classement = new ArrayList();
    }
    
    /**
     * Ajoute un job à la liste
     * 
     * @param job Un job
     * @param p La priorité du job
     */
    public void add(int job, double p) {
        Pair<Integer, Double> paire = new Pair(job, p);
        this.classement.add(paire);
    }
    
    /**
     * Trie le tableau des jobs en fonction de leur priorité
     */
    public void sort() {
        this.classement.sort((Pair<Integer, Double> p1, Pair<Integer, Double> p2) -> {
            Double a = p1.getValue();
            Double b = p2.getValue();
            return b.compareTo(a);
        });
    }
    
    /**
     * Revoie la taille du tableau de jobs
     * 
     * @return La taille du tableau de jobs
     */
    public double size() {
        return this.classement.size();
    }
    
    /**
     * Renvoie le numéro de job à un certain indice
     * 
     * @param i Un indice du tableau
     * @return Le numéro de job
     */
    public int getKey(int i) {
        return this.classement.get(i).getKey();
    }
    
    /**
     * Renvoie la priorité d'une job à un certain indice
     * 
     * @param i Un indice du tableau
     * @return La priorité du job
     */
    public double getValue(int i) {
        return this.classement.get(i).getValue();
    }
    
    /**
     * Transforme le tableau en un objet Result
     * 
     * @param classement Le classement des jobs
     * @return Un objet Result
     */
    public Result convertFromArrayList(int[] classement) {
        this.classement.clear();
        
        for (int j=0; j<classement.length; j++) {
            this.classement.add(new Pair(classement[j], 0.0));
        }
        
        return this;
    }
}