package csc133;

import java.util.Scanner;

public class gameFE {

    private static String currentPlayer;
    private static final char PLAYER_CHAR = 'O';
    private static final char COMPUTER_CHAR = 'X';



    public static String getCurrentPlayer(){
        return currentPlayer;
    }

    public static void setCurrentPlayer(String newPlayer){
        currentPlayer = newPlayer;
    }

    public static char getPLAYER_CHAR(){
        return PLAYER_CHAR;
    }

    public static char getCOMPUTER_CHAR(){
        return COMPUTER_CHAR;
    }

    public static void introduction(){
        Scanner kb = new Scanner(System.in);
        boolean input = false;
        while (!input){
            System.out.println("Would you like to start? 'n' for machine to start\n'y' if you want to start the game:");
            String tmp = kb.nextLine();
            if (tmp.equalsIgnoreCase("n")){
                currentPlayer = "Machine";
                input = true;
            }else if(tmp.equalsIgnoreCase("y")){
                currentPlayer = "Player";
                input = true;
            }

        }
    }

}
