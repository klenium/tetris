import Control.Concurrent.Async.Timer
import Control.Monad
import Data.Time.Clock.POSIX

currentTime :: IO Integer
currentTime = round `fmap` getPOSIXTime

main = do
      let conf = (setInitDelay 3000 (setInterval 1500 defaultConf))
    
      withAsyncTimer conf $ \ timer -> do
        forever $ do
          wait timer
          time <- currentTime
          putStrLn (show time)