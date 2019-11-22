import java.awt.*;

public class UniqueEnemy extends Rectangle implements Enemy {
    private int xE, yE;
    private int shield = 21;
    private int health = Screen_2.room.blockSize;
    private int save_health = health;
    private int healthSpace = 3, healthHeight = 6;
    private int enemyWalk = 0;
    private int up = 0, down = 1, right = 2, left = 3;
    private int direction = right;
    private int enemysize = Screen_2.room.blockSize;
    private boolean ingame = false;
    private int walkFrame = 0, walkSpeed = 5;
    private int spawnTime = 2000;
    private int id = 0;
    private int sum_road = 0;
    public UniqueEnemy(){

    }
    public boolean isIngame() {
        return ingame;
    }
    public void decreasePoint(){
        Screen_2.health -= 3;
    }
    public void increaseCoin() {
        Screen_2.coinage += 3;
    }
    public void deleteEnemy(){
        ingame = false;
    }
    public void decreaseHealth(int amo){
        if(shield > 0){
            shield -= amo;
            if(shield <=0){
                shield = 0;
                health -= amo;
            }
        }
        else {
            health -= amo;
            checkDeath();
        }
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
    public double getY() {
        return y;
    }
    public double getX() {
        return x;
    }
    public void spawnEnemy(){
        for(int i=0; i<Screen_2.room.block.length; i++){
            if(Screen_2.room.block[i][0].groundID == 1){
                setBounds(Screen_2.room.block[i][0].x, Screen_2.room.block[i][0].y, enemysize, enemysize);
                xE = 0;
                yE = i;
                break;
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
            if(enemyWalk == Screen_2.room.blockSize){
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
                if(Screen_2.an[yE][xE] == Value.end) {
                    deleteEnemy();
                    decreasePoint();
                }
                if(yE+1<Screen_2.room.worldHeight &&  Screen_2.an[yE+1][xE] == Value.grass && direction != up){
                    direction = down;
                }
                else if(yE-1>=0 && Screen_2.an[yE-1][xE] == Value.grass && direction!= down){
                    direction = up;
                }
                else if(xE+1<Screen_2.room.worldWidth && Screen_2.an[yE][xE+1] == Value.grass && direction != left) {
                    direction = right;
                }
                else if(xE-1>=0 && Screen_2.an[yE][xE-1] == Value.grass && direction != right) {
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
    public int getSum_road() {
        return sum_road;
    }
    public boolean isInRange(Tower tower){
        double d = (this.x-tower.getX())*(this.x-tower.getX()) + (this.y-tower.getY())*(this.y-tower.getY());
        if (Math.sqrt(d) <= tower.getRange()) return true;
        return false;
    }
    public void draw(Graphics g){
        g.drawImage(GameStage.tileset_enemy[id], x, y, width, height, null);
        g.setColor(new Color(180, 16, 0));
        g.fillRect(x, y- (healthSpace+healthHeight), save_health, healthHeight+1);
        g.setColor(new Color(53, 180, 16));
        g.fillRect(x, y- (healthSpace+healthHeight), health, healthHeight);
        g.setColor(new Color(0xABA1A1));
        g.fillRect(x + (enemysize-shield), y- (healthSpace+healthHeight), shield, healthHeight);
        g.setColor(new Color(0,0,0));
        g.drawRect(x, y-healthSpace-healthHeight, health-1, healthHeight);
    }
    public Rectangle getRect() {
        Rectangle rectangle = new Rectangle(x,y,enemysize,enemysize);
        return rectangle;
    }
}