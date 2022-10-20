package com.example.engine;

import com.example.interfaces.IFont;
import com.example.interfaces.IGraphics;
import com.example.interfaces.IImage;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;


public class JGraphics implements IGraphics {
    private Graphics2D graphics2D;


    @Override
    public IImage newImage(String name) {
        try {
            JImage jImage = new JImage(name);
            return jImage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public IFont newFont(String filename, int styleFlags, int size) {
        JFont jFont = new JFont(filename, styleFlags, size);

        return null;
    }

    @Override
    public void clear(int color) {

    }

    @Override
    public void translate(float x, float y) {

    }

    @Override
    public void scale(float x, float y) {

    }

    @Override
    public void save() {

    }

    @Override
    public void restore() {

    }

    @Override
    public void setColor(int color) {

    }

    @Override
    public void drawImage(IImage image, int x, int y) {
        this.graphics2D.drawImage((Image) image, x, y, null);
    }

    @Override
    public void fillSquare(int cx, int cy, int side) {
        this.graphics2D.fillRect(cx, cy, this.getWidth(), this.getHeight());
    }

    @Override
    public void drawSquare(int cx, int cy, int side) {

    }

    @Override
    public void drawLine(int initX, int initY, int endX, int endY) {

    }

    @Override
    public void drawText(String text, int x, int y) {

    }

    @Override
    public void setFont() {

    }

    @Override
    public void setResolution() {

    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }
}
