package com.evc.models;


public class Field {

    private int xCoordinate, yCoordinate;
    private FieldType fieldType;
    private String fieldValue;
    private Color color;
    private Font font;  // Italic, Aerial...
    private int fontSize;


    public Field(int xCoordinate, int yCoordinate, FieldType fieldType, String fieldValue,
                Color color, Font font, int fontSize) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.fieldType = fieldType;
        this.fieldValue = fieldValue;
        this.color = color;
        this.font = font;
        this.fontSize = fontSize;
    }

    public int getFontSize() {
        return fontSize;
    }

    public Font getFont() {
        return font;
    }

    public Color getColor() {
        return color;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public double getyCoordinate() {
        return yCoordinate;
    }

    public double getxCoordinate() {
        return xCoordinate;
    }
}
