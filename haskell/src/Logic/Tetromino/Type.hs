module Logic.Tetromino.Type (
  Tetromino(..)
) where

import Data.List
import Util.Point
import Util.Dimension
import Util.Boxed

data Tetromino = Tetromino {
  shapeType :: Int,
  parts :: [Point],
  position :: Point,
  rotation :: Int
}
  deriving Eq

mapParts :: Tetromino -> (Point -> b) -> [b]
mapParts tetromino f =
  map f $ parts tetromino

isPartExists :: Tetromino -> Int -> Int -> Bool
isPartExists tetromino x y =
  let needle = Point x y
      list = parts tetromino
  in find (== needle) list == Nothing

instance Boxed Tetromino where
  boundingBox tetromino =
    Dimension width height
    where width = 1 + (foldl max 0 $ mapParts tetromino x)
          height = 1 + (foldl max 0 $ mapParts tetromino y)

instance Show Tetromino where
  show tetromino =
    let width = boundingWidth tetromino
        height = boundingHeight tetromino
    in intercalate "\n" [
      concat [
        if isPartExists tetromino x y
        then " " else "X"
        | x <- [0 .. width - 1]
      ]
      | y <- [0 .. height - 1]
    ]