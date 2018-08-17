using System;
using hu.klenium.tetris.Logic;
using hu.klenium.tetris.Logic.Board;
using hu.klenium.tetris.Logic.Tetromino;
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
            string[] data = tetromino.getCurrentData();
            int width = data[0].Length;
            int height = data.Length;
            Assert.AreEqual(width, excepted[0].Length);
            Assert.AreEqual(height, excepted.Length);
            bool gridEequalsToExpected = true;
            string consoleView = "";
            for (int y = 0; y < height; ++y)
            {
                for (int x = 0; x < width; ++x)
                {
                    char cell = data[y][x];
                    char ch = excepted[y][x];
                    bool cellEmpty = cell == '.';
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