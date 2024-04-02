import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class AimLab extends JFrame implements ActionListener{

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int NUM_TARGETS = 3;
    private static final int TIME_LIMIT = 60; // seconds

    private List<CircleObject> targets;
    private Timer timer;
    private int score;
    private JLabel scoreLabel;
    private JLabel timeLabel;
    private int timeRemaining;

    public AimLab() {
        super("Aim Lab");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        JPanel gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);//จะทำให้ JPanel ทำการวาดภาพพื้นฐานของตัวเองก่อน การเรียกบรรทัดนี้เป็นสิ่งสำคัญเพื่อให้มั่นใจว่าส่วนประกอบอื่นๆจะแสดงผลได้ถูกต้อง
                for (CircleObject target : targets) {//เป็นการวนลูปผ่านวัตถุ CircleObject แต่ละอันที่อยู่ในลิสต์ targets โดย target เป็นตัวแปรชั่วคราวที่ใช้แทนวัตถุ CircleObject แต่ละอันในแต่ละรอบของลูป
                    target.paintObject(g);//เมธอด paintObject ที่อยู่ในคลาส CircleObject น่าจะมีการใช้ g.fillOval ในการวาดวงกลมลงบนหน้าจอ 
                }
            }
        };
        gamePanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (CircleObject target : targets) {
                    if (target.contains(e.getX(), e.getY())) {
                        score++;
                        scoreLabel.setText("Score: " + score);
                        target.resetPosition();
                        repaint();
                        break;
                    }
                }
            }
            @Override
            public void mouseReleased(MouseEvent e){
                for (CircleObject target : targets) {
                    if (target.contains(e.getX(), e.getY())) {
                        score++;
                        scoreLabel.setText("Score: " + score);
                        target.resetPosition();
                        repaint();
                        break;
                    }
                }
            }
        });
        contentPane.add(gamePanel, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        scoreLabel = new JLabel("Score: 0");
        infoPanel.add(scoreLabel);
        timeLabel = new JLabel("Time: " + TIME_LIMIT);
        infoPanel.add(timeLabel);
        contentPane.add(infoPanel, BorderLayout.NORTH);

        JButton resetButton = new JButton("Reset Game");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                targets.clear();
                resetGame();
            }
        });
        infoPanel.add(resetButton);

        timer = new Timer(1000, this);
        timer.start();

        targets = new ArrayList<>();
        for (int i = 0; i < NUM_TARGETS; i++) {
            targets.add(new CircleObject(0, 0, 600,400, 25));
        }
        resetTargets();
        timeRemaining = TIME_LIMIT;
        setVisible(true);
    }
    
    private void resetGame() {
        score = 0;
        scoreLabel.setText("Score: 0");
        timeRemaining = TIME_LIMIT;
        timeLabel.setText("Time: " + timeRemaining);
        resetTargets();
        timer.start();
    }

    private void resetTargets() {
        for (CircleObject target : targets) {
            target.resetPosition();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            timeRemaining--;
            timeLabel.setText("Time: " + timeRemaining);
            if (timeRemaining == 0) {
                timer.stop();
                int choice = JOptionPane.showConfirmDialog(this, "Game Over! Your score is: " + score + "\nDo you want to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    targets.clear();
                    resetGame();
                }if (choice == JOptionPane.NO_OPTION){
                    System.exit(choice);
                }
            }
        }
    }
    public static void main(String[] args) {
        new AimLab();
    }
}