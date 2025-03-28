package tile;

import java.awt.image.BufferedImage;
import java.util.HashSet;

public abstract class TileManager {
    protected int[][] map;
    public HashSet<Integer> solidTiles;
    protected int rows, cols;
    protected BufferedImage[] tileTextures;

    public TileManager(String filePath, int maxTileTypes) {
        tileTextures = new BufferedImage[maxTileTypes];
        loadMap(filePath);
        loadTextures();
    }

    public abstract void loadMap(String filePath);

    public abstract void loadTextures();

    public abstract int[][] getMap();

    public abstract void loadSolidTiles();

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public BufferedImage getTileImage(int tileType) {
        if (tileType < 0 || tileType >= tileTextures.length) return null;
        return tileTextures[tileType];
    }

}
