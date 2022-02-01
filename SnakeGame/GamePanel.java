package SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    private final int WIDTH_PANEL = 600;
    private final int HEIGHT_PANEL = 600;

    private final int UNIT_SIZE = 30;
    private final int GAME_UNITS = (WIDTH_PANEL * HEIGHT_PANEL) / (UNIT_SIZE * UNIT_SIZE);

    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];

    private static final int DELAY = 85;
    private static boolean isRunning = false;
    private static Timer timer;
    private static Random random;
    private static char direction = 'R';

    private static final Snake snake = new Snake();
    public static final Apple apple = new Apple();
    private static final Stone stone = new Stone();

    public GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(WIDTH_PANEL, HEIGHT_PANEL));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new SnakeAdapter());
        startGame();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        drawPanel(graphics);
    }

    private void drawPanel(Graphics graphics) {
        if (isRunning) {

            graphics.setColor(Color.RED);
            graphics.fillOval(apple.getX(), apple.getY(), UNIT_SIZE, UNIT_SIZE);

            graphics.setColor(Color.GRAY);
            graphics.fillOval(stone.getX(), stone.getY(), UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < snake.getSnakeLength(); i++) {
                if (i % 2 == 0) {
                    graphics.setColor(Color.RED);
                } else {
                    graphics.setColor(Color.WHITE);
                }
                graphics.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }

            graphics.setColor(Color.RED);
            graphics.setFont(new Font("Arial", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(graphics.getFont());
            graphics.drawString("Score: " + apple.getEatenApples(),
                    (WIDTH_PANEL - metrics.stringWidth("Score: " + apple.getEatenApples()))
                            / 2, graphics.getFont().getSize());

        } else {
            gameOver(graphics);
        }
    }

    private void gameOver(Graphics graphics) {
        //Score
        graphics.setColor(Color.RED);
        graphics.setFont(new Font("Arial", Font.BOLD, 40));
        FontMetrics scoreFont = getFontMetrics(graphics.getFont());
        graphics.drawString("Score: " + apple.getEatenApples(),
                (WIDTH_PANEL - scoreFont.stringWidth("Score: " + apple.getEatenApples())) / 2,
                graphics.getFont().getSize());

        //Game Over
        graphics.setColor(Color.RED);
        graphics.setFont(new Font(" Arial", Font.BOLD, 75));
        FontMetrics gameOverFont = getFontMetrics(graphics.getFont());
        graphics.drawString("Game Over", (WIDTH_PANEL -
                gameOverFont.stringWidth("Game Over")) / 2, HEIGHT_PANEL / 2);

        //PLay Again
        graphics.setColor(Color.RED);
        graphics.setFont(new Font(" Arial", Font.BOLD, 30));
        graphics.drawString("press enter or space", (WIDTH_PANEL / 2) - 135,
                (HEIGHT_PANEL / 2) + 40);
    }

    public void startGame() {
        timer = new Timer(DELAY, this);
        isRunning = true;
        timer.start();
        displayApple();
        putStone();
    }

    private void putStone() {
        int stoneX = random.nextInt((WIDTH_PANEL / UNIT_SIZE)) * UNIT_SIZE;
        int stoneY = random.nextInt((HEIGHT_PANEL / UNIT_SIZE)) * UNIT_SIZE;

        stone.setX(stoneX);
        stone.setY(stoneY);
    }

    private void displayApple() {
        int appleX = random.nextInt((WIDTH_PANEL / UNIT_SIZE)) * UNIT_SIZE;
        int appleY = random.nextInt((HEIGHT_PANEL / UNIT_SIZE)) * UNIT_SIZE;

        apple.setX(appleX);
        apple.setY(appleY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning) {
            moveSnake();
            checkForApples();
            checkCollisions();
        }
        repaint();
    }

    public void moveSnake() {
        for (int i = snake.getSnakeLength(); i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U' -> y[0] = (y[0] - UNIT_SIZE + HEIGHT_PANEL) % HEIGHT_PANEL;
            case 'D' -> y[0] = (y[0] + UNIT_SIZE) % HEIGHT_PANEL;
            case 'L' -> x[0] = (x[0] - UNIT_SIZE + WIDTH_PANEL) % WIDTH_PANEL;
            case 'R' -> x[0] = (x[0] + UNIT_SIZE) % WIDTH_PANEL;
        }
    }

    public void checkForApples() {
        //checks if snake coordinates of the head are equal to the apple coordinates
        if ((x[0] == apple.getX()) && (y[0] == apple.getY())) {
            apple.eatApple();
            snake.increaseLength();
            displayApple();
            putStone();
        }
    }

    public void checkCollisions() {
        //checks if snake tries to eat itself
        for (int i = snake.getSnakeLength(); i > 0; i--) {
            if ((x[0] == x[i]) && y[0] == y[i]) {
                isRunning = false;
                break;
            }
        }

        if ((x[0] == stone.getX()) && (y[0] == stone.getY())) {
            isRunning = false;
        }

        if (!isRunning) {
            timer.stop();
        }
    }

    public class SnakeAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;

                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;

                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;

                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;

                case KeyEvent.VK_ENTER:
                case KeyEvent.VK_SPACE:
                    if (!isRunning) {
                        restartGame();
                    }
                    break;
            }
        }
    }

    private void restartGame() {
        snake.resetLength();
        apple.resetApples();
        startGame();
    }
}