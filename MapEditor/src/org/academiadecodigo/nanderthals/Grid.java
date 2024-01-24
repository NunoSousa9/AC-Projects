package org.academiadecodigo.nanderthals;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;


import java.util.ArrayList;

public class Grid extends ArrayList<Cell> {

    public Grid(int cols, int rows) {
        createGrid(cols, rows);
        drawGrid();
    }

    public void createGrid(int columns, int rows) {

        for (int col = 0; col < columns; col++) {
            for (int row = 0; row < rows; row++) {
                int x = col * Game.cellSize + Game.PADDING;
                int y = row * Game.cellSize + Game.PADDING;
                this.add(new Cell(x, y, Game.cellSize, Game.cellSize));
            }
        }
    }

    private void drawGrid() {
        for (Cell c : this) {
            c.draw();
        }
    }

    public void paintCell(int col, int row) {
        for (int i= 0; i<this.size(); i++) {
            if (this.get(i).getX() == col && this.get(i).getY() == row) {
                if(!this.get(i).isPainted()) {
                    this.get(i).setPainted(true);
                    this.get(i).setColor(Color.BLACK);
                    this.get(i).fill();
                    return;
                }
                this.get(i).setPainted(false);
                this.get(i).draw();
            }
        }
    }

    public void clearGrid() {
        for (Cell c : this) {
            c.setPainted(false);
            c.draw();
        }
    }
}
