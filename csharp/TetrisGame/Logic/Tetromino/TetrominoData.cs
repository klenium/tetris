using hu.klenium.tetris.Util;

namespace hu.klenium.tetris.Logic.Tetromino
{
    public class TetrominoData
    {
        public Point[] Parts { get; }
        public Dimension BoundingBox { get; }
        public TetrominoData(Point[] parts, Dimension boundingBox)
        {
            this.Parts = parts;
            this.BoundingBox = boundingBox;
        }
    }
}