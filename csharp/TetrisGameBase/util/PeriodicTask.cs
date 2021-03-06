﻿using System;
using System.Timers;

namespace hu.klenium.tetris.util
{
    public class PeriodicTask
    {
        private Timer timer = null;
        public PeriodicTask(Action task, int delay)
        {
            timer = new Timer();
            timer.Interval = delay;
            timer.AutoReset = true;
            timer.Elapsed += (t, e) => task.Invoke();
        }
        public void Start()
        {
            timer.Enabled = true;
        }
        public void Stop()
        {
            timer.Enabled = false;
        }
        public void Reset()
        {
            Stop();
            Start();
        }
    }
}