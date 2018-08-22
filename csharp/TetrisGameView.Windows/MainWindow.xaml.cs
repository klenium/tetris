using hu.klenium.tetris.logic;
using hu.klenium.tetris.util;
using System.Collections.Generic;
using System.Windows;
using System.Windows.Input;

namespace TetrisGameView.Windows
{
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();

            Dimension gridSize = new Dimension(11, 16);
            int blockSize = 30;
            int fallingSpeed = 700;
            var controls = new Dictionary<Key, Command>()
            {
                [Key.W] = Command.ROTATE,
                [Key.A] = Command.MOVE_LEFT,
                [Key.S] = Command.MOVE_DOWN,
                [Key.D] = Command.MOVE_RIGHT,
                [Key.Space] = Command.DROP
            };
            GraphicGameFrame gameFrame = new GraphicGameFrame(gridSize, blockSize, this);
            TetrisGame game = new TetrisGame(gridSize, fallingSpeed);
            gameFrame.RegisterEventListeners(game, controls);
            game.OnTetrominoStateChanged += gameFrame.DisplayTetromino;
            game.OnBoardStateChanged += gameFrame.DisplayBoard;
            game.OnGameOver += gameFrame.DisplayGameOver;
            game.Start();
        }
    }
}