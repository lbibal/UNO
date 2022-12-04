package fr.pantheonsorbonne.miage.game;

import java.util.ArrayList;

public class Cards {

    private static final String[] COLORS = {"BLEU","VERT","ROUGE","JAUNE"};
    private ArrayList<Card> setCards = new ArrayList<Card>();

    public ArrayList<Card> getSetCards(){
        return this.setCards;
    }

    public void initCards(){
        int index = 0;
        for (String color : COLORS){
            setCards.add(index, new Card(0,color));
            index++;
            setCards.add(index, new Card(13,color));
            index++;
            for (int j = 1; j < 13; j++){
                setCards.add(index, new Card(j,color));
                index++;
                setCards.add(index, new Card(j,color));
                index++;
            }
        }
        for (int j = 0; j < 4; j++){
            setCards.add(index, new Card(14,""));
            index++;
        }
        for (int j = 0; j < 4; j++){
            setCards.add(index, new Card(15,""));
            index++;
        }
        for (int j = 0; j < 4; j++){
            setCards.add(index, new Card(16,""));
            index++;
        }
    }

}