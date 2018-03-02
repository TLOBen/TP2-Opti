package fr.uqac.algo.sep;

import fr.uqac.struct.FlowShopInfo;
import fr.uqac.struct.Result;
import fr.uqac.util.Sorter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Benjamin
 */
public class SEP extends Sorter {
    private List<Node> closedSet;
    private FlowShopInfo fsi;
    private Node currentNode;
    private Node classementFinal;
    
    public SEP() {
        this.closedSet = new ArrayList();
        this.currentNode = new Node();
    }

    @Override
    public Result sort(FlowShopInfo fsi) {
        this.fsi = fsi;
        this.classementFinal = solutionInitiale();
        sep();
        
        return createTempResult(this.classementFinal.sequence);
    }
    
    public void sep() {
        boolean cont = true;
        
        while(cont) {
            if (this.currentNode.sequence.size() == this.fsi.jobs && this.currentNode.makespan < this.classementFinal.makespan) {
                this.classementFinal = new Node();
                this.classementFinal.sequence = (ArrayList<Integer>) this.currentNode.sequence.clone();
                this.classementFinal.makespan = this.currentNode.makespan;
                
                removeGreaterClosedNode();
            } else {                
                for (int i=0; i<this.fsi.jobs; i++) {
                    if(!contains(this.currentNode.sequence, i)) {
                        Node newSequence = new Node();
                        newSequence.sequence = (ArrayList<Integer>) this.currentNode.sequence.clone();
                        newSequence.addSequence(i);
                        newSequence.makespan = calculateMakespan(newSequence.sequence);
                        
                        if(newSequence.makespan < this.classementFinal.makespan) {
                            addSequenceClosed(newSequence);
                        }
                    }
                }
            }

            if (this.closedSet.size() > 0) {
                if (this.classementFinal.makespan > this.closedSet.get(0).makespan) {
                    this.currentNode = this.closedSet.remove(0);
                } else {
                    cont = false;
                }
            } else {
                cont = false;
            }
        }
    }
    
    public void addSequenceClosed(Node sequence) {
        sequence.makespan = this.calculateMakespan(sequence.sequence);
                    
        for (int i=0; i<this.closedSet.size(); i++) {
            if (sequence.makespan < this.closedSet.get(i).makespan) {
                this.closedSet.add(i, sequence);
                
                return;
            }
        }
        
        this.closedSet.add(sequence);
    }
    
    public int calculateMakespan(ArrayList<Integer> sequence) {
        int makespan = 0;
        
        for (int i=0; i<this.fsi.machines; i++) {
            Result tempResult = createTempResult(sequence);
            FlowShopInfo tempFsi = createTempFsi(sequence, this.fsi, i+1);
            
            int tempMakespan = new Sorter().calculateMakespan(tempResult, tempFsi);
            
            for (int j=0; j<this.fsi.jobs; j++) {
                if (!contains(sequence, j)) {
                    tempMakespan += this.fsi.getProcessingTimes(i, j);
                }
            }
            
            int min = (int) Double.POSITIVE_INFINITY;
            for (int j=0; j<this.fsi.jobs; j++) {
                if (!contains(sequence, j)) {
                    int minTemp = 0;
                
                    for (int k=i+1; k<this.fsi.machines; k++) {
                        minTemp += this.fsi.getProcessingTimes(k, j);
                    }

                    if(minTemp < min) {
                        min = minTemp;
                    }
                }
            }
            
            if (min != (int) Double.POSITIVE_INFINITY) {
                tempMakespan += min;
            }
            
            if (tempMakespan > makespan) {
                makespan = tempMakespan;
            }
        }
        
        return makespan;
    }
    
    public Result createTempResult(ArrayList<Integer> sequence) {
        Result result = new Result();
        
        for (int i=0; i<sequence.size(); i++) {
            result.add(sequence.get(i), 0.0);
        }
        
        return result;
    }
    
    public FlowShopInfo createTempFsi(ArrayList<Integer> sequence, FlowShopInfo fsi, int machines) {
        FlowShopInfo tempFsi = new FlowShopInfo(fsi.jobs, machines);
        
        for (int i=0; i<tempFsi.machines; i++) {            
            for (int j=0; j<fsi.jobs; j++) {
                int processingTime = 0;
                
                if (contains(sequence, j)) {
                    processingTime = fsi.getProcessingTimes(i, j);
                }
                
                tempFsi.addProcessingTimes(i, j, processingTime);
            }
        }
        
        return tempFsi;
    }
    
    public boolean contains(ArrayList<Integer> sequence, int job) {
        return sequence.contains(job);
    }
    
    public Node solutionInitiale() {
        Node solution = new Node();
        
        for (int i=0; i<fsi.jobs; i++) {
            solution.addSequence(i);
        }
        solution.makespan = calculateMakespan(solution.sequence);
        
        return solution;
    }
    
    public void removeGreaterClosedNode() {
        ArrayList<Node> newClosedSet = new ArrayList();
        
        this.closedSet.forEach((set) ->{
            if (set.makespan < this.classementFinal.makespan) {
                newClosedSet.add(set);
            }
        });
        
        this.closedSet = newClosedSet;
    }
}
