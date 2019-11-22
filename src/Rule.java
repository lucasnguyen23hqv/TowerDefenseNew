import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Rule extends JDialog {
    private JButton back;
    private JPanel panel;
    private JLabel label;
    public Rule(JFrame frame,boolean bool){
        super(frame,bool);
        this.setSize(600,300);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setUndecorated(true);
        this.setLayout(null);
        label = new JLabel(new ImageIcon(getImage("res/rule.png")));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setSize(600,300);

        back = new JButton(new ImageIcon(getImage("res/backbutton.png")));
        back.setBorderPainted(false);
        back.setContentAreaFilled(false);
        back.setFocusPainted(false);
        back.setOpaque(false);
        back.addActionListener(actionListener);
        back.setSize(50,25);
        back.setLocation(50,250);
        this.add(label);
        this.add(back);
    }
    private Image getImage(String s){
        File imgFile = new File(s);
        BufferedImage im = null;
        try{
            im = ImageIO.read(imgFile);
        } catch (IOException e) {

        }
        return im;
    }


    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            setVisible(false);
        }
    };

    //public static void main(String[] args) { Rule r = new Rule(); }
}
