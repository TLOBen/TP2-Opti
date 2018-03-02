package fr.uqac.algo;

import fr.uqac.struct.FlowShopInfo;
import fr.uqac.util.Sorter;
import fr.uqac.struct.Result;

/**
 *
 * @author Benjamin
 */
public class CDS extends Sorter {
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
        
        Johnson johnson = new Johnson();
        
        return johnson.johnsonMethod(newFsi);
    }
}