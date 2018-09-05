import unittest
from tests.helpers import util
from tetris.logic.board.Board import Board
from tetris.logic.tetromino.Tetromino import Tetromino
from tetris.util.containers import *


class TetrominoTest(unittest.TestCase):
    def setUp(self):
        self.board = Board(Dimension(7, 8))
    
    def test_tetromino_type_O_grid(self):
        tetromino = Tetromino(0, self.board)
        util.check_tetromino_state(self, tetromino, [
            "##",
            "##"
        ])

    def test_tetromino_type_I_grid(self):
        tetromino = Tetromino(1, self.board)
        util.check_tetromino_state(self, tetromino, [
            "#",
            "#",
            "#",
            "#"
        ])

    def test_tetromino_type_L_grid(self):
        tetromino = Tetromino(2, self.board)
        util.check_tetromino_state(self, tetromino, [
            "#.",
            "#.",
            "##"
        ])

    def test_tetromino_type_J_grid(self):
        tetromino = Tetromino(3, self.board)
        util.check_tetromino_state(self, tetromino, [
            ".#",
            ".#",
            "##"
        ])

    def test_tetromino_type_S_grid(self):
        tetromino = Tetromino(4, self.board)
        util.check_tetromino_state(self, tetromino, [
            ".##",
            "##."
        ])

    def test_tetromino_type_Z_grid(self):
        tetromino = Tetromino(5, self.board)
        util.check_tetromino_state(self, tetromino, [
            "##.",
            ".##"
        ])

    def test_tetromino_type_T_grid(self):
        tetromino = Tetromino(6, self.board)
        util.check_tetromino_state(self, tetromino, [
            "###",
            ".#."
        ])

    def test_move_to_initial_position(self):
        self.create_tetromino_type_T()
        position = self.tetromino.position
        self.assertEqual(position, Point(0, 0))
        self.tetromino.move_to_initial_pos()
        position = self.tetromino.position
        self.assertEqual(position, Point(2, 0))

    def test_move_to_initial_position_rounding(self):
        self.create_tetromino_type_I()
        self.tetromino.rotate_right()
        self.tetromino.move_to_initial_pos()
        position = self.tetromino.position
        self.assertEqual(position, Point(2, 0))

    def test_rotate_type_T(self):
        self.create_tetromino_type_T()
        util.check_tetromino_state(self, self.tetromino, [
            "###",
            ".#."
        ])
        self.tetromino.rotate_right()
        util.check_tetromino_state(self, self.tetromino, [
            "#.",
            "##",
            "#."
        ])
        self.tetromino.rotate_right()
        util.check_tetromino_state(self, self.tetromino, [
            ".#.",
            "###"
        ])
        self.tetromino.rotate_right()
        util.check_tetromino_state(self, self.tetromino, [
            ".#",
            "##",
            ".#"
        ])
        self.tetromino.rotate_right()
        util.check_tetromino_state(self, self.tetromino, [
            "###",
            ".#."
        ])

    def test_rotate_type_I(self):
        self.create_tetromino_type_I()
        util.check_tetromino_state(self, self.tetromino, [
            "#",
            "#",
            "#",
            "#"
        ])
        self.tetromino.rotate_right()
        util.check_tetromino_state(self, self.tetromino, [
            "####"
        ])
        self.tetromino.rotate_right()
        util.check_tetromino_state(self, self.tetromino, [
            "#",
            "#",
            "#",
            "#"
        ])

    def test_cant_rotate(self):
        self.create_tetromino_type_I()
        util.control_tetromino(self.tetromino, "DDSSSS")
        self.board.add_tetromino(self.tetromino)
        self.create_tetromino_type_T()
        util.control_tetromino(self.tetromino, "WSSS")
        self.assertFalse(util.control_tetromino(self.tetromino, "W"))

    def test_move_left_when_rotating_at_right_side(self):
        self.create_tetromino_type_I()
        util.control_tetromino(self.tetromino, "DDDDD")
        self.assertEqual(self.tetromino.position, Point(5, 0))
        util.control_tetromino(self.tetromino, "W")
        self.assertEqual(self.tetromino.position, Point(3, 0))

    def test_dont_move_left_when_rotating_at_right_side_isnt_possible(self):
        self.create_tetromino_type_I()
        util.control_tetromino(self.tetromino, "DDDSSSS")
        self.board.add_tetromino(self.tetromino)
        self.create_tetromino_type_I()
        util.control_tetromino(self.tetromino, "DDDDDDSSSS")
        self.assertFalse(util.control_tetromino(self.tetromino, "W"))

    def test_move_left(self):
        self.create_tetromino_type_T()
        self.tetromino.move_to_initial_pos()
        util.control_tetromino(self.tetromino, "A")
        position = self.tetromino.position
        self.assertEqual(position, Point(1, 0))

    def test_move_down(self):
        self.create_tetromino_type_T()
        self.tetromino.move_to_initial_pos()
        util.control_tetromino(self.tetromino, "S")
        position = self.tetromino.position
        self.assertEqual(position, Point(2, 1))

    def test_move_right(self):
        self.create_tetromino_type_T()
        self.tetromino.move_to_initial_pos()
        util.control_tetromino(self.tetromino, "D")
        position = self.tetromino.position
        self.assertEqual(position, Point(3, 0))

    def create_tetromino_type_T(self):
        self.tetromino = Tetromino(6, self.board)

    def create_tetromino_type_I(self):
        self.tetromino = Tetromino(1, self.board)
