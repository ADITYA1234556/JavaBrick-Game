package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameClass extends JPanel implements KeyListener, ActionListener {

    private BrickGenerator brickGen;

    private boolean gamePlaying = false;
    private int score = 0;
    private int totalBricks = 21;

    private Timer timer;
    private int delay = 5;

    private int paddleX = 310;

    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;

    public GameClass() {
        brickGen = new BrickGenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g){

        // BACKGROUND
        g.setColor(Color.BLACK);
        g.fillRect(1, 1, 692, 592);

        // BORDER
        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0,690,3 );

        // DRAW BRICKS
        brickGen.draw((Graphics2D)g);

        // SCORES
        g.setColor(Color.WHITE);
        g.setFont(new Font("Times New Roman", Font.BOLD, 30));
        g.drawString("Score: " + score, 500, 30);

        // PADDLE
        g.setColor(Color.GREEN);
        g.fillRect(paddleX, 550, 100, 8);

        // BALL
        g.setColor(Color.RED);
        g.fillOval(ballPosX, ballPosY, 20, 20);

        // check game finished
        if (totalBricks <= 0){
            gamePlaying = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Times New Roman", Font.BOLD, 30));
            g.drawString("You Won", 150, 300);

            g.setFont(new Font("Times New Roman", Font.BOLD, 30));
            g.drawString("Press Enter to Restart", 200, 350);
        }

        // check if player lost
        if (ballPosY > 570){
            gamePlaying = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Times New Roman", Font.BOLD, 30));
            g.drawString("Game Over, Final score: " + score, 150, 300);

            g.setFont(new Font("Times New Roman", Font.BOLD, 30));
            g.drawString("Press Enter to Restart", 200, 350);
        }

        g.dispose();

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (gamePlaying) {
            if(new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(paddleX, 550, 100, 8))) {
                ballYdir = -ballYdir;
            }

            A: for (int i = 0; i < brickGen.map.length; i++) {
                for (int j = 0; j < brickGen.map[0].length; j++) {
                    if (brickGen.map[i][j] > 0) {
                        int brickX = j * brickGen.brickWidth + 80;
                        int brickY = i * brickGen.brickHeight + 50;
                        int brickWidth = brickGen.brickWidth;
                        int brickHeight = brickGen.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)) {
                            brickGen.setBrickValue(i, j, 0);
                            totalBricks--;
                            score += 10;

                            if (ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width){
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }

            // MOVE BALL
            ballPosX += ballXdir;
            ballPosY += ballYdir;
            if (ballPosX < 0){ //LEFT BORDER
                ballXdir = -ballXdir;
            }
            if (ballPosY < 0){ // TOP BORDER
                ballYdir = -ballYdir;
            }
            if (ballPosX > 670) {
                ballXdir = -ballXdir;
            }

        }
        repaint();
    }



    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (paddleX >= 600 ){
                paddleX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (paddleX <= 10 ){
                paddleX = 10;
            } else {
                moveLeft();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!gamePlaying) {
                gamePlaying = true;
                ballPosX = 120;
                ballPosY = 350;
                ballXdir = -1;
                ballYdir = -2;
                paddleX = 310;
                score = 0;
                totalBricks = 21;
                brickGen = new BrickGenerator(3,7);
                repaint();
            }
        }
    }
    public void moveRight() {
        gamePlaying = true;
        paddleX += 20;
    }

    public void moveLeft() {
        gamePlaying = true;
        paddleX -= 20;
    }


    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
