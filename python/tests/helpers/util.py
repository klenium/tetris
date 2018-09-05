import time
import sys
from tetris.logic.Command import Command


def control_tetromino(tetromino, commands):
    actions = {
        'W': tetromino.rotate_right,
        'A': tetromino.move_left,
        'S': tetromino.move_down,
        'D': tetromino.move_right
    }
    for command in commands:
        if not actions[command]():
            return False
    return True


def control_game(game, commands):
    signals = {
        'W': Command.ROTATE,
        'A': Command.MOVE_LEFT,
        'S': Command.MOVE_DOWN,
        'D': Command.MOVE_RIGHT,
        ' ': Command.DROP
    }
    for command in commands:
        game.handle_command(signals[command])


def check_board_state(testcase, board, excepted):
    _check_object_state(
        testcase,
        board.grid,
        board.size,
        excepted,
        lambda d, x, y: d[x][y],
        lambda c: not c
    )


def check_tetromino_state(testcase, tetromino, excepted):
    _check_object_state(
        testcase,
        tetromino.parts,
        tetromino.bounding_box,
        excepted,
        lambda d, x, y: _find_point(d, x, y),
        lambda c: c is None
    )


def run_later(delay, task):
    time.sleep(delay / 1000)
    task()


def _check_object_state(testcase, data, size, excepted, find_cell, check_cell_empty):
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
                console_view.append('.' if cell_empty else '#')
            else:
                console_view.append('!' if cell_empty else '?')
        console_view.append('|\n')
    console_view.extend(horizontal_border)
    if not grid_equals_to_expected:
        print('\n', ''.join(console_view), sep='', file=sys.stderr)
    testcase.assertTrue(grid_equals_to_expected)


def _find_point(points, x, y):
    for point in points:
        if point.x == x and point.y == y:
            return point
    return None
