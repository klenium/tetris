using hu.klenium.tetris.util;

namespace hu.klenium.tetris.logic.tetromino
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