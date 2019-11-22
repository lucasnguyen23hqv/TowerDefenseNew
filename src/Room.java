import java.awt.*;

public class Room {
    public int worldWidth=22;
    public int worldHeight=14;
    public int blockSize=42;
    public Block[][] block;
    public int m=0;
    public int[] x=new int[6];
    public int[] y=new int[6];
    public Rectangle mapdraw,tieude,background;
    public int k=(Screen.myWidth)/2-(worldWidth*blockSize)/2;
    public Room(){
        define();
    }
    public Rectangle river;
    public Rectangle map1;


    public void define(){
        block=new Block[worldHeight][worldWidth];
        for (int i=0;i<block.length;i++)
            for (int j=0;j<block[0].length;j++){
                block[i][j]=new Block(k+j*blockSize,i*blockSize,blockSize,blockSize,Value.groundGrass,Value.airAir);
            }

        mapdraw=new Rectangle(k,0,(Screen.myWidth-2*k),blockSize*worldHeight);
        tieude= new Rectangle(Screen.myWidth/2-250, blockSize*worldHeight-15, 500, 70);
        background= new Rectangle(0, 0, Screen.myWidth, Screen.myHeight);

    }

    public void define_putTower(){
        for (int i=0;i<Screen.room.block.length;i++)
            for (int j=0;j<Screen.room.block[0].length;j++){
                if (Screen.room.block[i][j].groundID == 3) {
                    x[m]=k+j*blockSize;
                    y[m]=i*blockSize;
                    m++;
                }
            }
    }
    public void physic(){
    }
    public void draw(Graphics g) {
        g.drawImage(GameStage.tileset_res[4], background.x, background.y, background.width, background.height, null);
        if(Screen.level == 1) {
            g.drawImage(GameStage.tileset_map[0], mapdraw.x, mapdraw.y, mapdraw.width, mapdraw.height, null);
        }
        else if(Screen.level == 2){
            g.drawImage(GameStage.tileset_map[1], mapdraw.x, mapdraw.y, mapdraw.width, mapdraw.height, null);
        }
        else if(Screen.level == 3){
            g.drawImage(GameStage.tileset_map[2], mapdraw.x, mapdraw.y, mapdraw.width, mapdraw.height, null);
        }
        g.drawImage(GameStage.tileset_res[6], tieude.x, tieude.y, tieude.width, tieude.height, null);
    }
}
