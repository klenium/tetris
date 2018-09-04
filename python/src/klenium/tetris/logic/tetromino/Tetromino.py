import math
from klenium.tetris.logic.tetromino.factory import get_tetromino_data
from klenium.tetris.util.containers import Point


class Tetromino:
    def __init__(self, shape_type, board):
        self._board = board
        self.position = Point(0, 0)
        self._data = get_tetromino_data(shape_type)
        self._rotation = 0

    @property
    def rotation(self):
        return self._rotation

    @rotation.setter
    def rotation(self, value):
        self._rotation = value % len(self._data)

    @property
    def parts(self):
        return self._data[self.rotation].parts

    @property
    def bounding_box(self):
        return self._data[self.rotation].bounding_box

    def move_to_initial_pos(self):
        board_width = self._board.size.width
        tetromino_width = self.bounding_box.width
        centered_tetromino_pos = math.ceil((board_width - tetromino_width) / 2.0)
        return self.try_push((centered_tetromino_pos, 0))

    def rotate_right(self):
        can_rotate = False
        old_rotation = self.rotation
        self.rotation = self.rotation + 1
        if self.can_push((0, 0)):
            can_rotate = True
        else:
            for i in range(1, self.bounding_box.width):
                can_rotate = self.try_push((-i, 0))
                if can_rotate:
                    break
        if not can_rotate:
            self.rotation = old_rotation
        return can_rotate

    def move_left(self):
        return self.try_push((-1, 0))

    def move_down(self):
        return self.try_push((0, 1))

    def move_right(self):
        return self.try_push((1, 0))

    def try_push(self, delta):
        can_slide = self.can_push(delta)
        if can_slide:
            self.position += delta
        return can_slide

    def can_push(self, delta):
        return self._board.can_add_tetromino(self, self.position + delta)
