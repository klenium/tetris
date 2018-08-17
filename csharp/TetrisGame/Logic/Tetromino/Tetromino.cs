using hu.klenium.tetris.util;
using hu.klenium.tetris.logic.board;
using System;

namespace hu.klenium.tetris.logic.tetromino
{
    public class Tetromino
    {
        private Board board;
        public int Type { get; }
        private TetrominoData[] Parts { get; }
        public Point[] CurrentParts
        {
            get { return Parts[Rotation].Parts; }
        }
        public Dimension BoundingBox
        {
            get { return Parts[Rotation].BoundingBox; }
        }
        public Point Position { get; private set; } = new Point(0, 0);
        private int _rotation = 0;
        private int Rotation {
            get { return _rotation; }
            set { _rotation = (value) % Parts.Length; }
        }
        public Tetromino(int type, Board board)
        {
            this.Type = type;
            this.board = board;
            Parts = TetrominoDataFactory.GetData(type);
        }

        public bool MoveToInitialPos()
        {
            float centerOfBoard = board.Size.width / 2.0f;
            float halfTetrominoWidth = BoundingBox.width / 2.0f;
            int centeredTetrominoPosX = (int)Math.Ceiling(centerOfBoard - halfTetrominoWidth);
            return TryPush(new Point(centeredTetrominoPosX, 0));
        }
        public bool RotateRight()
        {
            bool canRotate = false;
            int oldRotation = Rotation;
            ++Rotation;
            if (CanPushBy(new Point(0, 0)))
                canRotate = true;
            else
            {
                for (int i = 1; i < BoundingBox.width && !canRotate; ++i)
                    canRotate = TryPush(new Point(-i, 0));
            }
            if (!canRotate)
                Rotation = oldRotation;
            return canRotate;
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