package fr.uqac;

import fr.uqac.util.FlowShopInfo;

/**
 *
 * @author Julien CUSSET, Benjamin DAGOURET
 */
public class Johnson {
    public int[] johnsonMethod(FlowShopInfo fsi) {
        int[] schedule = new int[fsi.jobs];
        int indexScheduleStart = 0;
        int indexScheduleEnd = fsi.jobs - 1;
        
        for (int x=0; x<fsi.jobs; x++) {
            double minTime = Double.POSITIVE_INFINITY;
            int indexJob = 0;
            int indexMachine = 0;
            
            for (int i=0; i<fsi.machines; i++) {
                for (int j=0; j<fsi.jobs; j++) {
                    if (fsi.getProcessingTimes(i, j) <= minTime && !contains(schedule, j)) {
                        minTime = fsi.getProcessingTimes(i, j);
                        indexJob = j;
                        indexMachine = i;
                    }
                }
            }

            if (indexMachine == 0) {
                schedule[indexScheduleStart] = indexJob;
                indexScheduleStart++;
            } else {
                schedule[indexScheduleEnd] = indexJob;
                indexScheduleEnd--;
            }
        }
        
        return schedule;
    }
    
    private boolean contains(int[] schedule, int job) {
        for (int i=0; i<schedule.length; i++) {
            if (schedule[i] == job) {
                return true;
            }
        }
        
        return false;
    }
}
