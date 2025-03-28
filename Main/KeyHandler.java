package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public GamePanel gp;
    public UI ui;

    private int previousState;

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed,
                    shiftPressed,ePressed;

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

        if (gp.isQTEActive){
            gp.checkQTE(e.getKeyChar());
            return;
        }

        if(gp.gameState == UI.MAIN_MENU){
            if(code == KeyEvent.VK_W){
                ui.selectSound.play();
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
                ui.selectSound.play();
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
                ui.confirmSound.play();
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
                ui.selectSound.play();
                ui.optionIndex = (ui.optionIndex - 1 + ui.optionMenu.length) % ui.optionMenu.length;
            }
            if (code == KeyEvent.VK_S) {
                ui.selectSound.play();
                ui.optionIndex = (ui.optionIndex + 1) % ui.optionMenu.length;
            }
            if (code == KeyEvent.VK_ENTER && !enterPressed) {
                enterPressed = true;
                if (ui.optionIndex == 1){
                    ui.confirmSound.play();
                    ui.showFPS = !ui.showFPS;
                    ui.saveConfig();
                }
                if (ui.optionIndex == 2) {
                    gp.gameState = previousState;
                    ui.confirmSound.play();
                    ui.hideVolumeSlider();
                    ui.saveConfig();
                }
            }
            if (ui.optionIndex == 0){
                if (code == KeyEvent.VK_LEFT) {
                    ui.slidebarSound.play();
                    ui.volumeLevel = Math.max(0, ui.volumeLevel - 5);
                    if(ui.volumeLevel <= 0){
                        ui.volumeLevel = 0;
                    }
                    ui.music.setVolume(ui.volumeLevel / 100.0f);
                    ui.selectSound.setVolume(ui.volumeLevel / 100.0f);
                    ui.confirmSound.setVolume(ui.volumeLevel / 100.0f);
                    ui.slidebarSound.setVolume(ui.volumeLevel / 100.0f);
                    ui.volumeSlider.setValue(ui.volumeLevel);
                    ui.saveConfig();
                }
                else if (code == KeyEvent.VK_RIGHT) {
                    ui.slidebarSound.play();
                    ui.volumeLevel = Math.max(0, ui.volumeLevel + 5);
                    if(ui.volumeLevel >= 100){
                        ui.volumeLevel = 100;
                    }
                    ui.music.setVolume(ui.volumeLevel / 100.0f);
                    ui.selectSound.setVolume(ui.volumeLevel / 100.0f);
                    ui.confirmSound.setVolume(ui.volumeLevel / 100.0f);
                    ui.slidebarSound.setVolume(ui.volumeLevel / 100.0f);
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

            if (gp.currentTileMap == gp.tileMap3){
                int num = -1;
                if ((Player.worldX <= 1300 && Player.worldX >= 1180) && (Player.worldY <= 1015)){
                    num = 2;
                }
                else if ((Player.worldX >= 1400) && (Player.worldY <= 1260 && Player.worldY >= 1120)){
                    num = 1;
                }
                else if ((Player.worldX <= 1070) && (Player.worldY <= 1300 && Player.worldY >= 1160)){
                    num = 0;
                }

                if (((Player.worldX <= 1070 && Player.worldX >= 870) && (Player.worldY <= 1300 && Player.worldY >= 1160)) || ((Player.worldX <= 1540 && Player.worldX >= 1400) && (Player.worldY <= 1260 && Player.worldY >= 1120)) || ((Player.worldX <= 1300 && Player.worldX >= 1180) && (Player.worldY <= 1015 && Player.worldY >= 880))){
                    if (code == KeyEvent.VK_E){
                        ui.greenBarPosition = 280;
                        ui.showMiniGame = true;
                        ui.spaceAble = true;
                    }
                    else if (ui.spaceAble){
                        if ((code == KeyEvent.VK_SPACE) && (ui.greenBarPosition >= 370 && ui.greenBarPosition <= 440)){
                            ui.showMiniGame = false;

                            try {
                                MapManager.candyPosition.remove(num);
                            }
                            catch (Exception ex){
                                MapManager.candyPosition.remove(0);
                            }
                        }
                    }
                }
                else {
                    ui.showMiniGame = false;
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (gp.isQTEActive){
            return;
        }
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
        if (gp.isQTEActive) {
            char pressedKey = Character.toLowerCase((char) code);
            System.out.println("Pressed Key: " + pressedKey); // พิมพ์ปุ่มที่กด

            // ตรวจสอบว่าปุ่มที่กดตรงกับลำดับใน qteSequence หรือไม่
            System.out.println("Expected Key: " + gp.qteSequence.charAt(gp.KeyIndex)); // พิมพ์ตัวอักษรที่คาดหวัง
            // แปลง KeyCode เป็นตัวอักษร
            gp.checkQTE(pressedKey); // เช็คว่าผู้เล่นกดปุ่มถูกต้องใน QTE หรือไม่
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
