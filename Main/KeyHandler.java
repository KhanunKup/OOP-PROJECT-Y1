package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import Character.*;

public class KeyHandler implements KeyListener {
    public GamePanel gp;
    public UI ui;

    private int previousState;

    Sound candySound;

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed,
                    shiftPressed,spacePressed, isPlayerCollisionOn = false,checkMove = true; //change from true boolean;

    public KeyHandler(GamePanel gp, UI ui) {
        this.gp = gp;
        this.ui = ui;
        this.previousState = gp.gameState;
        candySound = new Sound(ui.volumeLevel / 100f, "res/sound/soundEffect/candy.wav");
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
                enterPressed = true;
                switch (ui.selectedIndex) {
                    case 0:
                        ui.bookOpening.play();
                        gp.gameState = UI.TXT_CUTSCENE;
                        gp.repaint();
                        new Thread(new Cutscene(gp, ui)).start();
                        break;

                    case 1:
                        ui.confirmSound.play();
                        gp.gameState = UI.OPTION;
                        break;

                    case 2:
                        ui.confirmSound.play();
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

                    ui.selectSound.setVolume(ui.volumeLevel / 100.0f);
                    ui.confirmSound.setVolume(ui.volumeLevel / 100.0f);
                    ui.slidebarSound.setVolume(ui.volumeLevel / 100.0f);
                    ui.volumeSlider.setValue(ui.volumeLevel);

                    gp.player.grassFootstep.setVolume(ui.volumeLevel / 100f);
                    gp.player.dirtFootstep.setVolume(ui.volumeLevel / 100f);

                    ui.mainMenuMusic.setVolume(ui.volumeLevel / 100.0f);
                    ui.map1soundtrack.setVolume(ui.volumeLevel / 100.0f);
                    ui.cutsceneFrightening.setVolume(ui.volumeLevel / 100.0f);
                    ui.cutsceneHiding.setVolume(ui.volumeLevel / 100.0f);
                    ui.saveConfig();
                }
                else if (code == KeyEvent.VK_RIGHT) {
                    ui.slidebarSound.play();
                    ui.volumeLevel = Math.max(0, ui.volumeLevel + 5);
                    if(ui.volumeLevel >= 100){
                        ui.volumeLevel = 100;
                    }
                    ui.mainMenuMusic.setVolume(ui.volumeLevel / 100.0f);
                    ui.selectSound.setVolume(ui.volumeLevel / 100.0f);
                    ui.confirmSound.setVolume(ui.volumeLevel / 100.0f);
                    ui.slidebarSound.setVolume(ui.volumeLevel / 100.0f);
                    ui.volumeSlider.setValue(ui.volumeLevel);
                    ui.saveConfig();
                }
            }
        }
        if(gp.gameState == UI.MOVING && checkMove) {
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

            if (code == KeyEvent.VK_X) {
                isPlayerCollisionOn = !isPlayerCollisionOn;
                ui.devmode = !ui.devmode;
            }

            if (gp.currentTileMap == gp.tileMap3) {
                int candyNum;
                if ((Player.worldX <= 1085 && Player.worldX >= 815) && (Player.worldY >= 1120)) {
                    candyNum = 0;
                } else if ((Player.worldX >= 1300 && Player.worldX <= 1520) && Player.worldY >= 1120) {
                    candyNum = 2;
                } else {
                    candyNum = 1;
                }


                if ((((Player.worldX <= 1070 && Player.worldX >= 870) && (Player.worldY <= 1300 && Player.worldY >= 1160)) && ui.checkCandy1) || (((Player.worldX <= 1540 && Player.worldX >= 1400) && (Player.worldY <= 1260 && Player.worldY >= 1120)) && ui.checkCandy3) || (((Player.worldX <= 1300 && Player.worldX >= 1180) && (Player.worldY <= 1015 && Player.worldY >= 880)) && ui.checkCandy2)) {
                    if (code == KeyEvent.VK_E) {
                        ui.greenBarPosition = 280;
                        ui.showMiniGame = true;
                        ui.spaceAble = true;
                    } else if (ui.spaceAble) {
                        if ((code == KeyEvent.VK_SPACE) && (ui.greenBarPosition >= 370 && ui.greenBarPosition <= 440)) {
                            ui.showMiniGame = false;

                            int[] targetPosition = {985, 1240};
                            switch (candyNum) {
                                case 0:
                                    targetPosition = new int[]{985, 1240};
                                    ui.checkCandy1 = false;
                                    break;
                                case 1:
                                    targetPosition = new int[]{1255, 935};
                                    ui.checkCandy2 = false;
                                    break;
                                case 2:
                                    targetPosition = new int[]{1485, 1210};
                                    ui.checkCandy3 = false;
                                    break;
                            }

                            // Remove the specific candy by its coordinates
                            for (int i = 0; i < MapManager.candyPosition.size(); i++) {
                                int[] pos = MapManager.candyPosition.get(i);
                                if (pos[0] == targetPosition[0] && pos[1] == targetPosition[1]) {
                                    candySound.play();
                                    MapManager.candyPosition.remove(i);
                                    ui.spaceAble = false;
                                    break;
                                }
                            }

                            ui.candyCount += 1;

                            //เจอลูกอมครบ
                            if (ui.candyCount == 3) {
                                UI.SCENE = 4;
                                ui.spaceAble = false;
                                ui.chalkPlayed = false;
                                gp.ui.showText = false;
                                gp.ui.imageDelay = 70;
                                gp.ui.showImage = true;
                                gp.ui.startFade();
                                keyBoolRelease();
                            }
                        }
                    }
                } else {
                    ui.showMiniGame = false;
                }

            } else if (gp.currentTileMap == gp.tileMap4) {
                if (ui.checkJailBreak){
                    if (((Player.worldX >= 1000 && Player.worldX <= 1120) && (Player.worldY <= 1320 && Player.worldY >= 1250))) {
                        if (code == KeyEvent.VK_E) {
                            ui.showMiniGame = true;
                            ui.spaceAble = true;
                            ui.imageWidth = gp.tileSize * 10;
                            ui.imageHeight = gp.tileSize * 2;

                        } else if (ui.spaceAble) {
                            if ((code == KeyEvent.VK_SPACE && ui.showMiniGame)) {
                                if (spacePressed){
                                    ui.imageWidth += 150;
                                }
                                spacePressed = false;
                            }
                        }
                    }
                    else {
                        ui.showMiniGame = false;
                        ui.spaceAble = false;
                        ui.timer = 10.0;
                    }
                }
                else {
                    if (ui.basementIndex == 1 || ui.basementIndex == 2 || ui.basementIndex == 3){
                        if (code == KeyEvent.VK_E){
                            ui.basementText = false;
                            ui.checkBasement = true;
                            new Thread(new Cutscene(gp, ui)).start();
                        }

                    }
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
            if (code == KeyEvent.VK_SPACE) spacePressed = true;
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
        isPlayerCollisionOn = false;
    }
}
