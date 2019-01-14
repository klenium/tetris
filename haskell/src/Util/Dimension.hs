module Util.Dimension (
  Dimension(..),
  plusDim,
  toPoint
) where

import Util.Point

data Dimension = Dimension {width :: Int, height :: Int}
  deriving (Eq, Show)

plusDim :: Point -> Dimension -> Point
plusDim (Point x1 y1) (Dimension w h) = Point (x1 + w) (y1 + h)

toPoint :: Dimension -> Point
toPoint (Dimension w h) = Point w h