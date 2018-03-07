package fr.uqac.algo;

import fr.uqac.struct.FlowShopInfo;
import fr.uqac.util.Sorter;
import fr.uqac.struct.Result;

/**
 * Classe utilisant la méthode CDS
 * 
 * @author Julien CUSSET, Benjamin DAGOURET
 */
public class CDS extends Sorter {
    /**
     * Constructor
     * 
     * @param fileName Le fichier à lire
     */
    public CDS(String fileName) {
        this.fileName = fileName;
    }
    
    @Override
    public String createStringResult(Result result, int makespan) {
        String resultString = new String();
        resultString = resultString + "+-------+-----+\n";
        resultString = resultString + "| order | job |\n";
        resultString = resultString + "+-------+-----+\n";
        
        for(int i=0; i<result.size(); i++) {
            resultString = resultString + String.format("| %5d | %3d |%n", i+1, result.getKey(i)+1);
        }

        resultString = resultString + "+-------+-----+\n";
        resultString = resultString + "Makespan = " + makespan + "\n";
        
        return resultString;
    }
    
    @Override
    public Result sort(FlowShopInfo fsi) {
        Result classementFinal = new Result();
        double makespan = Double.POSITIVE_INFINITY;
        
        for (int i=0; i<fsi.machines; i++) {
            Result classement = cds(fsi, i);
            
            double newMakespan = calculateMakespan(classement, fsi);
            if (newMakespan < makespan) {
                classementFinal = classement;
            }
        }
        
        return classementFinal;
    }
    
    /**
     * Méthode CDS
     * 
     * @param fsi Les données du fichier d'entrée
     * @param i L'itération de CDS
     * @return Un objet Result
     */
    public Result cds(FlowShopInfo fsi, int i) {
        FlowShopInfo newFsi = new FlowShopInfo(fsi.jobs, 2);
        
        for (int j=0; j<fsi.jobs; j++) {
            int P1 = 0;
            int P2 = 0;

            for (int k=0; k<=i/2; k++) {
                P1 += fsi.getProcessingTimes(k, j);
            }
            for (int k=i; k>i/2; k--) {
                P2 += fsi.getProcessingTimes(k, j);
            }
            
            newFsi.addProcessingTimes(0, j, P1);
            newFsi.addProcessingTimes(1, j, P2);
        }
        
        Johnson johnson = new Johnson(this.fileName);
        
        return johnson.johnsonMethod(newFsi);
    }
}