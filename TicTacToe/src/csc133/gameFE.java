package csc133;

import java.util.Scanner;
import mechanicsBE.slTTTBoard;

public class gameFE {

  private static String currentPlayer;
  private static final char PLAYER_CHAR = 'O';
  private static final char COMPUTER_CHAR = 'X';
  private static String userInput;
  private boolean end = false;
  private boolean gameContinue = true;
  private static slTTTBoard my_board = new slTTTBoard();

  public gameFE(slTTTBoard my_board) {
    gameFE.my_board = my_board;
  }

  // Setter Methods
  public static void setCurrentPlayer(String newPlayer) {
    currentPlayer = newPlayer;
  }

  // Getter Methods
  public static String getCurrentPlayer() {
    return currentPlayer;
  }

  public static char getPLAYER_CHAR() {
    return PLAYER_CHAR;
  }

  public static char getCOMPUTER_CHAR() {
    return COMPUTER_CHAR;
  }

  public static String getUserInput() {
    return userInput;
  }

  private int introduction() {
    Scanner kb = new Scanner(System.in);
    boolean input = false;
    while (!input) {
      System.out.println(
          "Would you like to start? 'n' for machine to start\n'y' if you want to start the game:");
      String tmp = kb.nextLine();
      if (tmp.equalsIgnoreCase("n")) {
        currentPlayer = "Machine";
        input = true;
      } else if (tmp.equalsIgnoreCase("y")) {
        currentPlayer = "Player";
        input = true;
      }else if (tmp.equalsIgnoreCase("q")){
        return slTTTBoard.GAME_QUIT;
      }
    }
    return 0;
  }

  public void startGame() {
    slTTTBoard.printBoard();
    while (gameContinue) {
      end = false;
      slTTTBoard.clearBoard();
      int exit_status = introduction();
      while (!end) {
        if (exit_status == 0){
          exit_status = slTTTBoard.play();
        }

        switch (exit_status) {
          case slTTTBoard.GAME_QUIT:
            System.out.println("\nSorry to see you go; come again!");
            end = true;
            gameContinue = false;
            break;
          case slTTTBoard.GAME_PLAYER:
            System.out.println("\nCongratulations! you have won!");
            end = true;
            break;
          case slTTTBoard.GAME_MACHINE:
            System.out.println("\nSorry, you did not win; try again!");
            end = true;
            break;
          case slTTTBoard.GAME_DRAW:
            System.out.println("\nHey, you almost beat me, let's try again!");
            end = true;
            break;
          default:
            break;
        }
      }
      slTTTBoard.printBoard();
    }
  }

  // Prompts user input and reads it
  public static void userPrompt() {
    Scanner kb = new Scanner(System.in);
    System.out.print(
        "Enter the row and col for your entry - (only) space separated; type 'q' to quit\nEnter row and col numbers (space separated): ");
    userInput = kb.nextLine();
  }
}
