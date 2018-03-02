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
        
        for (int i=0; i<classementFinal.sequence.size(); i++) {
            System.out.println(classementFinal.sequence.get(i));
        }
        
        return new Result();
    }
    
    public Result sep() {
        boolean cont = true;
        
        while(cont) {
            System.out.println(this.currentNode.sequence.size() + " " + this.currentNode.makespan + " " + this.classementFinal.makespan);
            
            if (this.currentNode.sequence.size() == this.fsi.jobs) {
                this.classementFinal = this.currentNode;
            } else {
                for (int i=0; i<this.fsi.jobs; i++) {
                    if(!contains(this.currentNode.sequence, i)) {
                        Node newSequence = new Node();
                        newSequence.sequence = (ArrayList<Integer>) this.currentNode.sequence.clone();
                        newSequence.addSequence(i);

                        addSequenceClosed(newSequence);
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
        
        return new Result();
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
            result.add(i, 0.0);
        }
        
        return result;
    }
    
    public FlowShopInfo createTempFsi(ArrayList<Integer> sequence, FlowShopInfo fsi, int machines) {
        FlowShopInfo tempFsi = new FlowShopInfo(sequence.size(), machines);
                
        for (int i=0; i<tempFsi.machines; i++) {
            int index = 0;
            
            for (int j=0; j<fsi.jobs; j++) {                
                if (contains(sequence, j)) {
                    int processingTime = fsi.getProcessingTimes(i, j);
                    tempFsi.addProcessingTimes(i, index, processingTime);
                    index++;
                }
            }
        }
        
        return tempFsi;
    }
    
    public boolean contains(ArrayList<Integer> sequence, int job) {
        if (sequence.contains(job)) {
            return true;
        } else {
            return false;
        }
    }
    
    public Node solutionInitiale() {
        Node solution = new Node();
        
        for (int i=0; i<fsi.jobs; i++) {
            solution.addSequence(i);
        }
        solution.makespan = calculateMakespan(solution.sequence);
        
        return solution;
    }
}
