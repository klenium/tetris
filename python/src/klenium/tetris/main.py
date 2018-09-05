from klenium.tetris.logic.Command import Command
from klenium.tetris.logic.TetrisGame import TetrisGame
from klenium.tetris.util.containers import Dimension
from klenium.tetris.view.GraphicGameFrame import GraphicGameFrame

grid_size = Dimension(11, 17)
square_size = 30
falling_speed = 700
controls = {
    'w': Command.ROTATE,
    'a': Command.MOVE_LEFT,
    's': Command.MOVE_DOWN,
    'd': Command.MOVE_RIGHT,
    '<space>': Command.DROP
}


frame = GraphicGameFrame(grid_size, square_size)
game = TetrisGame(grid_size, falling_speed)
game.board_state_change.on_change += frame.display_board
game.tetromino_state_change.on_change += frame.display_tetromino
game.game_over.on_change += frame.display_game_over
game.start()
frame.register_event_listeners(game, controls)
frame.show_window()
