from tetris.logic.TetrisGame import TetrisGame


class TestTetrisGame(TetrisGame):
    def __init__(self, size, types, falling_speed):
        super().__init__(size, falling_speed)
        self.types = types

    def get_next_tetromino_type(self):
        return self.types.popleft()
