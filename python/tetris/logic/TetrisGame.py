import random
from events import Events as NotifyEvent
from tetris.logic.board.Board import Board
from tetris.logic.tetromino.Tetromino import Tetromino
from tetris.util.PeriodicTask import PeriodicTask
from tetris.logic.Command import Command


class TetrisGame:
    def __init__(self, size, falling_speed):
        self.falling_tetromino: Tetromino = None
        self.board = Board(size)
        self.is_running = False
        self.gravity = PeriodicTask(lambda: self.handle_command(Command.FALL), falling_speed)
        self.board_state_change = NotifyEvent()
        self.tetromino_state_change = NotifyEvent()
        self.game_over = NotifyEvent()

    def start(self):
        self.is_running = self.generate_next_tetromino()
        if self.is_running:
            self.gravity.start()
            self.board_state_change.on_change(self.board)
            self.tetromino_state_change.on_change(self.falling_tetromino)

    def stop(self):
        self.is_running = False
        self.gravity.stop()
        self.game_over.on_change()

    def handle_command(self, command):
        if not self.is_running:
            return
        state_changed = False
        if command == Command.ROTATE:
            state_changed = self._rotate_tetromino()
        elif command == Command.MOVE_LEFT:
            state_changed = self._move_left_tetromino()
        elif command == Command.MOVE_RIGHT:
            state_changed = self._move_right_tetromino()
        elif command == Command.MOVE_DOWN:
            self.gravity.reset()
            state_changed = self._move_down_tetromino()
        elif command == Command.FALL:
            state_changed = self._move_down_tetromino()
        elif command == Command.DROP:
            while self._move_down_tetromino():
                pass
        if state_changed and self.is_running:
            self.tetromino_state_change.on_change(self.falling_tetromino)

    def tetromino_landed(self):
        self.board.add_tetromino(self.falling_tetromino)
        y = self.falling_tetromino.position.y
        height = self.falling_tetromino.bounding_box.height
        self.board.remove_full_rows(y, height)
        self.board_state_change.on_change(self.board)
        added = self.generate_next_tetromino()
        if added:
            self.gravity.reset()
            self.tetromino_state_change.on_change(self.falling_tetromino)
        else:
            self.stop()

    def generate_next_tetromino(self):
        shape_type = self.get_next_tetromino_type()
        self.falling_tetromino = Tetromino(shape_type, self.board)
        return self.falling_tetromino.move_to_initial_pos()

    def get_next_tetromino_type(self):
        return random.randint(0, 6)

    def _rotate_tetromino(self):
        return self.falling_tetromino.rotate_right()

    def _move_left_tetromino(self):
        return self.falling_tetromino.move_left()

    def _move_down_tetromino(self):
        moved = self.falling_tetromino.move_down()
        if not moved:
            self.tetromino_landed()
        return moved

    def _move_right_tetromino(self):
        return self.falling_tetromino.move_right()
