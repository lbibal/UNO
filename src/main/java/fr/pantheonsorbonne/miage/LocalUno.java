package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.game.*;
import java.util.*;

/**
 * This class implements the UNO game locally
 */
public class LocalUno extends UnoEngine {

    // All attributes are protected in order to perform the tests in the LocalUnoTest class
    protected ArrayList<Card> deckParty = new ArrayList<>();
    protected ArrayList<Card> deckPlayer1 = new ArrayList<>();
    protected ArrayList<Card> deckPlayer10 = new ArrayList<>();
    protected ArrayList<Card> deckPlayer2 = new ArrayList<>();
    protected ArrayList<Card> deckPlayer3 = new ArrayList<>();
    protected ArrayList<Card> deckPlayer4 = new ArrayList<>();
    protected ArrayList<Card> deckPlayer5 = new ArrayList<>();
    protected ArrayList<Card> deckPlayer6 = new ArrayList<>();
    protected ArrayList<Card> deckPlayer7 = new ArrayList<>();
    protected ArrayList<Card> deckPlayer8 = new ArrayList<>();
    protected ArrayList<Card> deckPlayer9 = new ArrayList<>();
    protected ArrayList<Card> drawPile = new ArrayList<>();
    protected ArrayList<Card> placedCards = new ArrayList<>();
    protected boolean mustPass = true;
    protected boolean mustPlayAgain = false;
    protected boolean reverse = false;
    protected int currentPlayer = 1;
    protected int currentValue;
    protected int lastPlayer = 1;
    protected int winner = 11;
    protected Map<Integer, ArrayList<Card>> allPlayers = new HashMap<>();
    protected Random rand = new Random();
    protected String currentColor;    
    


    public static void main(String[] args){
        LocalUno uno = new LocalUno();
        System.out.println("Bienvenue dans la version locale du jeu Uno. Débutons une partie !");
        uno.play();
    }



    /**
     * This function asks the user to enter on the keyboard the number of local players desired
     * @return The number of local players
    */
    public static int numberOfLocalPlayers(){
        Scanner sc = new Scanner(System.in);
        int nb = -1;
        while (nb < 2 || nb > 10){
            System.out.println("Veuillez entrer un nombre de joueurs (entre 2 et 10) : ");
            nb = sc.nextInt();
        }
        sc.close();
        return nb;
    }



    /**
     * This function adds 7 cards, randomly drawn from all the cards, to the deck specified in the parameter
     * @param deck - The deck to fill
     */
    public void addCardDeck(ArrayList<Card> deck){
        for (int i = 0; i < 7; i++){
            int index = rand.nextInt(deckParty.size());
            deck.add(deckParty.get(index));
            deckParty.remove(deckParty.get(index));
        }
    }
 
    
    
    /**
     * This function adds a number of cards, drawn from the draw pile, to a specified deck
     * @param numberOfCards - The number of cards to draw
     * @param deck - The deck in which we will add cards
     */
    public void draw(int numberOfCards, ArrayList<Card> deck){
        for (int i = 0; i < numberOfCards; i++){
            int index = rand.nextInt(drawPile.size());
            deck.add(drawPile.get(index));
            drawPile.remove(drawPile.get(index));
            if (drawPile.isEmpty()){
                resetDrawPile();
            }
        }
    }

    
    
    /**
     * This function initializes the players
     * We add, for each of the players, 7 cards
     * @param numberPlayers - The number of local players to initialize
     */
    public void initAllPlayersAndDecks(int numberPlayers){
        addCardDeck(deckPlayer1);
        allPlayers.put(1, deckPlayer1);
        addCardDeck(deckPlayer2);
        allPlayers.put(2, deckPlayer2);
        if (numberPlayers >= 3){
            addCardDeck(deckPlayer3);
            allPlayers.put(3, deckPlayer3);
        }
        if (numberPlayers >= 4){
            addCardDeck(deckPlayer4);
            allPlayers.put(4, deckPlayer4);
        }
        if (numberPlayers >= 5){
            addCardDeck(deckPlayer5);
            allPlayers.put(5, deckPlayer5);
        }
        if (numberPlayers >= 6){
            addCardDeck(deckPlayer6);
            allPlayers.put(6, deckPlayer6);
        }
        if (numberPlayers >= 7){
            addCardDeck(deckPlayer7);
            allPlayers.put(7, deckPlayer7);
        }
        if (numberPlayers >= 8){
            addCardDeck(deckPlayer8);
            allPlayers.put(8, deckPlayer8);
        }
        if (numberPlayers >= 9){
            addCardDeck(deckPlayer9);
            allPlayers.put(9, deckPlayer9);
        }
        if (numberPlayers == 10){
            addCardDeck(deckPlayer10);
            allPlayers.put(10, deckPlayer10);
        }
    }

    
    
    /**
     * This function initializes the draw pile, with the undealt cards (between 46 and 102 depending on the number of players)
     * We make sure that the first card in the draw pile is not a POSE-TOUT card, SUPER JOKER card or MIROIR card
     * @param numberPlayers - The number of local players
     */
    public void initDrawPile(int numberPlayers){
        for (int i = 0; i < (116-numberPlayers*7); i++){
            int index = rand.nextInt(deckParty.size());
            if (i == 0){
                while (deckParty.get(index).getValue() == 13 || deckParty.get(index).getValue() == 15 || deckParty.get(index).getValue() == 16){
                    index = rand.nextInt(deckParty.size());
                }
            }
            drawPile.add(deckParty.get(index));
            deckParty.remove(deckParty.get(index));
        }
    }
    
    
    
    /**
     * This function resets the draw pile when it is empty, omitting the last card placed, which will be kept for the rest of the game.
     */
    public void resetDrawPile(){
        System.out.println("Il n'y a plus de cartes à piocher.");
        System.out.println("On crée une nouvelle pioche en mélangeant les cartes déjà posées.");
        Card lastCardPosed = placedCards.get(placedCards.size()-1);
        placedCards.remove(placedCards.get(placedCards.size()-1));
        while (!(placedCards.isEmpty())){
            int index = rand.nextInt(placedCards.size());
            drawPile.add(placedCards.get(index));
            placedCards.remove(placedCards.get(index));
        }
        System.out.println("Cependant, la dernière carte jouée est conservée.");
        System.out.println("Il s'agit de la carte "+lastCardPosed.getFace()+".");
        placedCards.add(lastCardPosed);
    }

    
    
    /**
     * This function checks that a card can be placed (same color or same number or same symbol)
     * @param color - Color of the card to check
     * @param value - Value of the card to check
     * @return true if the considered card can be placed, false otherwise
     */
    public boolean canBePlaced(String color, int value){
        return (value != 16 && (color.equals(currentColor) || value == currentValue || color.equals("")));
    }

    
    
    /**
     * This function checks if penalties (+2 or +4 cards) should apply to the current player
     * If true, the current player draws 2 or 4 cards
     * @param deck - The deck of current player
     */
    public void penaltiesPlayers(ArrayList<Card> deck){
        if (currentValue == 12){
            draw(2, deck);
            System.out.println("Joueur "+currentPlayer+" pioche 2 cartes.");
        } else if (currentValue == 15){
            draw(4, deck);
            System.out.println("Joueur "+currentPlayer+" pioche 4 cartes.");
        }
    }

    
    
    /**
     * This function counts the number of cards of each color in a player's deck
     * By default, the color red is returned if there are that many cards of each color
     * @param deck - The player's deck to check
     * @return the most present color in the deck
     */
    public String countCardWithColor(ArrayList<Card> deck){
        int[] countColor = {0,0,0,0};
        for (Card card : deck){ 
            if (card.getColor().equals("ROUGE")){
                countColor[0] += 1;
            } else if (card.getColor().equals("JAUNE")){
                countColor[1] += 1;
            } else if (card.getColor().equals("BLEU")){
                countColor[2] += 1;
            } else if (card.getColor().equals("VERT")){
                countColor[3] += 1;
            }
        }
        if (countColor[0] > countColor[1] && countColor[0] > countColor[2] && countColor[0] > countColor[3]){
            return "ROUGE";
        } else if (countColor[1] > countColor[0] && countColor[1] > countColor[2] && countColor[1] > countColor[3]){
            return "JAUNE";
        } else if (countColor[2] > countColor[0] && countColor[2] > countColor[1] && countColor[2] > countColor[3]){
            return "BLEU";
        } else if (countColor[3] > countColor[0] && countColor[3] > countColor[1] && countColor[3] > countColor[2]){
            return "VERT";
        } else {
            for (Card card : deck){
                if (!(card.getColor().equals(""))){
                    return card.getColor();
                }
            }
            return currentColor;
        }
    }

    
    
    /**
     * This function simulates the placement of a card by a player and all the possible additional actions which result from it (choice of a color, etc).
     * @param index - The position of the card in the deck
     * @param deck - The deck of player
     */
    public void placeACard(int index, ArrayList<Card> deck){
        // If the card to be placed is the MIROIR card, the current color is not changed
        if (deck.get(index).getValue() != 16){
            currentColor = deck.get(index).getColor();
        }
        currentValue = deck.get(index).getValue();
        placedCards.add(deck.get(index));
        System.out.println("Joueur "+currentPlayer+" joue la carte "+deck.get(index).getFace()+".");
        allPlayers.get(currentPlayer).remove(deck.get(index));
        // If the placed card is the POSE-TOUT card, the player can place all his cards of the corresponding color directly afterwards
        if (currentValue == 13 && !(allPlayers.get(currentPlayer).isEmpty())){
            ArrayList<Card> poseTout = new ArrayList<>();
            ArrayList<Card> poseTout2 = new ArrayList<>();
            // If ever the player has a +2 card of the corresponding color, we make sure that he places it last so that the sanction applies to the next player
            for (Card card : allPlayers.get(currentPlayer)){
                if (card.getColor().equals(currentColor)){
                    if (card.getValue() != 12){
                        poseTout.add(card);
                    } else {
                        poseTout2.add(card);
                    }
                }
            }
            poseTout.addAll(poseTout2);
            // The following instruction is performed in order to always keep a card in hand
            if (poseTout.size() == allPlayers.get(currentPlayer).size()){
                poseTout.remove(poseTout.get(0));
            }
            if (!(poseTout.isEmpty())){
                for (Card card : poseTout){
                    currentValue = card.getValue();
                    placedCards.add(card);
                    System.out.println("Joueur "+currentPlayer+" joue la carte "+card.getFace()+".");
                    allPlayers.get(currentPlayer).remove(card);
                }
            }
        }
        if (currentValue == 10 || currentValue == 12 || currentValue == 15){
            mustPass = true; // This boolean will order the next player to skip their turn
        }
        if (currentValue == 11){
            reverse = !reverse; // Reversal of the direction of play
            mustPlayAgain = true; // This boolean is useful if there are only two players
        }
        if ((currentValue == 14 || currentValue == 15) && !(allPlayers.get(currentPlayer).isEmpty())){
            currentColor = countCardWithColor(allPlayers.get(currentPlayer));
            System.out.println("Joueur "+currentPlayer+" choisit la couleur "+currentColor+".");
        }
        if (allPlayers.get(currentPlayer).size() == 1){
            System.out.println("UNO dit joueur "+currentPlayer);
        }
    }

    
    
    /**
     * This function determines who is the next player
     * @param numberPlayers - The number of local players
     */
    public void nextPlayer(int numberPlayers){
        // If there are only two players and the INVERSION card has been placed, the current player plays again
        if (mustPlayAgain && numberPlayers == 2){
            mustPlayAgain = false;
        }
        // Normal sense
        else if (!reverse){
            if (currentPlayer < numberPlayers){
                currentPlayer++;
            } else {
                currentPlayer = 1;
            }
        // Reverse sense
        } else {
            if (currentPlayer > 1){
                currentPlayer--;
            } else {
                currentPlayer = numberPlayers;
            }
        }
    }

    
    
    /**
     * This function implements the strategy that each player has.
     */
    public void strategy(){
        // If the card placed by the previous player is a PASSER card, the current player skips his turn
        if (currentValue == 10  && mustPass){
            System.out.println("Joueur "+currentPlayer+" passe son tour.");
            mustPass = false;
        // If the card placed by the previous player is a +2 card or SUPER JOKER / +4 card, ...
        } else if ((currentValue == 12 || currentValue == 15) && mustPass){
            // we check if the current player has a MIRROR card
            ArrayList<Card> returnPenalty = new ArrayList<>();
            for (Card card : allPlayers.get(currentPlayer)){
                if (card.getValue() == 16){
                    returnPenalty.add(card);
                }
            }
            // If he has one, he places it ...
            if (!(returnPenalty.isEmpty())){
                int penalty = 2;
                if (currentValue == 15){penalty = 4;}
                mustPass = false;
                placeACard(0, returnPenalty);
                // and the previous player draws 2 or 4 cards
                if (lastPlayer != 0){
                    System.out.println("Joueur "+lastPlayer+" pioche "+penalty+" cartes.");
                    draw(penalty,allPlayers.get(lastPlayer));
                }
            // Otherwise he draws 2 or 4 cards
            } else {
                penaltiesPlayers(allPlayers.get(currentPlayer));
                System.out.println("Joueur "+currentPlayer+" passe son tour.");
                mustPass = false;
            }
        // If the two previous cases are not verified, the current player plays
        } else {
            // If the current player's last card is the MIROIR card, he may place it
            if (allPlayers.get(currentPlayer).size() == 1 && allPlayers.get(currentPlayer).get(0).getValue() == 16){
                placeACard(0, allPlayers.get(currentPlayer));
            // In all other cases, the player attempts to play
            } else {
                ArrayList<Card> possibility = new ArrayList<>();
                for (Card card : allPlayers.get(currentPlayer)){
                    if (canBePlaced(card.getColor(), card.getValue())){
                        possibility.add(card);
                    }
                }
                if (!(possibility.isEmpty())){
                    int index = rand.nextInt(possibility.size());
                    placeACard(index, possibility);
                } else {
                    allPlayers.get(currentPlayer).add(drawPile.get(0));
                    System.out.println("Joueur "+currentPlayer+" pioche 1 carte.");
                    if (canBePlaced(drawPile.get(0).getColor(), drawPile.get(0).getValue())){
                        placeACard(allPlayers.get(currentPlayer).size()-1, allPlayers.get(currentPlayer));
                    }
                    drawPile.remove(drawPile.get(0));
                }
            }
        }
    }

    
    
    /**
     * This function is the engine of the local game.
     */
    public void play(){
        // Initialization phase
        Cards cards = new Cards();
        cards.initCards();
        deckParty.addAll(cards.getSetCards());
        int numberPlayers = numberOfLocalPlayers();
        System.out.println("L'hôte distribue 7 cartes à chacun des "+numberPlayers+" joueurs.");
        initAllPlayersAndDecks(numberPlayers);
        System.out.println("Les cartes non distribuées constituent la pioche.");
        initDrawPile(numberPlayers);
        System.out.println("La première carte de la pioche est retournée.");
        placedCards.add(drawPile.get(0));
        drawPile.remove(drawPile.get(0));
        System.out.println("Il s'agit de la carte "+placedCards.get(0).getFace()+".");
        currentColor = placedCards.get(0).getColor();
        currentValue = placedCards.get(0).getValue();
        if (currentValue == 11){
            currentPlayer = numberPlayers;
        } else if (currentValue == 14){
            currentColor = countCardWithColor(deckPlayer1);
            System.out.println("Joueur 1 choisit alors la couleur "+currentColor+".");
        }
        // Game phase
        endParty:
        while (true){
            if (drawPile.isEmpty()){
                resetDrawPile();
            }
            strategy();
            if (allPlayers.get(currentPlayer).isEmpty()){
                winner = currentPlayer;     
                break endParty;
            }
            lastPlayer = currentPlayer;
            nextPlayer(numberPlayers);
        }
        System.out.println("Joueur "+winner+" a gagné la partie !");
    }
    
}