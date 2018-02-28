package fr.uqac.util;

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
        ArrayList<FlowShopInfo> fsiList = tfr.readFile("C:/Users/Benjamin/Downloads/tai100_20.txt", 2);
        
        for (int i=0; i<2; i++) {
            FlowShopInfo fsi = fsiList.get(i);
            ArrayList<Pair<Integer, Double>> classement = sort(fsi);
            int makespan = calculateMakespan(classement, fsi);
            
            display.displayResult(classement, makespan);
        }
    }
    
    public ArrayList<Pair<Integer, Double>> sort(FlowShopInfo fsi) {
        ArrayList<Pair<Integer, Double>> classement = new ArrayList();
        
        for (int j=0; j<fsi.jobs; j++) {
            Pair paire = new Pair(j, calculP(fsi, j));
            classement.add(paire);
        }
        
        classement.sort((Pair<Integer, Double> p1, Pair<Integer, Double> p2) -> {
            Double a = p1.getValue();
            Double b = p2.getValue();
            return b.compareTo(a);
        });
        
        return classement;
    }
    
    public int calculateMakespan(ArrayList<Pair<Integer, Double>> classement, FlowShopInfo fsi) {
        int[] makespanPerMachine = new int[fsi.machines];
        
        for (int i=0; i<fsi.machines; i++) {
            makespanPerMachine[i] = 0;
        }
        
        for (int i=0; i<classement.size(); i++) {
            for (int j=0; j<fsi.machines; j++) {
                if (j != 0) {
                    if (makespanPerMachine[j-1] > makespanPerMachine[j]) {
                        makespanPerMachine[j] = makespanPerMachine[j-1];
                    }
                }
                
                makespanPerMachine[j] += fsi.getProcessingTimes(j, classement.get(i).getKey());
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
