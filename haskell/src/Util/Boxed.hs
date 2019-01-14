module Util.Boxed (
  Boxed(..),
  boundingWidth,
  boundingHeight
) where

import Util.Dimension

class Boxed a where
  boundingBox :: a -> Dimension

bounding :: (Boxed a) => (Dimension -> Int) -> a -> Int
bounding takeResult box =
  takeResult $ boundingBox box

boundingWidth :: (Boxed a) => a -> Int
boundingWidth box =
  bounding width box

boundingHeight :: (Boxed a) => a -> Int
boundingHeight box =
  bounding height box