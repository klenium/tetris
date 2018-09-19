package.path = './src/?.lua;' .. package.path
local lu = require 'luaunit'
local Board = require 'board'
local Tetromino = require 'tetromino'
local TetrisGame = require 'tetrisgame'
local Point = require 'util'.point
local Dimension = require 'util'.dimension

function control_tetromino(tetromino, commands)
	local actions = {
		W = tetromino.rotate_right,
		A = tetromino.move_left,
		S = tetromino.move_down,
		D = tetromino.move_right
	}
	for i = 1, #commands do
		local ch = string.sub(commands, i, i)
		local action = actions[ch]
		if action ~= nil then
			if not action() then
				return false
			end
		else
			error('Unknown command: ' .. ch)
		end
	end
	return true
end
function control_game(game, commands)
	local actions = {
		W = 'ROTATE',
		A = 'MOVE_LEFT',
		S = 'MOVE_DOWN',
		D = 'MOVE_RIGHT'
	}
	actions[' '] = 'DROP'
	for i = 1, #commands do
		local ch = string.sub(commands, i, i)
		local command = actions[ch]
		if command ~= nil then
			game.handle_command(command)
		else
			error('Unknown command: ' .. ch)
		end
	end
end
function check_board_state(board, excepted)
    check_object_state(
        board.grid,
        board.size,
        excepted,
        function(d, x, y) return d[x][y] end,
        function(c) return not c end
    )
end
function check_tetromino_state(tetromino, excepted)
    check_object_state(
        tetromino.parts,
        tetromino.bounding_box,
        excepted,
        function(data, x, y)
			for _, point in pairs(data) do
				if point.x == x and point.y == y then
					return point
				end
			end
			return nil
		end,
        function(c) return c == nil end
    )
end
function check_object_state(data, size, excepted, find_cell, check_cell_empty)
    lu.assert_equals(size.width, #(excepted[1]))
    lu.assert_equals(size.height, #excepted)
    grid_equals_to_expected = true
    horizontal_border = '+' .. string.rep('-', size.width) .. '+\n'
    console_view = '\n' .. horizontal_border
    for y = 0, size.height - 1 do
        console_view = console_view .. '|'
        for x = 0, size.width - 1 do
            cell = find_cell(data, x, y)
            ch = string.sub(excepted[y + 1], x + 1, x + 1)
            cell_empty = check_cell_empty(cell)
            cell_equals = cell_empty == (ch == '.')
			if not cell_equals then
				grid_equals_to_expected = false
			end
            if cell_equals then
                console_view = console_view .. (cell_empty and '.' or '#')
            else
                console_view = console_view .. (cell_empty and '!' or '?')
			end
		end
        console_view = console_view .. '|\n'
	end
    console_view = console_view .. horizontal_border
    if not grid_equals_to_expected then
        print(console_view)
	end
	lu.assert_true(grid_equals_to_expected)
end
function add_test_data(board)
	--   .....
	--   ....#
	--   .#.##
	--   .####
	local tetromino = Tetromino(4, board)
	control_tetromino(tetromino, 'WWWDSS')
	board.add_tetromino(tetromino)
	tetromino = Tetromino(7, board)
	control_tetromino(tetromino, 'WWDDWDS')
	board.add_tetromino(tetromino)
end


TestBoard = {}
	function TestBoard:setUp()
		self.board = Board(Dimension(5, 4))
	end
    
	function TestBoard:test_new_board_is_empty()
        check_board_state(self.board, {
            '.....',
            '.....',
            '.....',
            '.....'
        })
    end
    function TestBoard:test_add_tetrominoes()
        add_test_data(self.board)
        check_board_state(self.board, {
            '.....',
            '....#',
            '.#.##',
            '.####'
        })
    end
    function TestBoard:test_moving_tetromino_inside_empty_board()
        local tetromino = Tetromino(1, self.board)
        lu.assert_true(control_tetromino(tetromino, 'DADDDSAADSDAD'))
    end
    function TestBoard:test_moving_tetromino_outside_board_box()
        local tetromino = Tetromino(1, self.board)
        lu.assert_false(control_tetromino(tetromino, 'A'))
        lu.assert_false(control_tetromino(tetromino, 'DDDD'))
        lu.assert_false(control_tetromino(tetromino, 'SSS'))
    end
    function TestBoard:test_invalid_tetromino_move_inside_used_board()
        add_test_data(self.board)
        local tetromino = Tetromino(1, self.board)
        lu.assert_false(control_tetromino(tetromino, 'S'))
        lu.assert_false(control_tetromino(tetromino, 'DDD'))
    end
    function TestBoard:test_remove_one_full_row()
        add_test_data(self.board)
        self.board.remove_full_rows(0, 4)
        check_board_state(self.board, {
            '.....',
            '....#',
            '.#.##',
            '.####'
        })
        local tetromino = Tetromino(2, self.board)
        self.board.add_tetromino(tetromino)
        check_board_state(self.board, {
            '#....',
            '#...#',
            '##.##',
            '#####'
        })
        self.board.remove_full_rows(0, 4)
        check_board_state(self.board, {
            '.....',
            '#....',
            '#...#',
            '##.##'
        })
    end
    function TestBoard:test_remove_multiple_full_rows()
        local tetromino
        add_test_data(self.board)
        self.board.remove_full_rows(0, 4)
        tetromino = Tetromino(7, self.board)
        control_tetromino(tetromino, 'DS')
        self.board.add_tetromino(tetromino)
        tetromino = Tetromino(2, self.board)
        self.board.add_tetromino(tetromino)
        self.board.remove_full_rows(0, 4)
        check_board_state(self.board, {
            '.....',
            '.....',
            '.....',
            '#....'
        })
    end



TestTetromino = {}
    function TestTetromino:setUp()
        self.board = Board(Dimension(7, 8))
		self.tetromino = nil
    end
    
	function TestTetromino:test_tetromino_type_O_grid()
        self.tetromino = Tetromino(1, self.board)
        check_tetromino_state(self.tetromino, {
            '##',
            '##'
        })
    end
    function TestTetromino:test_tetromino_type_I_grid()
        self.tetromino = Tetromino(2, self.board)
        check_tetromino_state(self.tetromino, {
            '#',
            '#',
            '#',
            '#'
        })
    end
    function TestTetromino:test_tetromino_type_L_grid()
        self.tetromino = Tetromino(3, self.board)
        check_tetromino_state(self.tetromino, {
            '#.',
            '#.',
            '##'
        })
    end
    function TestTetromino:test_tetromino_type_J_grid()
        self.tetromino = Tetromino(4, self.board)
        check_tetromino_state(self.tetromino, {
            '.#',
            '.#',
            '##'
        })
    end
    function TestTetromino:test_tetromino_type_S_grid()
        self.tetromino = Tetromino(5, self.board)
        check_tetromino_state(self.tetromino, {
                '.##',
                '##.'
        })
    end
    function TestTetromino:test_tetromino_type_Z_grid()
        self.tetromino = Tetromino(6, self.board)
        check_tetromino_state(self.tetromino, {
            '##.',
            '.##'
        })
    end
    function TestTetromino:test_tetromino_type_T_grid()
        self.tetromino = Tetromino(7, self.board)
        check_tetromino_state(self.tetromino, {
            '###',
            '.#.'
        })
    end
    function TestTetromino:test_move_to_initial_position()
        self.tetromino = Tetromino(7, self.board)
        lu.assert_equals(self.tetromino.position, Point(0, 0))
        self.tetromino.move_to_initial_pos()
        lu.assert_equals(self.tetromino.position, Point(2, 0))
    end
    function TestTetromino:test_move_to_initial_position_rounding()
        self.tetromino = Tetromino(2, self.board)
        self.tetromino.rotate_right()
        self.tetromino.move_to_initial_pos()
        lu.assert_equals(self.tetromino.position, Point(2, 0))
    end
    function TestTetromino:test_rotate_type_T()
        self.tetromino = Tetromino(7, self.board)
        check_tetromino_state(self.tetromino, {
            '###',
            '.#.'
        })
        self.tetromino.rotate_right()
        check_tetromino_state(self.tetromino, {
            '#.',
            '##',
            '#.'
        })
        self.tetromino.rotate_right()
        check_tetromino_state(self.tetromino, {
            '.#.',
            '###'
        })
        self.tetromino.rotate_right()
        check_tetromino_state(self.tetromino, {
            '.#',
            '##',
            '.#'
        })
        self.tetromino.rotate_right()
        check_tetromino_state(self.tetromino, {
            '###',
            '.#.'
        })
    end
    function TestTetromino:test_rotate_type_I()
        self.tetromino = Tetromino(2, self.board)
        check_tetromino_state(self.tetromino, {
            '#',
            '#',
            '#',
            '#'
        })
        self.tetromino.rotate_right()
        check_tetromino_state(self.tetromino, {
            '####'
        })
        self.tetromino.rotate_right()
        check_tetromino_state(self.tetromino, {
            '#',
            '#',
            '#',
            '#'
        })
    end
    function TestTetromino:test_cant_rotate()
        self.tetromino = Tetromino(2, self.board)
        control_tetromino(self.tetromino, 'DDSSSS')
        self.board.add_tetromino(self.tetromino)
        self.tetromino = Tetromino(7, self.board)
        control_tetromino(self.tetromino, 'WSSS')
        lu.assert_false(control_tetromino(self.tetromino, 'W'))
    end
    function TestTetromino:test_move_left_when_rotating_at_right_side()
        self.tetromino = Tetromino(2, self.board)
        control_tetromino(self.tetromino, 'DDDDD')
        lu.assert_equals(self.tetromino.position, Point(5, 0))
        control_tetromino(self.tetromino, 'W')
        lu.assert_equals(self.tetromino.position, Point(3, 0))
    end
    function TestTetromino:test_dont_move_left_when_rotating_at_right_side_isnt_possible()
        self.tetromino = Tetromino(2, self.board)
        control_tetromino(self.tetromino, 'DDDSSSS')
        self.board.add_tetromino(self.tetromino)
        self.tetromino = Tetromino(2, self.board)
        control_tetromino(self.tetromino, 'DDDDDDSSSS')
        lu.assert_false(control_tetromino(self.tetromino, 'W'))
    end
    function TestTetromino:test_move_left()
        self.tetromino = Tetromino(7, self.board)
        self.tetromino.move_to_initial_pos()
        control_tetromino(self.tetromino, 'A')
        lu.assert_equals(self.tetromino.position, Point(1, 0))
    end
    function TestTetromino:test_move_down()
        self.tetromino = Tetromino(7, self.board)
        self.tetromino.move_to_initial_pos()
        control_tetromino(self.tetromino, 'S')
        lu.assert_equals(self.tetromino.position, Point(2, 1))
    end
    function TestTetromino:test_move_right()
        self.tetromino = Tetromino(7, self.board)
        self.tetromino.move_to_initial_pos()
        control_tetromino(self.tetromino, 'D')
        lu.assert_equals(self.tetromino.position, Point(3, 0))
    end

	

TestTetris = {}
    function TestTetris:setUp()
		--             J, T, I, O
		local types = {4, 7, 2, 1}
		self.game = TetrisGame(Dimension(5, 6))
		self.game.get_next_tetromino_type = function()
			return table.remove(types, 1)
		end
    end
    
	function TestTetris:test_falling()
		-- Skipped in this version.
    end
    function TestTetris:test_board_size()
        lu.assert_equals(self.game.board.size.width, 5)
        lu.assert_equals(self.game.board.size.height, 6)
    end
    function TestTetris:test_unstarted_state()
        lu.assert_false(self.game.is_running)
        lu.assert_nil(self.game.falling_tetromino)
		control_game(self.game, 'S')
        lu.assert_nil(self.game.falling_tetromino)
    end
    function TestTetris:test_running_state()
        self.game.start()
        lu.assert_true(self.game.is_running)
        lu.assert_not_nil(self.game.falling_tetromino)
    end
    function TestTetris:test_stopped_state()
        self.game.start()
        control_game(self.game, ' ')
        control_game(self.game, ' ')
        local tetromino1 = self.game.falling_tetromino
        lu.assert_false(self.game.is_running)
        control_game(self.game, ' ')
        local tetromino2 = self.game.falling_tetromino
        lu.assert_is(tetromino1, tetromino2)
        lu.assert_equals(tetromino1.position, tetromino2.position)
    end
    function TestTetris:test_controlling_tetromino()
        self.game.start()
		local tetromino = self.game.falling_tetromino
        local position1 = tetromino.position
		control_game(self.game, 'A')
        local position2 = tetromino.position
        lu.assert_not_equals(position1, position2)
    end
    function TestTetris:test_tetromino_landing()
        self.game.start()
        local tetromino1 = self.game.falling_tetromino
		control_game(self.game, ' ')
        local tetromino2 = self.game.falling_tetromino
        lu.assert_not_is(tetromino1, tetromino2)
        lu.assert_equals(tetromino2.shape_type, 7)
        check_board_state(self.game.board, {
            '.....',
            '.....',
            '.....',
            '...#.',
            '...#.',
            '..##.'
        })
    end
    function TestTetris:test_remove_full_rows()
        self.game.start()
        control_game(self.game, 'DD ')
        control_game(self.game, 'WWA ')
        check_board_state(self.game.board, {
            '.....',
            '.....',
            '.....',
            '.....',
            '....#',
            '.#..#'
        })
    end
    function TestTetris:test_initial_position()
        self.game.start()
        local tetromino = self.game.falling_tetromino
        lu.assert_equals(tetromino.position, Point(2, 0))
        control_game(self.game, 'D ')
        tetromino = self.game.falling_tetromino
        lu.assert_equals(tetromino.position, Point(1, 0))
        control_game(self.game, ' ')
        tetromino = self.game.falling_tetromino
        lu.assert_equals(tetromino.position, Point(2, 0))
    end



lu.LuaUnit.run('-v', '-o', 'tap')
os.execute 'pause'