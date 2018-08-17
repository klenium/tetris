using hu.klenium.tetris.Util;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using TetrisGameTests.Helpers;

namespace TetrisGameTests
{
    [TestClass]
    public class PeriodicTaskTest
    {
        private static readonly int PERIOD = 500;
        private static readonly int OFFSET = PERIOD / 2;
        private PeriodicTask clock = null;
        private int foo = 1;
        [TestInitialize] public void SetUp()
        {
            clock = new PeriodicTask(() => ++foo, PERIOD);
        }
        [TestMethod] public void NonStarted()
        {
            TestUtil.RunLater(OFFSET + PERIOD, () => Assert.AreEqual(foo, 1));
        }
        [TestMethod] public void MultiplePeriods()
        {
            clock.Start();
            TestUtil.RunLater(OFFSET, () => Assert.AreEqual(foo, 1));
            TestUtil.RunLater(PERIOD, () => Assert.AreEqual(foo, 2));
            TestUtil.RunLater(PERIOD, () => Assert.AreEqual(foo, 3));
        }
        [TestMethod] public void Stoped()
        {
            clock.Start();
            TestUtil.RunLater(OFFSET, () => {
                Assert.AreEqual(foo, 1);
                clock.Stop();
            });
            TestUtil.RunLater(PERIOD, () => Assert.AreEqual(foo, 1));
            TestUtil.RunLater(PERIOD, () => Assert.AreEqual(foo, 1));
        }
        [TestMethod] public void ResetPeriodTime()
        {
            clock.Start();
            TestUtil.RunLater(OFFSET, () => {
                Assert.AreEqual(foo, 1);
                clock.Reset();
            });
            TestUtil.RunLater(OFFSET, () => Assert.AreEqual(foo, 1));
            TestUtil.RunLater(PERIOD, () => Assert.AreEqual(foo, 2));
        }
    }
}