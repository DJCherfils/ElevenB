import java.util.List;
import java.util.ArrayList;

/**
 * The ElevensBoard class represents the board in a game of Elevens.
 */
public class ElevensBoard{

    /**
     * The size (number of cards) on the board.
     */
    private static final int BOARD_SIZE = 9;

    /**
     * The ranks of the cards for this game to be sent to the deck.
     */
    private static final String[] RANKS = {"A","10"};

    //{"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    /**
     * The suits of the cards for this game to be sent to the deck.
     */
    private static final String[] SUITS =
            { "♠", "♥", "♦", "♣" };

    /**
     * The values of the cards for this game to be sent to the deck.
     */
    private static final int[] POINT_VALUES =
            {1, 10};
//{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 0, 0, 0};
    /**
     * Flag used to control debugging print statements.
     */
    private static final boolean I_AM_DEBUGGING = false;

    private Card[] cards;
    private Deck deck;
    /**
     * Creates a new <code>ElevensBoard</code> instance.
     */
    public ElevensBoard() {
        cards = new Card[BOARD_SIZE];
        deck = new Deck(RANKS, SUITS, POINT_VALUES);
        if (I_AM_DEBUGGING){
            System.out.println(deck);
            System.out.println("---------------");
        }
        deck.shuffle();
        dealMyCards();
    }

    public void newGame(){
        deck.shuffle();
        dealMyCards();
    }

    public int size(){
        return cards.length;
    }

    public boolean isEmpty(){
        for(int k = 0; k < cards.length; k ++){
            if(cards[k] != null){
                return false;
            }
        }
        return true;
    }

    public void deal(int k){
        cards[k] = deck.deal();
    }

    public int deckSize(){
        return deck.size();
    }

    public Card cardAt(int k){
        return cards[k];
    }

    public void replaceSelectedCards(List<Integer> selectedCards){
        for(Integer k : selectedCards){
            deal(k.intValue());
        }
    }

    public List<Integer> cardIndexes(){
        List<Integer> selected = new ArrayList<Integer>();
        for(int k = 0; k < cards.length; k++){
            if(cards[k] != null){
                selected.add(k);
            }
        }
        return selected;
    }

    public String toString(){
        String s = " ";
        for(int i = 0; i < cards.length; i++){
            s += (i + 1)+ "   ";
        }
        s +="\n";
        for(int k = 0; k < cards.length; k++){
            s = s + cards[k] + " ";
        }
        s += "\n";

        return s;
    }

    public boolean gameIsWon(){
        if(deck.isEmpty()){
            for(Card c: cards){
                if(c != null){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isLegal(List<Integer> selectedCards) {
        if (selectedCards.size() == 2) {
            return containsPairSum11(selectedCards);
        } else if (selectedCards.size() == 3) {
            return containsJQK(selectedCards);
        } else {
            return false;
        }
    }

    /**
     * Determine if there are any legal plays left on the board.
     * In Elevens, there is a legal play if the board contains
     * (1) a pair of non-face cards whose values add to 11, or (2) a group
     * of three cards consisting of a jack, a queen, and a king in some order.
     * @return true if there is a legal play left on the board;
     *         false otherwise.
     */
    public boolean anotherPlayIsPossible() {
        List<Integer> cIndexes = cardIndexes();
        return containsPairSum11(cIndexes) || containsJQK(cIndexes);
    }

    private void dealMyCards(){
        for(int k = 0; k < cards.length; k++){
            cards[k] = deck.deal();
        }
    }

    /**
     * Check for an 11-pair in the selected cards.
     * @param selectedCards selects a subset of this board.  It is list
     *                      of indexes into this board that are searched
     *                      to find an 11-pair.
     * @return true if the board entries in selectedCards
     *              contain an 11-pair; false otherwise.
     */
    private boolean containsPairSum11(List<Integer> selectedCards) {
        for (int sk1 = 0; sk1 < selectedCards.size(); sk1++) {
            int k1 = selectedCards.get(sk1).intValue();
            for (int sk2 = sk1 + 1; sk2 < selectedCards.size(); sk2++) {
                int k2 = selectedCards.get(sk2).intValue();
                if (cardAt(k1).pointValue() + cardAt(k2).pointValue() == 11) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check for a JQK in the selected cards.
     * @param selectedCards selects a subset of this board.  It is list
     *                      of indexes into this board that are searched
     *                      to find a JQK group.
     * @return true if the board entries in selectedCards
     *              include a jack, a queen, and a king; false otherwise.
     */
    private boolean containsJQK(List<Integer> selectedCards) {
        boolean foundJack = false;
        boolean foundQueen = false;
        boolean foundKing = false;
        for (Integer kObj : selectedCards) {
            int k = kObj.intValue();
            if (cardAt(k).rank().equals("J")) {
                foundJack = true;
            } else if (cardAt(k).rank().equals("Q")) {
                foundQueen = true;
            } else if (cardAt(k).rank().equals("K")) {
                foundKing = true;
            }
        }
        return foundJack && foundQueen && foundKing;
    }
}