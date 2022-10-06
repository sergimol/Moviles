package com.example.interfaces;

import java.io.IOException;

public interface IGraphics {
    IImage newImage(String name);

    IFont newFont(String filename, int styleFlags, int size);

    void clear(int color);

    void translate(float x, float y);

    void scale(float x, float y);

    void save();

    void restore();

    void setColor(int color);

    void drawImage(IImage image, int x, int y);

    void fillSquare(int cx, int cy, int side);

    void drawSquare(int cx, int cy, int side);

    void drawLine(int initX, int initY, int endX, int endY);

    void drawText(String text, int x, int y);

    void setFont();

    void setResolution();

    int getWidth();

    int getHeight();
}
