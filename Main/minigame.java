package Main;

public abstract class minigame{
    public boolean isQTEActive = false;
    private int qteTimeLeft = 3;
    public String qteSequence = "run"; // ลำดับปุ่มที่ต้องกด
    public int currentKeyIndex = 0;
    private long lastQTETime;
    public void checkQTETrigger()
    public GamePanel gp ;{
        // ตรวา่าไม่ได้เริ่ม
        if (!isQTEActive) {
            // คำนวณตำแหน่งของผู้เล่นบนหน้าจอ
            int fixedX = 25;
            int fixedY = gp.maxRow * gp.yTileSize - gp.yTileSize;

            int screenIdleX = fixedX - player.worldX + player.screenX;
            int screenIdleY = fixedY - player.worldY + player.screenY;

            // หากผู้เล่นอยู่ในช่วงที่กำหนด (X: 0-100, Y: 750-850) อันนี้ลองเทสดู
            if (screenIdleX >= 0 && screenIdleX <= 100 && screenIdleY >= 750 && screenIdleY <= 850) {
                startQTE();
            }
        }
    }

    public void startQTE() {
        qteTimeLeft = 3;
        currentKeyIndex = 0;
        isQTEActive = true;
        lastQTETime = System.currentTimeMillis();  // บันทึกเวลาเริ่มต้น

        player.state = "idle";
        player.keyH.upPressed = false;
        player.keyH.downPressed = false;
        player.keyH.leftPressed = false;
        player.keyH.rightPressed = false;

        repaint();  // อัพเดตหน้าจอเพื่อแสดง
    }

    public void checkQTE(char pressedKey) {
        if (isQTEActive) {
            System.out.println("Pressed: " + pressedKey + " | Expected: " + qteSequence.charAt(currentKeyIndex));


            if (pressedKey == qteSequence.charAt(currentKeyIndex)) {
                System.out.println("Correct!");
                player.worldX += 50;
            } else {
                System.out.println("Wrong Key!");
                player.worldX -= 50;
            }

            lastQTETime = System.currentTimeMillis();
            qteTimeLeft = 3;
            // ไปยังตัวอักษรถัดไปเสมอ
            currentKeyIndex++;

            // ถ้าไปถึงตัวสุดท้ายแล้วให้ปิด QTE
            if (currentKeyIndex >= qteSequence.length()) {
                isQTEActive = false;
                System.out.println("QTE Completed!");
            }

            repaint();
        }
    }

    public void updateQTE() {
        if (isQTEActive) {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = (currentTime - lastQTETime) / 1000;
            qteTimeLeft = 3 - (int) elapsedTime;

            if (qteTimeLeft <= 0) {
                isQTEActive = false;
                System.out.println("Time's up!");
                repaint();
            }
        }
    }
}
