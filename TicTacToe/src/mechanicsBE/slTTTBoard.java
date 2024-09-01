package mechanicsBE;

public class slTTTBoard {

    private char [][] board = new char[3][3];


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
        System.out.println("Play");
    }

    public void printBoard(){
        // for the rows
        for (int i = 0; i < 3; i++){
            // for the columns
            for (int j = 0; j < 3; j++){
                System.out.print(board[i][j] + "\t");
            }
            System.out.println();
        }

    }
}
