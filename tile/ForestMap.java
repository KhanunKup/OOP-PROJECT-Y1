package tile;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class ForestMap extends TileManager{
    public HashSet<Integer> grassTiles;
    public HashSet<Integer> dirtTiles;

    public ForestMap(String filePath){
        super(filePath, 39);
        this.grassTiles = new HashSet<Integer>();
        this.dirtTiles = new HashSet<Integer>();
        loadDirtTiles();
        loadGrassTiles();
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
            tileTextures[1] = ImageIO.read(new File("res/tiles/forest/Dirt1.png"));
            tileTextures[2] = ImageIO.read(new File("res/tiles/forest/Dirt2.png"));
            tileTextures[3] = ImageIO.read(new File("res/tiles/forest/Dirt3.png"));
            tileTextures[4] = ImageIO.read(new File("res/tiles/forest/Dirt4.png"));
            tileTextures[5] = ImageIO.read(new File("res/tiles/forest/Dirt5.png"));
            tileTextures[6] = ImageIO.read(new File("res/tiles/forest/Dirt6.png"));
            tileTextures[7] = ImageIO.read(new File("res/tiles/forest/Dirt7.png"));
            tileTextures[8] = ImageIO.read(new File("res/tiles/forest/Dirt8.png"));
            tileTextures[9] = ImageIO.read(new File("res/tiles/forest/Dirt9.png"));
            tileTextures[10] = ImageIO.read(new File("res/tiles/forest/Grass1.png"));
            tileTextures[11] = ImageIO.read(new File("res/tiles/forest/Grass2.png"));
            tileTextures[12] = ImageIO.read(new File("res/tiles/forest/Grass3.png"));
            tileTextures[13] = ImageIO.read(new File("res/tiles/forest/Grass4.png"));
            tileTextures[14] = ImageIO.read(new File("res/tiles/forest/Grass5.png"));
            tileTextures[15] = ImageIO.read(new File("res/tiles/forest/Grass6.png"));
            tileTextures[16] = ImageIO.read(new File("res/tiles/forest/Grass7.png"));
            tileTextures[17] = ImageIO.read(new File("res/tiles/forest/Grass8.png"));
            tileTextures[18] = ImageIO.read(new File("res/tiles/forest/Grass9.png"));
            tileTextures[19] = ImageIO.read(new File("res/tiles/forest/Grass10.png"));
            tileTextures[20] = ImageIO.read(new File("res/tiles/forest/Grass11.png"));
            tileTextures[21] = ImageIO.read(new File("res/tiles/forest/Grass12.png"));
            tileTextures[22] = ImageIO.read(new File("res/tiles/forest/Grass15.png"));
            tileTextures[23] = ImageIO.read(new File("res/tiles/forest/Grass14.png"));
            tileTextures[24] = ImageIO.read(new File("res/tiles/forest/Grass13.png"));
            tileTextures[25] = ImageIO.read(new File("res/tiles/forest/Grass16.png"));
            tileTextures[26] = ImageIO.read(new File("res/tiles/forest/Leaf1.png"));
            tileTextures[27] = ImageIO.read(new File("res/tiles/forest/Leaf2.png"));
            tileTextures[28] = ImageIO.read(new File("res/tiles/forest/Leaf3.png"));
            tileTextures[29] = ImageIO.read(new File("res/tiles/forest/Leaf4.png"));
            tileTextures[30] = ImageIO.read(new File("res/tiles/forest/Leaf5.png"));
            tileTextures[31] = ImageIO.read(new File("res/tiles/forest/Leaf6.png"));
            tileTextures[32] = ImageIO.read(new File("res/tiles/forest/Leaf7.png"));
            tileTextures[33] = ImageIO.read(new File("res/tiles/forest/Leaf8.png"));
            tileTextures[34] = ImageIO.read(new File("res/tiles/forest/Leaf9.png"));
            tileTextures[35] = ImageIO.read(new File("res/tiles/forest/Leaf10.png"));
            tileTextures[36] = ImageIO.read(new File("res/tiles/forest/Leaf11.png"));
            tileTextures[37] = ImageIO.read(new File("res/tiles/forest/Leaf12.png"));
            tileTextures[38] = ImageIO.read(new File("res/tiles/forest/Leaf13.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int[][] getMap() {
        return map;
    }

    public void loadSolidTiles() {
        //adding leaf to be solid tile (26-38)
        for (int i=26; i <= 38; i++) {
            solidTiles.add(i);
        }
    }

    public void loadGrassTiles() { //10-25
        for (int i=10; i <= 25; i++) {
            grassTiles.add(i);
        }
    }

    public void loadDirtTiles() { //1-9
        for (int i=1; i <= 9; i++) {
            dirtTiles.add(i);
        }
    }
}
