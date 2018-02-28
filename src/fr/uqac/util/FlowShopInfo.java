package fr.uqac.util;

import java.util.HashMap;

/**
 *
 * @author Benjamin
 */
public class FlowShopInfo {
    public int jobs;
    public int machines;
    public int seed;
    public int upperBound;
    public int lowerBound;
    private final HashMap<Integer, Integer> processingTimes;
    
    public FlowShopInfo() {
        this.processingTimes = new HashMap();
    }
    
    public int getProcessingTimes(int machine, int job) {
        return this.processingTimes.get(machine * jobs + job);
    }
    
    public void addProcessingTimes(int machine, int job, int processingTime) {
        this.processingTimes.put(machine * jobs + job, processingTime);
    }
    
    public void addInfo(int index, int info) {
        switch (index) {
            case 0: jobs = info;
            case 1: machines = info;
            case 2: seed = info;
            case 3: upperBound = info;
            case 4: lowerBound = info;
        }
    }
}