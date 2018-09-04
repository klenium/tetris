import unittest
from tests.helpers import util
from klenium.tetris.util.PeriodicTask import PeriodicTask
from tests.helpers.time_constant import PERIOD as PERIOD
from tests.helpers.time_constant import OFFSET as OFFSET


class PeriodicTaskSlowTest(unittest.TestCase):
    def setUp(self):
        self.foo = 1
        self.clock = PeriodicTask(lambda: ++self.foo, PERIOD)

    def test_non_started(self):
        util.run_later(OFFSET + PERIOD, lambda: self.assertEqual(self.foo, 1))

    def test_multiple_periods(self):
        self.clock.start()
        util.run_later(OFFSET, lambda: self.assertEqual(self.foo, 1))
        util.run_later(PERIOD, lambda: self.assertEqual(self.foo, 2))
        util.run_later(PERIOD, lambda: self.assertEqual(self.foo, 3))

    def test_stoped(self):
        self.clock.start()
        util.run_later(OFFSET, lambda: (
            self.assertEqual(self.foo, 1),
            self.clock.stop()
        ))
        util.run_later(PERIOD, lambda: self.assertEqual(self.foo, 1))
        util.run_later(PERIOD, lambda: self.assertEqual(self.foo, 1))

    def test_reset_period_time(self):
        self.clock.start()
        util.run_later(OFFSET, lambda: (
            self.assertEqual(self.foo, 1),
            self.clock.reset()
        ))
        util.run_later(OFFSET, lambda: self.assertEqual(self.foo, 1))
        util.run_later(PERIOD, lambda: self.assertEqual(self.foo, 2))
