package fr.uqac.util;

import fr.uqac.struct.Result;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe d'affichage des résultats
 * 
 * @author Julien CUSSET, Benjamin DAGOURET
 */
public class ResultFile {
    /**
     * Créée un texte contenant les résultats à écrire dans le fichier
     * 
     * @param result Les résultats
     * @param makespan Le makespan
     * @return Une chaîne de caractères
     */
    public String createStringResult(Result result, int makespan) {
        String resultString = new String();
        
        resultString = resultString + "+-------+-----+--------------+\n";
        resultString = resultString + "| order | job | Calculated P |\n";
        resultString = resultString + "+-------+-----+--------------+\n";
        
        for(int i=0; i<result.size(); i++) {
            int key = result.getKey(i);
            double value = result.getValue(i);
            
            if(value == (long) value) {
                resultString = resultString + String.format("| %5d | %3d | %12d |%n", i+1, key+1, (long)value);
            } else {
                resultString = resultString + String.format("| %5d | %3d | %12f |%n", i+1, result.getKey(i)+1, result.getValue(i));
            }
        }

        resultString = resultString + "+-------+-----+--------------+\n";
        resultString = resultString + "Makespan = " + makespan + "\n";
        
        return resultString;
    }
    
    /**
     * Créée un fichier avec la liste des jobs ordonnancés et le makespan
     * 
     * @param result L'ordonnancement des jobs
     * @param makespan Le temps de réalisation des jobs
     */
    public void makeFileResult(Result result, int makespan) {
        try {
            String resultString = createStringResult(result, makespan);
            
            File resultFile = new File("result.txt");
            resultFile.createNewFile();
            
            FileWriter fw = new FileWriter("result.txt", true);
            PrintWriter pw = new PrintWriter(fw);
            pw.printf("%s%n", resultString);
            pw.close();
        } catch (IOException ex) {
            Logger.getLogger(ResultFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}