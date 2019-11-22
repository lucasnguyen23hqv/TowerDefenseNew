import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Screen extends JPanel implements Runnable{
    private GameField gameField;
    private int wave = 1;
    private boolean run_game  = true;
    public Rectangle report = new Rectangle();
    public Thread thread=new Thread(this);
    public static int map=1;
    public static int myWidth, myHeight;
    public static int coinage=10,health=100;
    public static int level = 0;
    public static int run_level = 1;
    public boolean go_game = false;
    public static Room room;
    public static Save save;
    public static Store store;
    public static Point mse=new Point(0,0);
    private Shop shop;
    private JButton[] buttons;
    private JButton pause,play;
    private String state = "run_game";
    private JFrame frame;
    public int spawnFrame = 1;
    private JButton[] towers;
    private int sound = 1;
    private SoundTrack music = new SoundTrack();
    public Screen(Frame frame){
        gameField = new GameField();
        this.frame = frame;
        pause = setButton("res/pause.png");
        pause.setLocation(860,10);
        pause.addActionListener(actionListener2);
        play = setButton("res/continue.png");
        play.setLocation(860,10);
        play.addActionListener(actionListener2);
        play.setVisible(false);
        buttons = new JButton[6];
        for (int i = 0;i < 6;i++){
            buttons[i] = setButton("res/PutTower.png");
            buttons[i].addActionListener(actionListener1);
            add(buttons[i]);
        }
        add(pause);
        add(play);
        towers = new JButton[6];
        for (int i = 0;i < 6;i++){
            towers[i] = new JButton();
        }

        thread.start();
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

        return button;
    }

    ActionListener actionListener1 = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (shop == null){
                shop = new Shop(frame,true,coinage);
            }
            shop.updateMoney(coinage);
            shop.setVisible(true);
            for(int i=0;i < 6; i++) {
                if (e.getSource() == buttons[i]) {
                    int num = shop.checkTower();
                    gameField.addTower(room.x[i], room.y[i],num);
                    if (shop.getLost() != 0) {
                        buttons[i].setVisible(false);
                        if (num == 10){
                            towers[i] = setButton("res/normalTower.png");
                        }
                        else if (num == 15){
                            towers[i] = setButton("res/machineGunTower.png");;
                        }
                        else if (num == 20){
                            towers[i] = setButton("res/sniperTower.png");
                        }
                        towers[i].setLocation(room.x[i],room.y[i]);
                        towers[i].addActionListener(actionListener3);
                        add(towers[i]);
                    }
                    break;
                }
            }
            coinage -= shop.getLost();
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
            for (int i = 0;i < 6;i++){
                if (e.getSource() == towers[i]){
                    int result = JOptionPane.showConfirmDialog(null,
                            "Are U Sure? :((","SELL THIS TOWER???",
                            JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if (result == JOptionPane.YES_OPTION) {
                        towers[i].setEnabled(false);
                        towers[i].setVisible(false);
                        buttons[i].setVisible(true);
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
    public void setLocation_Tower(){
        for(int i =0 ; i<6; i++) {
            buttons[i].setLocation(room.x[i], room.y[i]);
            buttons[i].setVisible(true);
        }
        pause.setVisible(true);
    }
    public void hidden_button(){
        for(JButton jb : buttons){
            jb.setVisible(false);
        }
        for (JButton button : towers){
            button.setVisible(false);
        }
        play.setVisible(false);
        pause.setVisible(false);
    }
    public void define(){
        room =new Room();
        save=new Save();
        store=new Store();
        if(run_level == 1) {
            save.loadSave(new File("save/mission1.TD"));
            room.define_putTower();
            setLocation_Tower();
        }
        else if(run_level == 2){
            if(state != "next_game") {
                save.loadSave(new File("save/mission2.TD"));
                room.define_putTower();
                setLocation_Tower();
            }
        }
        else if(run_level == 3){
            if(state != "next_game") {
                save.loadSave(new File("save/mission3.TD"));
                room.define_putTower();
                setLocation_Tower();
            }
        }
        gameField.define_enemy();
    }
    public void define_all(){
        myWidth=getWidth();
        myHeight=getHeight();
        define();
    }

    protected void paintComponent(Graphics g){
        super.paintComponents(g);
        if (run_level == 1){
            define_all();
            level = 1;
            run_level = -1;
        }
        else if(run_level == 2){
            define_all();
            if(state != "next_game") {
                level = 2;
                run_level = -2;
            }
        }
        else if(run_level == 3){
            define_all();
            if(state != "next_game") {
                level = 3;
                run_level = -3;
            }
        }
        g.setColor(new Color(124, 88, 55));
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(new Color(0,0,100));
        g.drawLine(room.block[0][0].x-1,0,room.block[0][0].x-1,room.block[room.worldHeight-1][0].y-1+room.blockSize);//Ve bien ben trai
        g.drawLine(room.block[0][room.worldWidth-1].x+room.blockSize,0,room.block[0][room.worldWidth-1].x+room.blockSize,room.block[room.worldHeight-1][0].y-1+room.blockSize);//Ve bien ben phai
        g.drawLine(room.block[0][0].x,room.block[room.worldHeight-1][0].y+room.blockSize,room.block[0][room.worldWidth-1].x+room.blockSize,room.block[room.worldHeight-1][0].y+room.blockSize);//Ve bien duoi
        room.draw(g);
        gameField.draw_enemy(g);
        gameField.draw(g);
        store.draw(g);
        if(state == "next_game"){
            draw_report_next(g);
        }
    }
    public void draw_report_next(Graphics g){
        report.setBounds(0,0, getWidth(), getHeight());
        g.drawImage(GameStage.tileset_nextgame[0], 0,0, getWidth(), getHeight(), null);
    }
    public boolean checkGameOver() {
        if(health<=0){
            health = 0;
            return true;
        }
        return false;
    }
    public void run_wave1(){
        if(spawnFrame <= 5*gameField.getSpawnTime_normal()) {
            gameField.enemySpawner_normal(spawnFrame);
        }
        if(spawnFrame > 5* gameField.getSpawnTime_normal() && gameField.Count_enemy() == 0){
            wave += 1;
            spawnFrame = 0;
        }
    }
    public void run_wave2(){
        if(spawnFrame > 2000 && (spawnFrame-2000) <= 5*gameField.getSpawnTime_smaller()){
            gameField.enemySpawner_normal(spawnFrame - 2000);
            if((spawnFrame - 2000) <= 5*gameField.getSpawnTime_smaller()){
                gameField.enemySpawner_smaller(spawnFrame - 2000);
            }
        }
        if(spawnFrame > (5*gameField.getSpawnTime_smaller()+2000) && gameField.Count_enemy() == 0){
            wave += 1;
            spawnFrame = 0;
        }
    }
    public void run_wave3(){
        if(spawnFrame > 2000 && (spawnFrame - 2000) <= 10*gameField.getSpawnTime_smaller()){
            gameField.enemySpawner_normal(spawnFrame - 2000);
            if((spawnFrame - 2000) <= 10*gameField.getSpawnTime_smaller()){
                gameField.enemySpawner_smaller(spawnFrame - 2000);
            }
            if((spawnFrame - 2000) <= 5*gameField.getSpawnTime_tanker()){
                gameField.enemySpawner_tanker(spawnFrame - 2000);
            }
        }
        if(spawnFrame > (10*gameField.getSpawnTime_smaller()+2000) && gameField.Count_enemy() == 0){
            wave += 1;
            spawnFrame = 0;
        }
    }
    public void run_wave4(){
        if(spawnFrame > 2000 && (spawnFrame - 2000) <= 15*gameField.getSpawnTime_smaller()){
            gameField.enemySpawner_normal(spawnFrame - 2000);
            if((spawnFrame - 2000) <= 15*gameField.getSpawnTime_smaller()){
                gameField.enemySpawner_smaller(spawnFrame - 2000);
            }
            if((spawnFrame - 2000) <= 5*gameField.getSpawnTime_tanker()){
                gameField.enemySpawner_tanker(spawnFrame - 2000);
            }
            if((spawnFrame - 2000) <= gameField.getSpawnTime_boss()){
                gameField.enemySpawner_boss(spawnFrame - 2000);
            }
        }
        if(spawnFrame > (15*gameField.getSpawnTime_smaller()+2000) && gameField.Count_enemy() == 0){
            wave += 1;
            spawnFrame = 0;
        }
    }
    public void run_game(){
        if (spawnFrame == 1500) SoundLoader.play("res/Sound/attackwarning.wav");
        if(wave == 1){
            if (sound == 1) {
                music.play();
                sound = 0;
            }
            run_wave1();
        }
        else if(wave == 2){
            if (sound == 1) {
                music.play();
                sound = 0;
            }
            run_wave2();
        }
        else if(wave == 3){
            if (sound == 1) {
                music.play();
                sound = 0;
            }
            run_wave3();
        }
        else if(wave == 4){
            if (sound == 1) {
                music.play();
                sound = 0;
            }
            run_wave4();
        }
    }
    public void return_game(){
        if(wave == 2) {
            music.stop();
            sound = 1;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            state = "next_game";
            wave = 1;
            map += 1;
            run_level = (-run_level)+1;
            spawnFrame = 0;
            health = 100;
            coinage = 10;
            gameField.delete_tower();
            go_game = false;
        }
        return;
    }
    public void physic_game(){
        gameField.enemy_physic();
        gameField.towerFire();
        gameField.AI();
        gameField.Check_enemy_live();
    }
    public void run(){
        while(run_game){
            while (state == "pause_game"){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(state == "next_game"){
                spawnFrame += 4;
                if(spawnFrame > 3000){
                    state = "run_game";
                    spawnFrame = 0;
                    level += 1;
                }
                else{
                    hidden_button();
                    try {
                        Thread.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                continue;
            }
            if (level == 1){
                spawnFrame += 1;
                room.physic();
                run_game();
                return_game();
                physic_game();
            }
            else if(level == 2){
                spawnFrame += 1;
                if(spawnFrame > 500){
                    if(!go_game) spawnFrame = 0;
                    go_game = true;
                }
                if(go_game){
                    room.physic();
                    run_game();
                    return_game();
                    physic_game();
                }
            }
            else if(level == 3) {
                spawnFrame += 1;
                if (spawnFrame > 500) {
                    if (!go_game) spawnFrame = 0;
                    go_game = true;
                }
                if (go_game) {
                    room.physic();
                    run_game();
                    physic_game();
                }
            }
            repaint();
            try{
                Thread.sleep(3);
            }
            catch (Exception e) {}
        }
    }
}
