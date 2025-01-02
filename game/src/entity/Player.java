package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    // Sprite sheet references
    BufferedImage walkingSheet;
    BufferedImage idleSheet;

    // Define frame dimensions (adjust according to your sprite sheet dimensions)
    private final int FRAME_WIDTH = 48; // Width of each frame
    private final int FRAME_HEIGHT = 48; // Height of each frame

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100; // Initial X position
        y = 100; // Initial Y position
        speed = 4; // Initial speed
        direction = "down"; // Default direction
    }

    public void getPlayerImage() {
        try {
            // Load the sprite sheets
            walkingSheet = ImageIO.read(getClass().getResourceAsStream("/img/walking-spritesheet.png"));
            idleSheet = ImageIO.read(getClass().getResourceAsStream("/img/idle-spritesheet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        boolean isMoving = false;

        // Handle movement
        if (keyH.wPressed && keyH.aPressed) {
            direction = "upLeft";
            x -= speed / Math.sqrt(2);
            y -= speed / Math.sqrt(2);
            isMoving = true;
        } else if (keyH.wPressed && keyH.dPressed) {
            direction = "upRight";
            x += speed / Math.sqrt(2);
            y -= speed / Math.sqrt(2);
            isMoving = true;
        } else if (keyH.sPressed && keyH.aPressed) {
            direction = "downLeft";
            x -= speed / Math.sqrt(2);
            y += speed / Math.sqrt(2);
            isMoving = true;
        } else if (keyH.sPressed && keyH.dPressed) {
            direction = "downRight";
            x += speed / Math.sqrt(2);
            y += speed / Math.sqrt(2);
            isMoving = true;
        } else if (keyH.wPressed) {
            direction = "up";
            y -= speed;
            isMoving = true;
        } else if (keyH.sPressed) {
            direction = "down";
            y += speed;
            isMoving = true;
        } else if (keyH.aPressed) {
            direction = "left";
            x -= speed;
            isMoving = true;
        } else if (keyH.dPressed) {
            direction = "right";
            x += speed;
            isMoving = true;
        }

        // Handle animation
        spriteCounter++;
        if (spriteCounter > 15) {
            spriteNum++;
            if (spriteNum > 3) spriteNum = 1; // Loop through frames
            spriteCounter = 0;
        }

        // Reset to idle if not moving
        if (!isMoving) {
            spriteNum = 1; // Default to the first idle frame
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage currentFrame = null;

        // Extract frames based on direction and spriteNum
        switch (direction) {
            case "up":
                currentFrame = walkingSheet.getSubimage((spriteNum - 1) * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT);
                break;
            case "down":
                currentFrame = walkingSheet.getSubimage((spriteNum - 1) * FRAME_WIDTH, FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT);
                break;
            case "left":
                currentFrame = walkingSheet.getSubimage((spriteNum - 1) * FRAME_WIDTH, 2 * FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT);
                break;
            case "right":
                currentFrame = walkingSheet.getSubimage((spriteNum - 1) * FRAME_WIDTH, 3 * FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT);
                break;
            default:
                currentFrame = idleSheet.getSubimage(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
                break;
        }

        // Draw the current frame
        g2.drawImage(currentFrame, x, y, gp.tileSize, gp.tileSize, null);
    }
}
