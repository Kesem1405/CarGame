import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Car extends JPanel {

    private BufferedImage carImage;
    private int x, y;
    private int speed;

    public Car(int y) {
        Random rand = new Random();
        int carType = rand.nextInt(4) + 1;
        String fileName = "";
        switch(carType) {
            case 1:
                fileName = "Truck.png";
                break;
            case 2:
                fileName = "Police.png";
                break;
            case 3:
                fileName = "GreenCar.png";
                break;
            case 4:
                fileName = "BlueCar.png";
                break;
        }
        try {
            carImage = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.y = y;
        x = rand.nextInt(534) + 128;
        speed = rand.nextInt(5) + 5;
        setPreferredSize(new Dimension(70, 30)); // set size
        setOpaque(false);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean hasCollided(Player player) {
        Rectangle carRect = new Rectangle(x, y, 70, 30);
        Rectangle playerRect = new Rectangle(player.getX(), player.getY(), 70, 30);
        return carRect.intersects(playerRect);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(carImage, 0, 0, 70, 30, null);
    }

    public void move() {
        y += speed;
        if (y > 600) {
            // Car has gone off the bottom of the screen
            getParent().remove(this);
        }
    }
}