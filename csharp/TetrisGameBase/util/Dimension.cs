namespace hu.klenium.tetris.util
{
    public struct Dimension
    {
        public readonly int width;
        public readonly int height;
        public Dimension(int width, int height)
        {
            this.width = width;
            this.height = height;
        }

        public static implicit operator Dimension((int w, int h) tuple)
        {
            return new Dimension(tuple.w, tuple.h);
        }
    }
}