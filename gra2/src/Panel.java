
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class Panel extends JComponent {
    protected static JFrame rodzic;
    Color kolor;
    boolean aktywny, end;
    int szer,  wys,  kolum,  wiersz;

    public Panel(JFrame f, Color kolor1, int szer1, int wys1, int kolum1, int wiersz1) {
        rodzic = f;
        kolor = kolor1;
        szer = 8 + 63 * szer1;
        wys = 15 + 63 * wys1;
        kolum = kolum1;
        wiersz = wiersz1;
        aktywny = false;
        end=false;
        setBounds(szer, wys, szer1, wys1);
        setVisible(true);
    }

    protected Color getkolor(){return kolor;}
    protected void zmienaktywnosc(){aktywny=false;}
    protected boolean getaktywnosc(){return aktywny;}
    protected void zbicieP(){kolor=Color.GRAY;}
    protected boolean getend(){return end;}
    protected void zmienend(boolean k){end=k;}
    protected void zmienkolor(Color k){kolor=k;}
    protected void zmienaktywnosc2(boolean k){aktywny=k;}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

}