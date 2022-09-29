package com.example.interfaces;

public interface Graphics {
    Image newImage(String name);
    Font newFont(String filename, int size, boolean isBold);
    void clear(int color);
    void translate(float x, float y);
    void scale(float x, float y);
    void save();
    void restore();
    void setColor(int color);
    void drawImage(Image image, int x, int y);
    void fillSquare(int cx, int cy, int side);
    void drawSquare(int cx, int cy, int side);
    void drawLine(int initX, int initY, int endX, int endY);
    void drawText(String text, int x, int y);
    int getWidth();
    int getHeight();
}
