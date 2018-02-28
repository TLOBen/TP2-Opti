package fr.uqac;

import fr.uqac.util.FlowShopInfo;
import fr.uqac.util.Sorter;
import java.util.ArrayList;
import javafx.util.Pair;

/**
 *
 * @author Benjamin
 */
public class CDS extends Sorter {
    @Override
    public ArrayList<Pair<Integer, Double>> sort(FlowShopInfo fsi) {
        ArrayList<Pair<Integer, Double>> classementFinal = new ArrayList();
        double makespan = Double.POSITIVE_INFINITY;
        
        for (int i=1; i<fsi.machines-1; i++) {
            ArrayList<Pair<Integer, Double>> classement = cds(fsi, i);
            
            double newMakespan = calculateMakespan(classement, fsi);
            if (newMakespan < makespan) {
                classementFinal = classement;
            }
        }
        
        return classementFinal;
    }
    
    public ArrayList<Pair<Integer, Double>> cds(FlowShopInfo fsi, int i) {
        FlowShopInfo newFsi = new FlowShopInfo();
        newFsi.jobs = fsi.jobs;
        newFsi.machines = 2;
        
        for (int j=0; j<fsi.jobs; j++) {
            int P1 = 0;
            int P2 = 0;

            for (int k=0; k<=i/2; k++) {
                P1 += fsi.getProcessingTimes(k, j);
            }
            for (int k=i-1; k>i/2; k--) {
                P2 += fsi.getProcessingTimes(k, j);
            }
            
            newFsi.addProcessingTimes(0, j, P1);
            newFsi.addProcessingTimes(1, j, P2);
        }
        
        Johnson johnson = new Johnson();
        int[] jonhsonResult = johnson.johnsonMethod(newFsi);
        
        ArrayList<Pair<Integer, Double>> ret = new ArrayList();
        for (int j=0; j<jonhsonResult.length; j++) {
            ret.add(new Pair(j, 0.0));
        }
        
        return ret;
    }
}