import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Frame_2 extends JFrame{
    public static String title="Tower Defense";
    public static Dimension size=new Dimension(924,684);

    public Frame_2(){
        setTitle(title);
        setSize(size);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();

        setVisible(true);
    }

    public void init(){
        Screen_2 screen=new Screen_2(this);
        setContentPane(screen);
        setLayout(null);
    }

    public static void main(String[] args) {
        Frame_2 f = new Frame_2();
    }
}
