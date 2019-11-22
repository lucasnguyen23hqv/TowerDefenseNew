import java.awt.*;
import java.util.List;

public interface Tower{
    void setXY(int x, int y);

    int getRange();

    int getDame();

    int getRateOfFire();

    int getPrice();

    List<Bullet> getBullets();

    void fire();

    Enemy target(List<Enemy> enemies);

    double getX();

    double getY();

    void draw(Graphics g);

}
