using hu.klenium.tetris.logic;
using hu.klenium.tetris.util;
using System.Collections.Generic;
using System.Threading;

using ConsoleWindow = System.Console;

namespace TetrisGameView.Console
{
    class Program
    {
        private static bool IsRunning = true;
        private static TetrisGame game;
        private static Dictionary<char, Command> controls;
        static void Main(string[] args)
        {
            Dimension gridSize = new Dimension(11, 17);
            int fallingSpeed = 700;
            controls = new Dictionary<char, Command>()
            {
                ['w'] = Command.ROTATE,
                ['a'] = Command.MOVE_LEFT,
                ['s'] = Command.MOVE_DOWN,
                ['d'] = Command.MOVE_RIGHT,
                [' '] = Command.DROP
            };
            ConsoleGameFrame gameFrame = new ConsoleGameFrame(gridSize);
            game = new TetrisGame(gridSize, fallingSpeed);
            game.OnTetrominoStateChanged += gameFrame.DisplayTetromino;
            game.OnBoardStateChanged += gameFrame.DisplayBoard;
            game.OnGameOver += () =>
            {
                gameFrame.DisplayGameOver();
                IsRunning = false;
            };
            game.Start();
            new Thread(ReadKey).Start();
        }
        private static void ReadKey()
        {
            // Excepted to read one more key after the game stops.
            while (IsRunning)
            {
                var info = ConsoleWindow.ReadKey(true);
                try
                {
                    game.HandleCommand(controls[info.KeyChar]);
                }
                catch (KeyNotFoundException) {}
            }
        }
    }
}
