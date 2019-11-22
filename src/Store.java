import java.awt.*;

public class Store {
    public static int shopWidth=8;
    public static int buttonSize=42;
    public Rectangle[] button=new Rectangle[shopWidth];
    public static int cellScape=1;
    public static int awayFromRoom=10;
    public static int iconSize=20;
    public static int iconSpace=7;
    public static int iconTextY=11;

    public Rectangle buttonHealth;
    public Rectangle buttonCoins;

    public Store(){
        define();
    }
    public void define(){
        for (int i=0;i<button.length;i++){
            button[i]=new Rectangle(Screen.myWidth/2-(shopWidth*(buttonSize+cellScape))/2+(cellScape+buttonSize)*i,Screen.room.block[Screen.room.worldHeight-1][0].y+Screen.room.blockSize+awayFromRoom,buttonSize,buttonSize);
        }
        buttonHealth=new Rectangle(Screen.room.block[0][0].x+15,button[0].y+2,iconSize,iconSize);
        buttonCoins=new Rectangle(Screen.room.block[0][0].x+15,button[0].y+button[0].height-iconSize+2,iconSize,iconSize);

    }
    public void draw(Graphics g){
        g.drawImage(GameStage.tileset_res[1],buttonHealth.x,buttonHealth.y-5,buttonHealth.width,buttonHealth.height,null);
        g.drawImage(GameStage.tileset_res[2],buttonCoins.x,buttonCoins.y,buttonCoins.width,buttonCoins.height,null);
        g.setFont(new Font("Courier New",Font.BOLD,15));
        g.setColor(new Color(255,255,255));
        g.drawString(""+Screen.health,buttonHealth.x+buttonHealth.width+iconSpace,buttonHealth.y+iconTextY-1);
        g.drawString(""+Screen.coinage,buttonCoins.x+buttonCoins.width+iconSpace,buttonCoins.y+iconTextY+4);
        g.setFont(new Font("Arial",Font.BOLD,28));
        g.setColor(new Color(255, 250, 252));
        g.drawString("LEVEL "+Screen.level,Screen.myWidth-130,Screen.myHeight-22);
    }
}
