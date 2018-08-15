The goal is to create a very simple Tetris game:

- Tetris is a single-player game.
- In Tetris, there're seven kind of shapes. They're special 2D geometric shapes. Their mathematical name is tetromino. A tetromino is set of 4 units (squares), their position is given, fixed.
- Each game has one tetromino at the same time. The player can control it with his buttons.
- The tetromino can be rotated by 90 degrees to right by the player, if possible.
- The tetromino can be moved left, down or right by one unit by the player, if possible.
- Each game has one board, this is what the player sees. It is a grid, its cells may contain one tetromino unit.
- First, the board is empty.
- The board's size (number of rows and columns) is configurable.
- New tetrominoes are located at the board's top-center position.
- The tetromino moves down continously with some speed.
- The tetromino can't be moved outside of the board.
- The tetromino's units must not overlap other tetromino units.
- If a row in the board is full (ie. every cell contains one tetromino unit), that row is removed.
- When a row is removed, all rows over it are moved down by one row.
- When a row is removed, an empty row is inserted at the upper side of the board.
- When the tetromino can't be moved down anymore, a new tetromino is generated randomly.
- If creating a new tetromino if not possible without overlapping other unit in the board, the game is stopped.
- The player should try to remove as many rows as possible, by controlling the falling tetromino.