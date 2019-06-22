package gra;

public class Skrzynka {
    private int x;
    private int y;
    Skrzynka(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int dajX(){
        return x;
    }
    public int dajY(){
        return y;
    }
    public void ustawX(int a){
        x = a; 
    }
    public void ustawY(int b){
        y = b; 
    }
    public void ustaw(int a, int b){
        x = a;
        y = b;
    }
    @Override
    public String toString(){
        return "Skrzynka: X="+x+", Y="+y;
    }
}
