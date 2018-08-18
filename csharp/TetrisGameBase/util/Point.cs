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
        public void Deconstruct(out int x, out int y)
        {
            x = this.x;
            y = this.y;
        }

        public override string ToString()
        {
            return $"Point({x},{y})";
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

        public static implicit operator Point ((int x, int y) tuple)
        {
            return new Point(tuple.x, tuple.y);
        }
        // Convert a dimension to a vector/point.
        // The vector points from the origo to the boundig box's top-right side.
        // This behavior is excepted when comparing vectors to sizes.
        public static implicit operator Point (Dimension size)
        {
            return new Point(size.width, size.height);
        }

        public static Point operator + (Point left, Point right)
        {
            return new Point(left.x + right.x, left.y + right.y);
        }
        public static bool operator < (Point left, Point right)
        {
            return left.x < right.x || left.y < right.y;
        }
        public static bool operator > (Point left, Point right)
        {
            return left.x > right.x || left.y > right.y;
        }
    }
}