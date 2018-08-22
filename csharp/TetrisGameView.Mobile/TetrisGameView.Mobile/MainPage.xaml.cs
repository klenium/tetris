using Xamarin.Forms;

namespace TetrisGameView.Mobile
{
	public partial class MainPage : ContentPage
	{
        public Grid Frame { get { return GameScreen; } }
		public MainPage()
		{
			InitializeComponent();
        }
	}
}