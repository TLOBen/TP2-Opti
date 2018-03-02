package fr.uqac.util;

import fr.uqac.struct.Result;

/**
 *
 * @author Benjamin
 */
public class Display {
    public void displayResult(Result result, int makespan) {
        System.out.println("+-----+--------------+");
        System.out.println("| job | Calculated P |");
        System.out.println("+-----+--------------+");

        for(int i=0; i<result.size(); i++) {
            int key = result.getKey(i);
            double value = result.getValue(i);
            
            if(value == (long) value) {
                System.out.format("| %3d | %12d |%n", key, (long)value);
            } else {
                System.out.format("| %3d | %12f |%n", result.getKey(i), result.getValue(i));
            }
        }

        System.out.println("+-----+--------------+");
        System.out.println("Makespan = " + makespan + "\n");
    }
}