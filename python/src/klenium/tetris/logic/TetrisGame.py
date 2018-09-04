from klenium.tetris.logic.board.Board import Board


class TetrisGame:
    def __init__(self, size, falling_speed):
        self.falling_tetromino = None
        self.board = Board(size)
        self.is_running = False

    def start(self):
        pass

    def handle_command(self, command):
        pass
