package tile;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

// ใช้หลักการ การสืบทอด (Inheritance) กับ abstract class ที่ผู้ใช้สร้างขึ้นเอง
public class MinigameMap extends TileManager{
    public MinigameMap(String filePath){
        super(filePath, 2);
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
            tileTextures[1] = ImageIO.read(new File("res/tiles/celler/Celler-Room-Final1.png"));
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
