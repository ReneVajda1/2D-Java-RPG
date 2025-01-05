package Tiles;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];

        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldCol];

        getTileImage();
        loadMap("/maps/testm.txt");
    }

    public void getTileImage(){
        try{
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/img/tiles/grass.png"));

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/img/tiles/wall.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/img/tiles/water.png"));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/img/tiles/earth.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/img/tiles/bush.png"));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/img/tiles/sand.png"));

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/img/tiles/dust.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//this loads map from txt
public void loadMap(String filePath) {
    try {
        InputStream is = getClass().getResourceAsStream(filePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        int row = 0;

        // Read file line by line until all rows are processed
        while (row < gp.maxWorldRow) {
            String line = br.readLine(); // Read one line (row)
            if (line == null) break; // End of file check

            // Split the line into individual numbers
            String[] numbers = line.split(" ");

            // Populate the current row in mapTileNum
            for (int col = 0; col < gp.maxWorldCol; col++) {
                mapTileNum[col][row] = Integer.parseInt(numbers[col]);
            }

            row++; // Move to the next row
        }

        br.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;

            // Calculate screen position relative to the player's position
            int screenX = worldX - gp.player1.worldX + gp.player1.screenX;
            int screenY = worldY - gp.player1.worldY + gp.player1.screenY;

            // Only draw tiles that are within the visible screen bounds
            if (worldX + gp.tileSize > gp.player1.worldX - gp.player1.screenX &&
                    worldX - gp.tileSize < gp.player1.worldX + gp.player1.screenX &&
                    worldY + gp.tileSize > gp.player1.worldY - gp.player1.screenY &&
                    worldY - gp.tileSize < gp.player1.worldY + gp.player1.screenY) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
