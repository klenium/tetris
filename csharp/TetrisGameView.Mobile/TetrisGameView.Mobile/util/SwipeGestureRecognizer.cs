// Source: https://stackoverflow.com/a/45454234/2625561

using System;
using Xamarin.Forms;

namespace TetrisGameView.Mobile.util
{
    class SwipeGestureRecognizer : PanGestureRecognizer
    {
        public event Action SwipedUp;
        public event Action SwipedRight;
        public event Action SwipedDown;
        public event Action SwipedLeft;
        private double translatedX = 0;
        private double translatedY = 0;
        public SwipeGestureRecognizer()
        {
            PanUpdated += (_, e) =>
            {
                if (e.StatusType == GestureStatus.Running)
                {
                    translatedX = e.TotalX;
                    translatedY = e.TotalY;
                }
                else if (e.StatusType == GestureStatus.Completed)
                {
                    if (translatedX < 0 && Math.Abs(translatedX) > Math.Abs(translatedY))
                        SwipedLeft?.Invoke();
                    else if (translatedX > 0 && translatedX > Math.Abs(translatedY))
                        SwipedRight?.Invoke();
                    else if (translatedY < 0 && Math.Abs(translatedY) > Math.Abs(translatedX))
                        SwipedUp?.Invoke();
                    else if (translatedY > 0 && translatedY > Math.Abs(translatedX))
                        SwipedDown?.Invoke();
                }
            };
        }
    }
}