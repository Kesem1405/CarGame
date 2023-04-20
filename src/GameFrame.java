import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;

public class GameFrame extends JFrame {

    private BufferedImage backgroundImage;
    private BackgroundPanel backgroundPanel;
    private Player player;
    private Thread backgroundThread;
    private List<Car> cars = new ArrayList<>();
    private static final Random random = new Random();

    public GameFrame() {
        try {
            backgroundImage = ImageIO.read(new File("Road.png"));

            setTitle("Car game");
            setSize(1300, 1000);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setResizable(false);
            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(Color.BLACK);
            buttonPanel.setLayout(new GridLayout(3, 1));
            buttonPanel.setBackground(Color.WHITE);
            JButton startButton = new JButton("Start Game");
            JButton instructionsButton = new JButton("Instructions");
            JButton exitButton = new JButton("Exit Game");
            startButton.addActionListener(e -> {
                backgroundPanel.addCar();
                player = new Player();
                backgroundThread = new Thread(() -> {
                    backgroundPanel.add(player); // add the player instance to the background panel
                    System.out.println(backgroundPanel.getComponentCount()); // print the number of components in the background panel
                    boolean isOver = backgroundPanel.checkCollisions();
                    while (!player.isGameOver(isOver)) {
                        backgroundPanel.moveDown();
                        backgroundPanel.repaint();
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                    backgroundThread = null; // set the backgroundThread to null after it has stopped
                });
                backgroundThread.start();
                player.setVisible(true);
                player.requestFocusInWindow();
                startButton.setVisible(false);
                exitButton.setVisible(false);
                instructionsButton.setVisible(false);
                // Remove the button panel from the content pane
                remove(buttonPanel);
                setContentPane(backgroundPanel);
                validate();
            });
            exitButton.addActionListener(e -> System.exit(0));
            buttonPanel.add(startButton);
            buttonPanel.add(instructionsButton);
            buttonPanel.add(exitButton);
            // Set up the background panel
            backgroundPanel = new BackgroundPanel();
            setContentPane(backgroundPanel);

            // Add the button panel to the CENTER of the content pane
            add(buttonPanel, BorderLayout.CENTER);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        setVisible(true);
    }

    private class BackgroundPanel extends JPanel {
        private int y = 0;

        public void moveDown() {
            y += 10;
            if (y >= getHeight()) {
                y = 0;
            }
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, y, getWidth(), getHeight(), null);
            g.drawImage(backgroundImage, 0, y - getHeight(), getWidth(), getHeight(), null);
            for (Car car : cars) {
                car.paint(g);
            }
        }

        public void addCar() {
            String[] carNames = {"Truck", "GreenCar", "BlueCar", "RedCar", "Police"};
            int carWidth = 120;
            int carHeight = 80;
            int x = random.nextInt(getWidth() - carWidth);
            int y = -random.nextInt(getHeight()); // spawn the car above the top edge of the panel
            String carName = carNames[random.nextInt(carNames.length)];
            ImageIcon imageIcon = new ImageIcon(carName + ".png");
            Car car = new Car(imageIcon, player);
            car.setBounds(x, y, carWidth, carHeight);
            add(car);
            setComponentZOrder(car, 0); // make sure the car is painted in front of the background image
            cars.add(car); // add the car to the list of cars
        }

        public List<Car> getCars() {
            return cars;
        }
        private boolean checkCollisions() {
            Car closestCar = null;
            boolean isGameOver = false;
            int minDistance = Integer.MAX_VALUE;
            for (Car car : cars) {
                int distance = car.getY() - player.getY() - player.getHeight();
                if (distance < 0) {
                    continue; // skip cars that are above the player
                }
                if (distance < minDistance) {
                    closestCar = car;
                    minDistance = distance;
                }
            }
            if (closestCar != null && closestCar.getBounds().intersects(player.getBounds())) {
                isGameOver = true;
            }
            return isGameOver;
        }
    }
}