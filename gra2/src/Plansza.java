
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;


public class Plansza extends JFrame implements MouseListener {

    private boolean k;
    protected Panel[][] plansza;
    protected boolean[][] pionkiczarne, pionkibiale;
    private JTextField endingtext;
    protected int kolum=0, wiersz=0;


    public Plansza(int wie, int kol, int szer, int wys) {

        pionkibiale= new boolean[wie][kol];
        pionkiczarne= new boolean[wie][kol];
        plansza= new Panel[wie][kol];
        Color w;
        k=true;
        setLayout(new GridLayout(kol,wie));
        setSize(811,843);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if ((i + j) % 2 == 0) {
                    if (i < 2){ pionkibiale[j][i] = true; w=Color.WHITE;}
                    else if (i > 5){ pionkiczarne[j][i] = true; w=Color.BLACK;}
                    else w=Color.GRAY;
                }
                else w=Color.GRAY;
                plansza[j][i] = new Panel(this, w,szer/kol,wys/wie,j,i);
                add(plansza[j][i]);
            }
        }
        addMouseListener(this);
        setVisible(true);
    }

    protected Color getKolor(int i, int j){return plansza[i][j].getkolor();}
    protected void zmienAktywnosc(int i, int j){plansza[i][j].zmienaktywnosc();}
    protected boolean getAktywnosc(int i, int j){return plansza[i][j].getaktywnosc();}
    protected void zbicie(int i, int j){plansza[i][j].zbicieP();}
    protected boolean getk(){return k;}
    protected void zmienk(){k=!k;}
    protected boolean getEnd(int i,int j){return plansza[i][j].getend();}
    protected void zmienEND(int i,int j, boolean k){plansza[i][j].zmienend(k);}
    protected void zmienkolor(int i,int j, Color k){plansza[i][j].zmienkolor(k);}
    protected void zmienAktywnosc2(int i, int j, boolean k){plansza[i][j].zmienaktywnosc2(k);}

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paint(g);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    g2d.setColor(Color.GRAY);
                }
                else
                    g2d.setColor(Color.WHITE);
                g2d.fillRect(10 + 100 * i, 40 + 100 * j,100 , 100);

                if (pionkiczarne[i][j])
                        g2d.setColor(Color.BLACK);
                else if (pionkibiale[i][j])
                        g2d.setColor(Color.WHITE);

                if (pionkiczarne[i][j] || pionkibiale[i][j])
                    g2d.fillOval(25 + 100  * i, 55 + 100 * j, 70, 70);

                if(plansza[i][j].getend()){
                    g2d.setColor(Color.RED);
                    g2d.fillRect(50 + 100 * i, 80 + 100 * j,20 , 20);
                }


            }
        }
    }

    private void usuwanie(int x1, int y1, int x2, int y2, Color kolorbicia){
        zmienkolor(x2,y2,kolorbicia);
        if(kolorbicia==Color.WHITE) {
            pionkibiale[x2][y2] = true;
            pionkiczarne[(x2 - x1) / 2 + x1][(y2 - y1) / 2 + y1] = false;
            pionkibiale[x1][y1] = false;
            zbicie(x1, y1);
            zbicie((x2 - x1) / 2 + x1, (y2 - y1) / 2 + y1);
            if(y2==7) {
                zmienEND(x2,y2,true);
                System.out.println("Biala damka");}
            if(getEnd(x1,y1)){
                zmienEND(x1,y1,false);
                zmienEND(x2,y2,true);
            }
        }
        else if(kolorbicia==Color.BLACK){
            pionkiczarne[x2][y2] = true;
            pionkibiale[(x2 - x1) / 2 + x1][(y2 - y1) / 2 + y1] = false;
            pionkiczarne[x1][y1] = false;
            zbicie(x1, y1);
            zbicie((x2 - x1) / 2 + x1, (y2 - y1) / 2 + y1);
            if(y2==0) {
                zmienEND(x2,y2,true);
                System.out.println("Biala damka");
            }
            if(getEnd(x1,y1)){
                zmienEND(x1,y1,false);
                zmienEND(x2,y2,true);
            }}
        else
            System.out.println("blad");
    }

    private void ruch(int x1, int y1, int x2, int y2, Color kolorruchu){
        if(kolorruchu==Color.WHITE && y2==7) {
            zmienEND(x2,y2,true);
            System.out.println("Biala damka");
        }
        else if(kolorruchu==Color.BLACK && y2==0) {
            zmienEND(x2,y2,true);
            System.out.println("Biala damka");
        }
        if(kolorruchu==Color.WHITE) {
            zmienkolor(x2,y2,Color.WHITE);
            pionkibiale[x2][y2] = true;
            pionkibiale[x1][y1] = false;
            zbicie(x1, y1);
            if(getEnd(x1,y1)){
                zmienEND(x1,y1,false);
                zmienEND(x2,y2,true);
            }
        }
        else if(kolorruchu==Color.BLACK){
            zmienkolor(x2,y2,Color.BLACK);
            pionkiczarne[x2][y2] = true;
            pionkiczarne[x1][y1] = false;
            zbicie(x1, y1);
            if(getEnd(x1,y1)){
                zmienEND(x1,y1,false);
                zmienEND(x2,y2,true);
            }
        }
        else
            System.out.println("blad ruchu");
    }

    private void skrot(int i, int j){
        zmienAktywnosc(kolum,wiersz);
        zmienAktywnosc(i, j);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        kolum=(e.getX()-10)/100;
        wiersz=(e.getY()-40)/100;
        System.out.println(kolum +" " + wiersz);
        System.out.println(getKolor(kolum,wiersz));

        //ustalić wiersz i kolumnę
        klik();
    }

    private void klik()
    {
        boolean zmien=false, wb=false, wc=false;
        for (int j = 0; j < 8; j++)
            for (int i = 0; i < 8; i++){
                if(getKolor(i,j)==Color.WHITE)wb=true;
                if(getKolor(i,j)==Color.BLACK)wc=true;
                if (getAktywnosc(i, j) == true) {
                    zmien = true;
                    System.out.println(" " + i + j + " " + getKolor(i, j));
                    if (getKolor(i, j) == Color.WHITE && j == wiersz - 1 && (i == kolum + 1 || i == kolum - 1)) {
                        System.out.print("ruch białego w dół");
                        ruch(i, j, kolum, wiersz, Color.WHITE);
                        skrot(i,j);
                    } else if (getKolor(i, j) == Color.BLACK && j == wiersz + 1 && (i == kolum + 1 || i == kolum - 1)) {
                        ruch(i, j, kolum, wiersz, Color.BLACK);
                        System.out.print("ruch czarnego w górę");
                        skrot(i,j);
                    } else if (getKolor(i, j) == Color.BLACK && j == wiersz - 1 && (i == kolum + 1 || i == kolum - 1) && getEnd(i,j)) {
                        ruch(i, j, kolum, wiersz, Color.BLACK);
                        skrot(i,j);
                        System.out.print("ruch czarnego w dół");
                    } else if (getKolor(i, j) == Color.WHITE && j == wiersz + 1 && (i == kolum + 1 || i == kolum - 1) && getEnd(i,j)) {
                        ruch(i, j, kolum, wiersz, Color.WHITE);
                        skrot(i,j);
                        System.out.print("ruch białego w górę");
                    } else if (i == kolum && j == wiersz) {
                        System.out.print("klik");
                        zmienk();
                        zmienAktywnosc(kolum,wiersz);
                    } else if (biciegg(i, j) && i == kolum - 2 && j == wiersz - 2 &&((getKolor(i,j)==Color.BLACK && getEnd(i,j))||(getKolor(i,j)==Color.WHITE)) ) {
                        usuwanie(i, j, kolum, wiersz, getKolor(i, j));
                        System.out.print("biciegg");
                        skrot(i,j);
                    } else if (biciedg(i, j) && i == kolum + 2 && j == wiersz - 2 &&((getKolor(i,j)==Color.BLACK && getEnd(i,j))||(getKolor(i,j)==Color.WHITE))) {
                        usuwanie(i, j, kolum, wiersz, getKolor(i, j));
                        System.out.print("biciedg");
                        skrot(i,j);
                    } else if (biciegd(i, j) && i == kolum - 2 && j == wiersz + 2 &&((getKolor(i,j)==Color.WHITE && getEnd(i,j))||(getKolor(i,j)==Color.BLACK))) {
                        usuwanie(i, j, kolum, wiersz, getKolor(i, j));
                        System.out.print("biciegd");
                        skrot(i,j);
                    } else if (biciedd(i, j) && i == kolum + 2 && j == wiersz + 2 &&((getKolor(i,j)==Color.WHITE && getEnd(i,j))||(getKolor(i,j)==Color.BLACK))) {
                        usuwanie(i, j, kolum, wiersz, getKolor(i, j));
                        System.out.print("biciedd");
                        skrot(i,j);
                    } else System.out.println("zle klikniecie");

                }}

        if (!zmien){
            if((getk() && getKolor(kolum,wiersz)==Color.WHITE) || (!getk() && getKolor(kolum,wiersz)==Color.BLACK)) {
                zmienAktywnosc2(kolum,wiersz,!getAktywnosc(kolum,wiersz)) ;
                zmienk();
                System.out.println("Twoj ruch");
            }
            else System.out.println("nie Twoj ruch");
        }

        if(!wb){
            JFrame h = new JFrame();
            h.setSize(1800,980);
            h.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            h.setLayout(new GridLayout());
            endingtext= new JTextField("WYGRALI BIALI");
            h.add(endingtext);
            h.setVisible(true);}
        else if(!wc){
            JFrame h = new JFrame();
            h.setSize(1800,980);
            h.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            h.setLayout(new GridLayout());
            endingtext= new JTextField("WYGRALI CZARNI");
            h.add(endingtext);
            h.setVisible(true);}

        repaint();
    }


    private boolean biciegg(int x, int y){
        if(x<6 && y<6){
            if(getKolor(x+1,y+1)!=getKolor(x,y) || !pionkibiale[x+2][y+2] || !pionkiczarne[x+2][y+2])
                return true;
        }
        return false;
    }

    private boolean biciedg(int x, int y){
        if(x>1 && y<6){
            if(getKolor(x-1,y+1)!=getKolor(x,y) || !pionkibiale[x-2][y+2] || !pionkiczarne[x-2][y+2])
                return true;
        }
        return false;
    }

    private boolean biciegd(int x, int y){
        if(x<6 && y>1){
            if(getKolor(x+1,y-1)!=getKolor(x,y) || !pionkibiale[x+2][y-2] || !pionkiczarne[x+2][y-2])
                return true;
        }
        return false;
    }

    private boolean biciedd(int x, int y){
        if(x>1 && y>1){
            if(getKolor(x-1,y-1)!=getKolor(x,y) || !pionkibiale[x-2][y-2] || !pionkiczarne[x-2][y-2])
                return true;
        }
        return false;
    }


    @Override
    public void mouseExited(MouseEvent e){}
    @Override
    public void mousePressed(MouseEvent e) { }
    @Override
    public void mouseReleased(MouseEvent e) { }
    @Override
    public void mouseEntered(MouseEvent e){}

}