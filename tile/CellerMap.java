package tile;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CellerMap extends TileManager{
    public CellerMap(String filePath){
        super(filePath, 24);
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
            tileTextures[2] = ImageIO.read(new File("res/tiles/celler/Celler-Room-Final2.png"));
            tileTextures[3] = ImageIO.read(new File("res/tiles/celler/Celler-Room-Final3.png"));
            tileTextures[4] = ImageIO.read(new File("res/tiles/celler/Celler-Room-Final4.png"));
            tileTextures[5] = ImageIO.read(new File("res/tiles/celler/Celler-Room-Final5.png"));
            tileTextures[6] = ImageIO.read(new File("res/tiles/celler/Celler-Room-Final6.png"));
            tileTextures[7] = ImageIO.read(new File("res/tiles/celler/Celler-Room-Final7.png"));
            tileTextures[8] = ImageIO.read(new File("res/tiles/celler/Celler-Room-Final8.png"));
            tileTextures[9] = ImageIO.read(new File("res/tiles/celler/Celler-Room-Final9.png"));
            tileTextures[10] = ImageIO.read(new File("res/tiles/celler/Celler-Room-Final10.png"));
            tileTextures[11] = ImageIO.read(new File("res/tiles/celler/Celler-Room-Final11.png"));
            tileTextures[12] = ImageIO.read(new File("res/tiles/celler/Celler-Room-Final12.png"));
            tileTextures[13] = ImageIO.read(new File("res/tiles/celler/Celler-Room-Final13.png"));
            tileTextures[14] = ImageIO.read(new File("res/tiles/celler/Celler-Room-Final14.png"));
            tileTextures[15] = ImageIO.read(new File("res/tiles/celler/Celler-Room-Final15.png"));
            tileTextures[16] = ImageIO.read(new File("res/tiles/celler/Celler-Room-Final16.png"));
            tileTextures[17] = ImageIO.read(new File("res/tiles/celler/Celler-Room-Final17.png"));
            tileTextures[18] = ImageIO.read(new File("res/tiles/celler/Celler-Room-Final18.png"));
            tileTextures[19] = ImageIO.read(new File("res/tiles/celler/Celler-Room-Final19.png"));
            tileTextures[20] = ImageIO.read(new File("res/tiles/celler/Celler-Room-Final20.png"));
            tileTextures[21] = ImageIO.read(new File("res/tiles/celler/Celler-Room-Final21.png"));
            tileTextures[22] = ImageIO.read(new File("res/tiles/celler/Celler-Room-Final22.png"));
            tileTextures[23] = ImageIO.read(new File("res/tiles/celler/Celler-Room-Final23.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int[][] getMap() {
        return map;
    }
}

