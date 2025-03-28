package tile;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class HouseMap extends TileManager{
    public HouseMap(String filePath){
        super(filePath, 36);
        solidTiles = new HashSet<Integer>();
        loadSolidTiles();
    }

    @Override
    public void loadMap(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(" ");
                if (map == null) {
                    rows = tokens.length;
                    cols = tokens.length;
                    map = new int[rows][cols];
                }
                for (int i = 0; i < tokens.length; i++) {
                    map[row][i] = Integer.parseInt(tokens[i]);
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void loadTextures() {
        try{
            tileTextures[1] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final1.png"));
            tileTextures[2] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final2.png"));
            tileTextures[3] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final3.png"));
            tileTextures[4] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final4.png"));
            tileTextures[5] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final5.png"));
            tileTextures[6] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final6.png"));
            tileTextures[7] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final7.png"));
            tileTextures[8] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final8.png"));
            tileTextures[9] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final9.png"));
            tileTextures[10] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final10.png"));
            tileTextures[11] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final11.png"));
            tileTextures[12] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final12.png"));
            tileTextures[13] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final13.png"));
            tileTextures[14] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final14.png"));
            tileTextures[15] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final15.png"));
            tileTextures[16] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final16.png"));
            tileTextures[17] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final17.png"));
            tileTextures[18] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final18.png"));
            tileTextures[19] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final19.png"));
            tileTextures[20] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final20.png"));
            tileTextures[21] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final21.png"));
            tileTextures[22] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final22.png"));
            tileTextures[23] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final23.png"));
            tileTextures[24] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final24.png"));
            tileTextures[25] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final25.png"));
            tileTextures[26] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final26.png"));
            tileTextures[27] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final27.png"));
            tileTextures[28] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final28.png"));
            tileTextures[29] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final29.png"));
            tileTextures[30] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final30.png"));
            tileTextures[31] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final31.png"));
            tileTextures[32] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final32.png"));
            tileTextures[33] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final33.png"));
            tileTextures[34] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final34.png"));
            tileTextures[35] = ImageIO.read(new File("res/tiles/house/Witch-Hut-final35.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public int[][] getMap() {
        return map;
    }

    @Override
    public void loadSolidTiles() {
    }
}
