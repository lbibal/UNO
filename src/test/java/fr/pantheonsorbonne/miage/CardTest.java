package fr.pantheonsorbonne.miage;

import fr.pantheonsorbonne.miage.game.Card;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * This class tests the Card class
 */
public class CardTest{

    private static String BLUE = "BLEU";
    private static String GREEN = "VERT";
    private static String RED = "ROUGE";
    private static String YELLOW = "JAUNE";
    
    @Test
    public void getValueTest(){
        Card a = new Card(0,BLUE);
        assertEquals(0,a.getValue());
        Card b = new Card(13,RED);
        assertEquals(13,b.getValue());
    }

    @Test
    public void getStringTest(){
        Card a = new Card(0,BLUE);
        assertEquals("BLEU",a.getColor());
        Card b = new Card(13,RED);
        assertEquals("ROUGE",b.getColor());
    }

    @Test
    public void getFaceTest(){
        Card a = new Card(0,BLUE);
        assertEquals("0 BLEU",a.getFace());
        Card b = new Card(1,GREEN);
        assertEquals("1 VERT",b.getFace());
        Card c = new Card(2,RED);
        assertEquals("2 ROUGE",c.getFace());
        Card d = new Card(3,YELLOW);
        assertEquals("3 JAUNE",d.getFace());
        Card e = new Card(4,BLUE);
        assertEquals("4 BLEU",e.getFace());
        Card f = new Card(5,GREEN);
        assertEquals("5 VERT",f.getFace());
        Card g = new Card(6,RED);
        assertEquals("6 ROUGE",g.getFace());
        Card h = new Card(7,YELLOW);
        assertEquals("7 JAUNE",h.getFace());
        Card i = new Card(8,BLUE);
        assertEquals("8 BLEU",i.getFace());
        Card j = new Card(9,GREEN);
        assertEquals("9 VERT",j.getFace());
        Card k = new Card(10,RED);
        assertEquals("PASSER ROUGE",k.getFace());
        Card l = new Card(11,YELLOW);
        assertEquals("INVERSION JAUNE",l.getFace());
        Card m = new Card(12,BLUE);
        assertEquals("+2 BLEU",m.getFace());
        Card n = new Card(13,GREEN);
        assertEquals("POSE-TOUT VERT",n.getFace());
        Card o = new Card(14,"");
        assertEquals("JOKER",o.getFace());
        Card p = new Card(15,"");
        assertEquals("SUPER JOKER / +4",p.getFace());
        Card q = new Card(16,"");
        assertEquals("MIROIR",q.getFace());
    }

}