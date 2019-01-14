module Logic.TetrominoSpec where

import Data.List
import Data.Maybe
import Test.Hspec
import Util.Point
import Logic.Board
import Logic.Tetromino.Type
import Logic.Tetromino
import TestUtil

board = newBoard 7 8

tetrominoTypeO = newTetromino 0
tetrominoTypeI = newTetromino 1
tetrominoTypeL = newTetromino 2
tetrominoTypeJ = newTetromino 3
tetrominoTypeS = newTetromino 4
tetrominoTypeZ = newTetromino 5
tetrominoTypeT = newTetromino 6

cantMove = expectationFailure "Tetromino could not be moved"

checkTypeTMove :: String -> Point -> Expectation
checkTypeTMove command excepted = do
  let t1 = moveToInitialPos tetrominoTypeT board
  case controlTetromino t1 board command of
    (_, False) -> cantMove
    (t3, True) -> (position t3) `shouldBe` excepted

spec :: Spec
spec = parallel $ do
  describe "Check type-specific base grid" $ do
    specify "Type O" $
      checkGrid tetrominoTypeO [
          "XX",
          "XX"
        ]

    specify "Type I" $
      checkGrid tetrominoTypeI [
          "X",
          "X",
          "X",
          "X"
        ]

    specify "Type L" $
      checkGrid tetrominoTypeL [
          "X ",
          "X ",
          "XX"
        ]

    specify "Type J" $
      checkGrid tetrominoTypeJ [
          " X",
          " X",
          "XX"
        ]

    specify "Type S" $
      checkGrid tetrominoTypeS [
          " XX",
          "XX "
        ]

    specify "Type Z" $
      checkGrid tetrominoTypeZ [
          "XX ",
          " XX"
        ]

    specify "Type T" $
      checkGrid tetrominoTypeT [
          "XXX",
          " X "
        ]

  describe "Rotating" $ do
    it "Rotate a T-type tetromino" $ do
      checkGrid tetrominoTypeT [
          "XXX",
          " X "
        ]
      let t1 = rotateRight tetrominoTypeT board
      checkGrid t1 [
          "X ",
          "XX",
          "X "
        ]
      let t2 = rotateRight t1 board
      checkGrid t2 [
          " X ",
          "XXX"
        ]
      let t3 = rotateRight t2 board
      checkGrid t3 [
          " X",
          "XX",
          " X"
        ]
      let t4 = rotateRight t3 board
      checkGrid t4 [
          "XXX",
          " X "
        ]

    it "Rotate an I-type tetromino" $ do
      checkGrid tetrominoTypeI [
          "X",
          "X",
          "X",
          "X"
        ]
      let t1 = rotateRight tetrominoTypeI board
      checkGrid t1 [
          "XXXX"
        ]
      let t2 = rotateRight t1 board
      checkGrid t2 [
          "X",
          "X",
          "X",
          "X"
        ]

    it "Can't rotate" $ do
      let t1 = fst $ controlTetromino tetrominoTypeI board "DDSSSS"
      let b1 = addTetromino board t1
      let t2 = fst $ controlTetromino tetrominoTypeT b1 "WSSS"
      (snd $ controlTetromino t2 b1 "W") `shouldBe` False

    it "Move left when rotating at the right side" $ do
      let t1 = fst $ controlTetromino tetrominoTypeI board "DDDDD"
      (position t1) `shouldBe` (Point 5 0)
      case controlTetromino t1 board "W" of
        (_, False) -> cantMove
        (t2, True) -> (position t2) `shouldBe` (Point 3 0)

    it "Can't move left when rotating at the right side" $ do
      let t1 = fst $ controlTetromino tetrominoTypeI board "DDDSSSS"
      let b1 = addTetromino board t1
      case controlTetromino tetrominoTypeI b1 "DDDDDDSSSS" of
        (_, False) -> cantMove
        (t2, True) -> (snd $ controlTetromino t2 b1 "W") `shouldBe` False

  describe "Moving" $ do
    it "Move to initial position" $ do
      (position tetrominoTypeT) `shouldBe` (Point 0 0)
      let moved = moveToInitialPos tetrominoTypeT board
      (position moved) `shouldBe` (Point 2 0)

    it "Move to a rounded initial position" $ do
      let t1 = fst $ controlTetromino tetrominoTypeI board "W"
      let t2 = moveToInitialPos t1 board
      (position t2) `shouldBe` (Point 2 0)

    specify "left" $
      checkTypeTMove "A" (Point 1 0)

    specify "down" $
      checkTypeTMove "S" (Point 2 1)

    specify "right" $
      checkTypeTMove "D" (Point 3 0)