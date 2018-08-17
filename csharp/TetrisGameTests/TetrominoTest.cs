using hu.klenium.tetris.Logic.Tetromino;
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
    }
}