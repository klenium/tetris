using hu.klenium.tetris.Util;
using System.Linq;

namespace hu.klenium.tetris.Logic.Tetromino
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
                Point[] parts = new Point[GetPartsCount(mask)];
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

        private static int GetPartsCount(string[] data)
        {
            int result = 0;
            foreach (var line in data)
                result += line.Count(x => x == '#');
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