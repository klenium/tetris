using hu.klenium.tetris.util;
using hu.klenium.tetris.logic.board;
using System;

namespace hu.klenium.tetris.logic.tetromino
{
    public class Tetromino
    {
        private readonly Board board;
        private readonly TetrominoData[] _parts;
        private int _rotation = 0;
        private int Rotation
        {
            get { return _rotation; }
            set { _rotation = value % _parts.Length; }
        }
        public int Type { get; }
        public Point[] Parts
        {
            get { return _parts[Rotation].Parts; }
        }
        public Dimension BoundingBox
        {
            get { return _parts[Rotation].BoundingBox; }
        }
        public Point Position { get; private set; } = (0, 0);
        public Tetromino(int type, Board board)
        {
            this.Type = type;
            this.board = board;
            this._parts = TetrominoDataFactory.GetData(type);
        }

        public bool MoveToInitialPos()
        {
            float centerOfBoard = board.Size.width / 2.0f;
            float halfTetrominoWidth = BoundingBox.width / 2.0f;
            int centeredTetrominoPosX = (int)Math.Ceiling(centerOfBoard - halfTetrominoWidth);
            return TryPush((centeredTetrominoPosX, 0));
        }
        public bool RotateRight()
        {
            bool canRotate = false;
            int oldRotation = Rotation;
            ++Rotation;
            if (CanPushBy((0, 0)))
                canRotate = true;
            else
            {
                for (int i = 1; i < BoundingBox.width && !canRotate; ++i)
                    canRotate = TryPush((-i, 0));
            }
            if (!canRotate)
                Rotation = oldRotation;
            return canRotate;
        }
        public bool MoveLeft()
        {
            return TryPush((-1, 0));
        }
        public bool MoveDown()
        {
            return TryPush((0, 1));
        }
        public bool MoveRight()
        {
            return TryPush((1, 0));
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