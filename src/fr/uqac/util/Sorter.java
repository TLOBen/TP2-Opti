package fr.uqac.util;

import fr.uqac.struct.FlowShopInfo;
import fr.uqac.struct.Result;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Classe globale permettant l'exécution des méthodes d'ordonnancement
 * 
 * @author Julien CUSSET, Benjamin DAGOURET
 */
public class Sorter extends ResultFile {
    /**
     * Le fichier à lire
     */
    protected String fileName;
    
    /**
     * Appelle la lecture de fichier, exécute la méthode et affiche le résultat
     * 
     * @throws FileNotFoundException 
     */
    public void compute() throws FileNotFoundException {
        TxtFileReader tfr = new TxtFileReader();
        ArrayList<FlowShopInfo> fsiList = tfr.readFile(this.fileName, 2);
        
        for (int i=0; i<2; i++) {
            FlowShopInfo fsi = fsiList.get(i);
            Result classement = sort(fsi);
            int makespan = calculateMakespan(classement, fsi);
            
            makeFileResult(classement, makespan);
        }
    }
    
    /**
     * Trie les jobs afin d'obtenir le meilleur makespan
     * 
     * @param fsi Les données du fichier d'entrée
     * @return Un objet Result
     */
    public Result sort(FlowShopInfo fsi) {
        Result classement = new Result();
        
        for (int j=0; j<fsi.jobs; j++) {
            classement.add(j, calculP(fsi, j));
        }
        
        classement.sort();
        
        return classement;
    }
    
    /**
     * Calcule le makespan
     * 
     * @param result L'ordonnancement des jobs
     * @param fsi Les données du fichier en entrée
     * @return Le makespan
     */
    public int calculateMakespan(Result result, FlowShopInfo fsi) {
        int[] makespanPerMachine = new int[fsi.machines];
        
        for (int i=0; i<fsi.machines; i++) {
            makespanPerMachine[i] = 0;
        }
        
        for (int i=0; i<result.size(); i++) {
            for (int j=0; j<fsi.machines; j++) {
                if (j != 0) {
                    if (makespanPerMachine[j-1] > makespanPerMachine[j]) {
                        makespanPerMachine[j] = makespanPerMachine[j-1];
                    }
                }
                
                makespanPerMachine[j] += fsi.getProcessingTimes(j, result.getKey(i));
            }
        }
        
        return makespanPerMachine[fsi.machines - 1];
    }
    
    /**
     * Methode qui sera redéfinie dans les classes enfants
     * Calcule les priorités de chaque job
     * 
     * @param fsi Les données du fichier en entrée
     * @param job La job ou la priorité doit être calculée
     * @return La priorité de la job
     */
    public double calculP(FlowShopInfo fsi, int job) {
        return 0;
    }
}
