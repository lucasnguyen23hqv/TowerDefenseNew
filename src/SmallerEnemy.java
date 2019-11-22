import java.awt.*;

public class SmallerEnemy extends Rectangle implements Enemy {
    private int xE, yE;
    private int health = Screen.room.blockSize/2;
    private int save_health = health;
    private int healthSpace = 3, healthHeight = 6;
    private int enemyWalk = 0;
    private int up = 0, down = 1, right = 2, left = 3;
    private int direction =  right;
    private int enemysize = Screen.room.blockSize;
    private boolean ingame = false;
    private int walkFrame = 0, walkSpeed = 10;
    private int id = 2;
    private int spawnTime = 3000;
    private int sum_road = 0;
    public SmallerEnemy(){

    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    public boolean isIngame() {
        return ingame;
    }
    public void deleteEnemy(){
        ingame = false;
    }
    public void decreaseHealth(int amo){
        health -= amo;
        checkDeath();
    }
    public boolean checkDeath(){
        if(health<=0){
            health = 0;
            deleteEnemy();
            increaseCoin();
            return true;
        }
        return false;
    }
    public int getSpawnTime() {
        return spawnTime;
    }
    public void decreasePoint(){
        Screen.health -= 3;
    }
    public void increaseCoin(){
        Screen.coinage += 3;
    }
    public void spawnEnemy(){
        for(int i = 0; i< Screen.room.block.length; i++){
            if(Screen.room.block[i][0].groundID == Value.groundRoad){
                setBounds(Screen.room.block[i][0].x, Screen.room.block[i][0].y, enemysize, enemysize);
                xE = 0;
                yE = i;
            }
        }
        ingame = true;
    }
    public void physic(){
        if(walkFrame >= walkSpeed){
            sum_road += 1;
            if(direction == right){
                x+=1;
            }
            else if(direction == left){
                x-=1;
            }
            else if(direction == up){
                y-=1;
            }
            else{
                y+=1;
            }
            enemyWalk+=1;
            if(enemyWalk == Screen.room.blockSize){
                if(direction == right){
                    xE+=1;
                }
                else if(direction == left){
                    xE-=1;
                }
                else if(direction == up){
                    yE-=1;
                }
                else{
                    yE+=1;
                }
                if(Screen.room.block[yE][xE].groundID == Value.end){
                    deleteEnemy();
                    decreasePoint();
                }
                if(yE+1< Screen.room.worldHeight &&  Screen.room.block[yE+1][xE].groundID == Value.grass && direction != up){
                    direction = down;
                }
                else if(yE-1>=0 && Screen.room.block[yE-1][xE].groundID == Value.grass && direction!= down){
                    direction = up;
                }
                else if(xE+1< Screen.room.worldWidth && Screen.room.block[yE][xE+1].groundID == Value.grass && direction != left) {
                    direction = right;
                }
                else if(xE-1>=0 && Screen.room.block[yE][xE-1].groundID == Value.grass && direction != right) {
                    direction = left;
                }
                enemyWalk = 0;
            }
            walkFrame = 0;
        }
        else{
            walkFrame +=1;
        }
    }
    public boolean isInRange(Tower tower){
        double d = (this.x-tower.getX())*(this.x-tower.getX()) + (this.y-tower.getY())*(this.y-tower.getY());
        if (Math.sqrt(d) <= tower.getRange()) return true;
        return false;
    }
    public int getSum_road() {
        return sum_road;
    }

    public void draw(Graphics g){
        if(ingame){
            g.drawImage(GameStage.tileset_enemy[id], x, y, width, height, null);
            g.setColor(new Color(180, 16, 0));
            g.fillRect(x, y- (healthSpace+healthHeight), save_health*2, healthHeight+1);
            g.setColor(new Color(50,180,50));
            g.fillRect(x, y- (healthSpace+healthHeight), health*2, healthHeight);
            g.setColor(new Color(0,0,0));
            g.drawRect(x, y-healthSpace-healthHeight, health*2, healthHeight);
        }
    }
    public Rectangle getRect() {
        Rectangle rectangle = new Rectangle(x,y,enemysize,enemysize);
        return rectangle;
    }
}
