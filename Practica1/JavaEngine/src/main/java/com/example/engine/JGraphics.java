package com.example.engine;

import com.example.interfaces.IFont;
import com.example.interfaces.IGraphics;
import com.example.interfaces.IImage;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.swing.JFrame;


public class JGraphics implements IGraphics {
    private Graphics2D canvas;
    private JFrame myView;
    private BufferStrategy buffer;

    //private Thread renderThread;

    public JGraphics(JFrame renderView) {
        //Inicializacion de myView
        this.myView.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                //Component c = (Component)evt.getSource();
                System.out.println("componentResized: " + evt.getSource());
                canvas.dispose();

                buffer.show();
                canvas = (Graphics2D) buffer.getDrawGraphics();
            }
        });

        //Inicializacion de buffer y canvas
        this.buffer = this.myView.getBufferStrategy();
        this.canvas = (Graphics2D) buffer.getDrawGraphics();
    }


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
    public void clear(int color) {  //int para que me funcione tanto en desktop como en android

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
        this.canvas.drawImage((Image) image, x, y, null);
    }

    @Override
    public void fillSquare(int cx, int cy, int side) {
        this.canvas.fillRect(cx, cy, this.getWidth(), this.getHeight());
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

    @Override
    public void prepareFrame() {
        this.canvas = (Graphics2D) this.buffer.getDrawGraphics();
        //this.canvas.scale();      //Si el canvas cambia de tamaño queremos escalarlo y trasladarlo
        //this.canvas.translate();
        this.clear(0xffffff);
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
}
