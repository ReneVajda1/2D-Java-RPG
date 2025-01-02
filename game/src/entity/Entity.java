package entity;

import java.awt.image.BufferedImage;

public class Entity {
    public int x, y; // Position
    public int speed; // Speed of movement

    // Sprite sheets for animations
    public BufferedImage walkingSheet;
    public BufferedImage idleSheet;

    public String direction; // Current direction
    public int spriteCounter = 0; // Timer for frame switching
    public int spriteNum = 1; // Current frame number
}
