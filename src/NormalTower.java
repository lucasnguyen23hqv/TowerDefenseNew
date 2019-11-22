import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NormalTower extends Rectangle implements Tower{
    int rateOfFire = 700;
    int range = 150;
    int dame = 10;
    int price = 10;
    List<Bullet> bullets = new ArrayList<Bullet>();

    public NormalTower(){};
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

    public List<Bullet> getBullets() {
        return bullets;
    }

    @Override
    public int getPrice() {
        return price;
    }

    public void draw(Graphics g) {
        setBounds(x,y,42,42);
        g.drawImage(GameStage.tileset_tower[0], x, y, width, height,null);
    }

    long t = 0;
    public void fire() {
        long T = System.currentTimeMillis();
        if (T - t <= this.rateOfFire){
            return;
        }
        t = T;
        Bullet b = new Bullet(this);
        bullets.add(b);
        SoundLoader.play("res/Sound/normalSound.wav");
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
