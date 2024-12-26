package game;

import java.awt.*;

public class BrickGenerator {

    public int map[][];
    public int brickWidth;
    public int brickHeight;

    public BrickGenerator(int row, int column) {
        map = new int[row][column];
        for (int i=0; i<map.length; i++){
            for (int j=0; j<map[0].length; j++){
                map[i][j] = 1;
            }
        }

        brickWidth = 540 /column;
        brickHeight = 150 /row;
    }

    public void draw(Graphics2D g) {
        for (int i=0; i<map.length; i++){
            for (int j=0; j<map[0].length; j++){
                if (map[i][j] > 0 ){
                    g.setColor(Color.BLUE);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);


                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.BLACK);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    public void setBrickValue(int row, int column, int value) {
        map[row][column] = value;
    }
}
