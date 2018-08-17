using hu.klenium.tetris.Logic.Board;
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
        private Board board = new Board(new Dimension(7, 8));
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
            tetromino.MoveToInitialPos();
            TestUtil.ControlTetromino(tetromino, "A");
            Point position = tetromino.GetPosition();
            Assert.AreEqual(position, new Point(1, 0));
        }
        [TestMethod] public void MoveDown()
        {
            tetromino = new Tetromino(6);
            tetromino.MoveToInitialPos();
            TestUtil.ControlTetromino(tetromino, "S");
            Point position = tetromino.GetPosition();
            Assert.AreEqual(position, new Point(2, 1));
        }
        [TestMethod] public void MoveRight()
        {
            tetromino = new Tetromino(6);
            tetromino.MoveToInitialPos();
            TestUtil.ControlTetromino(tetromino, "D");
            Point position = tetromino.GetPosition();
            Assert.AreEqual(position, new Point(3, 0));
        }
        [TestMethod] public void RotateTypeT()
        {
            tetromino = new Tetromino(6);
            TestUtil.CheckTetrominoState(tetromino, new string[] {
                "###",
                ".#."
            });
            tetromino.RotateRight();
            TestUtil.CheckTetrominoState(tetromino, new string[] {
                "#.",
                "##",
                "#."
            });
            tetromino.RotateRight();
            TestUtil.CheckTetrominoState(tetromino, new string[] {
                ".#.",
                "###"
            });
            tetromino.RotateRight();
            TestUtil.CheckTetrominoState(tetromino, new string[] {
                ".#",
                "##",
                ".#"
            });
            tetromino.RotateRight();
            TestUtil.CheckTetrominoState(tetromino, new string[] {
                "###",
                ".#."
            });
        }
        [TestMethod] public void RotateTypeI()
        {
            tetromino = new Tetromino(1);
            TestUtil.CheckTetrominoState(tetromino, new string[] {
                "#",
                "#",
                "#",
                "#"
            });
            tetromino.RotateRight();
            TestUtil.CheckTetrominoState(tetromino, new string[] {
                "####"
            });
            tetromino.RotateRight();
            TestUtil.CheckTetrominoState(tetromino, new string[] {
                "#",
                "#",
                "#",
                "#"
            });
        }
        [TestMethod] public void MoveLeftWhenRotatingAtRightSide()
        {
            tetromino = new Tetromino(1);
            TestUtil.ControlTetromino(tetromino, "DDDDD");
            Assert.AreEqual(tetromino.GetPosition(), new Point(5, 0));
            TestUtil.ControlTetromino(tetromino, "W");
            Assert.AreEqual(tetromino.GetPosition(), new Point(3, 0));
        }
        [TestMethod] public void DontMoveLeftWhenRotatingAtRightSideIsntPossible()
        {
            tetromino = new Tetromino(1);
            TestUtil.ControlTetromino(tetromino, "DDDSSSS");
            board.AddTetromino(tetromino);
            tetromino = new Tetromino(1);
            TestUtil.ControlTetromino(tetromino, "DDDDDDSSSS");
            Assert.IsFalse(TestUtil.ControlTetromino(tetromino, "W"));
        }
    }
}