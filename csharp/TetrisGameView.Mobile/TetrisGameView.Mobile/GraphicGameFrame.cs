using hu.klenium.tetris.logic;
using hu.klenium.tetris.logic.board;
using hu.klenium.tetris.logic.tetromino;
using hu.klenium.tetris.util;
using TetrisGameView.Mobile.util;
using Xamarin.Forms;

using GameCommand = hu.klenium.tetris.logic.Command;

namespace TetrisGameView.Mobile
{
    class GraphicGameFrame
    {
        private readonly Dimension gridSize;
        private readonly Grid gridView;
        private bool[,] boardLayer;
        private bool[,] tetrominoLayer;
        private BoxView[,] boxes;
        public GraphicGameFrame(TetrisGame game, Dimension gridSize, Grid gridView)
        {
            this.gridSize = gridSize;
            this.gridView = gridView;
            this.boardLayer = new bool[gridSize.width, gridSize.height];
            this.tetrominoLayer = new bool[gridSize.width, gridSize.height];
            this.boxes = new BoxView[gridSize.width, gridSize.height];
            BuildGrid();
            RegisterEventListeners(game);
        }

        public void DisplayTetromino(Tetromino tetromino)
        {
            for (int x = 0; x < gridSize.width; ++x)
            {
                for (int y = 0; y < gridSize.height; ++y)
                    tetrominoLayer[x, y] = false;
            }
            foreach (var partOffset in tetromino.Parts)
            {
                (int x, int y) = tetromino.Position + partOffset;
                tetrominoLayer[x, y] = true;
            }
            UpdateFrameContent();
        }
        public void DisplayBoard(Board board)
        {
            for (int x = 0; x < gridSize.width; ++x)
            {
                for (int y = 0; y < gridSize.height; ++y)
                    boardLayer[x, y] = board.Grid[x, y];
            }
            UpdateFrameContent();
        }
        public void DisplayGameOver()
        {
            tetrominoLayer = new bool[gridSize.width, gridSize.height];
            boardLayer = new bool[gridSize.width, gridSize.height];
            UpdateFrameContent();
        }

        private void BuildGrid()
        {
            gridView.RowDefinitions = new RowDefinitionCollection();
            gridView.ColumnDefinitions = new ColumnDefinitionCollection();
            for (int x = 0; x < gridSize.width; ++x)
                gridView.ColumnDefinitions.Add(new ColumnDefinition { Width = new GridLength(1, GridUnitType.Star) });
            for (int y = 0; y < gridSize.height; ++y)
                gridView.RowDefinitions.Add(new RowDefinition { Height = new GridLength(1, GridUnitType.Star) });
            for (int x = 0; x < gridSize.width; ++x)
            {
                for (int y = 0; y < gridSize.height; ++y)
                {
                    boxes[x, y] = new BoxView();
                    gridView.Children.Add(boxes[x, y], x, y);
                }
            }
        }
        private void RegisterEventListeners(TetrisGame game)
        {
            var tap = new TapGestureRecognizer();
            tap.Tapped += (s, e) => { game.HandleCommand(GameCommand.ROTATE); };
            gridView.GestureRecognizers.Add(tap);
            var swipe = new SwipeGestureRecognizer();
            swipe.SwipedRight += () => { game.HandleCommand(GameCommand.MOVE_RIGHT); };
            swipe.SwipedDown += () => { game.HandleCommand(GameCommand.DROP); };
            swipe.SwipedLeft += () => { game.HandleCommand(GameCommand.MOVE_LEFT); };
            gridView.GestureRecognizers.Add(swipe);
        }
        private void UpdateFrameContent()
        {
            for (int x = 0; x < gridSize.width; ++x)
            {
                for (int y = 0; y < gridSize.height; ++y)
                {
                    bool cell = tetrominoLayer[x, y];
                    if (!cell)
                        cell = boardLayer[x, y];
                    Color fillColor = cell ? tetrominoColor : backgroundColor;
                    boxes[x, y].BackgroundColor = fillColor;
                }
            }
        }

        private readonly static Color tetrominoColor = Color.FromRgb(250, 250, 130);
        private readonly static Color backgroundColor = Color.FromRgb(23, 23, 23);
    }
}