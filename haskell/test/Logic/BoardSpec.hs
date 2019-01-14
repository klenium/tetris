module Logic.BoardSpec where

import Data.List
import Data.Maybe
import Test.Hspec
import Util.Point
import Util.Dimension
import Logic.Board
import Logic.Tetromino.Type
import Logic.Tetromino
import TestUtil

tetrominoTypeO = newTetromino 0
tetrominoTypeI = newTetromino 1
tetrominoTypeJ = newTetromino 3
tetrominoTypeT = newTetromino 6

emptyBoard = newBoard 5 4

testBoard =
  let t1 = fst $ controlTetromino tetrominoTypeJ emptyBoard "WWWDSS"
      b1 = addTetromino emptyBoard t1
      t2 = fst $ controlTetromino tetrominoTypeT b1 "WWDDWDS"
  in addTetromino b1 t2

spec :: Spec
spec = parallel $ do
  describe "Board" $ do
    it "is empty when newly created" $ do
      checkGrid emptyBoard [
          "     ",
          "     ",
          "     ",
          "     "
        ]

    specify "when test tetrominoes are added" $ do
      checkGrid testBoard [
          "     ",
          "    X",
          " X XX",
          " XXXX"
        ]

    it "allows moving a tetromino inside its bounding box" $ do
      let t1 = controlTetromino tetrominoTypeO emptyBoard "DADDDSAADSDAD"
      (snd t1) `shouldBe` True

    it "prevents moving a tetromino outsode its bounding box" $ do
      (snd $ controlTetromino tetrominoTypeO emptyBoard "A") `shouldBe` False
      (snd $ controlTetromino tetrominoTypeO emptyBoard "DDDD") `shouldBe` False
      (snd $ controlTetromino tetrominoTypeO emptyBoard "SSS") `shouldBe` False

    it "prevents invalid (overlapping) tetromino movements" $ do
      (snd $ controlTetromino tetrominoTypeO testBoard "S") `shouldBe` False
      (snd $ controlTetromino tetrominoTypeO testBoard "DDD") `shouldBe` False

    it "drops out one full row of its grid" $ do
      let b1 = removeFullRows testBoard 0 4
      checkGrid b1 [
          "     ",
          "    X",
          " X XX",
          " XXXX"
        ]
      let b2 = addTetromino b1 tetrominoTypeI
      checkGrid b2 [
          "X    ",
          "X   X",
          "XX XX",
          "XXXXX"
        ]
      let b3 = removeFullRows b2 0 4
      checkGrid b3 [
          "     ",
          "X    ",
          "X   X",
          "XX XX"
        ]

    it "drops out multiple full rows of its grid" $ do
      let t1 = fst $ controlTetromino tetrominoTypeT testBoard "DS"
      let b1 = addTetromino testBoard t1
      let b2 = addTetromino b1 tetrominoTypeI
      let b3 = removeFullRows b2 0 4
      checkGrid b3 [
          "     ",
          "     ",
          "     ",
          "X    "
        ]

checkPart :: [Point] -> Tetromino -> Board -> Bool
checkPart [] _ _ = True
checkPart (part:tail) tetromino board =
  let cellPos = (position tetromino) `plusPoint` part
      cell = ((grid board) !! (y cellPos)) !! (x cellPos)
  in if cell then error "???" else not cell && checkPart tail tetromino board