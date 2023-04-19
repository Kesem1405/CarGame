import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class GameFrame extends JFrame {

    private BufferedImage backgroundImage;
    private BackgroundPanel backgroundPanel;
    private Player player;
    private Thread backgroundThread;

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
                player = new Player();
                backgroundThread = new Thread(() -> {
                    backgroundPanel.add(player); // add the player instance to the background panel
                    System.out.println(backgroundPanel.getComponentCount()); // print the number of components in the background panel
                    while (!player.isGameOver()) {
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
        }

        public void addPlayer(Player player) {
            add(player);
            setComponentZOrder(player, 0); // make sure the player is painted in front of the background image
        }
    }
}