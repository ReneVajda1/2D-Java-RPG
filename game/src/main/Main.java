package main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame(); // Create JFrame
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure app closes when window is closed
        window.setResizable(false); // Disable resizing for consistency
        window.setTitle("RPG"); // Title of the window

        GamePanel gamePanel = new GamePanel(); // Create the game panel
        window.add(gamePanel); // Add game panel to the window
        window.pack(); // Ensure JFrame sizes to fit main.GamePanel
        window.setLocationRelativeTo(null); // Center window on screen
        window.setVisible(true); // Make the window visible

        gamePanel.startGameThread(); // Start the game loop
    }

}