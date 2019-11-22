import com.sun.corba.se.impl.orbutil.graph.Graph;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Shop extends JDialog {
    private JButton ntower,mtower,stower,back;
    private JLabel name1,name2,name3,describe1,describe2,describe3;
    private JLabel noti, report, coiNoti,label;
    private int tower = 0;
    private static int money;
    private int lost;

    private Image getImage(String s){
        File imgFile = new File(s);
        BufferedImage im = null;
        try{
            im = ImageIO.read(imgFile);
        } catch (IOException e) {

        }
        return im;
    }

    public Shop(JFrame frame, boolean bool,int coi){
        super(frame,bool);
        money = coi;
        this.setSize(500,300);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setUndecorated(true);
        this.setLayout(null);

        label = new JLabel(new ImageIcon(getImage("res/pick.png")));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setSize(500,300);


        /*noti = new JLabel("PICK A TOWER?");
        noti.setFont(new Font("Courier New", Font.BOLD, 30));
        noti.setForeground(Color.RED);
        noti.setSize(300,50);
        noti.setLocation(126,30);*/

        coiNoti = new JLabel("YOUR COIN: " + money);
        //noti.setFont(new Font("Courier New", Font.BOLD, 25));
        coiNoti.setForeground(Color.YELLOW);
        coiNoti.setSize(300,50);
        coiNoti.setLocation(204,205);

        report = new JLabel("YOU DON'T HAVE ENOUGH MONEY!!! :((");
        report.setForeground(Color.RED);
        report.setSize(300,50);
        report.setLocation(140,225);
        report.setVisible(false);

        ntower = setButton("res/normalTower.png");
        ntower.setLocation(95,75);
        name1 = new JLabel("Normal Tower");
        name1.setForeground(Color.BLUE);
        name1.setSize(100,20);
        name1.setLocation(78,125);
        describe1 = new JLabel("<html> Cost: 10$ <br> Dame: 10 <br> Range: 150 <br> Rate of Fire: 3");
        describe1.setSize(100,80);
        describe1.setLocation(90,145);

        mtower = setButton("res/machineGunTower.png");
        mtower.setLocation(220,75);
        name2 = new JLabel("Machine Gun Tower");
        name2.setForeground(Color.BLUE);
        name2.setSize(150,25);
        name2.setLocation(188,125);
        describe2 = new JLabel("<html> Cost: 15$ <br> Dame: 5 <br> Range: 100 <br> Rate of Fire: 5");
        describe2.setSize(100,80);
        describe2.setLocation(210,145);

        stower = setButton("res/sniperTower.png");
        stower.setLocation(345,75);
        name3 = new JLabel("Sniper Tower");
        name3.setForeground(Color.BLUE);
        name3.setSize(100,20);
        name3.setLocation(330,125);
        describe3 = new JLabel("<html> Cost: 20$ <br> Dame: 10 <br> Range: 400 <br> Rate of Fire: 1");
        describe3.setSize(100,80);
        describe3.setLocation(340,145);

        back = setButton("res/backbutton.png");
        back.setSize(50,25);
        back.setLocation(40,246);
        //this.add(noti);
        this.add(coiNoti);
        this.add(ntower);
        this.add(name1);
        this.add(describe1);
        this.add(mtower);
        this.add(name2);
        this.add(describe2);
        this.add(stower);
        this.add(name3);
        this.add(describe3);
        this.add(back);
        this.add(report);
        this.add(label);

    }

    public JButton setButton(String s){
        File imgFile = new File(s);
        BufferedImage image = null;
        try{
            image = ImageIO.read(imgFile);
        } catch (IOException e) {

        }
        JButton button = new JButton(new ImageIcon(image));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setSize(50,50);
        button.addActionListener(actionListener);
        return button;
    }
    public void updateMoney(int x){
        money = x;
        coiNoti.setText("YOUR COIN: "  + money);
    }

    int checkTower(){
        int x = tower;
        lost = tower;
        tower = 0;
        return x;
    }
     int getLost(){
        return lost;
    }

    ActionListener actionListener = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == ntower){
                if (money >= 10) {
                    tower = 10;
                    money -= 10;
                }
                else report.setVisible(true);
            }
            if (e.getSource() == mtower){
                if (money >= 20) {
                    tower = 15;
                    money -= 15;
                }
                else report.setVisible(true);
            }
            if (e.getSource() == stower){
                if (money >= 20) {
                    tower = 20;
                    money -= 20;
                }
                else report.setVisible(true);
            }
            if (e.getSource() == back){
                report.setVisible(false);
                setVisible(false);
            }
            if (tower != 0) {
                report.setVisible(false);
                setVisible(false);
            }

        }
    };

    //public static void main(String[] args) { Shop store = new Shop();  }
}


