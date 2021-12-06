// this program generates secret santa partners for a group given by the user, and ensures
// that partners from the previous year are not repeated and subgroup members are not paired
// with eachother.
import java.io.*;
import java.util.*;
import javax.swing.*;
public class SecretSanta {
   public static final String TITLE = "Secret Santa";
   public static void main(String[] args) throws FileNotFoundException {
      JFrame frame = new JFrame();
      Random rand = new Random();
      intro(frame);
      String out = JOptionPane.showInputDialog(null, "Output File?",
       TITLE, JOptionPane.QUESTION_MESSAGE);
      PrintStream output = new PrintStream(out);
      int size = Integer.parseInt(JOptionPane.showInputDialog(null, "How many people will be in your group?",
       TITLE, JOptionPane.QUESTION_MESSAGE));
      int subGroups = Integer.parseInt(JOptionPane.showInputDialog(null, "How many sub-groups will there be?",
       TITLE, JOptionPane.QUESTION_MESSAGE));
      repeatYear(size, subGroups, rand, frame, output);
   }
   
   // tells user about what the program will do
   public static void intro(JFrame frame) {
     String introMessage = "This program will generate who everyone will be giving a gift to for Secret Santa this year!";
     JOptionPane.showMessageDialog(null, introMessage, TITLE, JOptionPane.INFORMATION_MESSAGE);
   }
   
   // prompts the user to input how many people are in each subgroup.
   // then it asks for each person and who they gave a gift to the 
   // previous year.
   public static void repeatYear(int size, int subGroups, Random rand, JFrame frame, PrintStream output) {
      String[] names = new String[size];
      String[] gaveTo = new String[size];
      String[] givingTo = new String[size];
      int[] groupSize = new int[subGroups];
      int count = 0;    
      for (int i = 0; i < subGroups; i++) {
         int number = Integer.parseInt(JOptionPane.showInputDialog(null,
          "How many  people are in group #" + (i + 1) + "? ", TITLE, 
          JOptionPane.QUESTION_MESSAGE));
         groupSize[i] = number;
         for (int x = 0; x < number; x++) {
            names[count] = JOptionPane.showInputDialog(null, "Person #" + (x + 1) + ":",
             TITLE,JOptionPane.QUESTION_MESSAGE); 
            gaveTo[count] = JOptionPane.showInputDialog(frame, "Who did this person give a gift to last year?",
             TITLE, JOptionPane.QUESTION_MESSAGE);
            count++;    
         }
         System.out.println();
      }
      calculatePartners(subGroups, names, rand, gaveTo, givingTo, groupSize, frame, output);
   }
   
   // calculates new partners for the new secret santa, checking to 
   // meet all of the conditions set, then outputs results to user
   // and on choosen text file.
   public static void calculatePartners(int subGroups, String[] names, Random rand, 
                        String[] gaveTo, String[] givingTo, 
                        int[] groupSize, JFrame frame, PrintStream output) {
      int difference = 0;
      for (int i = 0; i < subGroups; i++) {
         String[] group = new String[groupSize[i]];
         for (int x = 0; x < group.length; x++) {
            group[x] = names[x + difference]; 
         } 
         for (int y = 0; y < group.length; y++) {
            boolean match = false;
            while (match == false) {
               int random = rand.nextInt(names.length);
               String recipients = names[random];
               if (!Arrays.asList(group).contains(recipients)
                && !gaveTo[y + difference].equals(recipients)
                && !Arrays.asList(givingTo).contains(names[random])){
                  givingTo[y + difference] = names[random];
                  match = true;
               }
            }
         }
         difference += groupSize[i];
      }
      System.out.println();
      int max = names.length;
      String message = "";
      for (int i = 0; i < max; i++) { 
         message += names[i] + " is giving to: " + givingTo[i] + "\n";   
      }
      JOptionPane.showMessageDialog(null, message , TITLE, JOptionPane.INFORMATION_MESSAGE);
      output.println(message);
      System.exit(0);
   }
}
