package com.example.engine;

import com.example.interfaces.IFont;
import com.example.interfaces.IGraphics;
import com.example.interfaces.IImage;

import java.awt.Graphics2D;

public class JGraphics implements IGraphics {
    private Graphics2D graphics2D;


    @Override
    public IImage newImage(String name) {
        return null;
    }

    @Override
    public IFont newFont(String filename, int size, boolean isBold) {
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

    }

    @Override
    public void fillSquare(int cx, int cy, int side) {

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
