package gra;

public class Gracz {
    private int x;
    private int y;
    Gracz(int x, int y){
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
        return "X="+x+", Y="+y;
    }
}