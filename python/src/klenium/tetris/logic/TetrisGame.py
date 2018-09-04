import random
from klenium.tetris.logic.board.Board import Board
from klenium.tetris.logic.tetromino.Tetromino import Tetromino
from klenium.tetris.util.PeriodicTask import PeriodicTask
from klenium.tetris.logic.Command import Command


class TetrisGame:
    def __init__(self, size, falling_speed):
        self.falling_tetromino: Tetromino = None
        self.board = Board(size)
        self.is_running = False
        self.gravity = PeriodicTask(lambda: self.handle_command(Command.FALL), falling_speed)

    def start(self):
        self.is_running = self.generate_next_tetromino()
        if self.is_running:
            self.gravity.start()

    def stop(self):
        self.is_running = False
        self.gravity.stop()

    def handle_command(self, command):
        if not self.is_running:
            return
        if command == Command.ROTATE:
            self._rotate_tetromino()
        elif command == Command.MOVE_LEFT:
            self._move_left_tetromino()
        elif command == Command.MOVE_RIGHT:
            self._move_right_tetromino()
        elif command == Command.MOVE_DOWN:
            self.gravity.reset()
            self._move_down_tetromino()
        elif command == Command.FALL:
            self._move_down_tetromino()
        elif command == Command.DROP:
            while self._move_down_tetromino():
                pass

    def tetromino_landed(self):
        self.board.add_tetromino(self.falling_tetromino)
        y = self.falling_tetromino.position.y
        height = self.falling_tetromino.bounding_box.height
        self.board.remove_full_rows(y, height)
        added = self.generate_next_tetromino()
        if added:
            self.gravity.reset()
        else:
            self.stop()

    def generate_next_tetromino(self):
        shape_type = self.get_next_tetromino_type()
        self.falling_tetromino = Tetromino(shape_type, self.board)
        return self.falling_tetromino.move_to_initial_pos()

    def get_next_tetromino_type(self):
        return random.randrange(0, 6)

    def _rotate_tetromino(self):
        self.falling_tetromino.rotate_right()

    def _move_left_tetromino(self):
        self.falling_tetromino.move_left()

    def _move_down_tetromino(self):
        moved = self.falling_tetromino.move_down()
        if not moved:
            self.tetromino_landed()
        return moved

    def _move_right_tetromino(self):
        self.falling_tetromino.move_right()
