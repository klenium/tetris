import sys
sys.path.insert(0, 'lib')

from tetris.logic.Command import Command
from tetris.main import run_game


run_game(
    rows=17,
    cols=11,
    size=30,
    speed=700,
    controls={
        'w': Command.ROTATE,
        'a': Command.MOVE_LEFT,
        's': Command.MOVE_DOWN,
        'd': Command.MOVE_RIGHT,
        '<space>': Command.DROP
    }
)
