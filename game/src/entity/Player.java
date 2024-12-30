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
            up1 = ImageIO.read(getClass().getResourceAsStream("/img/stolenBack.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/img/stolenBackWalk.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/img/stolenFront.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/img/stolenFrontWalk.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/img/rightStolen.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/img/rightStolenWalk.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/img/leftStolen.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/img/leftStolenWalk.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void update() {
        // Update direction and movement based on key inputs
        if (keyH.wPressed) {
            direction = "up"; // Set direction to up
            y -= speed; // Move up
        } else if (keyH.sPressed) {
            direction = "down"; // Set direction to down
            y += speed; // Move down
        } else if (keyH.aPressed) {
            direction = "left"; // Set direction to left
            x -= speed; // Move left
        } else if (keyH.dPressed) {
            direction = "right"; // Set direction to right
            x += speed; // Move right
        }
        spriteCounter++;
        if (spriteCounter>15) {
            if (spriteNum==1) {
                spriteNum = 2;
            } else if (spriteNum==2) {
                spriteNum=1;
            }
            spriteCounter=0;
        }
    }

    public void draw(Graphics2D g2) {
        //     g2.setColor(Color.white); // Set color for player
        //     g2.fillRect(x, y, gp.tileSize, gp.tileSize); // Draw player as rectangle
        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum==1){
                    image=up1;
                }
                if (spriteNum==2){
                    image=up2;
                }
                break;

            case "down":
                if (spriteNum==1){
                    image=down1;
                }
                if (spriteNum==2){
                    image=down2;
                }
                break;

            case "left":
                if (spriteNum==1){
                    image=left1;
                }
                if (spriteNum==2){
                    image=left2;
                }
                break;

            case "right":
                if (spriteNum==1){
                    image=right1;
                }
                if (spriteNum==2){
                    image=right2;
                }
                break;
        }
        // CHARACTER SIZE
        int characterWidth = (int) (gp.tileSize * 1.5);  // Scale width by 1.5x
        int characterHeight = (int) (gp.tileSize * 1.5); // Scale height by 1.5x

        g2.drawImage(image, x, y, characterWidth, characterHeight, null); // Draw with new size
    }
}
