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
    public BufferedImage leaf26,leaf27,leaf28,leaf29,leaf30,leaf31,leaf32,leaf33,leaf34,leaf35,leaf36,leaf37,leaf38
            ,flower22,flower23,flower24
            ,dirt1,dirt2,dirt3,dirt4,dirt5,dirt6,dirt7,dirt8,dirt9
            ,grass10,grass11,grass12,grass13,grass14,grass15,grass16,grass17,grass18,grass19,grass20,grass21,grass25;

    public TileMap(String filePath){
        loadMap(filePath);
        loadTextures();
    }

    public void loadMap(String filePath) {
        List<int[]> mapData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
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
            flower22 = ImageIO.read(new File("res/tiles/Grass15.png"));
            flower23 = ImageIO.read(new File("res/tiles/Grass14.png"));
            flower24 = ImageIO.read(new File("res/tiles/Grass13.png"));
            leaf26 = ImageIO.read(new File("res/tiles/Leaf1.png"));
            leaf27 = ImageIO.read(new File("res/tiles/Leaf2.png"));
            leaf28 = ImageIO.read(new File("res/tiles/Leaf3.png"));
            leaf29 = ImageIO.read(new File("res/tiles/Leaf4.png"));
            leaf30 = ImageIO.read(new File("res/tiles/Leaf5.png"));
            leaf31 = ImageIO.read(new File("res/tiles/Leaf6.png"));
            leaf32 = ImageIO.read(new File("res/tiles/Leaf7.png"));
            leaf33 = ImageIO.read(new File("res/tiles/Leaf8.png"));
            leaf34 = ImageIO.read(new File("res/tiles/Leaf9.png"));
            leaf35 = ImageIO.read(new File("res/tiles/Leaf10.png"));
            leaf36 = ImageIO.read(new File("res/tiles/Leaf11.png"));
            leaf37 = ImageIO.read(new File("res/tiles/Leaf12.png"));
            leaf38 = ImageIO.read(new File("res/tiles/Leaf13.png"));
            dirt1 = ImageIO.read(new File("res/tiles/Dirt1.png"));
            dirt2 = ImageIO.read(new File("res/tiles/Dirt2.png"));
            dirt3 = ImageIO.read(new File("res/tiles/Dirt3.png"));
            dirt4 = ImageIO.read(new File("res/tiles/Dirt4.png"));
            dirt5 = ImageIO.read(new File("res/tiles/Dirt5.png"));
            dirt6 = ImageIO.read(new File("res/tiles/Dirt6.png"));
            dirt7 = ImageIO.read(new File("res/tiles/Dirt7.png"));
            dirt8 = ImageIO.read(new File("res/tiles/Dirt8.png"));
            dirt9 = ImageIO.read(new File("res/tiles/Dirt9.png"));
            grass10 = ImageIO.read(new File("res/tiles/Grass1.png"));
            grass11 = ImageIO.read(new File("res/tiles/Grass2.png"));
            grass12 = ImageIO.read(new File("res/tiles/Grass3.png"));
            grass13 = ImageIO.read(new File("res/tiles/Grass4.png"));
            grass14 = ImageIO.read(new File("res/tiles/Grass5.png"));
            grass16 = ImageIO.read(new File("res/tiles/Grass7.png"));
            grass17 = ImageIO.read(new File("res/tiles/Grass8.png"));
            grass18 = ImageIO.read(new File("res/tiles/Grass9.png"));
            grass19 = ImageIO.read(new File("res/tiles/Grass10.png"));
            grass20 = ImageIO.read(new File("res/tiles/Grass11.png"));
            grass15 = ImageIO.read(new File("res/tiles/Grass6.png"));
            grass21 = ImageIO.read(new File("res/tiles/Grass12.png"));
            grass25 = ImageIO.read(new File("res/tiles/Grass16.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getTileImage(int tileType) {
        return switch (tileType) {
            case 1 -> dirt1;
            case 2 -> dirt2;
            case 3 -> dirt3;
            case 4 -> dirt4;
            case 5 -> dirt5;
            case 6 -> dirt6;
            case 7 -> dirt7;
            case 8 -> dirt8;
            case 9 -> dirt9;
            case 10 -> grass10;
            case 11 -> grass11;
            case 12 -> grass12;
            case 13 -> grass13;
            case 14 -> grass14;
            case 15 -> grass15;
            case 16 -> grass16;
            case 17 -> grass17;
            case 18 -> grass18;
            case 19 -> grass19;
            case 20 -> grass20;
            case 21 -> grass21;
            case 22 -> flower22;
            case 23 -> flower23;
            case 24 -> flower24;
            case 25 -> grass25;
            case 26 -> leaf26;
            case 27 -> leaf27;
            case 28 -> leaf28;
            case 29 -> leaf29;
            case 30 -> leaf30;
            case 31 -> leaf31;
            case 32 -> leaf32;
            case 33 -> leaf33;
            case 34 -> leaf34;
            case 35 -> leaf35;
            case 36 -> leaf36;
            case 37 -> leaf37;
            case 38 -> leaf38;
            default -> null;
        };
    }
}
