// this program generates secret santa partners for a group given by the user, and ensures
// that partners from the previous year are not repeated and subgroup members are not paired
// with eachother.
import java.io.*;
import java.util.*;
public class SecretSanta {
   public static void main(String[] args) {
      Scanner console = new Scanner(System.in);
      Random rand = new Random();
      intro();
      System.out.print("How many people will be in your group? ");
      int size = console.nextInt();
      System.out.println("How many seperate groups do you want to create within the main group");
      System.out.print("that do not give gifts to those inside their group? ");
      int subGroups = console.nextInt();
      System.out.println();
      repeatYear(size, subGroups, rand, console);
   }
   
   // tells user about what the program will do
   public static void intro() {
      System.out.println("This program will generate who everyone will be");
      System.out.println("giving a gift to for Secret Santa this year!");
      System.out.println();
   }
   
   // prompts the user to input how many people are in each subgroup.
   // then it asks for each person and who they gave a gift to the 
   // previous year.
   public static void repeatYear(int size, int subGroups, Random rand, Scanner console) {
      String[] names = new String[size];
      String[] gaveTo = new String[size];
      String[] givingTo = new String[size];
      int[] groupSize = new int[subGroups];
      int count = 0;    
      for (int i = 0; i < subGroups; i++) {
         System.out.print("How many people are in group #" + (i + 1) + "? " );
         int number = console.nextInt();
         groupSize[i] = number;
         for (int x = 0; x < number; x++) {
            System.out.print("Person #" + (x + 1) + ": ");
            names[count] = console.next();
            System.out.print("Who did this person give a gift to last year? ");
            gaveTo[count] = console.next();
            count++;    
         }
         System.out.println();
      }
      calculatePartners(subGroups, names, rand, gaveTo, givingTo, groupSize);
   }
   
   // calculates new partners for the new secret santa, checking to 
   // meet all of the conditions set. 
   public static void calculatePartners(int subGroups, String[] names, Random rand, 
                        String[] gaveTo, String[] givingTo, int[] groupSize) {
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
      for (int i = 0; i < max; i++) {
         System.out.println(names[i] + " is giving to: " + givingTo[i]);   
      }
   }
}