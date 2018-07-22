package hu.klenium.tetris;

import hu.klenium.tetris.view.SquareView;

/**
 *
 */
public class TetrominoDataFactory {
    /**
     * Creates every data of a tetromino, including the cells and views.
     * @param type The tetromino's type.
     * @return The tetromino's data in each rotation.
     */
    public static TetrominoData[] getData(int type) {
        String[][] masks = rawData[type];
        TetrominoData[] result = new TetrominoData[masks.length];
        for (int rotation = 0; rotation < masks.length; ++rotation) {
            String[] mask = masks[rotation];
            TetrominoPart[] parts = new TetrominoPart[getPartsCount(mask)];
            int height = mask.length;
            int width = mask[0].length();
            int k = 0;
            for (int i = 0; i < height; ++i) {
                for (int j = 0; j < width; ++j) {
                    if (mask[i].charAt(j) != ' ')
                        parts[k++] = new TetrominoPart(new SquareView(type), j, i);
                }
            }
            result[rotation] = new TetrominoData(parts, width, height);
        }
        return result;
    }

    /**
     *
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
     * Source of the tromino data.
     * - The first index is the thetromino type.
     * - The second index is the rotation. Some tetromino's data is the same in different
     *   rotations. These duplicated mask are not included, because rotating is cyclic,
     *   so it is easy to work without them.
     * - The third index and the character position specify a 2D matrix which stores the
     *   mask. X means there is a part of that tetromino data, space means there isn't.
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