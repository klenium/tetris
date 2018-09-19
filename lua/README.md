# Tetris game written in Lua

## Dependencies
- [luaunit](https://luarocks.org/modules/bluebird75/luaunit) for unit testing.
- [LÖVE](https://love2d.org/) for game GUI.

## Installing

1. Diwnload (and install if you want) LÖVE from its homepage.
2. For testing, install LuaUnit via LuaRocks:

        luarocks install luaunit

## Running the game

Follow [this page](https://love2d.org/wiki/Getting_Started) to see how to run the game in an easy way. Example on Windows, if you add `love.exe` to your `PATH` and run the command from the `tetris/lua` folder:

	love .

## Running unit tests

	lua tests.lua

Note: this project doesn't contain tests to check the game's falling feature. That would require a `PeriodicTask` class like in other projects. Lua doesn't support threads and non-blocking sleeps. Implementing a good timer class would need OS-specific calls or another library, but LÖVE can handle this already, so I didn't want another dependency.