module Util.Point (
  Point(..),
  plusPoint,
  notGreaterThan,
  notSmallerThan
) where

data Point = Point {x :: Int, y :: Int}
  deriving (Eq, Show)

plusPoint :: Point -> Point -> Point
plusPoint (Point x1 y1) (Point x2 y2) = Point (x1 + x2) (y1 + y2)

notGreaterThan :: Point -> Point -> Bool
notGreaterThan (Point x1 y1) (Point x2 y2) = x1 <= x2 && y1 <= y2

notSmallerThan :: Point -> Point -> Bool
notSmallerThan (Point x1 y1) (Point x2 y2) = x1 >= x2 && y1 >= y2