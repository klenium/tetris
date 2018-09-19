require 'math'
local Board = require 'board'
local Tetromino = require 'tetromino'

math.randomseed(os.time())

local function TetrisGame(size)
	local self = {
		is_running = false,
		falling_tetromino = nil,
		board = Board(size)
	}
	
	local function stop()
        self.is_running = false
	end
    local function generate_next_tetromino()
        shape_type = self.get_next_tetromino_type()
        self.falling_tetromino = Tetromino(shape_type, self.board)
        return self.falling_tetromino.move_to_initial_pos()
	end
    local function tetromino_landed()
        self.board.add_tetromino(self.falling_tetromino)
        y = self.falling_tetromino.position.y
        height = self.falling_tetromino.bounding_box.height
        self.board.remove_full_rows(y, height)
        added = generate_next_tetromino()
        if not added then
            stop()
		end
	end
    local function rotate_tetromino()
        return self.falling_tetromino.rotate_right()
	end
    local function move_left_tetromino()
		print('tetromino move left')
        return self.falling_tetromino.move_left()
	end
    local function move_down_tetromino()
        local moved = self.falling_tetromino.move_down()
        if not moved then
            tetromino_landed()
		end
        return moved
	end
    local function move_right_tetromino()
        return self.falling_tetromino.move_right()
	end
	
    function self.start()
        self.is_running = generate_next_tetromino()
	end
    function self.handle_command(command)
        if not self.is_running then
            return
		end
		local actions = {
			    ROTATE = rotate_tetromino,
			 MOVE_LEFT = move_left_tetromino,
			MOVE_RIGHT = move_right_tetromino,
			 MOVE_DOWN = move_down_tetromino,
			      FALL = move_down_tetromino,
			      DROP = function()
						       while move_down_tetromino() do end
						   end
		}
		local action = actions[command]
		if action ~= nil then
			action()
		end
	end
    function self.get_next_tetromino_type()
        return math.random(1, 7)
	end

	return self
end

return TetrisGame