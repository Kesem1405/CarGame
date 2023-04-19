import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends JLabel implements KeyListener {

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
        setIcon(new ImageIcon(carImage)); // set the car image as the icon of the JLabel
        setOpaque(false); // make the JLabel transparent
        setPreferredSize(new Dimension(carImage.getWidth(), carImage.getHeight()));
        addKeyListener(this);
        resize(carImage, 90, 120); // resize the car image
        setFocusable(true);
        requestFocusInWindow();
    }

    public boolean isGameOver(){
        return false;
    }

    private BufferedImage resize(BufferedImage image, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        return resizedImage;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT && x > 219) {
            x -= 10;
        } else if (key == KeyEvent.VK_RIGHT && x < 1020) {
            x += 10;
        } else if (key == KeyEvent.VK_UP && y > 0) {
            y -= 10;
        } else if (key == KeyEvent.VK_DOWN && y < 880) {
            y += 10;
        }
        setLocation(x, y);
    }

    public void keyTyped(KeyEvent e) {}

    public void keyReleased(KeyEvent e) {}
}