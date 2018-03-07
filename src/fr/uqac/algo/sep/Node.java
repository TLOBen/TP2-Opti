package fr.uqac.algo.sep;

import java.util.ArrayList;

/**
 * Noeud utilisé pour contenir la séquence de jobs à une certaine itération de SEP
 * 
 * @author Julien CUSSET, Benjamin DAGOURET
 */
public class Node {
    /**
     * Le makespan provisoire de la séquence
     */
    public int makespan;
    
    /**
     * La séquence actuelle
     */
    public ArrayList<Integer> sequence;
    
    /**
     * Constructeur
     */
    public Node() {
        this.makespan = (int) Double.POSITIVE_INFINITY;
        this.sequence = new ArrayList();
    }
    
    /**
     * Ajoute un job à la séquence
     * 
     * @param job L'indice du job
     */
    public void addSequence(Integer job) {
        this.sequence.add(job);
    }
}