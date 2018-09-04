from klenium.tetris.util.containers import Point


class Tetromino:
    def __init__(self, shape_type, board):
        self.position = Point(0, 0)

    def move_to_initial_pos(self):
        return False

    def rotate_right(self):
        return False

    def move_left(self):
        return False

    def move_down(self):
        return False

    def move_right(self):
        return False
