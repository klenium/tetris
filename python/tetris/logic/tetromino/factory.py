from tetris.util.containers import *


def get_tetromino_data(shape_type):
    TetrominoData = namedtuple('TetrominoData', ['parts', 'bounding_box'])
    masks = _rawData[shape_type]
    result = []
    for rotation in range(len(masks)):
        mask = masks[rotation]
        parts = []
        height = len(mask)
        width = len(mask[0])
        for y in range(height):
            for x in range(width):
                if mask[y][x] != ' ':
                    parts.append(Point(x, y))
        result.append(TetrominoData(parts, Dimension(width, height)))
    return result


_rawData = [
    [
        [
            "XX",
            "XX"
        ]
    ],
    [
        [
            "X",
            "X",
            "X",
            "X"
        ],
        [
            "XXXX"
        ]
    ],
    [
        [
            "X ",
            "X ",
            "XX"
        ],
        [
            "  X",
            "XXX"
        ],
        [
            "XX",
            " X",
            " X"
        ],
        [
            "XXX",
            "X  "
        ]
    ],
    [
        [
            " X",
            " X",
            "XX"
        ],
        [
            "XXX",
            "  X"
        ],
        [
            "XX",
            "X ",
            "X "
        ],
        [
            "X  ",
            "XXX"
        ]
    ],
    [
        [
            " XX",
            "XX "
        ],
        [
            "X ",
            "XX",
            " X"
        ]
    ],
    [
        [
            "XX ",
            " XX"
        ],
        [
            " X",
            "XX",
            "X "
        ]
    ],
    [
        [
            "XXX",
            " X "
        ],
        [
            "X ",
            "XX",
            "X "
        ],
        [
            " X ",
            "XXX"
        ],
        [
            " X",
            "XX",
            " X"
        ]
    ]
]
