package Main;

import static Main.UI.MOVING;

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

            if (UI.SCENE == 1){
                gp.repaint();
                Thread.sleep(5500);

                ui.imageDelay = 0;
                ui.showImage = true;
                gp.repaint();
                ui.cutsceneHiding.play();
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
                ui.cutsceneFrightening.play();
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
                Thread.sleep(6000);
                ui.showImage = false;
                gp.gameState = MOVING;
                ui.showText = false;
            }

            if (UI.SCENE == 4){
                ui.map3soundtrack.stop();
                ui.cutsceneEye.playOnce();
                if (ui.showDialog){
                    Thread.sleep( 5000);
                    ui.showDialog = false;
                }
                else {
                    if (ui.basementIndex == 1 || ui.basementIndex == 2 || ui.basementIndex == 3){
                        ui.looting.play();
                        Thread.sleep( 2000);
                        ui.checkBasement = false;
                        ui.basementText = true;
                        if (ui.basementIndex == 1){
                            ui.head = false;
                        }
                        else if (ui.basementIndex == 2){
                            ui.hand = false;
                        }
                        else if (ui.basementIndex == 3){
                            ui.intestines = false;
                        }
                        gp.keyH.checkMove = true;

                        if ((!ui.intestines && !ui.hand) && !ui.head){
                            ui.basementCount = 3;
                        }

                        if (ui.basementCount == 3){
                            ui.checkJailBreak = true;
                            ui.skull = true;
                            ui.basementIndex = 4;
                            Thread.sleep( 3000);
                            ui.skull = false;
                        }
                    }
                    else {
                        gp.repaint();

                        ui.imageDelay = 80;
                        gp.repaint();
                        Thread.sleep(5000);

                        ui.showImage = false;
                        gp.gameState = MOVING;
                        ui.showText = false;
                    }
                }
            }

            if (UI.SCENE == 5){
                if (ui.showDialog){
                    Thread.sleep( 3000);
                    ui.showDialog = false;
                }
            }

            if (UI.SCENE == 6){
                if (ui.showDialog){
                    Thread.sleep( 1500);
                    ui.showDialog = false;
                    ui.showText = false;
                }
            }

            if (UI.SCENE == 7){ //ending
                Thread.sleep( 5000);
                ui.showText = false;
            }

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
