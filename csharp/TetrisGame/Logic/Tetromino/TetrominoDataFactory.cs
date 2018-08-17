using hu.klenium.tetris.util;
using System.Linq;

namespace hu.klenium.tetris.logic.tetromino
{
    public static class TetrominoDataFactory
    {
        public static TetrominoData[] GetData(int type)
        {
            string[][] masks = rawData[type];
            TetrominoData[] result = new TetrominoData[masks.Length];
            for (int rotation = 0; rotation < masks.Length; ++rotation)
            {
                string[] mask = masks[rotation];
                Point[] parts = new Point[4];
                int height = mask.Length;
                int width = mask[0].Length;
                int partsCount = 0;
                for (int y = 0; y < height; ++y)
                {
                    for (int x = 0; x < width; ++x)
                    {
                        if (mask[y][x] != '.')
                            parts[partsCount++] = new Point(x, y);
                    }
                }
                result[rotation] = new TetrominoData(parts, new Dimension(width, height));
            }
            return result;
        }

        private readonly static string[][][] rawData = new string[][][] {
            new string[][] {
                new string[] {
                    "##",
                    "##"
                }
            },
            new string[][] {
                new string[] {
                    "#",
                    "#",
                    "#",
                    "#"
                },
                new string[] {
                    "####"
                }
            },
            new string[][] {
                new string[] {
                    "#.",
                    "#.",
                    "##"
                },
                new string[] {
                    "..#",
                    "###"
                },
                new string[] {
                    "##",
                    ".#",
                    ".#"
                },
                new string[] {
                    "###",
                    "#.."
                }
            },
            new string[][] {
                new string[] {
                    ".#",
                    ".#",
                    "##"
                },
                new string[] {
                    "###",
                    "..#"
                },
                new string[] {
                    "##",
                    "#.",
                    "#."
                },
                new string[] {
                    "#..",
                    "###"
                }
            },
            new string[][] {
                new string[] {
                    ".##",
                    "##."
                },
                new string[] {
                    "#.",
                    "##",
                    ".#"
                }
            },
            new string[][] {
                new string[] {
                    "##.",
                    ".##"
                },
                new string[] {
                    ".#",
                    "##",
                    "#."
                }
            },
            new string[][] {
                new string[] {
                    "###",
                    ".#."
                },
                new string[] {
                    "#.",
                    "##",
                    "#."
                },
                new string[] {
                    ".#.",
                    "###"
                },
                new string[] {
                    ".#",
                    "##",
                    ".#"
                }
            }
        };
    }
}