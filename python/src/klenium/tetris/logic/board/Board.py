class Board:
    def __init__(self, size):
        self.grid = [[False] * size.height] * size.width
        self.size = size

    def add_tetromino(self, tetromino):
        pass

    def remove_full_rows(self):
        pass
