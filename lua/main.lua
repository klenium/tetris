package.path = 'C:/Develop/projects/tetris/lua/src/?.lua;' .. package.path
--package.path = './src/?.lua;' .. package.path
local Dimension = require 'util'.dimension
local TetrisGame = require 'tetrisgame'

local function Color(r, g, b)
	return {r = r / 255.0, g = g / 255.0, b = b / 255.0}
end



local config = {
	grid_size = Dimension(11, 16),
	square_size = 30,
	falling_speed = 0.7,
	controls = {
			w = 'ROTATE',
			a = 'MOVE_LEFT',
			s = 'MOVE_DOWN',
			d = 'MOVE_RIGHT',
		space = 'DROP'
	},
	background_color = Color(23, 23, 23),
	tetromino_colors = {
		Color(0, 200, 100),
        Color(70, 130, 0),
        Color(160, 220, 0),
        Color(250, 230, 60),
        Color(250, 180, 50),
        Color(230, 100, 0),
        Color(200, 0, 0)
	}
}



local game = TetrisGame(config.grid_size)
local falling_timer = config.falling_speed
local game_width = config.square_size * config.grid_size.width
local game_height = config.square_size * config.grid_size.height

local function draw_square(color, x, y, width, height)
	width = width or config.square_size
	height = height or width
	local top = top or y * height
	local left = left or x * width
	local s = config.square_size
	love.graphics.setColor(color.r, color.g, color.b)
	love.graphics.rectangle('fill', left, top, width, height)
end


function love.load()
	game.start()
end
function love.keypressed(key)
	local command = config.controls[key]
	if command ~= nil then
		game.handle_command(command)
		if command == 'MOVE_DOWN' or command == 'DROP' then
			falling_timer = config.falling_speed
		end
	end
end
function love.update(dt)
	if game.is_running then
		falling_timer = falling_timer - dt
		if falling_timer <= 0 then
			game.handle_command('FALL')
			falling_timer = config.falling_speed
		end
	end
end
function love.draw()
	draw_square(config.background_color, 0, 0, game_width, game_height)
	if game.is_running then
		local board = game.board
		for x = 0, board.size.width - 1 do
			for y = 0, board.size.height - 1 do
				local cell = board.grid[x][y]
				if not (type(cell) == 'boolean' and not cell) then
					local color = config.tetromino_colors[cell]
					draw_square(color, x, y)
				end
			end
		end
		
		local tetromino = game.falling_tetromino
		for _, part_offset in pairs(tetromino.parts) do
			local p = tetromino.position + part_offset
			local color = config.tetromino_colors[tetromino.shape_type]
			draw_square(color, p.x, p.y)
		end
	else
		local font = love.graphics.newFont(24)
		local message = 'Game over!'
		local msg_width = font:getWidth(message)
		local msg_height = font:getHeight(message)
		love.graphics.setFont(font)
		love.graphics.setColor(1, 1, 1)
		love.graphics.print(message, (game_width - msg_width) / 2, (game_height - msg_height) / 2)
	end
end