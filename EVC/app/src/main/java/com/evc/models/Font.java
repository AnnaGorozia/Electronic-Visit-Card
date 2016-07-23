package com.evc.models;

public enum Font {
    ITALIC,
    AERIAL,
    ROMANIAN,
    NOTHING;

    public static Font getFont(int field_font_id) {
        switch (field_font_id) {
            case 1: {
                return Font.AERIAL;
            }

            case 2: {
                return Font.ITALIC;
            }

            case 3: {
                return Font.ROMANIAN;
            }
        }

        return Font.NOTHING;
    }

    public static int getfontid(Font font) {

        switch (font) {
            case AERIAL: return 1;
            case ITALIC: return 2;
            case ROMANIAN: return 3;
        }

        return 0;
    }
}
