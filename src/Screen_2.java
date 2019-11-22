import com.sun.org.apache.xerces.internal.impl.xs.SchemaNamespaceSupport;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Screen_2 extends JPanel implements Runnable {
    private GameField gameField;
    public static int myWidth, myHeight;
    private boolean checkfirst = true;
    private JButton[] check = new JButton[6];
    private boolean isPick[] = new boolean[6];
    private int ind[] = new int[3];
    private JButton[] button = new JButton[3];
    private JButton pause, play;
    private JButton confirm;
    private int spawnFrame = 0;
    private JFrame frame;
    public Thread thread=new Thread(this);
    private Shop shop;
    public static Room_2 room;
    public static Save save;
    public static Store store;
    public static int coinage = 1000, health = 100;
    private String state = "pick_game";
    private int count_button = 0;
    private static int count_way = -1;
    public static int an[][] = new int[14][22];
    public static int a[][] = new int[14][22];
    private static int re;
    private JButton[] towers;
    public Screen_2(Frame_2 frame){
        gameField = new GameField();
        this.frame = frame;
        pause = setButton("res/pause.png",50,50);
        pause.setLocation(860,10);
        pause.addActionListener(actionListener2);
        pause.setVisible(false);
        play = setButton("res/continue.png",50,50);
        play.setLocation(860,10);
        play.addActionListener(actionListener2);
        play.setVisible(false);
        confirm = setButton("res/play.png", 250, 70);
        confirm.setLocation(924/2-250/2, 500);
        confirm.addActionListener(actionListener4);
        add(confirm);
        add(pause);
        add(play);
        for(int i = 0; i<button.length; i++){
            button[i]= setButton("res/PutTower.png",50,50);
            button[i].addActionListener(actionListener1);
            button[i].setVisible(false);
            add(button[i]);
        }
        for(int i =0; i<check.length; i++){
            check[i] = setButton("res/bullet1.png",50,50);
            check[i].addActionListener(actionListener3);
            add(check[i]);
        }
        for(boolean ip : isPick) ip = false;
        for(int i : ind) i = -1;
        Random rd = new Random();
        re = rd.nextInt(11);
        towers = new JButton[6];
        for (int i = 0;i < 6;i++){
            towers[i] = new JButton();
        }
        thread.start();
    }
    ActionListener actionListener1 = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(count_button == -1) {
                if (shop == null) {
                    shop = new Shop(frame, true, coinage);
                }
                shop.updateMoney(coinage);
                shop.setVisible(true);
                for (int i = 0; i < button.length; i++) {
                    if (e.getSource() == button[i]) {
                        int num = shop.checkTower();
                        gameField.addTower(room.x[ind[i]], room.y[ind[i]], num);
                        if (shop.getLost() != 0) {
                            button[i].setVisible(false);
                            if (num == 10){
                                towers[i] = setButton("res/normalTower.png",42,42);
                            }
                            else if (num == 15){
                                towers[i] = setButton("res/machineGunTower.png",42,42);;
                            }
                            else if (num == 20){
                                towers[i] = setButton("res/sniperTower.png",42,42);
                            }
                            towers[i].setLocation(room.x[ind[i]],room.y[ind[i]]);
                            towers[i].addActionListener(actionListener5);
                            add(towers[i]);
                        }
                        break;
                    }
                }
                coinage -= shop.getLost();
            }
            else{
                for(int i=0; i<button.length; i++){
                    if(e.getSource() == button[i]){
                        if(i!=count_button-1) {
                            button[count_button - 1].setVisible(false);
                            button[i].setLocation(room.x[ind[count_button - 1]], room.y[ind[count_button - 1]]);
                        }
                        else{
                            button[i].setVisible(false);
                        }
                        check[ind[i]].setVisible(true);
                        isPick[ind[i]] = false;
                        ind[i] = ind[count_button - 1];
                        count_button -= 1;
                        break;
                    }
                }
            }
        }
    };
    ActionListener actionListener2 = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == pause){
                state = "pause_game";
                pause.setVisible(false);
                play.setVisible(true);
            }
            if (e.getSource() == play){
                state = "run_game";
                play.setVisible(false);
                pause.setVisible(true);
            }
        }
    };
    ActionListener actionListener3 = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(count_button == 3) return;
            for(int i=0; i<check.length; i++){
                if(e.getSource() == check[i]){
                    if(!isPick[i]) {
                        check[i].setVisible(false);
                        isPick[i] = true;
                        check_button(i);
                    }
                }
            }
        }
    };
    ActionListener actionListener4 = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (count_button == 3){
                if(e.getSource() == confirm){
                    for(JButton check_jb : check) check_jb.setVisible(false);
                    confirm.setVisible(false);
                    state = "run_game";
                    count_button = -1;
                    pause.setVisible(true);
                    find_min(6,0, a);
                    for(int i=0; i<14; i++){
                        for(int j=0; j<22; j++){
                            System.out.print(an[i][j]+" ");
                        }
                        System.out.print("\n");
                    }
                }
            }
        }
    };
    ActionListener actionListener5 = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0;i < 6;i++){
                if (e.getSource() == towers[i]){
                    int result = JOptionPane.showConfirmDialog(null,
                            "Are U Sure? :((","SELL THIS TOWER???",
                            JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if (result == JOptionPane.YES_OPTION) {
                        towers[i].setEnabled(false);
                        towers[i].setVisible(false);
                        button[i].setVisible(true);
                        coinage += gameField.checkTower(room.x[i], room.y[i]);
                        gameField.removeTower(room.x[i], room.y[i]);
                    }
                    else {
                    }
                    break;
                }
            }
        }
    };
    private void check_button(int id) {
        for (int i = 0; i < 3; i++) {
            if(count_button == i) {
                button[i].setVisible(true);
                button[i].setLocation(room.x[id], room.y[id]);
                ind[i] = id;
                count_button += 1;
                break;
            }
        }
    }
    public JButton setButton(String s, int width, int height){
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
        button.setSize(width,height);

        return button;
    }
    public void setLocation_Tower(){
        for(int i = 0 ; i<check.length; i++) {
            check[i].setLocation(room.x[i], room.y[i]);
        }
    }
    public void define(){
        myHeight = getHeight();
        myWidth = getWidth();
        room =new Room_2();
        save = new Save();
        //store=new Store();
        if(checkfirst) {
            save.loadSave_2(new File("save/mission_special.TD"));
            room.define_putTower();
            setLocation_Tower();
        }
        gameField.define_unique();
    }
    static void find_min(int x, int y, int a[][]){
        if(count_way == re) return;
        if(a[x][y] == 5){
            count_way += 1;
            if(count_way == re) {
                System.out.println(re);
                for (int i = 0; i < 14; i++) {
                    for (int j = 0; j < 22; j++) {
                        if (a[i][j] == 2) an[i][j] = 1;
                        else if(a[i][j] == 5) an[i][j] = 5;
                        else an[i][j] = 0;
                    }
                }
                return;
            }
        }
        if (x + 1 < a.length){
            if(a[x + 1][y] == 1 || a[x + 1][y] == 5) {
                a[x][y] = 2;
                find_min(x + 1, y, a);
                a[x][y] = 1;
            }
        }
        if (x - 1 >= 0) {
            if(a[x - 1][y] == 1 || a[x - 1][y] == 5) {
                a[x][y] = 2;
                find_min(x - 1, y, a);
                a[x][y] = 1;
            }
        }
        if (y + 1 < a[0].length) {
            if(a[x][y + 1] == 1 || a[x][y + 1] == 5) {
                a[x][y] = 2;
                find_min(x, y + 1, a);
                a[x][y] = 1;
            }
        }
        if (y - 1 >= 0){
            if(a[x][y - 1] == 1 || a[x][y - 1] == 5) {
                a[x][y] = 2;
                find_min(x, y - 1, a);
                a[x][y] = 1;
            }
        }
        return;
    }
    public void paintComponent(Graphics g){
        if(checkfirst) {
            define();
            checkfirst = false;
        }
        g.setColor(new Color(124, 88, 55));

        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(new Color(0,0,100));
        g.drawLine(room.block[0][0].x-1,0,room.block[0][0].x-1,room.block[room.worldHeight-1][0].y-1+room.blockSize);//Ve bien ben trai
        g.drawLine(room.block[0][room.worldWidth-1].x+room.blockSize,0,room.block[0][room.worldWidth-1].x+room.blockSize,room.block[room.worldHeight-1][0].y-1+room.blockSize);//Ve bien ben phai
        g.drawLine(room.block[0][0].x,room.block[room.worldHeight-1][0].y+room.blockSize,room.block[0][room.worldWidth-1].x+room.blockSize,room.block[room.worldHeight-1][0].y+room.blockSize);//Ve bien duoi
        room.draw(g);
        gameField.draw(g);
        gameField.draw_unique(g);
    }
    public void physic_game(){
        gameField.unique_physic();
        gameField.towerFire();
        gameField.AI();
        gameField.Check_enemy_live();
    }
    public void run(){
        while(true){
            while (state == "pause_game"){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(state == "run_game"){
                spawnFrame += 1;
                if(spawnFrame % gameField.getSpawnTime_unique() == 0){
                    gameField.enemySpawner_unique(spawnFrame);
                }
                physic_game();
            }
            repaint();
            try{
                Thread.sleep(3);

            }
            catch (Exception e) {}
        }
    }
}
