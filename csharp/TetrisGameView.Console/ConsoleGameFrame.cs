using hu.klenium.tetris.logic.board;
using hu.klenium.tetris.logic.tetromino;
using hu.klenium.tetris.util;
using TetrisGameView.Console.util.ExtensionMethods;

using ConsoleWindow = System.Console;

namespace TetrisGameView.Console
{
    // Disabling cursor visiblity and rewriting chars already printed
    // to the console is needed to avoid flickering that would be caused
    // by clearing then printing chars again to the console.
    class ConsoleGameFrame
    {
        private static readonly string filledCell = "██";
        private static readonly string emptryCell = "  ";
        private readonly int topMargin;
        private readonly int leftMargin;
        private readonly Dimension gridSize;
        private string[,] boardLayer;
        private string[,] tetrominoLayer;
        public ConsoleGameFrame(Dimension gridSize)
        {
            this.gridSize = gridSize;
            boardLayer = new string[gridSize.width, gridSize.height];
            tetrominoLayer = new string[gridSize.width, gridSize.height];
            ConsoleWindow.CursorVisible = false;
            topMargin = (ConsoleWindow.WindowHeight - gridSize.height - 1) / 2;
            leftMargin = (ConsoleWindow.WindowWidth - gridSize.width - 1) / 2;
            PrintBorderedFrame();
        }

        public void DisplayTetromino(Tetromino tetromino)
        {
            for (int x = 0; x < gridSize.width; ++x)
            {
                for (int y = 0; y < gridSize.height; ++y)
                    tetrominoLayer[x, y] = emptryCell;
            }
            foreach (Point partOffset in tetromino.Parts)
            {
                (int x, int y) = tetromino.Position + partOffset;
                tetrominoLayer[x, y] = filledCell;
            }
            UpdateFrameContent();
        }
        public void DisplayBoard(Board board)
        {
            bool[,] cells = board.Grid;
            for (int x = 0; x < gridSize.width; ++x)
            {
                for (int y = 0; y < gridSize.height; ++y)
                {
                    bool cell = cells[x, y];
                    boardLayer[x, y] = cell ? filledCell : emptryCell;
                }
            }
            UpdateFrameContent();
        }
        public void DisplayGameOver()
        {
            ClearFrameContent();
            string message = "Game Over!";
            int leftPos = (gridSize.width * emptryCell.Length - message.Length) / 2;
            int topPos = gridSize.height / 2;
            ConsoleWindow.SetCursorPosition(leftPos + 1, topPos + 1);
            ConsoleWindow.Write(message);
            ConsoleWindow.SetCursorPosition(0, gridSize.height + 2);
            ConsoleWindow.CursorVisible = true;
        }

        private void UpdateFrameContent()
        {
            for (int y = 0; y < gridSize.height; ++y)
            {
                for (int x = 0; x < gridSize.width; ++x)
                {
                    string cell = tetrominoLayer[x, y];
                    if (cell == emptryCell)
                        cell = boardLayer[x, y];
                    int left = leftMargin + 1 + x * emptryCell.Length;
                    int top = topMargin + 1 + y;
                    ConsoleWindow.SetCursorPosition(left, top);
                    ConsoleWindow.Write(cell);
                }
            }
        }
        private void ClearFrameContent()
        {
            string emptryRow = emptryCell.Repeat(gridSize.width);
            for (int y = 0; y < gridSize.height; ++y)
            {
                ConsoleWindow.SetCursorPosition(leftMargin, topMargin + y);
                ConsoleWindow.Write(emptryRow);
            }
        }
        private void PrintBorderedFrame()
        {
            for (int y = 0; y < topMargin; ++y)
                ConsoleWindow.WriteLine();
            string margin = " ".Repeat(leftMargin);
            string row = margin + "║" + emptryCell.Repeat(gridSize.width) + "║\n";
            string horizontalBorder = "═".Repeat(emptryCell.Length * gridSize.width);
            ConsoleWindow.WriteLine(margin + "╔" + horizontalBorder + "╗");
            ConsoleWindow.Write(row.Repeat(gridSize.height));
            ConsoleWindow.WriteLine(margin + "╚" + horizontalBorder + "╝");
        }
    }
}