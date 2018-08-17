using hu.klenium.tetris.logic.board;
using hu.klenium.tetris.logic.tetromino;
using hu.klenium.tetris.util;

namespace hu.klenium.tetris.logic
{
    public class TetrisGame
    {
        protected readonly Board board;
        protected Tetromino fallingTetromino;
        public TetrisGame(Dimension size, int fallingSpeed)
        {
            board = new Board(size);
        }
        public void Start()
        {

        }
    }
}