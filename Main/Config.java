package Main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
    private String filepath;
    private int volumeLevel;
    private boolean showFPS;
    public Config(String filepath){
        this.filepath = filepath;
    }
    public void save(int volumeLevel, boolean showFPS) {
        try (FileWriter writer = new FileWriter(filepath)) {
            writer.write("volumeLevel=" + volumeLevel + "\n");
            writer.write("showFPS=" + showFPS + "\n");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] keyValue = line.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = keyValue[1];
                    if (key.equals("volumeLevel")) {
                        volumeLevel = Integer.parseInt(value);
                    } else if (key.equals("showFPS")) {
                        showFPS = Boolean.parseBoolean(value);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getVolumeLevel() {
        return volumeLevel;
    }

    public boolean isShowFPS() {
        return showFPS;
    }
}
