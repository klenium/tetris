package hu.klenium.tetris;

import hu.klenium.tetris.view.SquareView;

public class TetrominoDataSource {
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
    private static int getPartsCount(String[] data) {
        int result = 0;
        for (String line : data)
            result += line.chars().filter(ch -> ch != ' ').count();
        return result;
    }

    private static String[][][] rawData = new String[][][] {
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