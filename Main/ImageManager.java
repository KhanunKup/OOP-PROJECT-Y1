package Main;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ImageManager {
    private Map<String, Image> images;

    public ImageManager(){
        images = new HashMap<>();
    }

    public void setImage(String key,String filepath){
        images.put(key, new ImageIcon(filepath).getImage());
    }

    public Image getImage(String key) {
        return images.get(key);
    }
}
