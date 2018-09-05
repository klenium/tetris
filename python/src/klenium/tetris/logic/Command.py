from enum import Enum

# noinspection PyArgumentList
Command = Enum('Command', [
    'ROTATE',
    'MOVE_LEFT',
    'MOVE_DOWN',
    'MOVE_RIGHT',
    'DROP',
    'FALL'
])
