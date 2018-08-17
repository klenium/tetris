using hu.klenium.tetris.Logic.Tetromino;
using hu.klenium.tetris.Util;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using TetrisGameTests.Helpers;

namespace TetrisGameTests
{
    [TestClass]
    public class TetrominoTest
    {
        private Tetromino tetromino;
        [TestMethod] public void CheckTetrominoTypeOGrid()
        {
            tetromino = new Tetromino(0);
            TestUtil.CheckTetrominoState(tetromino, new string[] {
                "##",
                "##"
            });
        }
        [TestMethod] public void CheckTetrominoTypeIGrid()
        {
            tetromino = new Tetromino(1);
            TestUtil.CheckTetrominoState(tetromino, new string[] {
                "#",
                "#",
                "#",
                "#"
            });
        }
        [TestMethod] public void CheckTetrominoTypeLGrid()
        {
            tetromino = new Tetromino(2);
            TestUtil.CheckTetrominoState(tetromino, new string[] {
                "#.",
                "#.",
                "##"
            });
        }
        [TestMethod] public void CheckTetrominoTypeJGrid()
        {
            tetromino = new Tetromino(3);
            TestUtil.CheckTetrominoState(tetromino, new string[] {
                ".#",
                ".#",
                "##"
            });
        }
        [TestMethod] public void CheckTetrominoTypeSGrid()
        {
            tetromino = new Tetromino(4);
            TestUtil.CheckTetrominoState(tetromino, new string[] {
                ".##",
                "##."
            });
        }
        [TestMethod] public void CheckTetrominoTypeZGrid()
        {
            tetromino = new Tetromino(5);
            TestUtil.CheckTetrominoState(tetromino, new string[] {
                "##.",
                ".##"
            });
        }
        [TestMethod] public void CheckTetrominoTypeTGrid()
        {
            tetromino = new Tetromino(6);
            TestUtil.CheckTetrominoState(tetromino, new string[] {
                "###",
                ".#."
            });
        }
        [TestMethod] public void MoveLeft()
        {
            tetromino = new Tetromino(6);
            TestUtil.ControlTetromino(tetromino, "A");
            Point position = tetromino.getPosition();
            Assert.AreEqual(position, new Point(1, 0));
        }
        [TestMethod] public void MoveDown()
        {
            tetromino = new Tetromino(6);
            TestUtil.ControlTetromino(tetromino, "S");
            Point position = tetromino.getPosition();
            Assert.AreEqual(position, new Point(2, 1));
        }
        [TestMethod] public void MoveRight()
        {
            tetromino = new Tetromino(6);
            TestUtil.ControlTetromino(tetromino, "D");
            Point position = tetromino.getPosition();
            Assert.AreEqual(position, new Point(3, 0));
        }
    }
}