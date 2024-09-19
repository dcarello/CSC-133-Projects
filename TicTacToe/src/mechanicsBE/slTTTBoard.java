package mechanicsBE;

import csc133.gameFE;

public class slTTTBoard {

    public static final int BOARD_ROWS = 3;
    public static final int BOARD_COLS = 3;

    private static char [][] board = new char[BOARD_ROWS][BOARD_COLS];
    private static boolean validInput = false;
    private static int row;
    private static int col;

    public static final int GAME_QUIT = 1;
    public static final int GAME_PLAYER = 2;
    public static final int GAME_MACHINE = 3;
    public static final int  GAME_DRAW = 4;



    public slTTTBoard(){
        // for the rows
        for (int row = 0; row < BOARD_ROWS; row++){
            // for the columns
            for (int col = 0; col < BOARD_COLS; col++){
                board[row][col] = '-';
            }
        }
    }

    // Returns [0: Game Continue, GAME_QUIT, GAME_PLAYER: PLayer won, GAME_MACHINE: Machine won, GAME_DRAW]
    public static int play(){
        int ret = 0;

        // Currently Machines Turn
        if (gameFE.getCurrentPlayer().equalsIgnoreCase("Machine")){
            int computerOutcome = computerTurn();

            if(computerOutcome == 2){
                return GAME_MACHINE;
            }
            else if(computerOutcome == 3){
                return GAME_DRAW;
            }

            // switches current player after turn is over
            gameFE.setCurrentPlayer("Player");
            printBoard();
        }
        // Currently Players Turn
        else if (gameFE.getCurrentPlayer().equalsIgnoreCase("Player")) {
            int playerOutcome = playerTurn();
            if (playerOutcome == 1) {
                return GAME_QUIT;
            }else if(playerOutcome == 2){
                return GAME_PLAYER;
            }
            else if(playerOutcome == 3){
                return GAME_DRAW;
            }
            // switches current player after turn is over
            gameFE.setCurrentPlayer("Machine");

        }
        return ret;
    }

    // Code for players turn. Returns [0: game is continuing, 2: Computer Won, 3: Game is draw]
    private static int computerTurn(){
        // Finds best move using minimax algorithm
        String ComputerInput = findBestMove();
        validateInput(ComputerInput);
        updateBoard();
        int gameContinue = checkContinue();
        if (gameContinue == 0){
            return 2;
        }else if (gameContinue == 1){
            return 3;
        }else{
            return 0;
        }
    }

    // Code for players turn. Returns [0: game is continuing, 1: Game Quit, 2: Player Won, 3: Game is draw]
    private static int playerTurn() {
        // reset input
        validInput = false;
        int quitValue = 0;
        while (!validInput) {
            gameFE.userPrompt();
            quitValue = validateInput(gameFE.getUserInput());
        }
        if (quitValue == 1) {
            return 1;
        }
        updateBoard();
        int gameContinue = checkContinue();
        if (gameContinue == 0){
            return 2;
        }else if (gameContinue == 1){
            return 3;
        }else{
            return 0;
        }
    }

    // Prints the current board
    public static void printBoard(){
        // for the rows
        for (int row = 0; row < BOARD_ROWS; row++){
            // for the columns
            for (int col = 0; col < BOARD_COLS; col++){
                System.out.print(board[row][col] + "\t");
            }
            System.out.println("\n");
        }

    }

    // Checks the board and validates user input. Returns 1 if user wants to quit
    private static int validateInput(String input){
        int ret = 0;
        // checks for the quit command from the user
        if (input.equalsIgnoreCase("q")){
            validInput = true;
            ret = 1;
            return ret;
        }
        // checks for the right amount of characters
        if (input.length() != 3){
            System.out.println("Not a valid input");
            return ret;
        }

        // adds the chars to the row and col and checks if they are in range
        row = Character.getNumericValue(input.charAt(0));
        col = Character.getNumericValue(input.charAt(2));
        if ((row > (BOARD_ROWS - 1)) || (row < 0)){
            System.out.println("Not a valid input");
            return ret;
        }
        if ((col > (BOARD_COLS - 1)) || (col < 0)){
            System.out.println("Not a valid input");
            return ret;
        }
        // validates that there is an open space on the board at the row and col inputted
        if (board[row][col] == '-'){
            validInput = true;
        }else{
            System.out.println("The cell is already marked. Try again.");
        }
        return ret;

    }

    // Updates the board with input
    private static void updateBoard(){
        if (gameFE.getCurrentPlayer().equalsIgnoreCase("Player")){
            board[row][col] = gameFE.getPLAYER_CHAR();
        }else if(gameFE.getCurrentPlayer().equalsIgnoreCase("Machine")){
            board[row][col] = gameFE.getCOMPUTER_CHAR();
        }
    }

    // Check for continue. Returns [-1: for continuing game, 0: Win, 1: For Draw]
    private static int checkContinue(){
        int ret = -1;
        if (checkWin(board)){
            ret = 0;
            return ret;
        }

        if (checkDraw(board)){
            ret = 1;
            return ret;
        }
        return ret;
    }

    // checks the board for a win and returns true or false
    private static boolean checkWin(char[][] functionBoard){
        if (checkHorizontal(functionBoard)){
            return true;
        }else if (checkVertical(functionBoard)){
            return true;
        }else if (checkForwardDiagonal(functionBoard)){
            return true;
        }else{
            return checkReverseDiagonal(functionBoard);
        }
    }

    // Checks for a win on all the horizontal rows
    private static boolean checkHorizontal(char[][] functionBoard){
        char currentBox;
        for (int row = 0; row < BOARD_ROWS; row++){
            // gets current character at beginning of row
            currentBox = functionBoard[row][0];
            // if the first character is a blank, not a win and moves to next row
            if (currentBox == '-'){
                continue;
            }
            for (int col = 1; col < BOARD_COLS; col++){
                // if the character doesn't match not a win and breaks out of loop
                if (currentBox != functionBoard[row][col]){
                    break;
                }
                // if it makes it to the end of loop without breaking out it is a win.
                if (col == 2){
                    return true;
                }
            }
        }
        return false;
    }

    // Checks for a win on all vertical columns
    private static boolean checkVertical(char[][] functionBoard){
        char currentBox;
        for (int col = 0; col < BOARD_COLS; col++){
            // gets current character at beginning of column
            currentBox = functionBoard[0][col];
            // if the first character is a blank, not a win and moves to next column
            if (currentBox == '-'){
                continue;
            }
            for (int row = 1; row < BOARD_ROWS; row++){
                // if the character doesn't match not a win and breaks out of loop
                if (currentBox != functionBoard[row][col]){
                    break;
                }
                // if it makes it to the end of loop without breaking out it is a win.
                if (row == 2){
                    return true;
                }
            }
        }
        return false;
    }

    // Checks for a win on the foward diagonal
    private static boolean checkForwardDiagonal(char[][] functionBoard){
        char currentBox = functionBoard[0][2];
        int col = 1;
        for (int row = 1; row < BOARD_ROWS; row++){
            // if there is a blank not a win and breaks out of loop
            if (currentBox == '-'){
                break;
            }
            // if the character doesn't match not a win and breaks out of loop
            if (currentBox != functionBoard[row][col]){
                break;
            }
            // if it makes it to the end of loop without breaking out it is a win.
            if (row == 2){
                return true;
            }
            col--;
        }
        return false;
    }

    // Checks for a win on the reverse diagonal
    private static boolean checkReverseDiagonal(char[][] functionBoard){
        char currentBox = functionBoard[0][0];
        for (int coord = 1; coord < BOARD_ROWS; coord++){
            // if there is a blank not a win and breaks out of loop
            if (currentBox == '-'){
                break;
            }
            // if the character doesn't match not a win and breaks out of loop
            if (currentBox != functionBoard[coord][coord]){
                break;
            }
            // if it makes it to the end of loop without breaking out it is a win.
            if (coord == 2){
                return true;
            }
        }
        return false;
    }

    private static boolean checkDraw(char[][] functionBoard){
        // for the rows
        for (int row = 0; row < BOARD_ROWS; row++){
            // for the columns
            for (int col = 0; col < BOARD_COLS; col++){
                // if there is an open spot no draw
                if (functionBoard[row][col] == '-'){
                    return false;
                }
            }
        }
        return true;
    }

    public static void clearBoard(){
        // for the rows
        for (int row = 0; row < BOARD_ROWS; row++){
            // for the columns
            for (int col = 0; col < BOARD_COLS; col++){
                board[row][col] = '-';
            }
        }
    }


    /*------------------------------------------------------------------------------------*
    *                                                                                     *
    * Help from Google and YouTube (TheCodingTrain) for understanding minimax algorithm   *
    *                                                                                     *
    *-------------------------------------------------------------------------------------*/
    private static String findBestMove(){
        int bestVal = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};

        // Traverse all cells, evaluate minimax for each empty cell
        for (int row = 0; row < BOARD_ROWS; row++) {
            for (int col = 0; col < BOARD_COLS; col++) {
                if (board[row][col] == '-') {
                    // Make the move
                    board[row][col] = gameFE.getCOMPUTER_CHAR();

                    // Computes possible outcomes for certain move
                    int moveVal = minimax(0, false);

                    // Undo the move
                    board[row][col] = '-';

                    // If the value of the current move is more than the best value, update best
                    if (moveVal > bestVal) {
                        bestMove[0] = row;
                        bestMove[1] = col;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove[0] + " " + bestMove[1];
    }

    // Help from Google for back tracking algorithm
    private static int minimax(int depth, boolean isComputer){
        int score = evaluate(!isComputer);

        // If Computer has won, return score minus depth
        if (score == 10){
            return score - depth;
        }

        // If PLayer has won, return score plus depth
        if (score == -10){
            return score + depth;
        }

        // If no moves left and no winner, it's a tie
        if (checkDraw(board)){
            return 0;
        }

        if (isComputer) {
            int bestVal = Integer.MIN_VALUE;

            // Goes through all positions
            for (int row = 0; row < BOARD_ROWS; row++) {
                for (int col = 0; col < BOARD_COLS; col++) {
                    // Check if cell is empty
                    if (board[row][col] == '-') {
                        // Make the move
                        board[row][col] = gameFE.getCOMPUTER_CHAR();

                        // Call minimax recursively and choose the maximum value
                        bestVal = Math.max(bestVal, minimax(depth + 1, false));

                        // Undo the move
                        board[row][col] = '-';
                    }
                }
            }
            return bestVal;
        } else {
            int bestVal = Integer.MAX_VALUE;

            // Goes through all positions
            for (int row = 0; row < BOARD_ROWS; row++) {
                for (int col = 0; col < BOARD_COLS; col++) {
                    // Check if cell is empty
                    if (board[row][col] == '-') {
                        // Make the move
                        board[row][col] = gameFE.getPLAYER_CHAR();

                        // Call minimax recursively and choose the minimum value
                        bestVal = Math.min(bestVal, minimax(depth + 1, true));

                        // Undo the move
                        board[row][col] = '-';
                    }
                }
            }
            return bestVal;
        }
    }

    // Evaluates current board for win, loss, and draw and assigns scores for each
    private static int evaluate(boolean isComputer){
        if(checkWin(board) && isComputer){
            return +10;
        }else if (checkWin(board) && !isComputer) {
            return -10;
        }else{
            return 0;
        }
    }
}
