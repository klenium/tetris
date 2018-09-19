require 'math'
local Point = require 'util'.point
local factory = require 'datafactory'

local function Tetromino(shape_type, board)
	local self = {
		board = board,
		shape_type = shape_type,
		position = Point(0, 0)
	}
	local _data = factory(shape_type)
	local _rotation = 0
	local property_getters = {
		rotation = function()
			return _rotation
		end,
		parts = function()
			return _data[_rotation + 1].parts
		end,
		bounding_box = function()
			return _data[_rotation + 1].bounding_box
		end
	}
	local property_setters = {
		rotation = function(value)
			_rotation = value % #_data
		end
	}
	
	local function can_push(delta)
        return self.board.can_add_tetromino(self, self.position + delta);
	end
	local function try_push(delta)
        local can_slide = can_push(delta)
        if can_slide then
            self.position = self.position + delta
		end
        return can_slide
	end

	function self.move_to_initial_pos()
        board_width = self.board.size.width
        tetromino_width = self.bounding_box.width
        centered_tetromino_pos = math.ceil((board_width - tetromino_width) / 2.0)
        return try_push(Point(centered_tetromino_pos, 0))
	end
	function self.rotate_right()
        local old_rotation = self.rotation
        self.rotation = old_rotation + 1
        if can_push(Point(0, 0)) then
			return true
		end
		for i = 1, self.bounding_box.width do
			if try_push(Point(-i, 0)) then
				return true
			end
		end
		self.rotation = old_rotation
        return false
	end
	function self.move_left()
        return try_push(Point(-1, 0))
	end
	function self.move_down()
        return try_push(Point(0, 1))
	end
	function self.move_right()
        return try_push(Point(1, 0))
	end
	
	setmetatable(self, {
		__index = function(obj, key)
			local getter = property_getters[key]
			if getter ~= nil then
				return getter()
			else
				error('Field ' .. key .. ' does not exists.')
			end
		end,
		__newindex = function(obj, key, value)
			local setter = property_setters[key]
			if setter ~= nil then
				setter(value)
			else
				error('Field ' .. key .. ' does not exists.')
			end
		end
	})
	
	return self
end

return Tetromino