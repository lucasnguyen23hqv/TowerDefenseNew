import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MachineGunTower extends Rectangle implements Tower{
    int rateOfFire = 300;
    int range = 100;
    int dame = 5;
    int price = 15;
    List<Bullet> bullets = new ArrayList<Bullet>();

    public MachineGunTower(){};
    public void setXY(int x,int y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getDame() {
        return dame;
    }

    public int getRange() {
        return range;
    }

    public int getRateOfFire() {
        return rateOfFire;
    }

    public int getPrice() {
        return price;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void draw(Graphics g) {
        setBounds(x,y,42,42);
        g.drawImage(GameStage.tileset_tower[1], x, y, width, height,null);
    }

    long t = 0;

    public void fire() {
        long T = System.currentTimeMillis();
        if (T - t <= 3*this.rateOfFire) {
            return;
        }
        t = T;
        Bullet b = new Bullet(this);
        bullets.add(b);

        SoundLoader.play("res/Sound/machineGunSound.wav");
    }

    public Enemy target(List<Enemy> enemies) {
        int max_road = 0;
        int ind = -1;
        for(int i=0; i<enemies.size(); i++){
            if(enemies.get(i).isInRange(this)){
                if(enemies.get(i).getSum_road()>max_road){
                    ind = i;
                    max_road = enemies.get(i).getSum_road();
                }
            }
        }
        if(ind!=-1) return enemies.get(ind);
        return null;
    }
}
