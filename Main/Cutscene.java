package Main;

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
            gp.repaint();
            Thread.sleep(5000);

            ui.imageDelay = 0;
            ui.showImage = true;
            gp.repaint();
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
            Thread.sleep(5000);

            ui.showImage = false;
            gp.gameState = 2;
            gp.repaint();

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
