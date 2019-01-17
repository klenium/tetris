import System.IO
import System.IO.NoBufferingWorkaround
 
main = do
  initGetCharNoBuffering
  c <- getCharNoBuffering
  putStr ("1:" ++ (show c) ++ "\n")
  main