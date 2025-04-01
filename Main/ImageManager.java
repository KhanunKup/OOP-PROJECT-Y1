package Main;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ImageManager {
    private Map<String, Image> images;
    private Map<String, Image[]> animations;

    public ImageManager(){
        images = new HashMap<>();
        animations = new HashMap<>();
    }

    public void setImage(String key,String filepath){
        images.put(key, new ImageIcon(filepath).getImage());
    }

//    ใช้หลักการ Overload กับ method ที่ผู้ใช้สร้างขึ้นเอง
    public void setImage(String key, String[] filepath) {
        Image[] imageArray = new Image[filepath.length];
        for (int i = 0; i < filepath.length; i++) {
            imageArray[i] = new ImageIcon(filepath[i]).getImage();
        }
        animations.put(key, imageArray);
    }

    public Image getImage(String key) {
        return images.get(key);
    }

    public Image[] getImages(String key) {
        return animations.get(key);
    }
}
