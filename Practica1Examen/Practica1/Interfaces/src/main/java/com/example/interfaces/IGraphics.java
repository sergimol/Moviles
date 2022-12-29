package com.example.interfaces;

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

    void drawRect(float cx, float cy, float sideX, float sideY, float strokeSize);

    void drawCircle(float x, float y, float r);

    void drawLine(float initX, float initY, float endX, float endY);

    void drawText(String text, float x, float y);

    void setFont(IFont font, float size);

    float getOriginalWidth();

    float getOriginalHeight();

    int getWidth();

    int getHeight();

    int getCanvasY();

    int getCanvasX();

    float relationAspectDimension();

    float getScale();

    void actualizaEscala();

    float getFontWidth(String text);

}
