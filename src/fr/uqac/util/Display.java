package fr.uqac.util;

import java.util.ArrayList;
import javafx.util.Pair;

/**
 *
 * @author Benjamin
 */
public class Display {
    public void displayResult(ArrayList<Pair<Integer, Double>> classement, int makespan) {
        System.out.println("+-----+--------------+");
        System.out.println("| job | Calculated P |");
        System.out.println("+-----+--------------+");

        for(int i=0; i<classement.size(); i++) {
            int key = classement.get(i).getKey();
            double value = classement.get(i).getValue();
            
            if(value == (long) value) {
                System.out.format("| %3d | %12d |%n", key, (long)value);
            } else {
                System.out.format("| %3d | %12f |%n", classement.get(i).getKey(), classement.get(i).getValue());
            }
        }

        System.out.println("+-----+--------------+");
        System.out.println("Makespan = " + makespan + "\n");
    }
}