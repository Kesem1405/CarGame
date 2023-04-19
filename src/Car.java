import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Car extends JLabel implements Runnable {

    private static final int WIDTH = 90;
    private static final int HEIGHT = 120;
    private static final int SPEED = 5;
    private static final Random RANDOM = new Random();

    private boolean collided;
    private Player player;

    public Car(ImageIcon imageIcon, Player player) {
        super(imageIcon);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setOpaque(false);
        this.player = player;
    }

    public void run() {
        while (getY() < player.getY() + player.getHeight()) {
            setLocation(getX(), getY() + SPEED);
            if (getBounds().intersects(player.getBounds())) {
                collided = true;
                break;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        setVisible(false);
    }

    public boolean hasCollided() {
        return collided;
    }

    public static Car createRandomCar(Player player) {
        String[] carNames = {"Truck", "PoliceCar", "BlueCar", "RedCar", "GreenCar"};
        int index = RANDOM.nextInt(carNames.length);
        ImageIcon imageIcon = new ImageIcon(carNames[index] + ".png");
        int x = 219 + RANDOM.nextInt(871 - WIDTH);
        int y = -HEIGHT - RANDOM.nextInt(1000);
        Car car = new Car(imageIcon, player);
        car.setLocation(x, y);
        return car;
    }
}