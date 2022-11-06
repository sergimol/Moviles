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

    void drawImage(IImage image, float x, float y, float width, float height);

    void fillRect(float cx, float cy, float sideX, float sideY);

    void drawRect(float cx, float cy, float sideX, float sideY);

    void drawCircle(float x, float y, float r);

    void drawLine(float initX, float initY, float endX, float endY);

    void drawText(String text, float x, float y);

    void setFont();

    void setResolution();

    int getWidth();

    int getHeight();

    void prepareFrame();

    void finishFrame();


}
