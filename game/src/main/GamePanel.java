package main;

import entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16; // 16x16 tile size
    final int scale = 5; // Scale factor for tiles
    public final int tileSize = originalTileSize * scale; // 48x48 tile size
    final int maxScreenCol = 16; // Columns
    final int maxScreenRow = 12; // Rows
    final int screenWidth = tileSize * maxScreenCol; // 768px width
    final int screenHeight = tileSize * maxScreenRow; // 576px height

    KeyHandler keyH = new KeyHandler(); // KeyHandler instance
    Thread gameThread; // Game loop thread
    Player player1 = new Player(this, keyH); // Instantiate player

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // Set screen size
        this.setBackground(Color.white); // Background color
        this.setDoubleBuffered(true); // Enable double buffering
        this.addKeyListener(keyH); // Add KeyHandler to panel
        this.setFocusable(true); // Allow panel to receive key events
    }

    public void startGameThread() {
        gameThread = new Thread(this); // Initialize game thread
        gameThread.start(); // Start thread
    }

    @Override
    public void run() {
        while (gameThread != null) {
            update(); // Update game logic
            repaint(); // Redraw screen
            try {
                Thread.sleep(16); // Pause for ~60 FPS
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

        player1.draw(g2); // Draw player
        g2.dispose(); // Release resources
    }
}
