package hu.klenium.tetris.logic.tetromino;

import hu.klenium.tetris.util.Dimension;
import hu.klenium.tetris.util.Point;

/**
 * Constructs data for tetrominoes.
 */
public class TetrominoDataFactory {
    /**
     * This is an utility class, should not be instantiated.
     */
    private TetrominoDataFactory() {}
    /**
     * Creates data of a tetromino (in every rotations).
     * @param type The tetromino's type.
     * @return The tetromino's data in each rotation.
     */
    public static TetrominoData[] getData(int type) {
        String[][] masks = rawData[type];
        TetrominoData[] result = new TetrominoData[masks.length];
        for (int rotation = 0; rotation < masks.length; ++rotation) {
            String[] mask = masks[rotation];
            Point[] parts = new Point[getPartsCount(mask)];
            int height = mask.length;
            int width = mask[0].length();
            int partsCount = 0;
            for (int y = 0; y < height; ++y) {
                for (int x = 0; x < width; ++x) {
                    if (mask[y].charAt(x) != ' ')
                        parts[partsCount++] = new Point(x, y);
                }
            }
            result[rotation] = new TetrominoData(parts, new Dimension(width, height));
        }
        return result;
    }

    /**
     * Used to count the cells of a tetromino data before the parts
     * are constructed. This is needed for fixed-length arrays.
     * @param data A raw tetromino data to check.
     * @return The count of used cells in the data.
     */
    private static int getPartsCount(String[] data) {
        int result = 0;
        for (String line : data)
            result += line.chars().filter(ch -> ch != ' ').count();
        return result;
    }

    /**
     * Source of the tetromino data.
     * <ul>
     *     <li>
     *         The first index is the thetromino type. There're 7 types in
     *         traditional Tetris.
     *     </li>
     *     <li>
     *         The second index is the rotation. Some tetromino's data
     *         are the same in different rotations. These duplicated mask
     *         are not included, because rotating is cyclic, so it is
     *         easy to work without them.
     *     </li>
     *     <li>
     *         The third index and the character position of the string
     *         specify a 2D matrix which stores the mask. X means there
     *         is a part of that tetromino data, space means there isn't.
     *     </li>
     * </ul>
     */
    private final static String[][][] rawData = new String[][][] {
        new String[][] {
            new String[] {
                "XX",
                "XX"
            }
        },
        new String[][] {
            new String[] {
                "X",
                "X",
                "X",
                "X"
            },
            new String[] {
                "XXXX"
            }
        },
        new String[][] {
            new String[] {
                "X ",
                "X ",
                "XX"
            },
            new String[] {
                "  X",
                "XXX"
            },
            new String[] {
                "XX",
                " X",
                " X"
            },
            new String[] {
                "XXX",
                "X  "
            }
        },
        new String[][] {
            new String[] {
                " X",
                " X",
                "XX"
            },
            new String[] {
                "XXX",
                "  X"
            },
            new String[] {
                "XX",
                "X ",
                "X "
            },
            new String[] {
                "X  ",
                "XXX"
            }
        },
        new String[][] {
            new String[] {
                " XX",
                "XX "
            },
            new String[] {
                "X ",
                "XX",
                " X"
            }
        },
        new String[][] {
            new String[] {
                "XX ",
                " XX"
            },
            new String[] {
                " X",
                "XX",
                "X "
            }
        },
        new String[][] {
            new String[] {
                "XXX",
                " X "
            },
            new String[] {
                "X ",
                "XX",
                "X "
            },
            new String[] {
                " X ",
                "XXX"
            },
            new String[] {
                " X",
                "XX",
                " X"
            }
        }
    };
}