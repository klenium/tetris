using System;
using System.Linq;
using hu.klenium.tetris.Logic;
using hu.klenium.tetris.Logic.Board;
using hu.klenium.tetris.Logic.Tetromino;
using hu.klenium.tetris.Util;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace TetrisGameTests.Helpers
{
    static class TestUtil
    {
        public static bool ControlTetromino(Tetromino tetromino, string commands)
        {

            return false;
        }
        public static void ControlTetromino(TetrisGame game, string commands)
        {

        }
        public static void CheckBoardState(Board board, string[] excepted)
        {

        }
        public static void CheckTetrominoState(Tetromino tetromino, string[] excepted)
        {
            TetrominoData data = tetromino.getCurrentData();
            int width = data.BoundingBox.width;
            int height = data.BoundingBox.height;
            Assert.AreEqual(width, excepted[0].Length);
            Assert.AreEqual(height, excepted.Length);
            bool gridEequalsToExpected = true;
            string consoleView = "";
            for (int y = 0; y < height; ++y)
            {
                for (int x = 0; x < width; ++x)
                {
                    bool cellEmpty;
                    try
                    {
                        data.Parts.First(offset => offset.x == x && offset.y == y);
                        cellEmpty = false;
                    }
                    catch (Exception)
                    {
                        cellEmpty = true;
                    }
                    char ch = excepted[y][x];
                    bool cellEquals = cellEmpty == (ch == '.');
                    gridEequalsToExpected &= cellEquals;
                    if (cellEquals)
                        consoleView += cellEmpty ? '.' : '#';
                    else
                        consoleView += cellEmpty ? '!' : '?';
                }
                consoleView += '\n';
            }
            if (!gridEequalsToExpected)
                Console.WriteLine(consoleView);
            Assert.IsTrue(gridEequalsToExpected);
        }
        public static void RunLater(int delay, Action task)
        {

        }
    }
}