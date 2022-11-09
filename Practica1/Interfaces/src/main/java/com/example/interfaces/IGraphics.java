package com.example.interfaces;

public interface IGraphics {

    int getCanvasY();

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

    void setFont(IFont font);

    void setResolution();

    float getOriginalWidth();

    float getOriginalHeight();

    int getWidth();

    int getHeight();

    float getScale();

    void prepareFrame();

    void finishFrame();

    float getFontWidth(String text);

    float relationAspectDimension();

    int getCanvasX();
}
