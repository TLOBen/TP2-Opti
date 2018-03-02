package fr.uqac.algo.sep;

import java.util.ArrayList;

/**
 *
 * @author Benjamin
 */
public class Node {
    public int makespan;
    public ArrayList<Integer> sequence;
    
    public Node() {
        this.makespan = (int) Double.POSITIVE_INFINITY;
        this.sequence = new ArrayList();
    }
    
    public void addSequence(Integer job) {
        this.sequence.add(job);
    }
}