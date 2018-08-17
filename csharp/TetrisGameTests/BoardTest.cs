using hu.klenium.tetris.Logic.Board;
using hu.klenium.tetris.Logic.Tetromino;
using hu.klenium.tetris.Util;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using TetrisGameTests.Helpers;

namespace TetrisGameTests
{
    [TestClass]
    public class BoardTest
    {
        private Board board = new Board(new Dimension(5, 4));
        [TestMethod] public void NewBoardIsEmpty()
        {
            TestUtil.CheckBoardState(board, new string[]{
                ".....",
                ".....",
                ".....",
                "....."
            });
        }
        [TestMethod] public void AddTetrominoes()
        {
            AddTestData();
            TestUtil.CheckBoardState(board, new string[]{
                ".....",
                "....#",
                ".#.##",
                ".####"
            });
        }

        public void AddTestData()
        {
            /* .....
               ....#
               .#.##
               .#### */
            Tetromino tetromino;
            tetromino = new Tetromino(3);
            TestUtil.ControlTetromino(tetromino, "WWWDSS");
            board.AddTetromino(tetromino);
            tetromino = new Tetromino(6);
            TestUtil.ControlTetromino(tetromino, "WWDDWDS");
            board.AddTetromino(tetromino);
        }
    }
}