local containers = require 'util'
local Point = containers.point
local Dimension = containers.dimension

local raw_data = {
    {
        {
            'XX',
            'XX'
        }
    },
    {
        {
            'X',
            'X',
            'X',
            'X'
        },
        {
            'XXXX'
        }
    },
    {
        {
            'X ',
            'X ',
            'XX'
        },
        {
            '  X',
            'XXX'
        },
        {
            'XX',
            ' X',
            ' X'
        },
        {
            'XXX',
            'X  '
        }
    },
    {
        {
            ' X',
            ' X',
            'XX'
        },
        {
            'XXX',
            '  X'
        },
        {
            'XX',
            'X ',
            'X '
        },
        {
            'X  ',
            'XXX'
        }
    },
    {
        {
            ' XX',
            'XX '
        },
        {
            'X ',
            'XX',
            ' X'
        }
    },
    {
        {
            'XX ',
            ' XX'
        },
        {
            ' X',
            'XX',
            'X '
        }
    },
    {
        {
            'XXX',
            ' X '
        },
        {
            'X ',
            'XX',
            'X '
        },
        {
            ' X ',
            'XXX'
        },
        {
            ' X',
            'XX',
            ' X'
        }
    }
}

local function get_tetromino_data(shape_type)
    local result = {}
	local masks = raw_data[shape_type]
    for rotation = 1, #masks do
        local mask = masks[rotation]
        local parts = {}
        local height = #mask
        local width = #(mask[1])
        for y = 0, height - 1 do
			for x = 0, width - 1 do
                if string.sub(mask[y + 1], x + 1, x + 1) ~= ' ' then
                    table.insert(parts, Point(x, y))
				end
			end
		end
        table.insert(result, {
			parts = parts,
			bounding_box = Dimension(width, height)
		})
	end
    return result
end

return get_tetromino_data