namespace hu.klenium.tetris.util
{
    public class Random
    {
        private static readonly System.Random random = new System.Random();
        public static int FromRange(int min, int max)
        {
            return random.Next(min, max + 1);
        }
    }
}