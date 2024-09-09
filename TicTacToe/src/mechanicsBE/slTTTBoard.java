package mechanicsBE;

import csc133.gameFE;

import java.util.ArrayList;
import java.util.Scanner;

public class slTTTBoard {

    private final int BOARD_ROWS = 3;
    private final int BOARD_COLS = 3;
    private char [][] board = new char[BOARD_ROWS][BOARD_COLS];
    private boolean end = false;
    private String userInput;
    private boolean validInput = false;
    private int row;
    private int col;

    private final ArrayList<String> computersMoves = new ArrayList<>();

    private void loadMoves(){
        computersMoves.add("1 1");
        computersMoves.add("0 2");
        computersMoves.add("0 0");
        computersMoves.add("2 2");
        computersMoves.add("2 0");
        computersMoves.add("0 1");
        computersMoves.add("1 2");
        computersMoves.add("1 0");
        computersMoves.add("2 1");
    }




    public slTTTBoard(){
        // for the rows
        for (int row = 0; row < BOARD_ROWS; row++){
            // for the columns
            for (int col = 0; col < BOARD_COLS; col++){
                board[row][col] = '-';
            }
        }
    }

    public void play(){
        gameFE.introduction();

        loadMoves();

        // play loop
        while(!end){
            if (gameFE.getCurrentPlayer().equalsIgnoreCase("Machine")){
                computerTurn();
                // switches current player after turn is over
                gameFE.setCurrentPlayer("Player");
                printBoard();
                if (end){
                    break;
                }
            }else if (gameFE.getCurrentPlayer().equalsIgnoreCase("Player")) {
                int quitGame = playerTurn();
                if (quitGame == 1) {
                    break;
                }
                // switches current player after turn is over
                gameFE.setCurrentPlayer("Machine");

                if (end){
                    printBoard();
                }
            }
        }

    }

    private void computerTurn(){
        userInput = computersMoves.getFirst();
        computersMoves.removeFirst();
        validateInput();
        updateBoard();
        checkContinue();

    }

    private int playerTurn(){
        // reset input
        validInput = false;
        while (!validInput){
            userPrompt();
            validateInput();
        }
        if (end){
            return 1;
        }
        updateBoard();
        checkContinue();
        return 0;
    }

    // Prints the current board
    public void printBoard(){
        // for the rows
        for (int row = 0; row < BOARD_ROWS; row++){
            // for the columns
            for (int col = 0; col < BOARD_COLS; col++){
                System.out.print(board[row][col] + "\t");
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

        computersMoves.remove(userInput);

        // adds the chars to the row and col and checks if they are in range
        row = Character.getNumericValue(userInput.charAt(0));
        col = Character.getNumericValue(userInput.charAt(2));
        if ((row > (BOARD_ROWS - 1)) || (row < 0)){
            System.out.println("Not a valid input");
            return;
        }
        if ((col > (BOARD_COLS - 1)) || (col < 0)){
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
        if (gameFE.getCurrentPlayer().equalsIgnoreCase("Player")){
            board[row][col] = gameFE.getPLAYER_CHAR();
        }else if(gameFE.getCurrentPlayer().equalsIgnoreCase("Machine")){
            board[row][col] = gameFE.getCOMPUTER_CHAR();
        }
    }

    // Check for continue
    private void checkContinue(){
        if (checkWin()){
            end = true;
            return;
        }

        boolean cont = false;
        // for the rows
        for (int row = 0; row < BOARD_ROWS; row++){
            // for the columns
            for (int col = 0; col < BOARD_COLS; col++){
                // if there is an open spot continue is set to true and breaks out of the loops
                if (board[row][col] == '-'){
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

    private boolean checkWin(){
        if (checkHorizontal()){
            return true;
        }else if (checkVertical()){
            return true;
        }else if (checkForwardDiagonal()){
            return true;
        }else{
            return checkReverseDiagonal();
        }
    }

    private boolean checkHorizontal(){
        char currentBox;
        for (int row = 0; row < BOARD_ROWS; row++){
            currentBox = board[row][0];
            if (currentBox == '-'){
                continue;
            }
            for (int col = 1; col < BOARD_COLS; col++){
                if (currentBox != board[row][col]){
                    break;
                }
                if (col == 2){
                    System.out.println("You won horizontally");
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkVertical(){
        char currentBox;
        for (int col = 0; col < BOARD_COLS; col++){
            currentBox = board[0][col];
            if (currentBox == '-'){
                continue;
            }
            for (int row = 1; row < BOARD_ROWS; row++){
                if (currentBox != board[row][col]){
                    break;
                }
                if (row == 2){
                    System.out.println("You won Vertically");
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkForwardDiagonal(){
        char currentBox = board[0][2];
        int col = 1;
        for (int row = 1; row < BOARD_ROWS; row++){
            if (currentBox == '-'){
                break;
            }
            if (currentBox != board[row][col]){
                break;
            }
            if (row == 2){
                System.out.println("You won forward diagonal");
                return true;
            }
            col--;
        }
        return false;
    }

    private boolean checkReverseDiagonal(){
        char currentBox = board[0][0];
        for (int coord = 1; coord < BOARD_ROWS; coord++){
            if (currentBox == '-'){
                break;
            }
            if (currentBox != board[coord][coord]){
                break;
            }
            if (coord == 2){
                System.out.println("You won reverse diagonal");
                return true;
            }
        }
        return false;
    }
}
