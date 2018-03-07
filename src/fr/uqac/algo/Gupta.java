package fr.uqac.algo;

import fr.uqac.struct.FlowShopInfo;
import fr.uqac.util.Sorter;

/**
 * Classe pour la méthode GUPTA
 * 
 * @author Julien CUSSET, Benjamin DAGOURET
 */
public class Gupta extends Sorter {
    /**
     * Constructor
     * 
     * @param fileName Le fichier à lire
     */
    public Gupta(String fileName) {
        this.fileName = fileName;
    }
    
    @Override
    public double calculP(FlowShopInfo fsi, int job) {
        double e;
        
        if (fsi.getProcessingTimes(0, job) < fsi.getProcessingTimes(fsi.machines - 1, job)) {
            e = 1;
        } else {
            e = -1;
        }
        
        double minP1 = fsi.getProcessingTimes(0, job) + fsi.getProcessingTimes(1, job);
        double minP2 = fsi.getProcessingTimes(fsi.machines - 2, job) + fsi.getProcessingTimes(fsi.machines - 1, job);
        
        return e / Double.min(minP1, minP2);
    }
}