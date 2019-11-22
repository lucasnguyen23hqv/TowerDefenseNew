import java.awt.*;

public class Block extends Rectangle{
    public int groundID;
    public int airID;
    public Block(int x,int y,int width,int height,int groundID,int airID){
        setBounds(x,y,width,height);            //thiet lap vi tri cua block
        this.groundID=groundID;
        this.airID=airID;
    }
    public void draw(Graphics g){
        if(groundID == 1 || groundID == 5){
            setBounds(x,y,42,42);
            g.drawImage(GameStage.tileset_bullet[1], x, y, width, height,null);
        }
    }
}
