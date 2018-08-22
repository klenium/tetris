using hu.klenium.tetris.logic.board;
using hu.klenium.tetris.logic.tetromino;
using hu.klenium.tetris.util;

namespace hu.klenium.tetris.logic
{
    public class TetrisGame
    {
        protected bool isRunning;
        protected readonly Board board;
        protected Tetromino fallingTetromino = null;
        private readonly PeriodicTask gravity;

        public delegate void TetrominoStateChange(Tetromino tetromino);
        public delegate void BoardStateChange(Board board);
        public delegate void GameOverEvent();
        public event TetrominoStateChange OnTetrominoStateChanged;
        public event BoardStateChange OnBoardStateChanged;
        public event GameOverEvent OnGameOver;

        public TetrisGame(Dimension size, int fallingSpeed)
        {
            board = new Board(size);
            gravity = new PeriodicTask(() => HandleCommand(Command.FALL), fallingSpeed);
        }

        public void Start()
        {
            isRunning = GenerateNextTetromino();
            if (isRunning)
            {
                gravity.Start();
                OnTetrominoStateChanged?.Invoke(fallingTetromino);
                OnBoardStateChanged?.Invoke(board);
            }
        }
        private void Stop()
        {
            isRunning = false;
            gravity.Stop();
            OnGameOver?.Invoke();
        }

        public void HandleCommand(Command command)
        {
            if (!isRunning)
                return;
            bool stateChanged = false;
            switch (command)
            {
                case Command.ROTATE:
                    stateChanged = RotatetTetromino();
                    break;
                case Command.MOVE_LEFT:
                    stateChanged = MoveTetrominoLeft();
                    break;
                case Command.MOVE_RIGHT:
                    stateChanged = MoveTetrominoRight();
                    break;
                case Command.MOVE_DOWN:
                    gravity.Reset();
                    stateChanged = MoveTetrominoDown();
                    break;
                case Command.FALL:
                    stateChanged = MoveTetrominoDown();
                    break;
                case Command.DROP:
                    bool lastMovedDown;
                    do
                    {
                        lastMovedDown = MoveTetrominoDown();
                        stateChanged |= lastMovedDown;
                    } while (lastMovedDown);
                    break;
            }
            if (stateChanged && isRunning)
                OnTetrominoStateChanged?.Invoke(fallingTetromino);
        }

        private void TetrominoLanded()
        {
            board.AddTetromino(fallingTetromino);
            board.RemoveFullRows(fallingTetromino.Position.y, fallingTetromino.BoundingBox.height);
            OnBoardStateChanged?.Invoke(board);
            bool tetrominoAdded = GenerateNextTetromino();
            if (tetrominoAdded)
            {
                gravity.Reset();
                OnTetrominoStateChanged?.Invoke(fallingTetromino);
            }
            else
                Stop();
        }
        private bool GenerateNextTetromino()
        {
            int type = GetNextTetrominoType();
            fallingTetromino = new Tetromino(type, board);
            return fallingTetromino.MoveToInitialPos();
        }
        protected virtual int GetNextTetrominoType()
        {
            return Random.FromRange(0, 6);
        }
        private bool MoveTetrominoLeft()
        {
            return fallingTetromino.MoveLeft();
        }
        private bool MoveTetrominoDown()
        {
            bool moved = fallingTetromino.MoveDown();
            if (!moved)
                TetrominoLanded();
            return moved;
        }
        private bool MoveTetrominoRight()
        {
            return fallingTetromino.MoveRight();
        }
        private bool RotatetTetromino()
        {
            return fallingTetromino.RotateRight();
        }
    }
}