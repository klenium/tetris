from klenium.tetris.logic.TetrisGame import TetrisGame


class TestTetrisGame(TetrisGame):
    def __init__(self, size, types, falling_speed):
        super().__init__(size, falling_speed)
        self.types = types
