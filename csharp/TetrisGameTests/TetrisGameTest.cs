using hu.klenium.tetris.logic;
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
        private static readonly int PERIOD = 500;
        private static readonly int OFFSET = PERIOD / 2;
        private TestTetrisGame game;
        [TestInitialize] public void SetUp()
        {
            //                                         J, T, I, O
            List<int> tetrominoTypes = new List<int> { 3, 6, 1, 0 };
            game = new TestTetrisGame(new Dimension(5, 6), tetrominoTypes, PERIOD);
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
            Assert.AreEqual(tetromino.Position, new Point(2, 0));
            TestUtil.RunLater(OFFSET + PERIOD, () => {
                Assert.AreEqual(tetromino.Position, new Point(2, 1));
            });
            TestUtil.RunLater(PERIOD, () => {
                Assert.AreEqual(tetromino.Position, new Point(2, 2));
            });
            TestUtil.RunLater(PERIOD, () => {
                Assert.AreEqual(tetromino.Position, new Point(2, 3));
            });
        }

        [TestMethod] public void UnStartedState()
        {
            Assert.IsFalse(game.State);
            Assert.IsNull(game.Tetromino);
            game.HandleCommand(Command.MOVE_DOWN);
            Assert.IsNull(game.Tetromino);
        }
        [TestMethod] public void RunningState()
        {
            game.Start();
            Assert.IsTrue(game.State);
            Assert.IsNotNull(game.Tetromino);
        }
        [TestMethod] public void StoppedState()
        {
            game.Start();
            TestUtil.ControlTetromino(game, " ");
            TestUtil.ControlTetromino(game, " ");
            Tetromino tetromino1 = game.Tetromino;
            Assert.IsFalse(game.State);
            TestUtil.ControlTetromino(game, " ");
            Tetromino tetromino2 = game.Tetromino;
            Assert.AreSame(tetromino1, tetromino2);
            Assert.AreEqual(tetromino1.Position, tetromino2.Position);
        }
        [TestMethod] public void ControlTetromino()
        {
            game.Start();
            Tetromino tetromino = game.Tetromino;
            Point position1 = tetromino.Position;
            game.HandleCommand(Command.MOVE_LEFT);
            Point position2 = tetromino.Position;
            Assert.AreNotEqual(position1, position2);
        }
        [TestMethod] public void TetrominoLanded()
        {
            game.Start();
            Tetromino tetromino1 = game.Tetromino;
            game.HandleCommand(Command.DROP);
            Tetromino tetromino2 = game.Tetromino;
            Assert.AreNotSame(tetromino1, tetromino2);
            Assert.AreEqual(tetromino2.Type, 6);
            TestUtil.CheckBoardState(game.Board, new string[] {
                ".....",
                ".....",
                ".....",
                "...#.",
                "...#.",
                "..##."
            });
        }
        [TestMethod] public void RemoveFullRows()
        {
            game.Start();
            TestUtil.ControlTetromino(game, "DD ");
            TestUtil.ControlTetromino(game, "WWA ");
            TestUtil.CheckBoardState(game.Board, new string[] {
                ".....",
                ".....",
                ".....",
                ".....",
                "....#",
                ".#..#"
            });
        }
        [TestMethod] public void InitialPosition()
        {
            game.Start();
            Assert.AreEqual(game.Tetromino.Position, new Point(2, 0));
            TestUtil.ControlTetromino(game, "D ");
            Assert.AreEqual(game.Tetromino.Position, new Point(1, 0));
            TestUtil.ControlTetromino(game, " ");
            Assert.AreEqual(game.Tetromino.Position, new Point(2, 0));
        }
    }
}