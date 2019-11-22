import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameStage  extends JFrame {
    private ImageIcon backGround = null;
    private JButton play, help, exit;
    private JButton screen1, screen2;
    private Rule rule;
    private JPanel panel;
    private  JFrame frame;
    public static Image[] tileset_res=new Image[100];
    public static Image[] tileset_enemy = new Image[4];
    public static Image[] tileset_tower = new Image[3];
    public static Image[] tileset_map = new Image[5];
    public static Image[] tileset_bullet = new Image[3];
    public static Image[] tileset_nextgame = new Image[1];
    public GameStage(){
        this.setSize(900,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setUndecorated(true);

        this.setBackGround("res/intro_game.png");
        panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                super.paintComponent(g2d);
                if(backGround!=null) {
                    g.drawImage(backGround.getImage(), 0, 0, getWidth(), getHeight(), null);
                }
            }
        };
        this.setContentPane(panel);
        this.setLayout(null);

        play = setImageButton("res/play.png");
        play.setLocation(50,450);

        help = setImageButton("res/help.png");
        help.setLocation(300,450);

        exit = setImageButton("res/exit.png");
        exit.setLocation(550,450);

        screen1 = setImageButton("res/classic.png");
        screen1.setLocation(180, 230);
        screen1.setSize(246,157);
        screen1.setVisible(false);

        screen2 = setImageButton("res/special.png");
        screen2.setLocation(460, 230);
        screen2.setSize(246,157);
        screen2.setVisible(false);

        this.add(play);
        this.add(help);
        this.add(exit);
        this.add(screen1);
        this.add(screen2);

        frame = this;
        SoundLoader.play("res/Sound/gameStart.wav");
        this.setVisible(true);
        tileset_res[0]=new ImageIcon("res/cell.png").getImage();
        tileset_res[1]=new ImageIcon("res/health.png").getImage();
        tileset_res[2]=new ImageIcon("res/coin.png").getImage();
        tileset_res[6] = new ImageIcon("res/tieude.png").getImage();
        //tileset_res[3] = new ImageIcon("res/background.png").getImage();
        tileset_res[4] = new ImageIcon("res/background2.png").getImage();
        tileset_res[5] = new ImageIcon("res/background3.jpg").getImage();
        tileset_map[0]=new ImageIcon("res/map1.png").getImage();
        tileset_map[1]=new ImageIcon("res/map2.png").getImage();
        tileset_map[2]=new ImageIcon("res/map3.png").getImage();
        tileset_map[3]=new ImageIcon("res/map4.png").getImage();
        tileset_enemy[0] = new ImageIcon("res/enemy1.png").getImage();
        tileset_enemy[1] = new ImageIcon("res/enemy2.png").getImage();
        tileset_enemy[2] = new ImageIcon("res/enemy3.png").getImage();
        tileset_enemy[3] = new ImageIcon("res/enemy4.png").getImage();
        tileset_tower[0] = new ImageIcon("res/normalTower.png").getImage();
        tileset_tower[1] = new ImageIcon("res/machineGunTower.png").getImage();
        tileset_tower[2] = new ImageIcon("res/sniperTower.png").getImage();
        tileset_bullet[0] = new ImageIcon("res/bullet1.png").getImage();
        tileset_bullet[1] = new ImageIcon("res/bullet2.png").getImage();
        tileset_bullet[2] = new ImageIcon("res/bullet3.png").getImage();
        tileset_nextgame[0] = new ImageIcon("res/next_level.png").getImage();

    }

    public void setBackGround(String s) {
        File imgFile = new File(s);
        BufferedImage image = null;
        try{
            image = ImageIO.read(imgFile);
        } catch (IOException e) {

        }
        this.backGround = new ImageIcon(image);
    }

    public JButton setImageButton(String s){
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
        button.setSize(250,70);
        button.addActionListener(actionListener);
        return button;
    }
    ActionListener actionListener = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == play){
                play.setVisible(false);
                help.setVisible(false);
                exit.setVisible(false);
                screen1.setVisible(true);
                screen2.setVisible(true);
            }
            if (e.getSource() == help){
                if (rule == null) {
                    rule = new Rule(frame, true);
                }
                rule.setVisible(true);
            }
            if (e.getSource() == exit){
                System.exit(0);
            }
            if(e.getSource() == screen1){
                SoundLoader.stop();
                setVisible(false);
                Frame frame = new Frame();
            }
            if(e.getSource() == screen2){
                SoundLoader.stop();
                setVisible(false);
                Frame_2 frame2 = new Frame_2();
            }

        }
    };


    public static void main(String[] args) {
        GameStage gameStage = new GameStage();
    }
}