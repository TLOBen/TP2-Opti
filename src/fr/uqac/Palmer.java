package fr.uqac;

import fr.uqac.util.FlowShopInfo;
import fr.uqac.util.Sorter;

/**
 *
 * @author Benjamin
 */
public class Palmer extends Sorter {    
    @Override
    public double calculP(FlowShopInfo fsi, int job) {
        int p = 0;
        
        for (int i=0; i<=fsi.machines/2; i++) {
            p -= (fsi.machines - (2*i+1)) * fsi.getProcessingTimes(i, job);
        }
        for (int i=fsi.machines-1; i>fsi.machines/2; i--) {
            p += (fsi.machines - (1 + 2*(i + 1 - fsi.machines))) * fsi.getProcessingTimes(i, job);
        }
        
        return p;
    }
}