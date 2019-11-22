import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameField {
    private int count_normal = 0;
    private int count_tanker = 0;
    private int count_smaller = 0;
    private int count_unique = 0;
    private List<Tower> towers = new ArrayList<Tower>();
    private List<Enemy> enemies = new ArrayList<Enemy>();
    private Enemy[] enemies_normal = new NormalEnemy[100];
    private Enemy[] enemies_tanker = new TankerEnemy[100];
    private Enemy[] enemies_smaller = new SmallerEnemy[100];
    private Enemy[] enemies_unique = new UniqueEnemy[100];
    private BossEnemy enmemy_boss;
    private JPanel panel;
    private Random rd = new Random();
    public void draw_unique(Graphics g){
        for(int i=0; i<enemies_unique.length; i++){
            if(enemies_unique[i].isIngame()){
                enemies_unique[i].draw(g);
            }
        }
    }
    public void draw_enemy(Graphics g){
        for(int i=0; i<enemies_normal.length; i++){
            if(enemies_normal[i].isIngame()){
                enemies_normal[i].draw(g);
            }
        }
        for(int i=0; i<enemies_tanker.length; i++){
            if(enemies_tanker[i].isIngame()){
                enemies_tanker[i].draw(g);
            }
        }
        for(int i=0; i<enemies_smaller.length; i++){
            if(enemies_smaller[i].isIngame()){
                enemies_smaller[i].draw(g);
            }
        }
        if(enmemy_boss.isIngame()){
            enmemy_boss.draw(g);
        }
    }
    public void define_enemy(){
        for(int i=0; i<enemies_normal.length; i++){
            enemies_normal[i] = new NormalEnemy();
        }
        for(int i=0; i<enemies_tanker.length; i++){
            enemies_tanker[i] = new TankerEnemy();
        }
        for(int i=0; i<enemies_smaller.length; i++){
            enemies_smaller[i] = new SmallerEnemy();
        }
        enmemy_boss = new BossEnemy();
    }
    public void define_unique(){
        for(int i=0; i<enemies_unique.length; i++){
            enemies_unique[i] = new UniqueEnemy();
        }
    }
    public void enemySpawner_normal(int spawnFrame) {
        if (count_normal < enemies_normal.length) {
            if (spawnFrame % enemies_normal[0].getSpawnTime() == 0) {
                enemies_normal[count_normal].spawnEnemy();
                enemies.add(enemies_normal[count_normal]);
                count_normal += 1;
            }
        }
    }
    public void enemySpawner_tanker(int spawnFrame) {
        if (count_tanker < enemies_tanker.length) {
            if (spawnFrame % enemies_tanker[0].getSpawnTime() == 0) {
                enemies_tanker[count_tanker].spawnEnemy();
                enemies.add(enemies_tanker[count_tanker]);
                count_tanker += 1;
            }
        }
    }
    public void enemySpawner_smaller(int spawnFrame) {
        if (count_smaller < enemies_smaller.length) {
            if (spawnFrame % enemies_smaller[0].getSpawnTime() == 0) {
                enemies_smaller[count_smaller].spawnEnemy();
                enemies.add(enemies_smaller[count_smaller]);
                count_smaller += 1;
            }
        }
    }
    public void enemySpawner_boss(int spawnFrame){
        if(spawnFrame == enmemy_boss.getSpawnTime()){
            enmemy_boss.spawnEnemy();
            enemies.add(enmemy_boss);
        }
    }
    public void enemySpawner_unique(int spawnFrame){
        if (count_unique < enemies_unique.length) {
            if (spawnFrame % enemies_unique[0].getSpawnTime() == 0) {
                enemies_unique[count_unique].spawnEnemy();
                enemies.add(enemies_unique[count_unique]);
                count_unique += 1;
            }
        }
    }
    public void unique_physic(){
        for(int i=0; i<enemies_unique.length; i++){
            if(enemies_unique[i].isIngame()){
                enemies_unique[i].physic();
            }
        }
    }
    public void enemy_physic(){
        for(int i=0; i<enemies_normal.length; i++) {
            if (enemies_normal[i].isIngame()) {
                enemies_normal[i].physic();
            }
        }
        for(int i=0; i<enemies_tanker.length; i++){
            if (enemies_tanker[i].isIngame()){
                enemies_tanker[i].physic();
            }
        }
        for(int i=0; i<enemies_smaller.length; i++){
            if (enemies_smaller[i].isIngame()){
                enemies_smaller[i].physic();
            }
        }
        if(enmemy_boss.isIngame()){
            enmemy_boss.physic();
        }
    }
    public void draw(Graphics g) {
        for (Tower tower : towers) {
            //tower.draw(g);
            for (Bullet bullet : tower.getBullets()) bullet.draw(g) ;
        }
    }

    int checkTower(int x, int y){
        for (Tower tower : towers){
            if (tower.getX() == x && tower.getY() == y)
                return tower.getPrice();
        }
        return 0;
    }

    void removeTower(int x,int y) {
        for (Tower tower : towers) {
            if (tower.getX() == x && tower.getY() == y) {
                towers.remove(tower);
                break;
            }
        }
    }

    void addTower(int x, int y, int type){
        Tower tower = null;
        switch (type){
            case 0:
                return;
            case 10:
                tower = new NormalTower();
                break;
            case 15:
                tower = new MachineGunTower();
                break;
            case 20:
                tower = new SniperTower();
                break;
        }
        tower.setXY(x,y);
        towers.add(tower);
    }

    void towerFire(){
        for (Tower tower : towers) {
            if (tower.target(enemies) != null) {
                tower.fire();
            }
        }
    }
    public int getSpawnTime_normal(){
        return enemies_normal[0].getSpawnTime();
    }
    public int getSpawnTime_tanker(){
        return enemies_tanker[0].getSpawnTime();
    }
    public int getSpawnTime_smaller(){
        return enemies_smaller[0].getSpawnTime();
    }
    public int getSpawnTime_boss(){
        return enmemy_boss.getSpawnTime();
    }
    public int getSpawnTime_unique(){
        return enemies_unique[0].getSpawnTime();
    }
    void AI() {
        if (enemies.isEmpty()) return;
        for (Tower tower : towers) {
            if (tower.target(enemies) != null) {
                Enemy enemy = tower.target(enemies);
                for (int i = tower.getBullets().size()-1; i >= 0; i--) {
                    if (tower.getBullets().size() == 0) break;
                    tower.getBullets().get(i).move(enemy,tower);
                    Rectangle rectangle = enemy.getRect().intersection(tower.getBullets().get(i).getRect());
                    if (!rectangle.isEmpty()) {
                        enemy.decreaseHealth(tower.getDame());
                        tower.getBullets().remove(i);
                        if (enemy.checkDeath() == true) {
                            for (Tower abc : towers) {
                                if (abc.target(enemies) != null && abc.target(enemies).equals(enemy)) {
                                    abc.getBullets().clear();
                                }
                                if (abc.target(enemies) == null) abc.getBullets().clear();
                            }
                            enemy.deleteEnemy();
                            if (rd.nextInt()%2 == 0) SoundLoader.play("res/Sound/maledeath.wav");
                            else SoundLoader.play("res/Sound/femaledeath.wav");
                        }
                    }
                }
            }
        }
    }
    public void Check_enemy_live(){
        int n = enemies.size();
        if(n == 0) return;
        int i =0;
        while(i < n){
            if(!enemies.get(i).isIngame()){
                n -= 1;
                enemies.remove(i);
            }
            else{
                i+=1;
            }
        }
    }
    public void delete_tower(){
        int i =0;
        while(i < towers.size()){
            towers.remove(i);
        }
    }
    public int Count_enemy(){
        return enemies.size();
    }
}