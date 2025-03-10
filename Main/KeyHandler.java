package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public GamePanel gp;
    public UI ui;
    public boolean upPressed, downPressed, leftPressed, rightPressed;

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
            if(code == KeyEvent.VK_ENTER) {
                switch (ui.selectedIndex) {
                    case 0:
                        gp.gameState = ui.TXT_CUTSCENE;
                        gp.repaint();

                        new Thread(() -> {
                            try {

                                gp.repaint();
                                Thread.sleep(5000);

                                ui.imageDelay = 0;
                                ui.showImage = true;
                                gp.repaint();
                                Thread.sleep(5000);


                                ui.imageDelay = 10;
                                gp.repaint();
                                Thread.sleep(5000);

                                ui.imageDelay = 20;
                                gp.repaint();
                                Thread.sleep(5000);

                                ui.showImage = false;
                                gp.gameState = 2;
                                gp.repaint();

                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }).start();
                        break;

                    case 1:
                        break;

                    case 2:
                        System.exit(0);
                        break;
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
        if(gp.gameState == ui.MOVING){
            if (code == KeyEvent.VK_W) upPressed = false;
            if (code == KeyEvent.VK_S) downPressed = false;
            if (code == KeyEvent.VK_A) leftPressed = false;
            if (code == KeyEvent.VK_D) rightPressed = false;
        }
    }
}
