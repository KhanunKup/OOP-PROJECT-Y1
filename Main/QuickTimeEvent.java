package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class QuickTimeEvent extends JFrame implements KeyListener {

    private JLabel lb;
    private Timer timer;
    private int timeLeft = 3;
    private boolean isEventActive = false;
    private char RandomKey;

    private int rounds = 0;
    private final int MaxRounds = 6;
    private int win = 0;

    public QuickTimeEvent() {
        setTitle("Quick Time Event");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        lb = new JLabel("Press the correct key!", SwingConstants.CENTER);
        lb.setFont(new Font("Arial", Font.BOLD, 24));
        add(lb, BorderLayout.CENTER);

        addKeyListener(this);
        setFocusable(true);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isEventActive) {
                    timeLeft--;
                    lb.setText("Time Left: " + timeLeft + "s");

                    if (timeLeft <= 0) {
                        lb.setText("Time's up! You failed."); //ตัวจับเวลา
                        //เพิ่มคอนดิชั่นลดเวลา
                        isEventActive = false;
                        timer.stop();

                        startNextQTE();
                    }
                }
            }
        });
    }

    public void startQTE() {
        Random random = new Random();
        RandomKey = "escape".charAt(rounds % 6);
        //เปลี่ยนคำได้ อันนี้ฟิกคำตามตัว

        lb.setText("Press the '" + RandomKey + "' key!");
        timeLeft = 3;
        isEventActive = true;
        timer.start();
    }

    private void startNextQTE() {
        if (rounds < MaxRounds) {
            Timer nextQTEtimer = new Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    rounds++;
                    startQTE();
                }
            });
            nextQTEtimer.setRepeats(false);
            nextQTEtimer.start();

        } else if (win == 6) {
            lb.setText("Escape Pass!"); // อันนี้ผ่านไม่มีอะไีร
        } else if (win >= 5) {
            lb.setText("Almost Died!!"); // เกือบแพ้
        } else {
            lb.setText("Game Over!."); // อันนี้แพ้ให้เกมโอเวอร์
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (isEventActive) {
            char pressedKey = e.getKeyChar();
            if (pressedKey == RandomKey) {
                lb.setText("Speed Up!");
                //เพิ่มคอนดีชั่นเพิ่มความเร็วตรงนี้นะครับสุดหล่อ
                win++;
                isEventActive = false;
                timer.stop();

                startNextQTE();
            } else {
                lb.setText("Slow Down!!");
                //ตรงนี้ก็ฝากลดความเร็วด้วยครับ
                isEventActive = false;
                timer.stop();

                startNextQTE();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        QuickTimeEvent qte = new QuickTimeEvent();
        qte.setVisible(true);

        Timer startTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                qte.startQTE();
            }
        });
        startTimer.setRepeats(false);
        startTimer.start();
    }
}