package tile;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class HouseAfterMap extends TileManager{
    public HouseAfterMap(String filePath){
        super(filePath, 42);
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
        try {
            tileTextures[1] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After1.png"));
            tileTextures[2] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After2.png"));
            tileTextures[3] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After3.png"));
            tileTextures[4] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After4.png"));
            tileTextures[5] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After5.png"));
            tileTextures[6] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After6.png"));
            tileTextures[7] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After7.png"));
            tileTextures[8] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After8.png"));
            tileTextures[9] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After9.png"));
            tileTextures[10] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After10.png"));
            tileTextures[11] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After11.png"));
            tileTextures[12] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After12.png"));
            tileTextures[13] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After13.png"));
            tileTextures[14] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After14.png"));
            tileTextures[15] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After15.png"));
            tileTextures[16] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After16.png"));
            tileTextures[17] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After17.png"));
            tileTextures[18] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After18.png"));
            tileTextures[19] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After19.png"));
            tileTextures[20] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After20.png"));
            tileTextures[21] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After21.png"));
            tileTextures[22] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After22.png"));
            tileTextures[23] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After23.png"));
            tileTextures[24] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After24.png"));
            tileTextures[25] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After25.png"));
            tileTextures[26] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After26.png"));
            tileTextures[27] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After27.png"));
            tileTextures[28] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After28.png"));
            tileTextures[29] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After29.png"));
            tileTextures[30] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After30.png"));
            tileTextures[31] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After31.png"));
            tileTextures[32] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After32.png"));
            tileTextures[33] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After33.png"));
            tileTextures[34] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After34.png"));
            tileTextures[35] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After35.png"));
            tileTextures[36] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After36.png"));
            tileTextures[37] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After37.png"));
            tileTextures[38] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After38.png"));
            tileTextures[39] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After39.png"));
            tileTextures[40] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After40.png"));
            tileTextures[41] = ImageIO.read(new File("res/tiles/houseafter/Witch-Hut-After41.png"));

        } catch (IOException e) {
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
