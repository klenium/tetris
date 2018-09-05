class Board:
    def __init__(self, size):
        self.grid = [[False for _ in range(size.height)] for _ in range(size.width)]
        self.size = size

    def can_add_tetromino(self, tetromino, position):
        if not self.is_box_inside_grid(position, tetromino.bounding_box):
            return False
        for part_offset in tetromino.parts:
            x, y = position + part_offset
            if self.grid[x][y]:
                return False
        return True

    def add_tetromino(self, tetromino):
        for part_offset in tetromino.parts:
            x, y = tetromino.position + part_offset
            self.grid[x][y] = True

    def remove_full_rows(self, start_index, height):
        for row in range(start_index, start_index + height):
            if self.is_row_full(row):
                self.move_down_boards_upper_part(row)

    def is_box_inside_grid(self, box_position, box_size):
        return box_position >= (0, 0) and (box_position + box_size) <= self.size

    def is_row_full(self, index):
        for x in range(self.size.width):
            if not self.grid[x][index]:
                return False
        return True

    def move_down_boards_upper_part(self, index):
        for y in range(index, 0, -1):
            for x in range(self.size.width):
                self.grid[x][y] = self.grid[x][y - 1]
        for x in range(self.size.width):
            self.grid[x][0] = False
