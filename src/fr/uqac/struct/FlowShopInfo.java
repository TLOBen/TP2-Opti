package fr.uqac.struct;

import java.util.HashMap;

/**
 *
 * @author Benjamin
 */
public class FlowShopInfo {
    public int jobs;
    public int machines;
    private final int[][] processingTimes;
    
    public FlowShopInfo(int jobs, int machines) {
        this.jobs = jobs;
        this.machines = machines;
        this.processingTimes = new int[machines][jobs];
    }
    
    public int getProcessingTimes(int machine, int job) {
        return this.processingTimes[machine][job];
    }
    
    public void addProcessingTimes(int machine, int job, int processingTime) {
        this.processingTimes[machine][job] = processingTime;
    }
}