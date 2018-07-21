package hu.klenium.tetris;

import hu.klenium.tetris.view.SquareView;
import javafx.scene.canvas.GraphicsContext;

public class BoardCell {
    private SquareView view = null;
    private final int posX;
    private final int posY;
    public BoardCell(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }
    public SquareView getView() {
        return view;
    }
    public void setView(SquareView view) {
        this.view = view;
    }
    public void clear() {
        this.view = null;
    }
    public boolean isEmpty() {
        return view == null;
    }
    public void update(GraphicsContext context, int size) {
        if (!isEmpty())
            view.update(context, posX, posY, size);
    }
}