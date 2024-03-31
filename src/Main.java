/**
 * This is a class that plays the GUI version of the Elevens game.
 * See accompanying documents for a description of how Elevens is played.
 */
import java.util.*;
public class Main {

//    /**
//     * Plays the GUI version of Elevens.
//     * @param args is not used.
//     */
//    public static void main(String[] args) {
//        Board board = new ElevensBoard();
//        CardGameGUI gui = new CardGameGUI(board);
//        gui.displayGame();
//    }
    public static void main(String[] args) {
        ElevensBoard board = new ElevensBoard();
        System.out.print("Welcome to Eleven!");
        System.out.println(board.deckSize() + " Undealt cards remaining");
        while(!board.gameIsWon()){
            System.out.println("Here's your cards: ");
            System.out.println(board);
            Scanner sc = new Scanner(System.in);
            int size = 0;
            String[] inAr = null;
            while((size!=3) && (size!=2)){
                System.out.println("Enter two or three cards to remove: ");
                inAr = sc.nextLine().split(" ");
                size = inAr.length;
            }
            List<Integer> indList = new ArrayList<Integer>();
            System.out.print("You chose: ");
            for(String val: inAr){
                System.out.print(" " + board.cardAt(Integer.valueOf(val) - 1));
                indList.add(Integer.valueOf(val) - 1);
            }
            System.out.println();
            if(board.isLegal(indList)){
                board.replaceSelectedCards(indList);
                System.out.println(board.deckSize() + " undealt cards remaining");
            }
            if(!board.anotherPlayIsPossible() && !board.gameIsWon()){
                System.out.println("Dang! No play is possible! Try Again!");
                break;
            }
        }
        if(board.gameIsWon()){
            System.out.println("You Won!");
        }
    }



}