from collections import namedtuple


class Point(namedtuple('Point', ['x', 'y'])):
    __slots__ = ()  # For performance, see https://stackoverflow.com/q/472000/2625561

    def __add__(self, other):
        return Point(self[0] + other[0], self[1] + other[1])

    def __iadd__(self, other):
        return self + other

    def __le__(self, other):
        # Default tuple comparison uses 'or'.
        return self[0] <= other[0] and self[1] <= other[1]


Dimension = namedtuple('Dimension', ['width', 'height'])
