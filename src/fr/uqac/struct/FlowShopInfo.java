package fr.uqac.struct;

/**
 * Classe contenant toutes les informations afin d'ordonnancer les jobs
 * 
 * @author Julien CUSSET, Benjamin DAGOURET
 */
public class FlowShopInfo {
    /**
     * Le nombre de jobs
     */
    public int jobs;
    
    /**
     * Le nombre de machines
     */
    public int machines;
    
    /**
     * Le temps d'exécution pour chaque job sur chaque machine
     */
    private final int[][] processingTimes;
    
    /**
     * Constructeur
     * 
     * @param jobs Le nombre de jobs
     * @param machines Le nombre de machines
     */
    public FlowShopInfo(int jobs, int machines) {
        this.jobs = jobs;
        this.machines = machines;
        this.processingTimes = new int[machines][jobs];
    }
    
    /**
     * Renvoie le temps d'exécution d'un job j sur une machine m
     * 
     * @param machine L'indice de la machine
     * @param job L'indice du job
     * @return Le temps d'exécution
     */
    public int getProcessingTimes(int machine, int job) {
        return this.processingTimes[machine][job];
    }
    
    /**
     * Ajoute le temps d'exécution d'un job j sur une machine m
     * 
     * @param machine L'indice de la machine
     * @param job L'indice du job
     * @param processingTime Le temps d'exécution
     */
    public void addProcessingTimes(int machine, int job, int processingTime) {
        this.processingTimes[machine][job] = processingTime;
    }
}