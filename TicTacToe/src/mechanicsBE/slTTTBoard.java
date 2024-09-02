package mechanicsBE;

import java.util.Scanner;

public class slTTTBoard {

    private char [][] board = new char[3][3];
    private boolean end = false;
    private String userInput;
    private boolean validInput = false;
    private int row;
    private int col;


    public slTTTBoard(){

        // for the rows
        for (int i = 0; i < 3; i++){
            // for the columns
            for (int j = 0; j < 3; j++){
                board[i][j] = '-';
            }
        }
    }

    public void play(){
        // play loop
        while(!end){
            // reset input
            validInput = false;

            while (!validInput){
                userPrompt();
                validateInput();
            }
            if (end){
                break;
            }
            updateBoard();
            checkContinue();
            printBoard();
        }

    }

    // Prints the current board
    public void printBoard(){
        // for the rows
        for (int i = 0; i < 3; i++){
            // for the columns
            for (int j = 0; j < 3; j++){
                System.out.print(board[i][j] + "\t");
            }
            System.out.println("\n");
        }

    }

    // Prompts user input and reads it
    private void userPrompt(){
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter the row and col for your entry - space (only) separated: Enter row and col numbers (space separated): ");
        userInput = kb.nextLine();

    }

    // Checks the board and validates user input
    private void validateInput(){
        // checks for the quit command from the user
        if (userInput.equalsIgnoreCase("q")){
            validInput = true;
            end = true;
            System.out.println("Goodbye - come again!");
            return;
        }
        // checks for the right amount of characters
        if (userInput.length() != 3){
            System.out.println("Not a valid input");
            return;
        }
        // adds the chars to the row and col and checks if they are in range
        row = Character.getNumericValue(userInput.charAt(0));
        col = Character.getNumericValue(userInput.charAt(2));
        if (row > 2 || row < 0){
            System.out.println("Not a valid input");
            return;
        }
        if (col > 2 || col < 0){
            System.out.println("Not a valid input");
            return;
        }
        // validates that there is an open space on the board at the row and col inputted
        if (board[row][col] == '-'){
            validInput = true;
        }else{
            System.out.println("The cell is already marked. Try again.");
        }


    }

    // Updates the board with input
    private void updateBoard(){
        board[row][col] = 'P';
    }

    // Check for continue
    private void checkContinue(){
        boolean cont = false;
        // for the rows
        for (int i = 0; i < 3; i++){
            // for the columns
            for (int j = 0; j < 3; j++){
                // if there is an open spot continue is set to true and breaks out of the loops
                if (board[i][j] == '-'){
                    cont = true;
                    break;
                }
            }
            if (cont){
                break;
            }
        }
        if (!cont){
            end = true;
        }

    }
}
