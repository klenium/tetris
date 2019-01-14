module Logic.Tetromino (
  Tetromino(..),
  newTetromino,
  moveToInitialPos,
  rotateRight,
  moveLeft,
  moveDown,
  moveRight
) where

import Data.List
import Util.Point
import Util.Dimension
import Util.Boxed
import Logic.Tetromino.Type
import Logic.Tetromino.DataSource
import Logic.Board

newTetromino :: Int -> Tetromino
newTetromino shapeType =
  Tetromino {
    shapeType = shapeType,
    parts = getTetrominoData shapeType 0,
    position = Point 0 0,
    rotation = 0
  }

halfOf :: Int -> Float
halfOf int = (fromIntegral int) / 2

moveToInitialPos :: Tetromino -> Board -> Tetromino
moveToInitialPos tetromino board =
  let centerOfBoard = halfOf $ boundingWidth board
      halfTetrominoWidth = halfOf $ boundingWidth tetromino
      centeredTetrominoPosX = ceiling $ centerOfBoard - halfTetrominoWidth
  in tryPush tetromino board $ Point centeredTetrominoPosX 0

rotateRight :: Tetromino -> Board -> Tetromino
rotateRight original board =
  let nextRotation = (rotation original + 1) `mod` 4
      rotated = original {
        rotation = nextRotation,
        parts = getTetrominoData (shapeType original) nextRotation
      }
  in if canAddTetromino board rotated $ position original
     then rotated
     else let maxPush = boundingWidth rotated
              pushed = multiMoveLeft rotated board 1 maxPush
          in if pushed /= rotated then pushed else original

moveLeft :: Tetromino -> Board -> Tetromino
moveLeft tetromino board =
  tryPush tetromino board (Point (-1) 0)

multiMoveLeft :: Tetromino -> Board -> Int -> Int -> Tetromino
multiMoveLeft tetromino board current max
  | current == max = tetromino
  | otherwise =
      let pushed = tryPush tetromino board (Point (-current) 0)
      in if pushed /= tetromino
         then pushed
         else multiMoveLeft tetromino board (current + 1) max

moveDown :: Tetromino -> Board -> Tetromino
moveDown tetromino board =
  tryPush tetromino board (Point 0 1)

moveRight :: Tetromino -> Board -> Tetromino
moveRight tetromino board =
  tryPush tetromino board (Point 1 0)

tryPush :: Tetromino -> Board -> Point -> Tetromino
tryPush tetromino board delta =
  let newPos = (position tetromino) `plusPoint` delta
  in
    if canAddTetromino board tetromino newPos
    then tetromino { position = newPos }
    else tetromino