package tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TileMap {
    public int[][] map;
    public int rows, cols;
    public BufferedImage leafmid,leafcorner,dirt;

    public TileMap(String filePath){
        loadMap(filePath);
        loadTextures();
    }

    public void loadMap(String filePath) {
        List<int[]> mapData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("res/map/Map1.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(" ");
                int[] row = new int[tokens.length];
                for (int i = 0; i < tokens.length; i++) {
                    row[i] = Integer.parseInt(tokens[i]);
                }
                mapData.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        rows = mapData.size();
        cols = mapData.get(0).length;
        map = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            map[i] = mapData.get(i);
        }
    }

    public int[][] getMap() {
        return map;
    }

    public void loadTextures() {
        try {
            leafmid = ImageIO.read(new File("res/tiles/Leaf-Middle.png"));
            leafcorner = ImageIO.read(new File("res/tiles/Leaf9.png"));
            dirt = ImageIO.read(new File("res/tiles/Dirt9.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getTileImage(int tileType) {
        return switch (tileType) {
            case 5,10 -> leafmid;
            case 9 -> leafcorner;
            case 0 -> dirt;
            default -> null;
        };
    }
}
