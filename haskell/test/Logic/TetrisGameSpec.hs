module Logic.TetrisGameSpec where

import Test.Hspec

spec :: Spec
spec = parallel $ do
  describe "Check type-specific base grid" $ do
    specify "Type O" $
      pending