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

    public final int screenX;
    public final int screenY;
    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX =gp.screenWidth / 2 - (gp.tileSize/2); // this returns halfway point of screen
        screenY = gp.screenHeight / 2 - (gp.tileSize/2);
        setDefaultValues();
        loadSpritesheets();
        loadFrames();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize*23;
        worldY = gp.tileSize*21;
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
        boolean isMoving = false;

        if (keyH.wPressed && keyH.dPressed) {
            direction = "upRight";
            worldX += diagonalSpeed;
            worldY -= diagonalSpeed;
            isMoving = true;
        } else if (keyH.wPressed && keyH.aPressed) {
            direction = "upLeft";
            worldX -= diagonalSpeed;
            worldY -= diagonalSpeed;
            isMoving = true;
        } else if (keyH.sPressed && keyH.dPressed) {
            direction = "downRight";
            worldX += diagonalSpeed;
            worldY += diagonalSpeed;
            isMoving = true;
        } else if (keyH.sPressed && keyH.aPressed) {
            direction = "downLeft";
            worldX -= diagonalSpeed;
            worldY += diagonalSpeed;
            isMoving = true;
        } else if (keyH.wPressed) {
            direction = "up";
            worldY -= speed;
            isMoving = true;
        } else if (keyH.sPressed) {
            direction = "down";
            worldY += speed;
            isMoving = true;
        } else if (keyH.aPressed) {
            direction = "left";
            worldX -= speed;
            isMoving = true;
        } else if (keyH.dPressed) {
            direction = "right";
            worldX += speed;
            isMoving = true;
        }

        loadDirectionalFrames(direction);

        // Update animation frame
        spriteCounter++;
        if (isMoving) {
            if (spriteCounter > 10) { // Adjust walking speed
                currentFrame = (currentFrame + 1) % WALKING_FRAME_COUNT; // 0 to 8
                spriteCounter = 0;
            }
        } else {
            if (spriteCounter > 20) { // Adjust idle speed
                currentFrame = (currentFrame + 1) % IDLE_FRAME_COUNT; // 0 to 2
                spriteCounter = 0;
            }
        }
    }

    private void loadDirectionalFrames(String direction) {
        try {
            if (direction.equals("right")) {
                loadFramesFromSpritesheet(idleRightSheet, walkingRightSheet);
            } else if (direction.equals("left")) {
                loadFramesFromSpritesheet(idleLeftSheet, walkingLeftSheet);
            } else if (direction.equals("upRight")) {
                loadFramesFromSpritesheet(idleUpRightSheet, walkingUpRightSheet);
            } else if (direction.equals("upLeft")) {
                loadFramesFromSpritesheet(idleUpLeftSheet, walkingUpLeftSheet);
            } else if (direction.equals("downRight")) {
                loadFramesFromSpritesheet(idleDownRightSheet, walkingDownRightSheet);
            } else if (direction.equals("downLeft")) {
                loadFramesFromSpritesheet(idleDownLeftSheet, walkingDownLeftSheet);
            }
        } catch (Exception e) {
            System.out.println("Error loading frames for direction: " + direction);
            e.printStackTrace();
        }
    }

    private void loadFramesFromSpritesheet(BufferedImage idleSheet, BufferedImage walkingSheet) {
        for (int i = 0; i < IDLE_FRAME_COUNT; i++) {
            idleFrames[i] = idleSheet.getSubimage(i * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT);
        }
        for (int i = 0; i < WALKING_FRAME_COUNT; i++) {
            walkingFrames[i] = walkingSheet.getSubimage(i * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT);
        }
    }

    public void draw(Graphics2D g2) {
        try {
            BufferedImage imageToDraw = (keyH.wPressed || keyH.aPressed || keyH.sPressed || keyH.dPressed)
                    ? walkingFrames[currentFrame]
                    : idleFrames[currentFrame];

            // Scale the character to 1.5x size
            int scaledWidth = (int) (FRAME_WIDTH * 1.5);
            int scaledHeight = (int) (FRAME_HEIGHT * 1.5);

            g2.drawImage(imageToDraw, screenX, screenY, scaledWidth, scaledHeight, null);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Animation frame out of bounds: " + currentFrame);
            e.printStackTrace();
            currentFrame = 0; // Reset to the first frame of the current animation
        }
    }
}
