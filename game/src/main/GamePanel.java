package main;

import entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3; // Scale factor
    public final int tileSize = originalTileSize * scale; // 48x48 tile
    final int maxScreenCol = 16; // Screen width in tiles
    final int maxScreenRow = 12; // Screen height in tiles
    final int screenWidth = tileSize * maxScreenCol; // 768px wide
    final int screenHeight = tileSize * maxScreenRow; // 576px high

    KeyHandler keyH = new KeyHandler(); // KeyHandler instance
    Thread gameThread; // Game loop thread
    Player player1 = new Player(this, keyH); // Create the player

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black); // Background color
        this.setDoubleBuffered(true); // Performance optimization
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameThread != null) {
            update(); // Update game logic
            repaint(); // Redraw the screen
            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        player1.update(); // Update player
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        player1.draw(g2); // Draw the player

        g2.dispose();
    }
}
