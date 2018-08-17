using System;

namespace hu.klenium.tetris.Logic.Tetromino
{
    public class Tetromino
    {
        private readonly string[][] parts;
        public Tetromino(int type)
        {
            parts = new string[1][];
            if (type == 0)
            {
                parts[0] = new string[]
                {
                    "##",
                    "##"
                };
            }
            else if (type == 1)
            {
                parts[0] = new string[]
                {
                    "#",
                    "#",
                    "#",
                    "#"
                };
            }
            else if (type == 2)
            {
                parts[0] = new string[]
                {
                    "#.",
                    "#.",
                    "##"
                };
            }
            else if (type == 3)
            {
                parts[0] = new string[]
                {
                    ".#",
                    ".#",
                    "##"
                };
            }
            else if (type == 4)
            {
                parts[0] = new string[]
                {
                    ".##",
                    "##."
                };
            }
            else if (type == 5)
            {
                parts[0] = new string[]
                {
                    "##.",
                    ".##"
                };
            }
            else if (type == 6)
            {
                parts[0] = new string[]
                {
                    "###",
                    ".#."
                };
            }
        }
        public string[] getCurrentData()
        {
            return parts[0];
        }
    }
}