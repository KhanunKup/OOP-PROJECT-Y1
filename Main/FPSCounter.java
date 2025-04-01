package Main;

public class FPSCounter {
    private int fps;
    private long lastTime;
    private int frameCount;

    public FPSCounter() {
        fps = 0;
        frameCount = 0;
        lastTime = System.nanoTime();
    }

    public void update() {
        frameCount++;
        long currentTime = System.nanoTime();
        long elapsedTime = currentTime - lastTime;

        if (elapsedTime >= 1000000000) { // ทุกๆ 1 วินาที
            fps = frameCount;
            frameCount = 0;
            lastTime = currentTime;
        }
    }

    public int getFps(){
        return fps;
    }
}

