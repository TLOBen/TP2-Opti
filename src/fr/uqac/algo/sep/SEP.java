package fr.uqac.algo.sep;

import fr.uqac.struct.FlowShopInfo;
import fr.uqac.struct.Result;
import fr.uqac.util.Sorter;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe pour la méthode SEP
 * 
 * @author Julien CUSSET, Benjamin DAGOURET
 */
public class SEP extends Sorter {
    /**
     * La liste des séquences à parcourir
     */
    private List<Node> closedSet;
    
    /**
     * Les données du fichier d'entrée
     */
    private FlowShopInfo fsi;
    
    /**
     * Le noeud actuellement utilisé
     */
    private Node currentNode;
    
    /**
     * La solution provisoire qui deviendra finale
     */
    private Node classementFinal;
    
    /**
     * Constructeur
     */
    public SEP(String fileName) {
        this.fileName = fileName;
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
    
    @Override
    public String createStringResult(Result result, int makespan) {
        String resultString = new String();
        resultString = resultString + "+-----+\n";
        resultString = resultString + "| job |\n";
        resultString = resultString + "+-----+\n";
        
        for(int i=0; i<result.size(); i++) {
            resultString = resultString + String.format("| %3d |%n", result.getKey(i));
        }

        resultString = resultString + "+-----+\n";
        resultString = resultString + "Makespan = " + makespan + "\n";
        
        return resultString;
    }
    
    /**
     * Méthode SEP
     */
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
    
    /**
     * Ajoute une séquence aux tableau de séquences à parcourir
     * 
     * @param sequence Une séquence de jobs
     */
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
    
    /**
     * Calcule le makespan d'une séquence
     * 
     * @param sequence Une séquence de jobs
     * @return Le makespan de la séquence
     */
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
    
    /**
     * Créée un résultat temporaire en fonction d'une séquence
     * 
     * @param sequence Une séquence de jobs
     * @return Un objet Result
     */
    public Result createTempResult(ArrayList<Integer> sequence) {
        Result result = new Result();
        
        for (int i=0; i<sequence.size(); i++) {
            result.add(sequence.get(i), 0.0);
        }
        
        return result;
    }
    
    /**
     * Créée un nouveau objet FlowShopInfo à partir de celuilu à partir
     * du fichier et d'une séquence
     * 
     * @param sequence Une séquence de jobs
     * @param fsi Les données du fichier d'entrée
     * @param machines Le nombre de machines
     * @return Un nouveau FlowShopInfo
     */
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
    
    /**
     * Vérifie si un job apartient à une séquence
     * 
     * @param sequence Une séquence de jobs
     * @param job Un job
     * @return vrai si le job est contenu dans la séquence, sinon faux
     */
    public boolean contains(ArrayList<Integer> sequence, int job) {
        return sequence.contains(job);
    }
    
    /**
     * Créée une solution initiale en prenant les jobs de 1 à n
     * 
     * @return Un noeud contenant une solution initiale
     */
    public Node solutionInitiale() {
        Node solution = new Node();
        
        for (int i=0; i<fsi.jobs; i++) {
            solution.addSequence(i);
        }
        solution.makespan = calculateMakespan(solution.sequence);
        
        return solution;
    }
    
    /**
     * Suprime les noeuds ayant un makespan plus grand que le résultat provisoires
     */
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