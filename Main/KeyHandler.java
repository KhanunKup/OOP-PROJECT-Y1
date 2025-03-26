package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public GamePanel gp;
    public UI ui;

    private int previousState;

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed,
                    shiftPressed;

    public KeyHandler(GamePanel gp, UI ui) {
        this.gp = gp;
        this.ui = ui;
        this.previousState = gp.gameState;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(gp.gameState == UI.MAIN_MENU){
            if(code == KeyEvent.VK_W){
                if (ui.selectedIndex > 0){
                    ui.selectedIndex--;
                }else {
                    ui.selectedIndex = ui.menuOptions.length - 1;
                }

                if (ui.pointerIndex - 1 >= 0) {
                    ui.pointerIndex -= 1;
                }
                else {
                    ui.pointerIndex = ui.pointerPosition.length-1;
                }
            }
            if(code == KeyEvent.VK_S){
                if (ui.selectedIndex < ui.menuOptions.length - 1){
                    ui.selectedIndex++;
                } else {
                    ui.selectedIndex = 0;
                }

                if (ui.pointerIndex + 1 < ui.pointerPosition.length) {
                    ui.pointerIndex += 1;
                }
                else {
                    ui.pointerIndex = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER && !enterPressed) {
                enterPressed = true;
                switch (ui.selectedIndex) {
                    case 0:
                        gp.gameState = UI.TXT_CUTSCENE;
                        gp.repaint();
                        new Thread(new Cutscene(gp, ui)).start();
                        break;

                    case 1:
                        gp.gameState = UI.OPTION;
                        break;

                    case 2:
                        System.exit(0);
                        break;
                }
            }
        }
        if(gp.gameState == UI.OPTION){
            if (code == KeyEvent.VK_W) {
                ui.optionIndex = (ui.optionIndex - 1 + ui.optionMenu.length) % ui.optionMenu.length;
            }
            if (code == KeyEvent.VK_S) {
                ui.optionIndex = (ui.optionIndex + 1) % ui.optionMenu.length;
            }
            if (code == KeyEvent.VK_ENTER && !enterPressed) {
                enterPressed = true;
                if (ui.optionIndex == 1){
                    ui.showFPS = !ui.showFPS;
                    ui.saveConfig();
                }
                if (ui.optionIndex == 2) {
                    gp.gameState = previousState;
                    ui.hideVolumeSlider();
                    ui.saveConfig();
                }
            }
            if (ui.optionIndex == 0){
                if (code == KeyEvent.VK_LEFT) {
                    ui.volumeLevel = Math.max(0, ui.volumeLevel - 5);
                    ui.music.setVolume(ui.volumeLevel / 100.0f);
                    ui.volumeSlider.setValue(ui.volumeLevel);
                    ui.saveConfig();
                }
                else if (code == KeyEvent.VK_RIGHT) {
                    ui.volumeLevel = Math.max(0, ui.volumeLevel + 5);
                    ui.music.setVolume(ui.volumeLevel / 100.0f);
                    ui.volumeSlider.setValue(ui.volumeLevel);
                    ui.saveConfig();
                }
            }
        }
        if(gp.gameState == UI.MOVING){
            if (code == KeyEvent.VK_W) upPressed = true;
            if (code == KeyEvent.VK_S) downPressed = true;
            if (code == KeyEvent.VK_A) leftPressed = true;
            if (code == KeyEvent.VK_D) rightPressed = true;
            if (code == KeyEvent.VK_SHIFT) shiftPressed = true;
            if (code == KeyEvent.VK_ENTER) enterPressed = true;

            if (code == KeyEvent.VK_ESCAPE) {
                previousState = gp.gameState;
                gp.gameState = UI.OPTION;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(gp.gameState == ui.MAIN_MENU){
            if(code == KeyEvent.VK_ENTER){
                enterPressed = false;
            }
        }
        if(gp.gameState == ui.OPTION){
            if(code == KeyEvent.VK_ENTER){
                enterPressed = false;
            }
        }
        if(gp.gameState == ui.MOVING){
            if (code == KeyEvent.VK_W) upPressed = false;
            if (code == KeyEvent.VK_S) downPressed = false;
            if (code == KeyEvent.VK_A) leftPressed = false;
            if (code == KeyEvent.VK_D) rightPressed = false;
            if (code == KeyEvent.VK_SHIFT) shiftPressed = false;
            if (code == KeyEvent.VK_ENTER) enterPressed = false;
        }
    }

    public void keyBoolRelease() {
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
        enterPressed = false;
        shiftPressed = false;
    }
}
