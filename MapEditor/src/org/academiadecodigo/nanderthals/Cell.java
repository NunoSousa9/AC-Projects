package org.academiadecodigo.nanderthals;


import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class Cell extends Rectangle {
    private boolean isPainted;

    public Cell(double v, double v1, double v2, double v3) {
        super(v, v1, v2, v3);
    }

    public boolean isPainted() {
        return isPainted;
    }

    public void setPainted(boolean painted) {
        isPainted = painted;
    }

    @Override
    public String toString() {
        return "Cell{x=" + this.getX() +
                "y=" + this.getY() +
                ", width=" + this.getWidth() +
                ", height=" + this.getHeight() +
                ", isPainted=" + isPainted +
                '}';
    }
}
