package org.academiadecodigo.nanderthals;



import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

import static org.academiadecodigo.nanderthals.Game.PADDING;
import static org.academiadecodigo.nanderthals.Game.cellSize;

public class Cursor {
    private Rectangle rectangle;
    private int x;
    private int y;

    public Cursor() {
        this.rectangle = new Rectangle(0,0,cellSize, cellSize);
        this.rectangle.setColor(Color.RED);
        this.x = PADDING;
        this.y = PADDING;
    }

    public void draw(int x, int y) {
        this.rectangle.translate(x, y);
        this.rectangle.draw();
        this.rectangle.fill();
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void moveLeft() {
        rectangle.translate(-cellSize, 0);
        x -= cellSize;
    }

    public void moveRight() {
        rectangle.translate(cellSize, 0);
        x += cellSize;
    }

    public void moveUp() {
        rectangle.translate(0, -cellSize);
        y -= cellSize;
    }

    public void moveDown() {
        rectangle.translate(0, cellSize);
        y += cellSize;
    }


}

