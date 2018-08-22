namespace TetrisGameView.Console.util.ExtensionMethods
{
    public static class StringRepeatExtension
    {
        public static string Repeat(this string part, int times)
        {
            string result = "";
            for (int i = 0; i < times; ++i)
                result += part;
            return result;
        }
    }
}