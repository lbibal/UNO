package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.game.Cards;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * This class tests the Deck class
 */
public class CardsTest{
    
    @Test
    public void initCardsTest(){
        Cards deck = new Cards();
        deck.initCards();
        assertEquals(116,deck.setCards.size());
    }

}
