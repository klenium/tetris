using Android.App;
using Android.Content.PM;
using Android.OS;
using System;

namespace TetrisGameView.Mobile.Droid
{
    [Activity(
        Label = "Tetris",
        MainLauncher = true,
        Theme = "@style/MainTheme",
        ScreenOrientation = ScreenOrientation.Portrait
    )]
    public class MainActivity : global::Xamarin.Forms.Platform.Android.FormsAppCompatActivity
    {
        protected override void OnCreate(Bundle bundle)
        {
            TabLayoutResource = Resource.Layout.Tabbar;
            ToolbarResource = Resource.Layout.Toolbar;

            base.OnCreate(bundle);

            global::Xamarin.Forms.Forms.Init(this, bundle);
            LoadApplication(new App());
        }
    }
}