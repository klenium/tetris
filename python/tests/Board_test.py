import unittest
import tests.helpers.util as util
from klenium.tetris.logic.board.Board import Board
from klenium.tetris.logic.tetromino.Tetromino import Tetromino
from klenium.tetris.util.containers import Dimension


class BoardTest(unittest.TestCase):
    def setUp(self):
        self.board = Board(Dimension(5, 4))

    def test_new_board_is_empty(self):
        util.check_board_state(self, self.board, [
            ".....",
            ".....",
            ".....",
            "....."
        ])

    def test_add_tetrominoes(self):
        self.add_test_data()
        util.check_board_state(self, self.board, [
            ".....",
            "....#",
            ".#.##",
            ".####"
        ])

    def test_moving_tetromino_inside_empty_board(self):
        tetromino = Tetromino(0, self.board)
        self.assertTrue(util.control_tetromino(tetromino, "DADDDSAADSDAD"))

    def test_moving_tetromino_outside_board_box(self):
        tetromino = Tetromino(0, self.board)
        self.assertFalse(util.control_tetromino(tetromino, "A"))
        self.assertFalse(util.control_tetromino(tetromino, "DDDD"))
        self.assertFalse(util.control_tetromino(tetromino, "SSS"))

    def test_invalid_tetromino_move_inside_used_board(self):
        self.add_test_data()
        tetromino = Tetromino(0, self.board)
        self.assertFalse(util.control_tetromino(tetromino, "S"))
        self.assertFalse(util.control_tetromino(tetromino, "DDD"))

    def test_remove_one_full_row(self):
        self.add_test_data()
        self.board.remove_full_rows()
        util.check_board_state(self, self.board, [
            ".....",
            "....#",
            ".#.##",
            ".####"
        ])
        tetromino = Tetromino(1, self.board)
        self.board.add_tetromino(tetromino)
        util.check_board_state(self, self.board, [
            "#....",
            "#...#",
            "##.##",
            "#####"
        ])
        self.board.remove_full_rows()
        util.check_board_state(self, self.board, [
            ".....",
            "#....",
            "#...#",
            "##.##"
        ])

    def test_remove_multiple_full_rows(self):
        self.add_test_data()
        self.board.remove_full_rows()
        tetromino = Tetromino(6, self.board)
        util.control_tetromino(tetromino, "DS")
        self.board.add_tetromino(tetromino)
        tetromino = Tetromino(1, self.board)
        self.board.add_tetromino(tetromino)
        self.board.remove_full_rows()
        util.check_board_state(self, self.board, [
            ".....",
            ".....",
            ".....",
            "#...."
        ])

    def add_test_data(self):
        #   .....
        #   ....#
        #   .#.##
        #   .####
        tetromino = Tetromino(3, self.board)
        util.control_tetromino(tetromino, "WWWDSS")
        self.board.add_tetromino(tetromino)
        tetromino = Tetromino(6, self.board)
        util.control_tetromino(tetromino, "WWDDWDS")
        self.board.add_tetromino(tetromino)
