namespace hu.klenium.tetris.util
{
    public struct Point
    {
        public readonly int x;
        public readonly int y;
        public Point(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
        public static Point operator+(Point left, Point right)
        {
            return new Point(left.x + right.x, left.y + right.y);
        }
        public override string ToString()
        {
            return $"Point({x},{y})";
        }
    }
}