using hu.klenium.tetris.util;
using hu.klenium.tetris.logic.board;
using System;

namespace hu.klenium.tetris.logic.tetromino
{
    public class Tetromino
    {
        private Board board;
        private TetrominoData[] Parts { get; }
        private Point Position { get; set; } = new Point(0, 0);
        private int _rotation = 0;
        private int Rotation {
            get { return _rotation; }
            set { _rotation = (value) % Parts.Length; }
        }
        public Tetromino(int type, Board board)
        {
            this.board = board;
            Parts = TetrominoDataFactory.GetData(type);
        }
        public TetrominoData GetCurrentData()
        {
            return Parts[Rotation];
        }
        public Point GetPosition()
        {
            return Position;
        }

        public bool MoveToInitialPos()
        {
            Dimension boardSize = board.Size;
            float centerOfBoard = boardSize.width / 2.0f;
            Dimension tetrominoBoundingBox = Parts[Rotation].BoundingBox;
            float halfTetrominoWidth = tetrominoBoundingBox.width / 2.0f;
            int centeredTetrominoPosX = (int)Math.Ceiling(centerOfBoard - halfTetrominoWidth);
            return TryPush(new Point(centeredTetrominoPosX, 0));
        }
        public bool RotateRight()
        {
            ++Rotation;
            return true;
        }
        public bool MoveLeft()
        {
            return TryPush(new Point(-1, 0));
        }
        public bool MoveDown()
        {
            return TryPush(new Point(0, 1));
        }
        public bool MoveRight()
        {
            return TryPush(new Point(1, 0));
        }
        
        private bool TryPush(Point delta)
        {
            bool canSlide = CanPushBy(delta);
            if (canSlide)
                Position += delta;
            return canSlide;
        }
        private bool CanPushBy(Point delta)
        {
            return board.CanAddTetromino(this, Position + delta);
        }
    }
}