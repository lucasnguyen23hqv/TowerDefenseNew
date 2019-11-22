import java.awt.*;

public class Room_2 {
    public int worldWidth=22;
    public int worldHeight=14;
    public int blockSize=42;
    public Block[][] block;
    public int m=0;
    public int[] x=new int[6];
    public int[] y=new int[6];
    public Rectangle mapdraw,tieude,background;
    public int k=0;//(Screen_2.myWidth)/2-(worldWidth*blockSize)/2;
    public Room_2(){
        define();
    }

    public void define(){
        block=new Block[worldHeight][worldWidth];
        for (int i=0;i<block.length;i++)
            for (int j=0;j<block[0].length;j++){
                block[i][j]=new Block(k+j*blockSize,i*blockSize,blockSize,blockSize,Value.groundGrass,Value.airAir);
            }

        mapdraw=new Rectangle(k,0,(Screen_2.myWidth-2*k),blockSize*worldHeight);
        tieude= new Rectangle(Screen_2.myWidth/2-250, blockSize*worldHeight-15, 500, 70);
        background= new Rectangle(0, 0, Screen_2.myWidth, Screen_2.myHeight);

    }

    public void define_putTower(){
        for (int i=0;i<Screen_2.room.block.length;i++)
            for (int j=0;j<Screen_2.room.block[0].length;j++){
                if (Screen_2.room.block[i][j].groundID == 3) {
                    x[m]=k+j*blockSize;
                    y[m]=i*blockSize;
                    m++;
                }
            }
    }
    public void physic(){
    }
    public void draw(Graphics g) {
        for(int i = 0; i<block.length; i++){
            for(int j =0 ;j<block[0].length; j++){
                block[i][j].draw(g);
            }
        }
        g.drawImage(GameStage.tileset_res[5], background.x, background.y, background.width, background.height, null);
        g.drawImage(GameStage.tileset_map[3], mapdraw.x, mapdraw.y, mapdraw.width, mapdraw.height, null);
        g.drawImage(GameStage.tileset_res[6], tieude.x, tieude.y, tieude.width, tieude.height, null);

    }
}
