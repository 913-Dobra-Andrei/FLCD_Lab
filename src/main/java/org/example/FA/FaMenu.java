package org.example.FA;


import java.io.IOException;
import java.util.Scanner;

public class FaMenu {
    public static void main(String[] args) throws IOException {
        var fa = new FiniteAutomata("src/main/resources/FA.in");
        var scanner = new Scanner(System.in);
        while(true){
            printMenu();
            var option = scanner.nextInt();
            if (option == 0)
                break;
            else if (option == 1)
                System.out.println(fa.getStates());
            else if (option == 2)
                System.out.println(fa.getAlphabet());
            else if (option == 3)
                System.out.println(fa.getTransitions());
            else if (option == 4)
                System.out.println(fa.getInitialState());
            else if (option == 5)
                System.out.println(fa.getFinalStates());
            else if (option == 6){
                scanner.nextLine();

                System.out.print("Enter Sequence:");
                var sequence = scanner.nextLine();
                System.out.println(fa.checkSequence(sequence));
            }
        }
    }

    private static void printMenu() {
        System.out.println("Please choose an option from 0 to 8");
        System.out.println("\t1 - Print all states");
        System.out.println("\t2 - Print alphabet");
        System.out.println("\t3 - Print all transitions");
        System.out.println("\t4 - Print initial state");
        System.out.println("\t5 - Print final states");
        System.out.println("\t6 - Check if sequence is accepted by the FA");
        System.out.print(">");
    }
}
