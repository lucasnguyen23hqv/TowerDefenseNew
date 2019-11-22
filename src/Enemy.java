import java.awt.*;

public interface Enemy{
    int getSpawnTime();
    boolean isIngame();
    void spawnEnemy();
    void physic();
    void draw(Graphics g);
    boolean checkDeath();
    void decreaseHealth(int amo);
    void deleteEnemy();
    void decreasePoint();
    void increaseCoin();
    boolean isInRange(Tower tower);
    double getX();
    double getY();
    Rectangle getRect();
    int getSum_road();
}
