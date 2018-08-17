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
            bool success = true;
            for (int i = 0; i < commands.Length && success; ++i)
            {
                switch (commands[i])
                {
                    case 'A': success = tetromino.MoveLeft(); break;
                    case 'S': success = tetromino.MoveDown(); break;
                    case 'D': success = tetromino.MoveRight(); break;
                    default: throw new Exception();
                }
            }
            return success;
        }
        public static void ControlTetromino(TetrisGame game, string commands)
        {

        }
        public static void CheckBoardState(Board board, string[] excepted)
        {
            bool[,] grid = board.Grid;
            int width = board.Size.width;
            int height = board.Size.height;
            Assert.AreEqual(width, excepted[0].Length);
            Assert.AreEqual(height, excepted.Length);
            bool gridEqualsToExpected = true;
            string consoleView = "";
            for (int y = 0; y < height; ++y)
            {
                for (int x = 0; x < width; ++x)
                {
                    bool cell = grid[x, y];
                    char ch = excepted[y][x];
                    bool cellEmpty = !cell;
                    bool cellEquals = cellEmpty == (ch == '.');
                    gridEqualsToExpected &= cellEquals;
                    if (cellEquals)
                        consoleView += cellEmpty ? '.' : '#';
                    else
                        consoleView += cellEmpty ? '!' : '?';
                }
                consoleView += '\n';
            }
            if (!gridEqualsToExpected)
                Console.WriteLine(consoleView);
            Assert.IsTrue(gridEqualsToExpected);
        }
        public static void CheckTetrominoState(Tetromino tetromino, string[] excepted)
        {
            TetrominoData data = tetromino.GetCurrentData();
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