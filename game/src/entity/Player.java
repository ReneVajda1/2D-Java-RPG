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

    private BufferedImage idleRightSheet, idleLeftSheet, idleUpRightSheet, idleUpLeftSheet,
            idleDownRightSheet, idleDownLeftSheet, walkingRightSheet, walkingLeftSheet,
            walkingUpRightSheet, walkingUpLeftSheet, walkingDownRightSheet, walkingDownLeftSheet;

    private BufferedImage[] idleFrames, walkingFrames;

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
        direction = "right"; // Default facing direction
        currentFrame = 0;
        spriteCounter = 0;
    }

    private void loadSpritesheets() {
        try {
            System.out.println("Loading spritesheets...");

            // Load idle spritesheets
            idleRightSheet = ImageIO.read(getClass().getResourceAsStream("/img/b-idle-right.png"));
            idleLeftSheet = ImageIO.read(getClass().getResourceAsStream("/img/b-idle-left.png"));
            idleUpRightSheet = ImageIO.read(getClass().getResourceAsStream("/img/b-idle-rightUP.png"));
            idleUpLeftSheet = ImageIO.read(getClass().getResourceAsStream("/img/b-idle-leftUP.png"));
            idleDownRightSheet = ImageIO.read(getClass().getResourceAsStream("/img/b-idle-rightDown.png"));
            idleDownLeftSheet = ImageIO.read(getClass().getResourceAsStream("/img/b-idle-leftDown.png"));

            // Load walking spritesheets
            walkingRightSheet = ImageIO.read(getClass().getResourceAsStream("/img/b-walk-right.png"));
            walkingLeftSheet = ImageIO.read(getClass().getResourceAsStream("/img/b-walk-left.png"));
            walkingUpRightSheet = ImageIO.read(getClass().getResourceAsStream("/img/b-walk-upRight.png"));
            walkingUpLeftSheet = ImageIO.read(getClass().getResourceAsStream("/img/b-walk-upLeft.png"));
            walkingDownRightSheet = ImageIO.read(getClass().getResourceAsStream("/img/b-walk-downRight.png"));
            walkingDownLeftSheet = ImageIO.read(getClass().getResourceAsStream("/img/b-walk-downLeft.png"));

            System.out.println("All spritesheets loaded successfully!");
        } catch (IOException e) {
            System.out.println("Failed to load a spritesheet. Check paths.");
            e.printStackTrace();
        }
    }

    private void loadFrames() {
        FRAME_WIDTH = walkingRightSheet.getWidth() / WALKING_FRAME_COUNT;
        FRAME_HEIGHT = walkingRightSheet.getHeight(); // Assuming 1 row for all spritesheets

        idleFrames = new BufferedImage[IDLE_FRAME_COUNT];
        walkingFrames = new BufferedImage[WALKING_FRAME_COUNT];

        loadDirectionalFrames("right"); // Default to right-facing animations
    }

    public void update() {
        double diagonalSpeed = speed / Math.sqrt(2); // Adjust diagonal speed

        if (keyH.wPressed && keyH.dPressed) {
            direction = "upRight";
            x += diagonalSpeed;
            y -= diagonalSpeed;
        } else if (keyH.wPressed && keyH.aPressed) {
            direction = "upLeft";
            x -= diagonalSpeed;
            y -= diagonalSpeed;
        } else if (keyH.sPressed && keyH.dPressed) {
            direction = "downRight";
            x += diagonalSpeed;
            y += diagonalSpeed;
        } else if (keyH.sPressed && keyH.aPressed) {
            direction = "downLeft";
            x -= diagonalSpeed;
            y += diagonalSpeed;
        } else if (keyH.wPressed) {
            direction = "up";
            y -= speed;
        } else if (keyH.sPressed) {
            direction = "down";
            y += speed;
        } else if (keyH.aPressed) {
            direction = "left";
            x -= speed;
        } else if (keyH.dPressed) {
            direction = "right";
            x += speed;
        } else {
            direction = "idle";
        }

        loadDirectionalFrames(direction);

        // Update animation frame
        spriteCounter++;
        if (spriteCounter > 10) { // Change frame every 10 updates
            currentFrame = (currentFrame + 1) % (direction.equals("idle") ? IDLE_FRAME_COUNT : WALKING_FRAME_COUNT);
            spriteCounter = 0;
        }
    }

    private void loadDirectionalFrames(String direction) {
        try {
            BufferedImage currentIdleSheet = null;
            BufferedImage currentWalkingSheet = null;

            switch (direction) {
                case "right":
                    currentIdleSheet = idleRightSheet;
                    currentWalkingSheet = walkingRightSheet;
                    break;
                case "left":
                    currentIdleSheet = idleLeftSheet;
                    currentWalkingSheet = walkingLeftSheet;
                    break;
                case "upRight":
                    currentIdleSheet = idleUpRightSheet;
                    currentWalkingSheet = walkingUpRightSheet;
                    break;
                case "upLeft":
                    currentIdleSheet = idleUpLeftSheet;
                    currentWalkingSheet = walkingUpLeftSheet;
                    break;
                case "downRight":
                    currentIdleSheet = idleDownRightSheet;
                    currentWalkingSheet = walkingDownRightSheet;
                    break;
                case "downLeft":
                    currentIdleSheet = idleDownLeftSheet;
                    currentWalkingSheet = walkingDownLeftSheet;
                    break;
            }

            if (currentIdleSheet != null && currentWalkingSheet != null) {
                for (int i = 0; i < IDLE_FRAME_COUNT; i++) {
                    idleFrames[i] = currentIdleSheet.getSubimage(i * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT);
                }
                for (int i = 0; i < WALKING_FRAME_COUNT; i++) {
                    walkingFrames[i] = currentWalkingSheet.getSubimage(i * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading directional frames for: " + direction);
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        try {
            BufferedImage imageToDraw = direction.equals("idle") ? idleFrames[currentFrame] : walkingFrames[currentFrame];
            g2.drawImage(imageToDraw, x, y, null);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Animation frame out of bounds: " + currentFrame);
            e.printStackTrace();
            currentFrame = 0; // Reset the frame to avoid crashing
        }
    }
}
