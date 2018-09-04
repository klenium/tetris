import time
import sys
from klenium.tetris.logic.Command import Command


def control_tetromino(tetromino, commands):
    success = True
    for char in commands:
        success = {
            'W': lambda: tetromino.rotate_right(),
            'A': lambda: tetromino.move_left(),
            'S': lambda: tetromino.move_down(),
            'D': lambda: tetromino.move_right()
        }[char]()
        if not success:
            break
    return success


def control_game(game, commands):
    for char in commands:
        game.handleCommand({
            'W': lambda: Command.ROTATE,
            'A': lambda: Command.MOVE_LEFT,
            'S': lambda: Command.MOVE_DOWN,
            'D': lambda: Command.MOVE_RIGHT,
            ' ': lambda: Command.DROP
        }[char]())


def check_board_state(testcase, board, excepted):
    __check_object_state(
        testcase,
        board.grid,
        board.size,
        excepted,
        lambda d, x, y: d[x][y],
        lambda c: not c
    )


def check_tetromino_state(testcase, tetromino, excepted):
    data = tetromino.getCurrentData()
    __check_object_state(
        testcase,
        data.parts,
        data.boundingBox,
        excepted,
        lambda d, x, y: __find_point(d, x, y),
        lambda c: c is None
    )


def run_later(delay, task):
    time.sleep(delay / 1000)
    task()


def __check_object_state(testcase, data, size, excepted, find_cell, check_cell_empty):
    testcase.assertEqual(size.width, len(excepted[0]))
    testcase.assertEqual(size.height, len(excepted))
    grid_equals_to_expected = True
    horizontal_border = ['+', '-' * size.width, '+\n']
    console_view = horizontal_border.copy()
    for y in range(size.height):
        console_view.append('|')
        for x in range(size.width):
            cell = find_cell(data, x, y)
            ch = excepted[y][x]
            cell_empty = check_cell_empty(cell)
            cell_equals = cell_empty == (ch == '.')
            grid_equals_to_expected &= cell_equals
            if cell_equals:
                console_view.append('.' if cell_empty else 'X')
            else:
                console_view.append('!' if cell_empty else '?')
        console_view.append('|\n')
    console_view.extend(horizontal_border)
    if not grid_equals_to_expected:
        print('\n', ''.join(console_view), sep='', file=sys.stderr)
    testcase.assertTrue(grid_equals_to_expected)


def __find_point(points, x, y):
    for point in points:
        if point.x == x and point.y == y:
            return point
    return None
