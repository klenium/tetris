from tetris.logic.TetrisGame import TetrisGame
from tetris.util.containers import Dimension
from tetris.view.GraphicGameFrame import GraphicGameFrame


def run_game(rows, cols, size, speed, controls):
    grid_size = Dimension(cols, rows)
    square_size = size
    falling_speed = speed
    frame = GraphicGameFrame(grid_size, square_size)
    game = TetrisGame(grid_size, falling_speed)
    game.board_state_change.on_change += frame.display_board
    game.tetromino_state_change.on_change += frame.display_tetromino
    game.game_over.on_change += frame.display_game_over
    game.start()
    frame.register_event_listeners(game, controls)
    frame.show_window()
