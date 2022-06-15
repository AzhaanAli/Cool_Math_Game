import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static short[] IDENTITY_ARRAY = new short[]{
            2, 7, 6, 9, 5, 1, 4, 3, 8
    };

    public static void main(String[] args) {

        startGame();

    }

    public static int getBestMove(byte[] board){

        int max = Short.MIN_VALUE;
        ArrayList<Integer> bestMoves = new ArrayList<>();

        for(int i = 0; i < 9; i++)
            if(board[i] == 0)
            {
                board[i] = 2;
                int loss = minimax(board, false);
                board[i] = 0;

                if(loss > max)
                {
                    max = loss;
                    bestMoves.clear();
                    bestMoves.add(i);
                }
                else if(loss == max)
                    bestMoves.add(i);
            }

        return bestMoves.get((int)(Math.random() * bestMoves.size()));
    }

    public static int minimax(byte[] board, boolean aiTurn){

        if (isWon(board)) return evaluateBoard(board, aiTurn);
        int minMax = aiTurn? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for(int i = 0; i < 9; i++)
            if(board[i] == 0)
            {
                board[i] = (byte) (aiTurn? 2 : 1);
                int loss = minimax(board, !aiTurn);
                minMax = aiTurn?
                        Math.max(minMax, loss):
                        Math.min(minMax, loss);
                board[i] = 0;
            }
        return minMax;

    }

    public static short getZeros(byte[] board){

        short  count = 0;
        for(byte i : board)
            if(i == 0)
               count++;
        return count;

    }

    public static short evaluateBoard(byte[] board, boolean aiTurn){

        if(!isWon(board)) return 0;
        short count = (short) (getZeros(board) + 1);
        if(aiTurn) count *= -1;
        return count;

    }

    public static boolean isWon(byte[] board){

        for(int i = 0; i < 3; i++)
        {
            if(
                board[i] != 0 &&
                board[i + 3] == board[i] &&
                board[i + 6] == board[i]
            ) return true;

            int times3 = i * 3;

            if(
                board[times3] != 0 &&
                board[times3 + 1] == board[times3] &&
                board[times3 + 2] == board[times3]
            ) return true;
        }

        if(
            board[0] != 0 &&
            board[4] == board[0] &&
            board[8] == board[0]
        ) return true;

        return board[2] != 0 &&
               board[4] == board[2] &&
               board[6] == board[2];

    }

    public static int getIndexEquivalent(int num){

        for(int i = 0; i < 9; i++)
            if(IDENTITY_ARRAY[i] == num)
                return i;
        return -1;

    }

    public static void wipeConsole(){

        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

    }

    public static void startGame(){

        byte[] board = new byte[9];

        Scanner s = new Scanner(System.in);

        wipeConsole();
        System.out.println("The goal is to choose 3 numbers from the list that add to 15.");
        System.out.print("If you understand the instructions, press enter.");
        s.nextLine();
        wipeConsole();

        ArrayList<String> playerList = new ArrayList<>();
        ArrayList<String> aiList     = new ArrayList<>();

        ArrayList<String> remainingMoves = new ArrayList<>();
        for(int i = 1; i <= 9; i++) remainingMoves.add(i + "");

        boolean playerTurn = Math.random() > .5;
        while(!isWon(board))
        {

            if(playerTurn)
            {
                System.out.println("Enemy list: " + aiList);
                System.out.println("Your list: " + playerList);
                System.out.println("\nRemaining numbers: " + remainingMoves);

                String choice = "";
                while(!remainingMoves.contains(choice))
                {
                    System.out.print("Choose a number from the list: ");
                    choice = s.nextLine();
                }
                int intChoice = Integer.parseInt(choice);
                playerList.add(choice);
                int index = getIndexEquivalent(intChoice);
                board[index] = 1;
                remainingMoves.remove(choice);

                wipeConsole();
                System.out.println("You chose " + choice + "!");
            }
            else
            {
                int choice = getBestMove(board);
                String choiceStr = IDENTITY_ARRAY[choice] + "";
                aiList.add(choiceStr);
                board[choice] = 2;
                remainingMoves.remove(choiceStr);
                System.out.println("Ai chose " + choiceStr + "!\n");
            }

            if (isWon(board))
            {
                System.out.print("\nGAME OVER: ");
                System.out.print(playerTurn? "You" : "Ai");
                System.out.println(" gathered 3 numbers that add to 15.");
            }
            else if(getZeros(board) == 0)
                System.out.print("\nGAME OVER: You failed to gather 3 numbers that add to 15.");

            playerTurn = !playerTurn;
        }

        System.out.println("Enemy list: " + aiList);
        System.out.println("Your list: " + playerList);

    }

}