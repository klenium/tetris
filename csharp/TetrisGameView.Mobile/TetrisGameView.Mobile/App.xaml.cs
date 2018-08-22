using hu.klenium.tetris.logic;
using hu.klenium.tetris.util;
using Xamarin.Forms;
using Xamarin.Forms.Xaml;

[assembly: XamlCompilation (XamlCompilationOptions.Compile)]
namespace TetrisGameView.Mobile
{
	public partial class App : Application
	{
        private TetrisGame game;
        public App()
		{
			InitializeComponent();
			MainPage = new MainPage();

            Dimension gridSize = new Dimension(11, 16);
            int fallingSpeed = 1500;

            game = new TetrisGame(gridSize, fallingSpeed);
            var gameFrame = new GraphicGameFrame(game, gridSize, ((MainPage) MainPage).Frame);
            game.OnTetrominoStateChanged += gameFrame.DisplayTetromino;
            game.OnBoardStateChanged += gameFrame.DisplayBoard;
            game.OnGameOver += gameFrame.DisplayGameOver;
        }
        protected override void OnStart()
		{
            game.Start();
		}
		protected override void OnSleep()
		{
			// Should call game.Pause.
		}
	}
}