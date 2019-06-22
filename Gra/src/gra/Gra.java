package gra;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Gra extends JFrame {
    public ArrayList<Integer> pozycjaMuru = new ArrayList<>();
    public ArrayList<Integer> pozycjaRuchu = new ArrayList<>();
    public ArrayList<Integer> pozycjaPol = new ArrayList<>();
    public ArrayList<Integer> pozycjaSkrzyn = new ArrayList<>();
    int pozycjaGracza=0;
    int pozycjaGraczaX=0;
    int pozycjaGraczaY=0;
    int iloscMuru=0;
    int iloscRuchu=0;
    int iloscPol=0;
    int iloscSkrzyn=0;
    int iloscSkrzynZamknietych=0;
    int nRozmiar = wczytaj("test");
    int rozmiar = (int) Math.sqrt(nRozmiar);
    JLabel mapa[] = new JLabel[nRozmiar];
    Skrzynka skrzynka[] = new Skrzynka[iloscSkrzyn];
    Pole pole[] = new Pole [iloscPol];
    Gracz gracz = new Gracz(pozycjaGraczaX,pozycjaGraczaY);
      
    public int wczytaj(String nazwaPlikuTekstowego) throws FileNotFoundException{
        File file = new File("C:\\Users\\HP\\Documents\\NetBeansProjects\\Poprawa\\Gra\\src\\gra\\"+nazwaPlikuTekstowego+".txt");
        Scanner scan = new Scanner(file);
        Scanner scanD = new Scanner(file);
        int ile=0;
        int max=0;
        while(scanD.hasNextLine()){
            String kodD = scanD.nextLine();
            if(kodD.length()>max){
                max = kodD.length();
            }
        }
        int iter = 0;
        while(scan.hasNextLine()){
            String kod = scan.nextLine();
            while(kod.length()!=max){
                kod=kod+"X";
            }
            ile=ile+kod.length();
            System.out.println(kod);
                for(int dl=0;dl<kod.length();dl++){
                    if(String.valueOf(kod.charAt(dl)).equals("X")) pozycjaMuru.add(dl+iter);
                    else if(String.valueOf(kod.charAt(dl)).equals("O")) pozycjaRuchu.add(dl+iter);
                    else if(String.valueOf(kod.charAt(dl)).equals("P")) pozycjaPol.add(dl+iter);
                    else if(String.valueOf(kod.charAt(dl)).equals("S")) pozycjaSkrzyn.add(dl+iter);
                    else if(String.valueOf(kod.charAt(dl)).equals("G")) pozycjaGracza=dl+iter;
                }
            iter = iter + kod.length();
        }
        int kwadrat = (int) Math.sqrt(ile);
        iloscMuru=pozycjaMuru.size();
        iloscRuchu=pozycjaRuchu.size();
        iloscPol=pozycjaPol.size();
        iloscSkrzyn=pozycjaSkrzyn.size();
        pozycjaGraczaX=pozycjaGracza%kwadrat;
        pozycjaGraczaY=pozycjaGracza/kwadrat;
        return ile;
    }
    public Gra() throws FileNotFoundException{
        super("Gra");
        setLocation(600,200);
        System.out.println("-----------------------------------------------------------------------------------------------------------");
        System.out.println("Ilosc Muru = "+iloscMuru);
        System.out.println("Ilosc Ruchu = "+iloscRuchu);
        System.out.println("Ilosc Pol = "+iloscPol);
        System.out.println("Ilosc Skrzyn = "+iloscSkrzyn);
        System.out.println("Gracz jest zawsze = 1");
        System.out.println("Sumujac, mamy "+(iloscMuru+iloscRuchu+iloscPol+iloscSkrzyn+1)+" pol, podczas gdy wczytano "+rozmiar*rozmiar+" pol.");
        System.out.println("PozycjaX = "+pozycjaGraczaX);
        System.out.println("PozycjaY = "+pozycjaGracza/rozmiar);
        System.out.println("PozycjaGracza = "+pozycjaGracza);
        System.out.println("Gracz z definicji = "+gracz);
        System.out.println("-----------------------------------------------------------------------------------------------------------");
        for(int i=0;i<nRozmiar;i++){
            mapa[i] = new JLabel("",SwingConstants.CENTER);
            mapa[i].setOpaque(true);
            add(mapa[i]);
        }
        for(int i=0;i<iloscSkrzyn;i++){
            int x=Integer.parseInt(pozycjaSkrzyn.get(i).toString());
            skrzynka[i] = new Skrzynka(x%rozmiar,x/rozmiar);
            postawSkrzynke(i);
            System.out.println(skrzynka[i]);
        }
        for(int i=0;i<iloscPol;i++){
            int x=Integer.parseInt(pozycjaPol.get(i).toString());
            pole[i] = new Pole(x%rozmiar,x/rozmiar);
            postawPole(i);
            System.out.println(pole[i]);
        }
        addKeyListener(new TAdapter());
        stworzMape(pozycjaMuru,pozycjaRuchu,pozycjaSkrzyn,pozycjaPol);
        postawGracza();
        setLayout(new GridLayout(rozmiar,rozmiar));
        setVisible(true);
        setSize(700,700);
        setFocusable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) throws FileNotFoundException {
        new Gra();
    }
    public void obramuj(ArrayList lista){
        int x;
        for(int i=0;i<lista.size();i++){
            x=Integer.parseInt(lista.get(i).toString());
            zmien("X", Color.black, x);
        }
    }
    public void nastaw(){
        for(int i=0;i<nRozmiar;i++){
            if(mapa[i].getText()!="X"){
                zmien("O", Color.white, i);
            }
        }
    }
    public void stworzMape(ArrayList listaM, ArrayList listaO, ArrayList listaS, ArrayList listaP){
        int x;
        for(int i=0;i<listaO.size();i++){
            x=Integer.parseInt(listaO.get(i).toString());
            zmien("O", Color.white, x);
        }
        for(int i=0;i<listaS.size();i++){
            x=Integer.parseInt(listaS.get(i).toString());
            zmien("S", Color.red, x);
        }
        for(int i=0;i<listaP.size();i++){
            x=Integer.parseInt(listaP.get(i).toString());
            zmien("P", Color.green, x);
        }
        for(int i=0;i<listaM.size();i++){
            x=Integer.parseInt(listaM.get(i).toString());
            zmien("X", Color.black, x);
        }
    }
    public void zmien(String napis, Color kolor, int numer){
        mapa[numer].setText(napis);
        mapa[numer].setBackground(kolor);
        mapa[numer].setForeground(kolor);
    }
    public int dajPozycjeGracza(){
        return (gracz.dajY()*rozmiar)+gracz.dajX();
    }
    public void postawGracza(){
        zmien("G",Color.blue,dajPozycjeGracza());
    }
    public void odmalujBialo(){
        zmien("O",Color.white,dajPozycjeGracza());
    }
    public int dajPozycjeSkrzyni(int numer){
        return (skrzynka[numer].dajY()*rozmiar)+skrzynka[numer].dajX();
    }
    public void przesunSkrzynke(int x, int przesunX, int przesunY){
        int numer=0;
        while(x!=dajPozycjeSkrzyni(numer)){
                numer++;
            }
        zamienMiejscamiSkrzynie(numer, przesunX, przesunY);
        for(int poz=0;poz<pole.length;poz++){
            if(dajPozycjeSkrzyni(numer)==dajPozycjePola(poz)) zatrzymajSkrzynke(numer);
        }
    }
    public void postawSkrzynke(int x){
        zmien("S",Color.red,dajPozycjeSkrzyni(x));
    }
    public int sprawdzSkrzynke(int x){
        int numer=0;
        for(int i=0;i<skrzynka.length;i++) if(x==dajPozycjeSkrzyni(i)) numer = i;
        return numer;
    }
    public void zamienMiejscamiSkrzynie(int x, int kierunekX, int kierunekY){
        zmien("O",Color.white,dajPozycjeSkrzyni(x));
        skrzynka[x].ustawX(skrzynka[x].dajX()+kierunekX);
        skrzynka[x].ustawY(skrzynka[x].dajY()+kierunekY);
        zmien("S",Color.red,dajPozycjeSkrzyni(x));
    }
    public void zatrzymajSkrzynke(int x){
        zmien("U",Color.magenta,dajPozycjeSkrzyni(x));
        iloscSkrzynZamknietych++;
    }
    public int dajPozycjePola(int numer){
        return (pole[numer].dajY()*rozmiar)+pole[numer].dajX();
    }
    public void sprawdzPole(int x){
        for(int i=0;i<pole.length;i++) if(x==dajPozycjePola(i)) postawPole(i);
    }
    public void postawPole(int x){
        zmien("P",Color.green,dajPozycjePola(x));
    }
    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    System.out.println("Lewo");
                    if(mapa[(gracz.dajY()*rozmiar)+(gracz.dajX()-1)].getText().equals("X") || mapa[(gracz.dajY()*rozmiar)+(gracz.dajX()-1)].getText().equals("U")){
                        System.out.println("Nie można iśc w lewo");
                    }
                    else if(mapa[(gracz.dajY()*rozmiar)+(gracz.dajX()-1)].getText().equals("S")){
                        System.out.println("Przestawiamy skrzyneczkę w lewo");
                        if(mapa[(gracz.dajY()*rozmiar)+(gracz.dajX()-2)].getText().equals("S") || mapa[(gracz.dajY()*rozmiar)+(gracz.dajX()-2)].getText().equals("X") || mapa[(gracz.dajY()*rozmiar)+(gracz.dajX()-2)].getText().equals("U"))
                            System.out.println("Nie można iść w lewo");
                        else przesunSkrzynke((gracz.dajY()*rozmiar)+(gracz.dajX()-1), -1,0);
                    }
                    else{
                        odmalujBialo();
                        sprawdzPole((gracz.dajY()*rozmiar)+gracz.dajX());
                        gracz.ustawX(gracz.dajX()-1);
                        postawGracza();
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                   System.out.println("Prawo");
                    if(mapa[(gracz.dajY()*rozmiar)+(gracz.dajX()+1)].getText().equals("X") || mapa[(gracz.dajY()*rozmiar)+(gracz.dajX()+1)].getText().equals("U")){
                        System.out.println("Nie można iśc w prawo");
                    }
                    else if(mapa[(gracz.dajY()*rozmiar)+(gracz.dajX()+1)].getText().equals("S")){
                        System.out.println("Przestawiamy skrzyneczkę w prawo");
                        if(mapa[(gracz.dajY()*rozmiar)+(gracz.dajX()+2)].getText().equals("S") || mapa[(gracz.dajY()*rozmiar)+(gracz.dajX()+2)].getText().equals("X") || mapa[(gracz.dajY()*rozmiar)+(gracz.dajX()+2)].getText().equals("U"))
                            System.out.println("Nie można iść w prawo");
                        else przesunSkrzynke((gracz.dajY()*rozmiar)+(gracz.dajX()+1), 1,0);
                    }
                    else{
                        odmalujBialo();
                        sprawdzPole((gracz.dajY()*rozmiar)+gracz.dajX());
                        gracz.ustawX(gracz.dajX()+1);
                        postawGracza();
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    System.out.println("Gora");
                    if(mapa[((gracz.dajY()-1)*rozmiar)+gracz.dajX()].getText().equals("X") || mapa[((gracz.dajY()-1)*rozmiar)+gracz.dajX()].getText().equals("U")){
                        System.out.println("Nie można iśc w górę");
                    }
                    else if(mapa[((gracz.dajY()-1)*rozmiar)+gracz.dajX()].getText().equals("S")){
                        System.out.println("Przestawiamy skrzyneczkę w górę");
                        if(mapa[((gracz.dajY()-2)*rozmiar)+gracz.dajX()].getText().equals("S") || mapa[((gracz.dajY()-2)*rozmiar)+gracz.dajX()].getText().equals("X") || mapa[((gracz.dajY()-2)*rozmiar)+gracz.dajX()].getText().equals("U"))
                            System.out.println("Nie można iść w górę");
                        else przesunSkrzynke(((gracz.dajY()-1)*rozmiar)+gracz.dajX(), 0,-1);
                    }
                    else{
                        odmalujBialo();
                        sprawdzPole((gracz.dajY()*rozmiar)+gracz.dajX());
                        gracz.ustawY(gracz.dajY()-1);
                        postawGracza();
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    System.out.println("Dol");
                    if(mapa[((gracz.dajY()+1)*rozmiar)+gracz.dajX()].getText().equals("X") || mapa[((gracz.dajY()+1)*rozmiar)+gracz.dajX()].getText().equals("U")){
                        System.out.println("Nie można iśc w dół");
                    }
                    else if(mapa[((gracz.dajY()+1)*rozmiar)+gracz.dajX()].getText().equals("S")){
                        System.out.println("Przestawiamy skrzyneczkę w dół");
                        if(mapa[((gracz.dajY()+2)*rozmiar)+gracz.dajX()].getText().equals("S") || mapa[((gracz.dajY()+2)*rozmiar)+gracz.dajX()].getText().equals("X") || mapa[((gracz.dajY()+2)*rozmiar)+gracz.dajX()].getText().equals("U"))
                            System.out.println("Nie można iść w dół");
                        else przesunSkrzynke(((gracz.dajY()+1)*rozmiar)+gracz.dajX(), 0,1);
                    }
                    else{
                        odmalujBialo();
                        sprawdzPole((gracz.dajY()*rozmiar)+gracz.dajX());
                        gracz.ustawY(gracz.dajY()+1);
                        postawGracza();
                    }
                }
            }
    }
}
