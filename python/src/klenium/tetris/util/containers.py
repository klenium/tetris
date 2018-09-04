from collections import namedtuple


class Point(namedtuple('Point', ['x', 'y'])):
    __slots__ = ()

    def __add__(self, other):
        return Point(self[0] + other[0], self[1] + other[1])

    def __iadd__(self, other):
        return self + other


Dimension = namedtuple('Dimension', ['width', 'height'])
