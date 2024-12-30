package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp; // Reference to GamePanel
    KeyHandler keyH; // Reference to KeyHandler

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp; // Initialize GamePanel reference
        this.keyH = keyH; // Initialize KeyHandler reference
        setDefaultValues(); // Set initial player values
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100; // Initial X position
        y = 100; // Initial Y position
        speed = 4; // Initial speed
        direction = "down";
    }
    // this makes SPRITE IMAGES POSSIBLE
    public void getPlayerImage(){
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/img/barb-right-walk.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/img/barb-right-idle.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/img/barb-left-idle.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/img/barb-left-walk.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/img/barb-right-idle.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/img/barb-right-walk.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/img/barb-left-idle.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/img/barb-left-walk.png"));

            upLeft1 = ImageIO.read(getClass().getResourceAsStream("/img/barb-left-idle.png"));
            upRight1 = ImageIO.read(getClass().getResourceAsStream("/img/barb-left-walk.png"));
            downLeft1 = ImageIO.read(getClass().getResourceAsStream("/img/barb-left-idle.png"));
            downRight1 = ImageIO.read(getClass().getResourceAsStream("/img/barb-left-walk.png"));

            upLeft2 = ImageIO.read(getClass().getResourceAsStream("/img/barb-left-idle.png"));
            upRight2 = ImageIO.read(getClass().getResourceAsStream("/img/barb-right-idle.png"));

            downLeft2 = ImageIO.read(getClass().getResourceAsStream("/img/barb-left-idle.png"));
            downRight2 = ImageIO.read(getClass().getResourceAsStream("/img/barb-right-idle.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void update() {
        // Adjust diagonal movement speed
        double diagonalSpeed = speed / Math.sqrt(2);

        // Movement logic
        boolean isMoving = false;
        if (keyH.wPressed && keyH.aPressed) {
            direction = "upLeft";
            x -= diagonalSpeed;
            y -= diagonalSpeed;
            isMoving = true;
        } else if (keyH.wPressed && keyH.dPressed) {
            direction = "upRight";
            x += diagonalSpeed;
            y -= diagonalSpeed;
            isMoving = true;
        } else if (keyH.sPressed && keyH.aPressed) {
            direction = "downLeft";
            x -= diagonalSpeed;
            y += diagonalSpeed;
            isMoving = true;
        } else if (keyH.sPressed && keyH.dPressed) {
            direction = "downRight";
            x += diagonalSpeed;
            y += diagonalSpeed;
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

        // Handle sprite animation
        spriteCounter++;
        if (spriteCounter > 15) { // Change animation every 15 updates
            if (spriteNum == 1) {
                spriteNum = 2;
            } else {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        // If not moving, set to idle pose
        if (!isMoving) {
            spriteNum = 1; // Default idle pose
        }
    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        // Switch images based on direction and sprite number
        switch (direction) {
            case "up":
                if (spriteNum == 1) image = up1;
                else if (spriteNum == 2) image = up2;
                break;
            case "down":
                if (spriteNum == 1) image = down1;
                else if (spriteNum == 2) image = down2;
                break;
            case "left":
                if (spriteNum == 1) image = left1;
                else if (spriteNum == 2) image = left2;
                break;
            case "right":
                if (spriteNum == 1) image = right1;
                else if (spriteNum == 2) image = right2;
                break;
            case "upLeft":
                if (spriteNum == 1) image = upLeft1;
                else if (spriteNum == 2) image = upLeft2;
                break;
            case "upRight":
                if (spriteNum == 1) image = upRight1;
                else if (spriteNum == 2) image = upRight2;
                break;
            case "downLeft":
                if (spriteNum == 1) image = downLeft1;
                else if (spriteNum == 2) image = downLeft2;
                break;
            case "downRight":
                if (spriteNum == 1) image = downRight1;
                else if (spriteNum == 2) image = downRight2;
                break;
        }
        // CHARACTER SIZE
        int characterWidth = (int) (gp.tileSize * 1.5);  // Scale width by 1.5x
        int characterHeight = (int) (gp.tileSize * 1.5); // Scale height by 1.5x

        g2.drawImage(image, x, y, characterWidth, characterHeight, null); // Draw with new size
    }
}
