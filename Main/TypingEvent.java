package Main;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class TypingEvent extends JFrame implements KeyListener {

    private JLabel lb;
    private Timer timer;
    private int timeLeft = 5;  // ตั้งเวลาเป็น 5 วินาที
    private boolean isEventActive = false;

    private int rounds = 0;
    private final int MaxRounds = 6;
    private int win = 0;

    // คำที่ต้องพิมพ์
    private String[] words = {"escape", "findout", "witch", "run", "rush", "monster"};
    private String currentWord;
    private StringBuilder typedWord;  //เป็นเหมือนลิสแต่ว่าเป็นลิสของสติง

    public TypingEvent() {
        setTitle("Typing Event");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        lb = new JLabel("Press the correct word!", SwingConstants.CENTER);
        lb.setFont(new Font("Arial", Font.BOLD, 24));
        add(lb, BorderLayout.CENTER);

        addKeyListener(this);
        setFocusable(true);

        typedWord = new StringBuilder();

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isEventActive) {
                    timeLeft--;
                    //ยังนับเวลาอยู่

                    if (timeLeft <= 0) {
                        lb.setText("Time's up! You failed.");
                        //เพิ่มคอนดิชั่นตรงนี้
                        isEventActive = false;
                        timer.stop();

                        startNextTPE();
                    }
                }
            }
        });
    }

    public void startTPE() {
        // เลือกคำจากอาร์เรย์ words โดยใช้ rounds เพื่อเลือกคำตามลำดับ
        currentWord = words[rounds % words.length];

        lb.setText("Type the word: " + currentWord);
        timeLeft = 5;  // ตั้งเวลาใหม่
        isEventActive = true;
        typedWord.setLength(0);  // รีเซ็ตคำที่พิมพ์
        timer.start();
    }

    private void startNextTPE() {
        if (rounds < MaxRounds) {
            Timer nextTPEtimer = new Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    rounds++;
                    startTPE();  // เรียกใช้ startTPE เพื่อเริ่มรอบถัดไป
                }
            });
            nextTPEtimer.setRepeats(false);
            nextTPEtimer.start();
        } else if (win >= 4) {
            lb.setText("Escape Pass!");//ผ่าน obj
        } else if (win >= 3) {
            lb.setText("Almost Died!!");// อันนี้ก็ผ่าน
        } else {
            lb.setText("Game Over!");//ไม่ผ่านเริ่มใหม่
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (isEventActive) {
            char pressedKey = e.getKeyChar();  //ดึงตัวอักษรที่กดมาโชว์ให้คนดู

            // เพิ่มตัวอักษรที่ผู้เล่นกดลงใน typedWord
            typedWord.append(pressedKey);

            // ตรวจว่ามันตรงมั้ย
            if (typedWord.toString().equals(currentWord)) {
                lb.setText("Correct! Moving to next word.");
                win++;
                isEventActive = false;
                timer.stop();

                startNextTPE();  // เริ่มรอบถัดไป
            } else if (typedWord.length() > currentWord.length()) {
                // ถ้าคำที่พิมพ์ยาวเกิน
                lb.setText("Wrong word! Try again!");
                typedWord.setLength(0);  // รีเซ็ตคำ
            } else {
                lb.setText("Keep going! You typed: " + typedWord);  // ให้ข้อความต่อไป
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        TypingEvent tpe = new TypingEvent();
        tpe.setVisible(true);

        Timer startTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tpe.startTPE();
            }
        });
        startTimer.setRepeats(false);
        startTimer.start();
    }
}
