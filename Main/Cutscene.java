package Main;

import static Main.UI.MOVING;
import static Main.UI.TXT_CUTSCENE;

public class Cutscene implements Runnable {
    public GamePanel gp;
    public UI ui;

    public Cutscene(GamePanel gp, UI ui) {
        this.gp = gp;
        this.ui = ui;
    }

    @Override
    public void run() {
        try {
            System.out.println("Current Scene: " + UI.SCENE);

            if (UI.SCENE == 1){
                gp.repaint();
                Thread.sleep(5500);

                ui.imageDelay = 0;
                ui.showImage = true;
                gp.repaint();
                ui.cutscene.play();
                Thread.sleep(5000);

                ui.flashScreen = true;
                gp.repaint();

                ui.imageDelay = 10;
                gp.repaint();
                Thread.sleep(5000);

                ui.flashScreen = true;
                gp.repaint();

                ui.imageDelay = 20;
                gp.repaint();
                Thread.sleep(3000);

                ui.flashScreen = true;
                gp.repaint();

                ui.imageDelay = 30;
                gp.repaint();
                Thread.sleep(3000);

                ui.flashScreen = true;
                gp.repaint();

                ui.imageDelay = 40;
                gp.repaint();
                Thread.sleep(3000);

                ui.flashScreen = true;
                gp.repaint();

                ui.imageDelay = 50;
                gp.repaint();
                Thread.sleep(3000);

                ui.showImage = false;
                gp.gameState = 2;
                ui.setAlpha(255);
                ui.setAlphaText(255);
                ui.alphaSpeed = 5;
                ui.imageDelay = 0;
                ui.checkAlphaText = false;
                gp.repaint();
            }

            if (UI.SCENE == 3){
                gp.repaint();
                Thread.sleep(3000);
                ui.showImage = false;
                gp.gameState = MOVING;
                ui.showText = false;
            }

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
