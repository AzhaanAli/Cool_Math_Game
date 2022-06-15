import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static int[] IDENTITY_ARRAY = new int[]{
            2, 7, 6, 9, 5, 1, 4, 3, 8
    };

    public static void main(String[] args) {

        short[] board = new short[]{
                0, 0, 0,
                0, 0, 0,
                0, 0, 0
        };

        Scanner s = new Scanner(System.in);

        ArrayList<String> playerList = new ArrayList<>();
        ArrayList<String> aiList     = new ArrayList<>();

        ArrayList<String> remainingMoves = new ArrayList<>();
        for(int i = 1; i <= 9; i++) remainingMoves.add(i + "");

        boolean playerTurn = false;
        while(!isWon(board))
        {

            if(playerTurn)
            {
                System.out.println("Enemy list: " + aiList);
                System.out.println("Your list: " + playerList);
                System.out.println("\n");
                System.out.println("Remaining numbers: " + remainingMoves);
                System.out.print("Choose a number from the list: ");

                String choice = s.nextLine();
                int intChoice = Integer.parseInt(choice);
                playerList.add(choice);
                int index = getIndexEquivalent(intChoice);
                board[index] = 1;
                remainingMoves.remove(choice);

                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                System.out.println("You chose " + choice + "!");

            }
            else
            {
                int choice = getBestMove(board);
                String choiceStr = IDENTITY_ARRAY[choice] + "";
                aiList.add(choiceStr);
                board[choice] = 2;
                remainingMoves.remove(choiceStr);

                System.out.println("Ai chose " + choiceStr + "!");
            }



            playerTurn = !playerTurn;

        }



    }

    public static int getBestMove(short[] baseBoard){

        int max = Short.MIN_VALUE;
        ArrayList<Integer> bestMoves = new ArrayList<>();

        for(int i = 0; i < 9; i++)
            if(baseBoard[i] == 0)
            {
                baseBoard[i] = 2;
                int loss = minimax(baseBoard, false);
                baseBoard[i] = 0;

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

    public static int minimax(short[] baseBoard, boolean aiTurn){

        if (isWon(baseBoard))
            return evaluateBoard(baseBoard, aiTurn);

        int minMax = aiTurn?
                Integer.MIN_VALUE:
                Integer.MAX_VALUE;

        for(int i = 0; i < 9; i++)
            if(baseBoard[i] == 0)
            {
                baseBoard[i] = (short) (aiTurn? 2 : 1);
                int loss = minimax(baseBoard, !aiTurn);
                minMax = aiTurn?
                        Math.max(minMax, loss):
                        Math.min(minMax, loss);
                baseBoard[i] = 0;
            }
        return minMax;

    }
    public static short evaluateBoard(short[] board, boolean aiTurn){

        if(isWon(board))
        {
            short count = 1;
            for(short i : board)
                if(i == 0)
                    count++;
            if(aiTurn) count *= -1;
            return count;
        }
        return 0;

    }
    public static boolean isWon(short[] board){

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

    public static void printBoard(short[] board){

        for(int i = 0; i < 9; i++)
        {
            System.out.print(board[i] + " ");
            if ((i + 1) % 3 == 0)
                System.out.println();
        }
    }

}