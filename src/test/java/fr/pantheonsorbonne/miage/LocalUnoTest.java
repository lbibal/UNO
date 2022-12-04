package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.game.Card;
import fr.pantheonsorbonne.miage.game.Cards;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * This class tests the LocalUno class
 */
public class LocalUnoTest extends LocalUno{

    @Test
    public void addCardDeckTest(){
        LocalUno unoTest1 = new LocalUno();
        Cards deck1 = new Cards();
        deck1.initCards();
        unoTest1.deckParty.addAll(deck1.setCards);
        assertEquals(116,unoTest1.deckParty.size());
        unoTest1.addCardDeck(unoTest1.deckPlayer1);
        unoTest1.addCardDeck(unoTest1.deckPlayer2);
        assertEquals(7,unoTest1.deckPlayer1.size());
        assertEquals(7,unoTest1.deckPlayer2.size());
        assertEquals(102,unoTest1.deckParty.size());
    }

    @Test
    public void drawTest(){
        LocalUno unoTest2 = new LocalUno();
        Cards deck2 = new Cards();
        deck2.initCards();
        unoTest2.deckParty.addAll(deck2.setCards);
        unoTest2.addCardDeck(unoTest2.deckPlayer1);
        unoTest2.addCardDeck(unoTest2.deckPlayer2);
        unoTest2.initDrawPile(2);
        assertEquals(102,unoTest2.drawPile.size());
        unoTest2.draw(2,unoTest2.deckPlayer1);
        assertEquals(9,unoTest2.deckPlayer1.size());
        assertEquals(100,unoTest2.drawPile.size());
        unoTest2.draw(4,unoTest2.deckPlayer2);
        assertEquals(11,unoTest2.deckPlayer2.size());
        assertEquals(96,unoTest2.drawPile.size());
    }

    @Test
    public void initAllPlayersAndDecksTest(){
        LocalUno unoTest3 = new LocalUno();
        Cards deck3 = new Cards();
        deck3.initCards();
        unoTest3.deckParty.addAll(deck3.setCards);
        unoTest3.initAllPlayersAndDecks(10);
        assertEquals(10,unoTest3.allPlayers.size());
        for (Map.Entry<Integer, ArrayList<Card>> entry : unoTest3.allPlayers.entrySet()) {
            assertEquals(7,entry.getValue().size());
        }
        assertEquals(46,unoTest3.deckParty.size());
    }

    @Test
    public void initDrawPileTest(){
        LocalUno unoTest4 = new LocalUno();
        Cards deck4 = new Cards();
        deck4.initCards();
        unoTest4.deckParty.addAll(deck4.setCards);
        unoTest4.initDrawPile(2);
        assertEquals(102,unoTest4.drawPile.size());
        Card first = unoTest4.drawPile.get(0);
        assertEquals(false,first.value == 13);
        assertEquals(false,first.value == 15);
        assertEquals(false,first.value == 16);
    } 

    @Test
    public void resetDrawPileTest(){
        LocalUno unoTest5 = new LocalUno();
        Cards deck5 = new Cards();
        deck5.initCards();
        unoTest5.placedCards.addAll(deck5.setCards);
        assertEquals(116,unoTest5.placedCards.size());
        assertEquals("MIROIR",unoTest5.placedCards.get(unoTest5.placedCards.size()-1).getFace());
        unoTest5.resetDrawPile();
        assertEquals(115,unoTest5.drawPile.size());
        assertEquals(1,unoTest5.placedCards.size());
        assertEquals("MIROIR",unoTest5.placedCards.get(unoTest5.placedCards.size()-1).getFace());        
    }

    @Test
    public void canBePlacedTest(){
        LocalUno unoTest6 = new LocalUno();
        unoTest6.currentColor = "ROUGE";
        assertEquals(false,unoTest6.canBePlaced("ROUGE",16));
        assertEquals(true,unoTest6.canBePlaced("ROUGE",5));
        unoTest6.currentValue = 5;
        assertEquals(true,unoTest6.canBePlaced("JAUNE",5));
        assertEquals(true,unoTest6.canBePlaced("",14));
        assertEquals(false,unoTest6.canBePlaced("VERT",0));
    } 

    @Test
    public void penaltiesPlayersTest(){
        LocalUno unoTest7 = new LocalUno();
        Cards deck7 = new Cards();
        deck7.initCards();
        unoTest7.deckParty.addAll(deck7.setCards);
        unoTest7.initAllPlayersAndDecks(2);
        unoTest7.initDrawPile(2);
        unoTest7.currentValue = 12;
        unoTest7.penaltiesPlayers(unoTest7.deckPlayer1);
        assertEquals(9,unoTest7.deckPlayer1.size());
        unoTest7.currentValue = 15;
        unoTest7.penaltiesPlayers(unoTest7.deckPlayer2);
        assertEquals(11,unoTest7.deckPlayer2.size());
    }

    @Test
    public void countCardWithColorTest(){
        LocalUno unoTest8 = new LocalUno();
        // Case tested : One color is majority
        ArrayList<Card> deck1 = new ArrayList<>();
        deck1.add(new Card(0,"VERT"));
        deck1.add(new Card(0,"JAUNE"));
        deck1.add(new Card(1,"JAUNE"));
        assertEquals("JAUNE",unoTest8.countCardWithColor(deck1));
        // Case tested : One color is majority, with no-color cards
        ArrayList<Card> deck2 = new ArrayList<>();
        deck2.add(new Card(0,"ROUGE"));
        deck2.add(new Card(16,""));
        deck2.add(new Card(1,"ROUGE"));
        deck2.add(new Card(1,"JAUNE"));
        assertEquals("ROUGE",unoTest8.countCardWithColor(deck2));
        // Case tested : Equality in the presence of colors
        ArrayList<Card> deck3 = new ArrayList<>();
        deck3.add(new Card(0,"BLEU"));
        deck3.add(new Card(0,"JAUNE"));
        assertEquals("BLEU",unoTest8.countCardWithColor(deck3));
        // Cas tested : Only cards without colors
        ArrayList<Card> deck4 = new ArrayList<>();
        unoTest8.currentColor = "VERT";
        deck4.add(new Card(16,""));
        assertEquals("VERT",unoTest8.countCardWithColor(deck4));
    }

    @Test
    public void placeACardTest(){

        // Case tested : The MIROIR card that doesn't change the current color
        LocalUno unoTest9 = new LocalUno();
        ArrayList<Card> deck1 = new ArrayList<>();
        unoTest9.allPlayers.put(1,deck1);
        unoTest9.currentPlayer = 1;
        unoTest9.currentColor = "ROUGE";
        unoTest9.currentValue = 6;
        deck1.add(new Card(16,""));
        unoTest9.placeACard(0,deck1);
        assertEquals(0,deck1.size());
        assertEquals(1,unoTest9.placedCards.size());
        assertEquals("ROUGE",unoTest9.currentColor);
        assertEquals(16,unoTest9.currentValue);

        // Case tested : Play the POSE-TOUT card of a certain color
        LocalUno unoTest10 = new LocalUno();
        ArrayList<Card> deck2 = new ArrayList<>();
        unoTest10.allPlayers.put(2,deck2);
        unoTest10.currentPlayer = 2;
        unoTest10.currentColor = "ROUGE";
        unoTest10.currentValue = 6;
        deck2.add(new Card(13,"VERT"));
        deck2.add(new Card(12,"VERT"));
        deck2.add(new Card(1,"BLEU"));
        deck2.add(new Card(0,"VERT"));
        unoTest10.placeACard(0,deck2);
        assertEquals(1,deck2.size());
        assertEquals("1 BLEU",deck2.get(0).getFace());
        assertEquals(3,unoTest10.placedCards.size());
        assertEquals("VERT",unoTest10.currentColor);
        assertEquals(12,unoTest10.currentValue);

        // Case tested : Keep a card in hand when you only have cards of the same color and you play the POSE-TOUT card
        LocalUno unoTest11 = new LocalUno();
        ArrayList<Card> deck3 = new ArrayList<>();
        unoTest11.allPlayers.put(3,deck3);
        unoTest11.currentPlayer = 3;
        unoTest11.currentColor = "ROUGE";
        unoTest11.currentValue = 6;
        deck3.add(new Card(13,"VERT"));
        deck3.add(new Card(12,"VERT"));
        unoTest11.placeACard(0,deck3);
        assertEquals(1,deck3.size());
        assertEquals("+2 VERT",deck3.get(0).getFace());
        assertEquals(1,unoTest11.placedCards.size());
        assertEquals("VERT",unoTest11.currentColor);
        assertEquals(13,unoTest11.currentValue);

        // Default case tested : A card with no effect or special action is played
        LocalUno unoTest12 = new LocalUno();
        ArrayList<Card> deck4 = new ArrayList<>();
        unoTest12.allPlayers.put(4,deck4);
        unoTest12.currentPlayer = 4;
        unoTest12.currentColor = "ROUGE";
        unoTest12.currentValue = 6;
        deck4.add(new Card(8,"VERT"));
        unoTest12.placeACard(0,deck4);
        assertEquals(0,deck4.size());
        assertEquals("8 VERT",unoTest12.placedCards.get(0).getFace());
        assertEquals(1,unoTest12.placedCards.size());
        assertEquals("VERT",unoTest12.currentColor);
        assertEquals(8,unoTest12.currentValue);
    }

    @Test
    public void nextPlayerTest(){
        LocalUno unoTest13 = new LocalUno();
        unoTest13.currentPlayer = 1;
        unoTest13.nextPlayer(2);
        assertEquals(2,unoTest13.currentPlayer);
        unoTest13.mustPlayAgain = true;
        nextPlayer(2);
        assertEquals(2,unoTest13.currentPlayer);
        unoTest13.mustPlayAgain = false;
        unoTest13.nextPlayer(2);
        assertEquals(1,unoTest13.currentPlayer);

        unoTest13.currentPlayer = 1;
        unoTest13.nextPlayer(4);
        assertEquals(2,unoTest13.currentPlayer);
        unoTest13.nextPlayer(4);
        assertEquals(3,unoTest13.currentPlayer);
        unoTest13.nextPlayer(4);
        assertEquals(4,unoTest13.currentPlayer);
        unoTest13.nextPlayer(4);
        assertEquals(1,unoTest13.currentPlayer);
        unoTest13.reverse = true;
        unoTest13.nextPlayer(4);
        assertEquals(4,unoTest13.currentPlayer);
        unoTest13.nextPlayer(4);
        assertEquals(3,unoTest13.currentPlayer);
        unoTest13.reverse = false;
        unoTest13.nextPlayer(4);
        assertEquals(4,unoTest13.currentPlayer);
    }
    
    @Test
    public void strategyTest(){

        // Case tested : Player 1 plays a +2 card and player 2 counters with a MIROIR card
        // Thereby, player 1 draws 2 cards
        LocalUno unoTest14 = new LocalUno(); 
        ArrayList<Card> deck1 = new ArrayList<>();
        unoTest14.allPlayers.put(1,deck1);
        ArrayList<Card> deck2 = new ArrayList<>();
        unoTest14.allPlayers.put(2,deck2);
        unoTest14.drawPile.add(new Card(0,"ROUGE"));
        unoTest14.drawPile.add(new Card(1,"BLEU"));
        unoTest14.drawPile.add(new Card(2,"JAUNE"));
        unoTest14.drawPile.add(new Card(3,"VERT"));
        unoTest14.drawPile.add(new Card(4,"ROUGE"));
        unoTest14.drawPile.add(new Card(5,"BLEU"));
        deck1.add(new Card(0,"VERT"));
        deck1.add(new Card(1,"JAUNE"));
        deck2.add(new Card(16,""));
        unoTest14.lastPlayer = 1;
        unoTest14.currentPlayer = 2;
        unoTest14.currentColor = "ROUGE";
        unoTest14.currentValue = 12;
        unoTest14.mustPass = true;
        unoTest14.strategy();
        assertEquals(4,unoTest14.drawPile.size());
        assertEquals(4,deck1.size());
        assertEquals(0,deck2.size());
        assertEquals("ROUGE",unoTest14.currentColor);
        assertEquals(16,unoTest14.currentValue);

        deck1.clear();
        deck2.clear();

        // Case tested : Player 1 plays a +4 card and player 2 hasn't a MIROIR card
        // Thereby, player 2 draws 4 cards
        deck1.add(new Card(0,"VERT"));
        deck2.add(new Card(1,"JAUNE"));
        deck2.add(new Card(2,"BLEU"));
        unoTest14.lastPlayer = 1;
        unoTest14.currentPlayer = 2;
        unoTest14.currentColor = "";
        unoTest14.currentValue = 15;
        unoTest14.mustPass = true;
        unoTest14.strategy();
        assertEquals(0,unoTest14.drawPile.size());
        assertEquals(1,deck1.size());
        assertEquals(6,deck2.size());
        
        deck1.clear();
        deck2.clear();

        // Case tested : Player 2 only has the MIROIR card left
        deck2.add(new Card(16,""));
        unoTest14.lastPlayer = 1;
        unoTest14.currentPlayer = 2;
        unoTest14.currentColor = "ROUGE";
        unoTest14.currentValue = 7;
        unoTest14.strategy();
        assertEquals(0,deck2.size());

        deck1.clear();
        deck2.clear();

        // Case tested : Player 1 plays the 7 VERT card and player 2 plays the 7 JAUNE card
        deck2.add(new Card(7,"JAUNE"));
        unoTest14.lastPlayer = 1;
        unoTest14.currentPlayer = 2;
        unoTest14.currentColor = "VERT";
        unoTest14.currentValue = 7;
        unoTest14.strategy();
        assertEquals(0,deck2.size());
        assertEquals("JAUNE",unoTest14.currentColor);
        assertEquals(7,unoTest14.currentValue);

        deck1.clear();
        deck2.clear();

        // Case tested : Player 2 cannot play, draws and plays the drawn card
        deck2.add(new Card(8,"VERT"));
        unoTest14.drawPile.add(new Card(9,"JAUNE"));
        unoTest14.lastPlayer = 1;
        unoTest14.currentPlayer = 2;
        unoTest14.currentColor = "JAUNE";
        unoTest14.currentValue = 7;
        unoTest14.strategy();
        assertEquals(0,unoTest14.drawPile.size());
        assertEquals(1,deck2.size());
        assertEquals("JAUNE",unoTest14.currentColor);
        assertEquals(9,unoTest14.currentValue);

        deck1.clear();
        deck2.clear();

        // Case tested : Player 2 cannot play, draws and keeps the drawn card
        deck2.add(new Card(8,"VERT"));
        unoTest14.drawPile.add(new Card(1,"ROUGE"));
        unoTest14.lastPlayer = 1;
        unoTest14.currentPlayer = 2;
        unoTest14.currentColor = "JAUNE";
        unoTest14.currentValue = 7;
        unoTest14.strategy();
        assertEquals(0,unoTest14.drawPile.size());
        assertEquals(2,deck2.size());
        assertEquals("JAUNE",unoTest14.currentColor);
        assertEquals(7,unoTest14.currentValue);        
    }
    
}