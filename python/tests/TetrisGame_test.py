import unittest

from klenium.tetris.logic.Command import Command
from tests.helpers import util
from klenium.tetris.util.Dimension import Dimension
from klenium.tetris.util.Point import Point
from tests.helpers.TestTetrisGame import TestTetrisGame
from tests.helpers.time_constant import PERIOD as PERIOD
from tests.helpers.time_constant import OFFSET as OFFSET


class TetrisGameSlowTest(unittest.TestCase):
    def setUp(self):
        #        J, T, I, O
        types = [3, 6, 1, 0]
        self.game = TestTetrisGame(Dimension(5, 6), types, PERIOD)

    def checkFalling(self):
        self.game.start()
        tetromino = self.game.falling_tetromino
        self.assertEqual(tetromino.position,  Point(2, 0))
        util.run_later(OFFSET + PERIOD, lambda: self.assertEqual(tetromino.position, Point(2, 1)))
        util.run_later(PERIOD, lambda: self.assertEqual(tetromino.position, Point(2, 2)))
        util.run_later(PERIOD, lambda: self.assertEqual(tetromino.position, Point(2, 3)))

    def checlBoardSize(self):
        self.assertEqual(self.game.board.size.width, 5)
        self.assertEqual(self.game.board.size.height, 6)

    def unStartedState(self):
        self.assertFalse(self.game.is_running)
        self.assertIsNone(self.game.falling_tetromino)
        self.game.handle_command(Command.MOVE_DOWN)
        self.assertIsNone(self.game.falling_tetromino)

    def runningState(self):
        self.game.start()
        self.assertTrue(self.game.is_running)
        self.assertIsNotNone(self.game.falling_tetromino)

    def stoppedState(self):
        self.game.start()
        util.control_tetromino(self.game, " ")
        util.control_tetromino(self.game, " ")
        tetromino1 = self.game.falling_tetromino
        self.assertFalse(self.game.is_running)
        util.control_tetromino(self.game, " ")
        tetromino2 = self.game.falling_tetromino
        self.assertIs(tetromino1, tetromino2)
        self.assertEqual(tetromino1.position, tetromino2.position)

    def control_tetromino(self):
        self.game.start()
        tetromino = self.game.falling_tetromino
        position1 = tetromino.position
        self.game.handle_command(Command.MOVE_LEFT)
        position2 = tetromino.position
        self.assertNotEqual(position1, position2)

    def tetrominoLanded(self):
        self.game.start()
        tetromino1 = self.game.falling_tetromino
        self.game.handle_command(Command.DROP)
        tetromino2 = self.game.falling_tetromino
        self.assertIsNot(tetromino1, tetromino2)
        self.assertEqual(tetromino2.shape_type, 6)
        util.check_board_state(self, self.game.board, [
            ".....",
            ".....",
            ".....",
            "...#.",
            "...#.",
            "..##."
        ])

    def removeFullRows(self):
        self.game.start()
        util.control_tetromino(self.game, "DD ")
        util.control_tetromino(self.game, "WWA ")
        util.check_board_state(self, self.game.board, [
            ".....",
            ".....",
            ".....",
            ".....",
            "....#",
            ".#..#"
        ])

    def initialPosition(self):
        self.game.start()
        tetromino = self.game.falling_tetromino
        self.assertEqual(tetromino.position,  Point(2, 0))
        util.control_tetromino(self.game, "D ")
        tetromino = self.game.falling_tetromino
        self.assertEqual(tetromino.position,  Point(1, 0))
        util.control_tetromino(self.game, " ")
        tetromino = self.game.falling_tetromino
        self.assertEqual(tetromino.position,  Point(2, 0))
