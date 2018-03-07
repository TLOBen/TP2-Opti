package fr.uqac.main;

import fr.uqac.algo.CDS;
import fr.uqac.algo.Gupta;
import fr.uqac.algo.Johnson;
import fr.uqac.algo.Palmer;
import fr.uqac.algo.sep.SEP;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Classe Main
 * 
 * @author Julien CUSSET, Benjamin DAGOURET
 */
public class Main {
    public static int method = 1;
    public static String fileName = "";
    
    /**
     * Ecrit les premières informations dans le fichier résultat (date, méthode et fichier)
     * Si un fichier résultat.txt existe, il sera supprimé
     */
    public static void printFile() throws IOException {
        File resultFile = new File("result.txt");
        if (resultFile.exists()) {
            resultFile.delete();
        }
        resultFile.createNewFile();
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        FileWriter fw = new FileWriter("result.txt", true);
        PrintWriter pw = new PrintWriter(fw);
        
        pw.printf("%s%n", "======================================================================================");
        pw.printf("%s", "Method: ");
        
        switch(method) {
            case 1:
                pw.printf("%s%n", "Jonhson");
                break;
            case 2:
                pw.printf("%s%n", "Palmer");
                break;
            case 3:
                pw.printf("%s%n", "Gupta");
                break;
            case 4:
                pw.printf("%s%n", "CDS");
                break;
            case 5:
                pw.printf("%s%n", "SEP");
                break;
        }
        
        pw.printf("%s%s%n", "File: ", fileName);
        pw.printf("%s%s%n", "Date: ", dateFormat.format(date));
        pw.printf("%s%n%n", "======================================================================================");
        pw.close();
    }
    
    /**
     * Execution de la méthode sélectionnée
     */
    public static void compute() {
        try {
            printFile();
            
            switch(method) {
                case 1:
                    Johnson johnson = new Johnson(fileName);
                    johnson.compute();
                    break;
                case 2:
                    Palmer palmer = new Palmer(fileName);
                    palmer.compute();
                    break;
                case 3:
                    Gupta gupta = new Gupta(fileName);
                    gupta.compute();
                    break;
                case 4:
                    CDS cds = new CDS(fileName);
                    cds.compute();
                    break;
                case 5:
                    SEP sep = new SEP(fileName);
                    sep.compute();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Choix du fichier
     * 
     * @param parent La fenetre principale
     */
    public static void chooseFile(JFrame parent) {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        chooser.setFileFilter(filter);
        
        int returnVal = chooser.showOpenDialog(parent);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
           fileName = chooser.getSelectedFile().getAbsolutePath();
        }
    }
    
    /**
     * Fenetre principale
     */
    public static void createFrame() {
        JFrame frame = new JFrame("TP2 - Exercice 1");
        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(new GridLayout(5, 1));
        JPanel panelSouth = new JPanel();
        panelSouth.setLayout(new GridLayout(1, 1));
        
        JRadioButton radioButton1 = new JRadioButton("Johnson");
        radioButton1.setSelected(true);
        JRadioButton radioButton2 = new JRadioButton("Palmer");
        JRadioButton radioButton3 = new JRadioButton("Gupta");
        JRadioButton radioButton4 = new JRadioButton("CDS");
        JRadioButton radioButton5 = new JRadioButton("SEP");
        
        ButtonGroup group = new ButtonGroup();
        group.add(radioButton1);
        group.add(radioButton2);
        group.add(radioButton3);
        group.add(radioButton4);
        group.add(radioButton5);
        
        panelCenter.add(radioButton1);
        panelCenter.add(radioButton2);
        panelCenter.add(radioButton3);
        panelCenter.add(radioButton4);
        panelCenter.add(radioButton5);
        
        radioButton1.addActionListener((ActionEvent e) -> {
            method = 1;
        });
        radioButton2.addActionListener((ActionEvent e) -> {
            method = 2;
        });
        radioButton3.addActionListener((ActionEvent e) -> {
            method = 3;
        });
        radioButton4.addActionListener((ActionEvent e) -> {
            method = 4;
        });
        radioButton5.addActionListener((ActionEvent e) -> {
            method = 5;
        });
        
        JButton confirm = new JButton("OK");
        confirm.addActionListener((ActionEvent e) -> {
            chooseFile(frame);
            
            if (!fileName.equals("")) {
                compute();
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        panelSouth.add(confirm);

        frame.add(panelCenter, BorderLayout.CENTER);
        frame.add(panelSouth, BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.setSize(200, 200);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * Main
     * 
     * @param args Arguments données en ligne de commande
     */
    public static void main(String[] args) {
        createFrame();
    }
}