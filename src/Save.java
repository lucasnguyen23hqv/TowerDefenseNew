import java.io.*;
import java.util.*;

public class Save {
    public void loadSave(File loadPath){
        try {
            Scanner loadScanner = new Scanner(loadPath);
            //Screen.room.map=loadScanner.nextLine();
            while (loadScanner.hasNext()){
                for (int y=0;y<Screen.room.block.length;y++)
                    for (int x=0;x<Screen.room.block[0].length;x++){
                        Screen.room.block[y][x].groundID=loadScanner.nextInt();
                    }
            }
            loadScanner.close();

        }
        catch (Exception e){}
    }
    public void loadSave_2(File loadPath){
        try {
            Scanner loadScanner = new Scanner(loadPath);
            //Screen.room.map=loadScanner.nextLine();
            while (loadScanner.hasNext()){
                for (int y=0;y<Screen_2.room.block.length;y++)
                    for (int x=0;x<Screen_2.room.block[0].length;x++){
                        Screen_2.room.block[y][x].groundID=loadScanner.nextInt();
                        Screen_2.a[y][x] = Screen_2.room.block[y][x].groundID;
                    }
            }
            loadScanner.close();

        }
        catch (Exception e){}
    }
}
