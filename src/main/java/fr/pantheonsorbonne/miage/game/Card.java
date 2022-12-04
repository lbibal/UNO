package fr.pantheonsorbonne.miage.game;

public class Card{

    private int value;
    private String color;

    public Card(int v, String c){
        value = v;
        color = c;
    }
    
    public int getValue(){
        return this.value;
    }

    public String getColor(){
        return this.color;
    }

    public String getFace(){
        String face = "";
        switch(this.value){
            case 10: face += "PASSER"; 
                break;
            case 11: face += "INVERSION"; 
                break;
            case 12: face += "+2"; 
                break;
            case 13: face += "POSE-TOUT"; 
                break;
            case 14: face += "JOKER"; 
                break;
            case 15: face += "SUPER JOKER / +4"; 
                break;
            case 16: face += "MIROIR"; 
                break;
            default: face += String.valueOf(this.value); 
                break;
        }
        if (!(color.equals(""))){
            face += " "+this.color;
        }
        return face;
    }
    
}