local function Board(size)
	local _grid = {}
	for x = 0, size.width - 1 do
		_grid[x] = {}
		for y = 0, size.height - 1 do
			_grid[x][y] = false
		end
	end
	local self = {
		size = size,
		grid = _grid
	}
	
    local function is_box_inside_grid(box_position, box_size)
        return box_position.x >= 0
		   and box_position.y >= 0
		   and (box_position.x + box_size.width) <= self.size.width
		   and (box_position.y + box_size.height) <= self.size.height
	end
    local function is_row_full(index)
        for x = 0, self.size.width - 1 do
            if not self.grid[x][index] then
                return false
			end
		end
        return true
	end
    local function move_down_boards_upper_part(index)
        for y = index, 1, -1 do
            for x  = 0, self.size.width - 1 do
                self.grid[x][y] = self.grid[x][y - 1]
			end
		end
        for x = 0, self.size.width - 1 do
            self.grid[x][0] = false
		end
	end
	
    function self.can_add_tetromino(tetromino, from)
        if not is_box_inside_grid(from, tetromino.bounding_box) then
            return false
		end
        for _, part_offset in pairs(tetromino.parts) do
            local p = from + part_offset
            if self.grid[p.x][p.y] then
                return false
			end
		end
        return true
	end
    function self.add_tetromino(tetromino)
        for _, part_offset in pairs(tetromino.parts) do
            local p = tetromino.position + part_offset
            self.grid[p.x][p.y] = tetromino.shape_type
		end
	end
    function self.remove_full_rows(start_index, height)
        for row  = start_index, start_index + height do
            if is_row_full(row) then
                move_down_boards_upper_part(row)
			end
		end
	end

	return self
end

return Board