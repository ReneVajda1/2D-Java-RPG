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

    private BufferedImage idleSheet, walkingSheet; // Spritesheets
    private BufferedImage[] idleFrames, walkingFrames; // Frames for animations

    private final int IDLE_FRAME_COUNT = 3; // Number of frames in idle spritesheet
    private final int WALKING_FRAME_COUNT = 9; // Number of frames in walking spritesheet

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        loadSpritesheets();
        loadFrames();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "idle";
    }

    private void loadSpritesheets() {
        try {
            idleSheet = ImageIO.read(getClass().getResourceAsStream("/img/b-idle-right.png"));
            walkingSheet = ImageIO.read(getClass().getResourceAsStream("/img/b-walk-right.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFrames() {
        FRAME_WIDTH = walkingSheet.getWidth() / WALKING_FRAME_COUNT;
        FRAME_HEIGHT = walkingSheet.getHeight(); // Assuming 1 row

        idleFrames = new BufferedImage[IDLE_FRAME_COUNT];
        walkingFrames = new BufferedImage[WALKING_FRAME_COUNT];

        for (int i = 0; i < IDLE_FRAME_COUNT; i++) {
            idleFrames[i] = idleSheet.getSubimage(i * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT);
        }

        for (int i = 0; i < WALKING_FRAME_COUNT; i++) {
            walkingFrames[i] = walkingSheet.getSubimage(i * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT);
        }
    }

    public void update() {
        boolean isMoving = false;

        if (keyH.wPressed) {
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

        // Update animation frame
        spriteCounter++;
        if (spriteCounter > 10) { // Change frame every 10 updates
            currentFrame = (currentFrame + 1) % (isMoving ? WALKING_FRAME_COUNT : IDLE_FRAME_COUNT);
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage imageToDraw = isMoving() ? walkingFrames[currentFrame] : idleFrames[currentFrame];
        g2.drawImage(imageToDraw, x, y, FRAME_WIDTH, FRAME_HEIGHT, null);
    }

    private boolean isMoving() {
        return keyH.wPressed || keyH.sPressed || keyH.aPressed || keyH.dPressed;
    }
}
