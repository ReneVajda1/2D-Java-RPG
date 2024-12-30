package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean wPressed, aPressed, sPressed, dPressed; // Key states

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // Get key code

        if (code == KeyEvent.VK_W) wPressed = true; // W key pressed
        if (code == KeyEvent.VK_S) sPressed = true; // S key pressed
        if (code == KeyEvent.VK_A) aPressed = true; // A key pressed
        if (code == KeyEvent.VK_D) dPressed = true; // D key pressed
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode(); // Get key code

        if (code == KeyEvent.VK_W) wPressed = false; // W key released
        if (code == KeyEvent.VK_S) sPressed = false; // S key released
        if (code == KeyEvent.VK_A) aPressed = false; // A key released
        if (code == KeyEvent.VK_D) dPressed = false; // D key released
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }
}
