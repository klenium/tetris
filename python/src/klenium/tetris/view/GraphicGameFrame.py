import sys
from appJar import gui


background_color = '#171717'
tetromino_color = '#FAE63C'


class GraphicGameFrame:
    def __init__(self, grid_size, square_size):
        self.last_tetromino_data = []
        self.last_board_data = []
        self.controls = []
        self.game = None
        self.grid_size = grid_size
        self.square_size = square_size
        width = grid_size.width * square_size
        height = grid_size.height * square_size
        self.app = gui('Tetris', (width, height), showIcon=False)
        self.app.setResizable(False)
        self.app.hideTitleBar()  # Needed to "fix" incurrect window height bug.
        self.app.setGuiPadding(0, 0)  # Needed to "fix" incorrect size of the window's content.
        self.app.setLocation("CENTER")
        self.app.addCanvas("main", height, width)

    def show_window(self):
        self.app.go()  # Infinite loop, must be called last.

    def register_event_listeners(self, game, controls):
        self.game = game
        self.controls = controls
        self.app.bindKeys(list(controls.keys()) + ['<Escape>'], self._handle_key_press)

    def _handle_key_press(self, key):
        if key == '<Escape>':  # Because title bar is hidden.
            sys.exit(0)
        if key in self.controls:
            self.game.handle_command(self.controls[key])

    def display_tetromino(self, tetromino):
        self.last_tetromino_data = []
        for part_offset in tetromino.parts:
            self.last_tetromino_data.append(tetromino.position + part_offset)
        self.app.queueFunction(self._render_canvas)

    def display_board(self, board):
        self.last_board_data = board.grid
        self.app.queueFunction(self._render_canvas)

    def display_game_over(self):
        w = self.grid_size.width
        h = self.grid_size.height
        self.last_board_data = [[False] * h] * w
        self.last_tetromino_data = []
        self.app.queueFunction(self._render_canvas)

    def _render_canvas(self):
        self.app.clearCanvas('main')
        for x in range(self.grid_size.width):
            for y in range(self.grid_size.height):
                cell = self.last_board_data[x][y]
                color = tetromino_color if cell else background_color
                self._draw_square(color, (x, y))
        for part_position in self.last_tetromino_data:
            self._draw_square(tetromino_color, part_position)

    def _draw_square(self, color, position):
        s = self.square_size
        x = position[0] * s
        y = position[1] * s
        self.app.addCanvasRectangle('main', x, y, s, s, fill=color, outline='')
