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

        [TestMethod] public void MovingTetrominoInsideEmptyBoard()
        {
            Tetromino tetromino = new Tetromino(0);
            Assert.IsTrue(TestUtil.ControlTetromino(tetromino, "DADDDSAADSDAD"));
        }
        [TestMethod] public void MovingTetrominoOutsideBoardBox()
        {
            Tetromino tetromino = new Tetromino(0);
            Assert.IsFalse(TestUtil.ControlTetromino(tetromino, "A"));
            Assert.IsFalse(TestUtil.ControlTetromino(tetromino, "DDDD"));
            Assert.IsFalse(TestUtil.ControlTetromino(tetromino, "SSS"));
        }
        [TestMethod] public void InvalidTetrominoMoveInsideUsedBoard()
        {
            AddTestData();
            Tetromino tetromino = new Tetromino(0);
            Assert.IsFalse(TestUtil.ControlTetromino(tetromino, "S"));
            Assert.IsFalse(TestUtil.ControlTetromino(tetromino, "DDD"));
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