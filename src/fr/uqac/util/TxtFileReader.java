package fr.uqac.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe permettant de lire les fichiers de tests
 * 
 * @author Julien CUSSET, Benjamin DAGOURET
 */
public class TxtFileReader {
    /**
     * Methode de lecture d'un fichier de test
     * 
     * @param filePath Le chemin vers le fichier test
     * @param instanceNumber Le nombre d'instances qu'on veut lire
     * @return Les informations des n premières instances contenus dans ce fichier
     */
    public ArrayList<FlowShopInfo> readFile(String filePath, int instanceNumber) {
        ArrayList<FlowShopInfo> fsiList = new ArrayList();
        String line;
        Scanner scanner;

        // Ouverture du fichier
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            // On ne lit que les deux premières instances
            for (int i=0; i<instanceNumber; i++) {
                // Nouvelle instance
                FlowShopInfo fsi = new FlowShopInfo();
                
                // On passe la première ligne
                bufferedReader.readLine();

                // Ligne avec le nombre de jobs, machines, ...
                if((line = bufferedReader.readLine()) != null) {
                    scanner = new Scanner(line);

                    for (int j=0; j<5 && scanner.hasNextInt(); j++) {
                        fsi.addInfo(j, scanner.nextInt());
                    }
                }
                
                // On passe la ligne suivante
                bufferedReader.readLine();
                
                // On lit le nombre de lignes correspondant au nombre de machines
                for (int j=0; j<fsi.machines; j++) {
                    if((line = bufferedReader.readLine()) != null) {
                        scanner = new Scanner(line);
                        
                        for (int k=0; k<fsi.jobs && scanner.hasNextInt(); k++) {
                            fsi.addProcessingTimes(j, k, scanner.nextInt());
                        }
                    }
                }
                
                // On ajoute la nouvelle instance à la liste
                fsiList.add(fsi);
            }

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filePath + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + filePath + "'");
        }
        
        return fsiList;
    }
}