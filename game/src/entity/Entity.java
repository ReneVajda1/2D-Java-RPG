package entity;

import java.awt.image.BufferedImage;

public class Entity {
    public int worldX, worldY; // Character's position
    public int speed; // Movement speed
    public String direction; // Current movement direction

    // Animation fields
    public int spriteCounter = 0; // Counter to control animation frame updates
    public int currentFrame = 0; // Current frame in the animation

    // Spritesheet dimensions
    public int FRAME_WIDTH; // Width of each frame
    public int FRAME_HEIGHT; // Height of each frame
}
