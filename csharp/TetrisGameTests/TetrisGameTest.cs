using hu.klenium.tetris.logic.tetromino;
using hu.klenium.tetris.util;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System.Collections.Generic;
using TetrisGameTests.Helpers;

namespace TetrisGameTests
{
    [TestClass]
    public class TetrisGameTest
    {
        private TestTetrisGame game;
        [TestInitialize] public void SetUp()
        {
            //                                         J, T, I, O
            List<int> tetrominoTypes = new List<int> { 3, 6, 1, 0 };
            game = new TestTetrisGame(new Dimension(5, 6), tetrominoTypes);
        }
        [TestMethod] public void CheclBoardSize()
        {
            Assert.AreEqual(game.Board.Size.width, 5);
            Assert.AreEqual(game.Board.Size.height, 6);
        }
        [TestMethod] public void CheckFalling()
        {
            game.Start();
            Tetromino tetromino = game.Tetromino;
            Assert.AreEqual(tetromino.GetPosition(), new Point(2, 0));
            TestUtil.RunLater(75, () => {
                Assert.AreEqual(tetromino.GetPosition(), new Point(2, 1));
            });
            TestUtil.RunLater(125, () => {
                Assert.AreEqual(tetromino.GetPosition(), new Point(2, 2));
            });
            TestUtil.RunLater(175, () => {
                Assert.AreEqual(tetromino.GetPosition(), new Point(2, 3));
            });
        }
    }
}