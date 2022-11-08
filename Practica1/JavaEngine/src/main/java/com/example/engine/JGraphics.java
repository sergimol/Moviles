package com.example.engine;

import com.example.interfaces.IFont;
import com.example.interfaces.IGraphics;
import com.example.interfaces.IImage;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;

public class JGraphics implements IGraphics {
    private JFrame myView;
    private BufferStrategy buffer;
    private Graphics2D canvas;

    //private String path = "AndroidEngine/src/main/assets/";
    private String path = "DesktopGame/assets/";
    private AffineTransform saveTransform;

    // La altura ideal en px del canvas (el ancho deberá ser 2/3 de la altura para mantener la relación 2:3 que espera la lógica)
    final int PREFFERED_CANVAS_HEIGHT = 1080;
    final int PREFFERED_CANVAS_WIDTH = PREFFERED_CANVAS_HEIGHT * 2 / 3;
    // El ancho ideal del canvas en px para mantener la relación 2:3 y que se irá actualizando respecto de la altura
    private int canvasWidth;
    private int canvasHeight;
    // El valor para escalar objetos respecto al tamaño ideal y que se irá actualizando respecto del tamaño del canvas
    private float scale;
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
        try {
            JFont jFont = new JFont(path + filename, styleFlags, size);
            return jFont;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public void clear(int color) {
        canvas.setColor(new Color(color));
        canvas.fillRect(0, 0, getCanvasWidth(), getCanvasHeight());
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
        canvas.setColor(new Color(color));
    }

    @Override
    public void drawImage(IImage image, float x, float y, float width, float height) {
        Image scaled = null;
        try {
            scaled = resizeImage((BufferedImage) ((JImage) image).getImage(), (int) width, (int) height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.canvas.drawImage(scaled, (int) x + myView.getInsets().left, (int) y + myView.getInsets().top, null);
    }

    @Override
    public void fillRect(float cx, float cy, float sideX, float sideY) {
        this.canvas.fillRect((int) cx, (int) cy, (int) sideX, (int) sideY);
    }

    @Override
    public void drawRect(float cx, float cy, float sideX, float sideY) {
        this.canvas.drawRect((int) cx, (int) cy, (int) sideX, (int) sideY);
    }

    @Override
    public void drawCircle(float x, float y, float r) {

    }

    @Override
    public void drawLine(float initX, float initY, float endX, float endY) {
        this.canvas.drawLine((int) initX, (int) initY, (int) endX, (int) endY);
    }

    @Override
    public void drawText(String text, float x, float y) {
        canvas.drawString(text, x, y);
    }

    @Override
    public void setFont(IFont font) {
        canvas.setFont(((JFont) font).font_);
    }

    @Override
    public void setResolution() {

    }

    @Override
    public int getCanvasWidth() {
        return canvasWidth;
    }

    @Override
    public int getCanvasHeight() {
        return canvasHeight;
    }

    public int getWidth() {
        return myView.getWidth() - myView.getInsets().right - myView.getInsets().left;
    }

    @Override
    public int getHeight() {
        return myView.getHeight() - myView.getInsets().top - myView.getInsets().bottom;
    }

    @Override
    public float getScale() {
        return scale;
    }

    @Override
    public void prepareFrame() {
        this.canvas = (Graphics2D) this.buffer.getDrawGraphics();
        save();

        this.canvas.setTransform(new AffineTransform());


        float w = getWidth() / 2;
        float h = getHeight() / 3;

        int xTranslation = myView.getInsets().right;
        int yTranslation = myView.getInsets().top;
        if (w != h) {
            if (w < h) {
                canvasWidth = getWidth();
                canvasHeight = getWidth() * 3 / 2;

                //el alto es mayor que el ancho
                yTranslation += ((getHeight() - canvasHeight) / 2);
            } else {
                canvasHeight = getHeight();
                canvasWidth = getHeight() * 2 / 3;

                //el ancho es mayor que el alto
                xTranslation = (getWidth() - canvasWidth) / 2;
            }
        }

        //scale = (float) (canvasHeight * canvasWidth) / (PREFFERED_CANVAS_HEIGHT * PREFFERED_CANVAS_WIDTH);
        float scaleX = (float) canvasWidth / PREFFERED_CANVAS_WIDTH;
        float scaleY = (float) canvasHeight / PREFFERED_CANVAS_HEIGHT;
        scale = Math.min(scaleX, scaleY);
        translate(xTranslation, yTranslation);
        clear(0XFFFFFFFF);
        canvas.setColor(Color.BLACK);
        canvas.drawRect(0, 0, 20, 20);
        canvas.drawRect(canvasWidth - 20, 0, 20, 20);
    }

    @Override
    public void finishFrame() {
        this.canvas.dispose();
    }

    @Override
    public float getFontWidth(String text) {
        return canvas.getFontMetrics().stringWidth(text);
    }

    boolean cambioBuffer() {
        if (this.buffer.contentsRestored()) {
            return false;
        }
        this.buffer.show();

        return !this.buffer.contentsLost();
    }

    //Devuelve window
    public JFrame getWindow() {
        return myView;
    }

    BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }
}
