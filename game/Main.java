package game;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        GameClass gameClass = new GameClass();

        JFrame frame = new JFrame();
        frame.setBounds(10, 10,700,700);
        frame.setTitle("Brickbreaker Game");
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(gameClass);
        frame.addKeyListener(gameClass);
    }
}
