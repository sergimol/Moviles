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
            JFont jFont = new JFont(path +  filename, styleFlags, size);

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

    public int getWidth(){ return myView.getWidth(); }

    @Override
    public int getHeight() {
        return myView.getHeight();
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



        canvasWidth = getHeight() * 2 / 3;
        canvasHeight = getWidth() * 3 / 2;

        float w = getWidth()/2;
        float h = getHeight()/3;

        int xTranslation = 0;
        int yTanslation = 0;
        if (w!=h){
            if (w < h)
            {
                //el alto es mayor que el ancho
                yTanslation = ((getHeight() - canvasHeight) / 3);
                scale = (float) (getHeight() * canvasWidth) / (PREFFERED_CANVAS_HEIGHT * PREFFERED_CANVAS_WIDTH);
                System.out.println("alto mayor " + yTanslation);
            }
            else{
                //el ancho es mayor que el alto
                xTranslation = (getWidth() - canvasWidth) / 2;
                scale = (float) (getHeight() * canvasWidth) / (PREFFERED_CANVAS_HEIGHT * PREFFERED_CANVAS_WIDTH);

            }
        }
        else{
            //el marco normal
            scale = (float) (getHeight() * canvasWidth) / (PREFFERED_CANVAS_HEIGHT * PREFFERED_CANVAS_WIDTH);

        }

        translate(xTranslation, yTanslation);

        //scale(scaleRate, scaleRate);
        setColor(0);
        drawRect(0, 0, canvasWidth, canvasHeight);
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
    }

    BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }
}
