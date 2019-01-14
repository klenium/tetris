module Logic.Board (
  Board(..),
  newBoard,
  addTetromino,
  canAddTetromino,
  removeFullRows
) where

import Data.List
import Util.Dimension
import Util.Point
import Util.Boxed
import Logic.Tetromino.Type

type Cell = Bool
type Row = [Cell]
type Grid = [Row]
type GridPartition = [Row]
type GridPartitions = [GridPartition]

data Board = Board {
  grid :: Grid
}
  deriving Eq

instance Boxed Board where
  boundingBox board =
    let width = length $ head $ grid board
        height = length $ grid board
    in Dimension width height

instance Show Board where
  show board =
    intercalate "\n" [
      concat [
        if not cell then " " else "X"
        | cell <- row
      ]
      | row <- grid board
    ]

emptyRow :: Int -> Row
emptyRow width = take width $ repeat False

newBoard :: Int -> Int -> Board
newBoard width height =
  Board { grid = take height $ repeat $ emptyRow width }

addTetromino :: Board -> Tetromino -> Board
addTetromino board tetromino =
  let relativePos = position tetromino
      changeFrom = y relativePos
      changeTo = changeFrom + boundingHeight tetromino
  in transformPartitions board changeFrom changeTo (\[top, middle, bottom] ->
       let newMiddle = addPartsToPartition middle (parts tetromino) (x relativePos) 0
       in [top, newMiddle, bottom]
     )

canAddTetromino :: Board -> Tetromino -> Point -> Bool
canAddTetromino board tetromino from =
  if not $ isBoxInsideGrid board from (boundingBox tetromino)
  then False
  else checkPart $ parts tetromino
    where checkPart [] = True
          checkPart (part:tail) =
            let cellPos = from `plusPoint` part
                cell = (grid board) !! (y cellPos) !! (x cellPos)
            in not cell && checkPart tail

removeFullRows :: Board -> Int -> Int -> Board
removeFullRows board startRow amount =
  removeFullRowsReversed board (startRow + amount) amount

removeFullRowsReversed :: Board -> Int -> Int -> Board
removeFullRowsReversed board _ 0 = board
removeFullRowsReversed board endRow numOfRowsToCheck =
  let width = boundingWidth board
      newBoard = transformPartitions board (endRow - 1) endRow (\[top, middle, bottom] ->
          if isRowFull $ head middle
          then [(emptyRow width) : top, [], bottom]
          else [top, middle, bottom]
        )
      nextEndRow = if board == newBoard then (endRow - 1) else endRow
  in removeFullRowsReversed newBoard nextEndRow (numOfRowsToCheck - 1)

isBoxInsideGrid :: Board -> Point -> Dimension -> Bool
isBoxInsideGrid board boxPosition boxSize =
  let boardTopLeft = Point 0 0
      boardBottomRight = toPoint $ boundingBox board
      boxTopLeft = boxPosition
      boxBottomRight = boxPosition `plusDim` boxSize
  in boxTopLeft `notSmallerThan` boardTopLeft
     && boxBottomRight `notGreaterThan` boardBottomRight

isRowFull :: Row -> Bool
isRowFull row = foldl1 (&&) row

replaceRowCellsWith :: Row -> [Int] -> Cell -> Row
replaceRowCellsWith row [] _ = row
replaceRowCellsWith row (cutPoint:tail) newValue =
  let leftPart = take cutPoint row
      rightPart = drop (cutPoint + 1) row
      newRow = leftPart ++ newValue : rightPart
  in replaceRowCellsWith newRow tail newValue

addPartsToPartition :: GridPartition -> [Point] -> Int -> Int -> GridPartition
addPartsToPartition [] _ _ _ = []
addPartsToPartition (row:tail) parts relativeX offsetY =
  let (partsInThisRow, remainingParts) = partition ((== offsetY) . y) parts
      pointsToChange = map ((+ relativeX) . x) partsInThisRow
      newRow = replaceRowCellsWith row pointsToChange True
      otherRows = addPartsToPartition tail remainingParts relativeX (offsetY + 1)
  in newRow : otherRows

transformPartitions :: Board -> Int -> Int -> (GridPartitions -> GridPartitions) -> Board
transformPartitions board firstCut secondCut transform =
  let topPart = take firstCut $ grid board
      middlePart = drop firstCut $ take secondCut $ grid board
      bottomPart = drop secondCut $ grid board
  in Board { grid = concat $ transform $ [topPart, middlePart, bottomPart] }
