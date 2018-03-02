package fr.uqac.util;

import fr.uqac.struct.FlowShopInfo;
import fr.uqac.struct.Result;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.util.Pair;

/**
 *
 * @author Benjamin
 */
public class Sorter {
    public void compute() throws FileNotFoundException {
        Display display = new Display();
        TxtFileReader tfr = new TxtFileReader();
        ArrayList<FlowShopInfo> fsiList = tfr.readFile("C:/Users/Benjamin/Downloads/tai20_5.txt", 2);
        
        for (int i=0; i<2; i++) {
            FlowShopInfo fsi = fsiList.get(i);
            Result classement = sort(fsi);
            int makespan = calculateMakespan(classement, fsi);
            
            display.displayResult(classement, makespan);
        }
    }
    
    public Result sort(FlowShopInfo fsi) {
        Result classement = new Result();
        
        for (int j=0; j<fsi.jobs; j++) {
            Pair paire = new Pair(j, calculP(fsi, j));
            classement.add(j, calculP(fsi, j));
        }
        
        classement.sort();
        
        return classement;
    }
    
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
     */
    public double calculP(FlowShopInfo fsi, int job) {
        return 0;
    }
}
