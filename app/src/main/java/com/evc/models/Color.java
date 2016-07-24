package com.evc.models;

public enum  Color {
    RED,
    BLUE,
    GREEN,
    WHITE,
    BLACK,
    YELLOW,
    NOTHING;

    public static Color getColor(int field_color_id) {

        switch (field_color_id) {
            case 1: {
                return Color.BLACK;
            }

            case 2: {
                return Color.BLUE;
            }

            case 3: {
                return Color.GREEN;
            }

            case 4: {
                return Color.RED;
            }

            case 5: {
                return Color.WHITE;
            }

            case 6: {
                return Color.YELLOW;
            }
        }

        return Color.NOTHING;
    }

    public static int getColorid(Color color) {
        switch (color) {
            case BLACK: return 1;
            case BLUE: return 2;
            case GREEN: return 3;
            case RED: return 4;
            case WHITE: return 5;
            case YELLOW: return 6;
        }

        return 0;
    }
}
