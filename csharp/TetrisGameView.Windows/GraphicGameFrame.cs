using System;
using System.Collections.Generic;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Shapes;
using hu.klenium.tetris.logic;
using hu.klenium.tetris.logic.board;
using hu.klenium.tetris.logic.tetromino;
using hu.klenium.tetris.util;

namespace TetrisGameView.Windows
{
    class GraphicGameFrame
    {
        private readonly Dimension gridSize;
        private readonly int blockSize;
        private int Width
        {
            get { return gridSize.width * blockSize; }
        }
        private int Height
        {
            get { return gridSize.height * blockSize; }
        }
        private TetrisGame game;
        private Dictionary<Key, Command> controls;
        private MainWindow window;
        public GraphicGameFrame(Dimension gridSize, int blockSize, MainWindow window)
        {
            this.gridSize = gridSize;
            this.blockSize = blockSize;
            this.window = window;
            window.BoardCanvas.Width = Width;
            window.BoardCanvas.Height = Height;
            window.TetrominoCanvas.Width = Width;
            window.TetrominoCanvas.Height = Height;
            window.KeyDown += HandleKeyPress;
        }
        public void RegisterEventListeners(TetrisGame game, Dictionary<Key, Command> keys)
        {
            this.game = game;
            this.controls = keys;
        }
        private void HandleKeyPress(object sender, KeyEventArgs args)
        {
            Key pressedKey = args.Key;
            try
            {
                Command command = controls[pressedKey];
                game.HandleCommand(command);
            }
            catch (KeyNotFoundException) {}
        }

        public void DisplayTetromino(Tetromino tetromino)
        {
            UpdateScreen(() =>
            {
                window.TetrominoCanvas.Children.Clear();
            foreach (Point partOffset in tetromino.Parts)
            {
                Point position = tetromino.Position + partOffset;
                DrawSquare(window.TetrominoCanvas, tetrominoColor, position);
                }
            });
        }
        public void DisplayBoard(Board board)
        {
            UpdateScreen(() =>
            {
                bool[,] cells = board.Grid;
            for (int x = 0; x < gridSize.width; ++x)
            {
                for (int y = 0; y < gridSize.height; ++y)
                {
                    bool cell = cells[x, y];
                    Color fillColor = cell ? tetrominoColor : backgroundColor;
                    DrawSquare(window.BoardCanvas, fillColor, (x, y));
                }
                }
            });
        }
        public void DisplayGameOver()
        {
            UpdateScreen(() =>
            {
                window.TetrominoCanvas.Children.Clear();
                DrawSquare(window.BoardCanvas, backgroundColor, 0.7f, (0, 0), (Width, Height));
            });
        }

        private void DrawSquare(Canvas canvas, Color color, Point position)
        {
            DrawSquare(canvas, color, 1.0f, position * blockSize, (blockSize, blockSize));
        }
        private void DrawSquare(Canvas canvas, Color color, float opacity, Point position, Dimension size)
        {
            SolidColorBrush brush = new SolidColorBrush(color)
            {
                Opacity = opacity
            };
            Rectangle square = new Rectangle
            {
                Fill = brush,
                Width = size.width,
                Height = size.height,
                SnapsToDevicePixels = true
            };
            Canvas.SetLeft(square, position.x);
            Canvas.SetTop(square, position.y);
            canvas.Children.Add(square);
        }

        private void UpdateScreen(Action job)
        {
            window.Dispatcher.Invoke(job);
        }

        private readonly static Color tetrominoColor = Color.FromRgb(250, 250, 130);
        private readonly static Color backgroundColor = Color.FromRgb(23, 23, 23);
    }
}