import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends JPanel implements KeyListener {

    private BufferedImage carImage;
    private int x, y;

    public Player() {
        try {
            carImage = ImageIO.read(new File("Player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        x = 350;
        y = 480;

        setPreferredSize(new Dimension(carImage.getWidth(), carImage.getHeight()));
        setOpaque(false); // make the panel transparent
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
    }

    public boolean isGameOver(){
        return false;
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(carImage, x, y, null);
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT && x > 200) {
            x -= 10;
        } else if (key == KeyEvent.VK_RIGHT && x < 500) {
            x += 10;
        }
        repaint();
    }

    public void keyTyped(KeyEvent e) {}

    public void keyReleased(KeyEvent e) {}
}