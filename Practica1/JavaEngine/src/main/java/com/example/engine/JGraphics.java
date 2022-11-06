package com.example.engine;

import com.example.interfaces.IFont;
import com.example.interfaces.IGraphics;
import com.example.interfaces.IImage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;

public class JGraphics implements IGraphics {
    private Graphics2D canvas;
    private JFrame myView;
    private BufferStrategy buffer;
    private String path = "DesktopGame/assets/";
    private AffineTransform saveTransform;

    // La altura ideal en px del canvas (el ancho deberá ser 2/3 de la altura para mantener la relación 2:3 que espera la lógica)
    final int PREFFERED_CANVAS_HEIGHT = 1080;
    //private Thread renderThread;

    public JGraphics(JFrame window) {
        //Inicializacion de myView
        myView = window;
        //Inicializacion de buffer
        myView.createBufferStrategy(2);
        buffer = myView.getBufferStrategy();
        //Inicializacion de canvas
        canvas = (Graphics2D) buffer.getDrawGraphics();    //se supone que sobra porque lo cogemos en cada prepareFrame
    }

    @Override
    public IImage newImage(String name) {
        try {
            JImage jImage = new JImage(path + name);
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
    public void clear(int color) {  //int para que me funcione tanto en desktop como en android

    }

    @Override
    public void translate(float x, float y) {
        this.canvas.translate(x, y);
    }

    @Override
    public void scale(float x, float y) {
        this.canvas.scale(x, y);
    }

    @Override
    public void save() {
        saveTransform = this.canvas.getTransform();
    }

    @Override
    public void restore() {
        this.canvas.setTransform(saveTransform);
    }

    @Override
    public void setColor(int color) {
        this.canvas.setColor(new Color(color));
    }

    @Override
    public void drawImage(IImage image, int x, int y, int width, int height) {
        Image scaled = null;
        try {
            scaled = resizeImage((BufferedImage) ((JImage) image).getImage(), width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.canvas.drawImage(scaled, x + myView.getInsets().left, y + myView.getInsets().top, null);
    }

    @Override
    public void fillSquare(int cx, int cy, int side) {
        this.canvas.fillRect(cx, cy, side, side);
    }

    @Override
    public void drawSquare(int cx, int cy, int side) {
        this.canvas.drawRect(cx, cy, side, side);
    }

    @Override
    public void drawLine(int initX, int initY, int endX, int endY) {
        this.canvas.drawLine(initX, initY, endX, endY);
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
        return myView.getWidth();
    }

    @Override
    public int getHeight() {
        return myView.getHeight();
    }

    @Override
    public void prepareFrame() {
        this.canvas = (Graphics2D) this.buffer.getDrawGraphics();
        save();
        int idealCanvasWidth = getWidth() * 2 / 3;
        int xTranslation = (getWidth() - idealCanvasWidth) / 2;
        if(xTranslation > 0)
            translate(xTranslation, 0);

        float scaleRate = (float) getHeight() / PREFFERED_CANVAS_HEIGHT;
        scale(scaleRate, scaleRate);
        //this.clear(0xffffff);
    }

    @Override
    public void finishFrame() {
        this.canvas.dispose();
    }

    boolean cambioBuffer() {
        if (this.buffer.contentsRestored()) {
            return false;
        }
        this.buffer.show();

        return !this.buffer.contentsLost();

    }

    //Devuelve window
    public JFrame getMyView() {
        return myView;
    };

    BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }
}
