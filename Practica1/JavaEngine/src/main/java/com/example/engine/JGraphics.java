package com.example.engine;

import com.example.interfaces.IFont;
import com.example.interfaces.IGraphics;
import com.example.interfaces.IImage;

import java.awt.Color;
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

    // VALORES ORIGINALES DEL WINDOW
    private float ORIGINAL_CANVAS_WIDTH;
    private float ORIGINAL_CANVAS_HEIGHT;

    private int centricoCanvasX;
    private int centricoCanvasY;

    private float scale;

    public JGraphics(JFrame window) {
        //Inicializacion de myView
        myView = window;

        //Guardamos valores

        ORIGINAL_CANVAS_WIDTH = window.getHeight();
        ORIGINAL_CANVAS_HEIGHT = window.getWidth();
        if (ORIGINAL_CANVAS_WIDTH <= ORIGINAL_CANVAS_HEIGHT * 2 / 3) {
            ORIGINAL_CANVAS_HEIGHT = ORIGINAL_CANVAS_WIDTH * 3.0f / 2.0f;
        } else {
            ORIGINAL_CANVAS_WIDTH = ORIGINAL_CANVAS_HEIGHT * 2.0f / 3.0f;
        }


        //Inicializacion de buffer
        myView.createBufferStrategy(2);
        buffer = myView.getBufferStrategy();
        //Inicializacion de canvas
        canvas = (Graphics2D) buffer.getDrawGraphics();    //se supone que sobra porque lo cogemos en cada prepareFrame

        actualizaEscala();
    }

    //DEVUELVEN EL (0,0) DE LAS COORDENADAS DEL CANVAS RESPECTO LA PANTALLA
    @Override
    public int getCanvasX() {
        return centricoCanvasX;
    }

    @Override
    public int getCanvasY() {
        return centricoCanvasY;
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
        double auxX = ORIGINAL_CANVAS_WIDTH;
        double auxY = ORIGINAL_CANVAS_HEIGHT;
        canvas.fillRect(0, 0, (int) auxX, (int) auxY);    //
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
    public void setFont(IFont font, float size) {
        ((JFont) font).setSize(size);
        canvas.setFont(((JFont) font).font_);
    }

    @Override
    public void setResolution() {

    }

    //DEVUELVEN TAMAÑO DEL CANVAS SIN ESCALA
    public float getOriginalWidth() {
        return ORIGINAL_CANVAS_WIDTH;
    }

    public float getOriginalHeight() {
        return ORIGINAL_CANVAS_HEIGHT;
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

    void actualizaEscala() {
        float w = getWidth();
        float h = getHeight();

        if (w <= h * 2.0f / 3.0f) {
            //Nos quedamos con el ancho
            scale = (float) ((float) getWidth() / (float) ORIGINAL_CANVAS_WIDTH);
        } else {
            //Nos quedamos con el alto
            scale = (float) ((float) getHeight() / (float) ORIGINAL_CANVAS_HEIGHT);
        }
    }

    @Override
    public void prepareFrame() {
        this.canvas = (Graphics2D) this.buffer.getDrawGraphics();
        save();

        //TOMA DE ESCALA
        actualizaEscala();

        //ESCALA DE CANVAS
        float ESCALAX = scale;
        float ESCALAY = scale;

        int CENTROX = ((int) ((getWidth() / 2) * canvas.getTransform().getScaleX()) + myView.getInsets().left);
        int CENTROY = ((int) ((getHeight() / 2) * canvas.getTransform().getScaleY()) + myView.getInsets().top);

        int CENTROCANVASX = (int) (ORIGINAL_CANVAS_WIDTH * ESCALAX) / 2;
        int CENTROCANVASY = (int) (ORIGINAL_CANVAS_HEIGHT * ESCALAY) / 2;

        centricoCanvasX = CENTROX - CENTROCANVASX;
        centricoCanvasY = CENTROY - CENTROCANVASY;

        translate(centricoCanvasX, centricoCanvasY);
        canvas.scale(ESCALAX, ESCALAY);

        clear(0XFFFFFFFF);

        //canvas.drawRect(0, 0, 20, 20);
        //canvas.drawRect(canvasWidth - 20, 0, 20, 20);
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

    @Override
    public float relationAspectDimension() {
        float w = getWidth();
        float h = getHeight();

        if (w <= h * 2.0f / 3.0f) {
            //Nos quedamos con el ancho
            return w;
        } else {
            //Nos quedamos con el alto
            return h;
        }
    }
}
