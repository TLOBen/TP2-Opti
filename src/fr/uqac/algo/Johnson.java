package fr.uqac.algo;

import fr.uqac.struct.FlowShopInfo;
import fr.uqac.struct.Result;
import fr.uqac.util.Sorter;

/**
 * Classe pour la méthode de Jonhson
 * 
 * @author Julien CUSSET, Benjamin DAGOURET
 */
public class Johnson extends Sorter {
    /**
     * Constructor
     * 
     * @param fileName Le fichier à lire
     */
    public Johnson(String fileName) {
        this.fileName = fileName;
    }
    
    @Override
    public Result sort(FlowShopInfo fsi) {
        return johnsonMethod(fsi);
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
    
    /**
     * Méthode principale de johnson
     * 
     * @param fsi les informations du fichier
     * @return Un objet Result
     */
    public Result johnsonMethod(FlowShopInfo fsi) {
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
        
        return new Result().convertFromArrayList(schedule);
    }
    
    /**
     * Vérifie si un job est contenu dans le tableau d'ordonnancement
     * 
     * @param schedule Tableau d'ordonnancement
     * @param job Le job à vérifier
     * @return vrai si le job est dans le tableau, sinon faux
     */
    private boolean contains(int[] schedule, int job) {
        for (int i=0; i<schedule.length; i++) {
            if (schedule[i] == job) {
                return true;
            }
        }
        
        return false;
    }
}