module TestUtil (
  controlTetromino,
  checkGrid
) where

import Data.List
import Test.Hspec
import Logic.Board
import Logic.Tetromino.Type
import Logic.Tetromino

{- The result type cound be `Maybe Tetromino`, so `Nothing` could
   signal failures. But the successfulness of this method is often
   ignored. This doesn't cause any problem due to the other unit tests.
   Checking and getting the value of a `Maybe` is more complicated
   than ignoring this tuple's 2nd value. -}
controlTetromino :: Tetromino -> Board -> [Char] -> (Tetromino, Bool)
controlTetromino final _ [] = (final, True)
controlTetromino previous board (command:tail) =
  let next = case command of
        'W' -> rotateRight previous board
        'A' -> moveLeft previous board
        'S' -> moveDown previous board
        'D' -> moveRight previous board
        otherwise -> previous
  in if previous == next
     then (previous, False)
     else controlTetromino next board tail

checkGrid :: (Show a) => a -> [String] -> Expectation
checkGrid box excepted =
  let actual = show box
      excepted' = intercalate "\n" excepted
  in actual `shouldBe` excepted'