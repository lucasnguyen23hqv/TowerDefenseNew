import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Frame extends JFrame{
    public static String title="Tower Defense";
    public static Dimension size=new Dimension(924,684);

    public Frame(){
        setTitle(title);
        setSize(size);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();

        setVisible(true);
    }

    public void init(){
        //setLayout(new GridLayout(1,1,0,0));
        Screen screen=new Screen(this);
        setContentPane(screen);
        setLayout(null);
    }

    public static void main(String[] args) {
        Frame f = new Frame();
    }
}
