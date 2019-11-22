import java.awt.*;

public class Bullet extends Rectangle{
    int speed;
    int range;
    int dame;
    Tower tower;
    public Bullet(Tower tower){
        this.speed = 2;
        this.range = tower.getRange();
        this.dame = tower.getDame();
        this.setXY(tower.getX(),tower.getY());
        this.tower = tower;
    }

    public void setXY(double x,double y){
        this.x = (int)x + 21;
        this.y = (int)y + 21;
    }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void move(Enemy enemy, Tower tower){
        if (this.x > enemy.getX() + 21) this.x-=speed;
        if (this.x < enemy.getX() + 21) this.x+=speed;
        if (this.y > enemy.getY() + 21) this.y-=speed;
        if (this.y < enemy.getY() + 21) this.y+=speed;
    }

    public void draw(Graphics g) {
        setBounds(x,y,15,15);
        if(tower instanceof NormalTower){
            g.drawImage(GameStage.tileset_bullet[0], x, y, width, height,null);
        }
        if(tower instanceof MachineGunTower){
            g.drawImage(GameStage.tileset_bullet[1], x, y, width, height,null);
        }
        if(tower instanceof SniperTower){
            g.drawImage(GameStage.tileset_bullet[2], x, y, width, height,null);
        }
    }

    public Rectangle getRect(){
        Rectangle rectangle = new Rectangle(x,y,5,5);
        return rectangle;
    }

}
