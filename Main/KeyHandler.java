package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public GamePanel gp;
    public UI ui;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;

    public KeyHandler(GamePanel gp, UI ui) {
        this.gp = gp;
        this.ui = ui;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(gp.gameState == ui.MAIN_MENU){
            if(code == KeyEvent.VK_W){
                if (ui.selectedIndex > 0){
                    ui.selectedIndex--;
                }else {
                    ui.selectedIndex = ui.menuOptions.length - 1;
                }
            }
            if(code == KeyEvent.VK_S){
                if (ui.selectedIndex < ui.menuOptions.length - 1){
                    ui.selectedIndex++;
                } else {
                    ui.selectedIndex = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER && !enterPressed) {
                enterPressed = true;
                switch (ui.selectedIndex) {
                    case 0:
                        gp.gameState = ui.TXT_CUTSCENE;
                        gp.repaint();
                        new Thread(new Cutscene(gp, ui)).start();
                        break;

                    case 1:
                        gp.gameState = ui.OPTION;
                        break;

                    case 2:
                        System.exit(0);
                        break;
                }
            }
        }
        if(gp.gameState == ui.OPTION){
            if (code == KeyEvent.VK_W) {
                ui.optionIndex = (ui.optionIndex - 1 + ui.optionMenu.length) % ui.optionMenu.length;
            }
            if (code == KeyEvent.VK_S) {
                ui.optionIndex = (ui.optionIndex + 1) % ui.optionMenu.length;
            }
            if (code == KeyEvent.VK_ENTER && !enterPressed) {
                enterPressed = true;
                if (ui.optionIndex == 0) {
                    Main.setFullScreen(!Main.isFullScreen);
                    ui.optionMenu[0] = "Full Screen: " + (Main.isFullScreen ? "ON" : "OFF");
                } else if (ui.optionIndex == 1) {
                    gp.gameState = ui.MAIN_MENU;
                    ui.optionIndex = 0;
                }
            }
        }
        if(gp.gameState == ui.MOVING){
            if (code == KeyEvent.VK_W) upPressed = true;
            if (code == KeyEvent.VK_S) downPressed = true;
            if (code == KeyEvent.VK_A) leftPressed = true;
            if (code == KeyEvent.VK_D) rightPressed = true;
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
        }
    }
}
