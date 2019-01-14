# Tetris game written in Python

## Dependencies
- [appJar](http://appjar.info/) for GUI.
- [events](https://github.com/pyeve/events) for C#-like event handling.

	pip install appjar
    pip install events

## Installing

    pip install -r requirements.txt --target=lib

## Running the game

    python ./game.py
	
Controls:

- W: Rotate tetromino.
- A, S, D: Move left, down, and right the tetromino.
- Space: Drop tetromino.
- Esc: Exit game.

## Running unit tests

	python -m unittest discover -s tests
