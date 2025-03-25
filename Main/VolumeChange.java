package Main;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class    VolumeChange implements ChangeListener {
    public UI ui;

    public VolumeChange(UI ui){
        this.ui = ui;
    }
    @Override
    public void stateChanged(ChangeEvent e) {
        ui.volumeLevel = ui.volumeSlider.getValue();
        ui.music.setVolume(ui.volumeLevel / 100.0f);
    }
}
