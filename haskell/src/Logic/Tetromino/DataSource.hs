module Logic.Tetromino.DataSource (
  getTetrominoData
) where

import Util.Point

rawData :: [[[String]]]

getTetrominoData :: Int -> Int -> [Point]
getTetrominoData shapeType rotation =
  let shapeMasks = rawData !! shapeType
      index = rotation `mod` (length shapeMasks)
      mask = shapeMasks !! index
  in filterMask mask 0
  where filterMask [] _ = []
        filterMask (row:restRows) y =
          let rowPoints = filterRow row 0 y
          in rowPoints ++ filterMask restRows (y + 1)
          where filterRow [] _ _ = []
                filterRow (' ':restCols) x y =
                  filterRow restCols (x + 1) y
                filterRow ('X':restCols) x y =
                  (Point x y) : filterRow restCols (x + 1) y


rawData = [
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