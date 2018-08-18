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
        // Convert a dimension to a vector (which points from the origo to the boundig box size).
        public Point(Dimension size)
        {
            this.x = size.width;
            this.y = size.height;
        }
        public override string ToString()
        {
            return $"Point({x},{y})";
        }
        public static Point operator + (Point left, Point right)
        {
            return new Point(left.x + right.x, left.y + right.y);
        }
        public static Point operator + (Point left, Dimension right)
        {
            return new Point(left.x + right.width, left.y + right.height);
        }
        public static bool operator < (Point left, Point right)
        {
            return left.x < right.x || left.y < right.y;
        }
        public static bool operator > (Point left, Point right)
        {
            return left.x > right.x || left.y > right.y;
        }
        public override bool Equals(object obj)
        {
            if (obj == null) return false;
            if (!(obj is Point)) return false;
            Point other = (Point)obj;
            return this.x == other.x && this.y == other.y;
        }
        public override int GetHashCode()
        {
            unchecked
            {
                int result = 1;
                result = 31 * result + x.GetHashCode();
                result = 31 * result + y.GetHashCode();
                return result;
            }
        }
    }
}