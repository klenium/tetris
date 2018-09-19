local function Point(x, y)
	local p = {x = x, y = y}
	setmetatable(p, {
		__add = function(left, right)
			return Point(left.x + right.x, left.y + right.y)
		end
	})
	return p
end

local function Dimension(width, height)
	return {width = width, height = height}
end

return {
	point = Point,
	dimension = Dimension
}