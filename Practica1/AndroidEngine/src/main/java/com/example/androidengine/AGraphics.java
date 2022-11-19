package com.example.androidengine;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class AGraphics {
    private AssetManager assetManager;

    private SurfaceView myView;
    private SurfaceHolder holder;
    private Canvas canvas;

    private Paint paint;    //Para elegir colores en hexadecimal

    // VALORES ORIGINALES DEL WINDOW
    private float ORIGINAL_CANVAS_WIDTH;
    private float ORIGINAL_CANVAS_HEIGHT;

    //COORDENADAS (0,0) DEL CANVAS
    private int centricoCanvasX;
    private int centricoCanvasY;

    //ESCALA ACTUALIZADA DEL CANVAS
    private float scale;

    public AGraphics(SurfaceView window, AssetManager aManager) {
        assetManager = aManager;

        myView = window;
        holder = myView.getHolder();

        paint = new Paint();
    }

    public void init() {
        while (this.holder.getSurfaceFrame().width() == 0) ;

        //VALORES ORIGINALES DEL WINDOW
        ORIGINAL_CANVAS_WIDTH = holder.getSurfaceFrame().width();
        ORIGINAL_CANVAS_HEIGHT = holder.getSurfaceFrame().height();
        if (ORIGINAL_CANVAS_WIDTH <= ORIGINAL_CANVAS_HEIGHT * 2 / 3) {
            ORIGINAL_CANVAS_HEIGHT = ORIGINAL_CANVAS_WIDTH * 3.0f / 2.0f;
        } else {
            ORIGINAL_CANVAS_WIDTH = ORIGINAL_CANVAS_HEIGHT * 2.0f / 3.0f;
        }

        paint.setColor(0XFF000000);     //Color negro predefinido
        actualizaEscala();
        System.out.println(scale);
        //canvas.scale(scale, scale);
    }

    public int getCanvasX() {
        return centricoCanvasX;
    }

    public int getCanvasY() {
        return centricoCanvasY;
    }

    public AImage newImage(String name) {
        try {
            AImage aImage = new AImage(name, assetManager);
            return aImage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AFont newFont(String filename, int styleFlags, int size) {
        AFont aFont = new AFont(filename, styleFlags, size, assetManager);
        return aFont;
    }

    public void clear(int color) {
        paint.setColor(color);
        canvas.drawColor(paint.getColor());
    }

    public void translate(float x, float y) {
        canvas.translate(x, y);
    }

    public void scale(float x, float y) {
        canvas.scale(x, y);
    }

    public void save() {
        canvas.save();
    }

    public void restore() {
        canvas.restore();
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    public void drawImage(AImage image, float x, float y, float width, float height) {
        canvas.drawBitmap(image.getImage(), x, y, paint);
    }

    public void drawCircle(float x, float y, float r) {
        canvas.drawCircle(x, y, r, paint);
    }

    public void fillRect(float cx, float cy, float sideX, float sideY) {
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(cx, cy, cx + sideX, cy + sideY, paint);
    }

    public void drawRect(float cx, float cy, float sideX, float sideY, float strokeSize) {
        paint.setStrokeWidth(strokeSize * 5);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(cx, cy, cx + sideX, cy + sideY, paint);
    }

    public void drawLine(float initX, float initY, float endX, float endY) {
        canvas.drawLine(initX, initY, endX, endY, paint);
    }

    public void drawText(String text, float x, float y) {
        canvas.drawText(text, x, y, paint);
    }

    public void setFont(AFont font, float size) {
        paint.setTextSize(font.getSize()/scale);
        paint.setTypeface(font.font_);
    }

    public void setResolution() {

    }

    //DEVUELVEN TAMANO DEL CANVAS SIN ESCALA
    public float getOriginalWidth() {
        return ORIGINAL_CANVAS_WIDTH;
    }

    public float getOriginalHeight() {
        return ORIGINAL_CANVAS_HEIGHT;
    }

    public int getWidth() {
        return myView.getWidth();
    }

    public int getHeight() {
        return myView.getHeight();
    }

    public float getScale() {
        return scale;
    }

    public void actualizaEscala() {
        float w = holder.getSurfaceFrame().width();
        float h = holder.getSurfaceFrame().height();

        if (w <= h * 2.0f / 3.0f) {
            //Nos quedamos con el ancho
            scale = (float) ((float) getWidth() / (float) ORIGINAL_CANVAS_WIDTH);
        } else {
            //Nos quedamos con el alto
            scale = (float) ((float) getHeight() / (float) ORIGINAL_CANVAS_HEIGHT);
        }

        float ESCALAX = scale;
        float ESCALAY = scale;

        int CENTROX = (int) (getWidth() / 2);
        int CENTROY = (int) (getHeight() / 2);

        int CENTROCANVASX = (int) (ORIGINAL_CANVAS_WIDTH * ESCALAX) / 2;
        int CENTROCANVASY = (int) (ORIGINAL_CANVAS_HEIGHT * ESCALAY) / 2;

        centricoCanvasX = CENTROX - CENTROCANVASX;
        centricoCanvasY = CENTROY - CENTROCANVASY;

        System.out.println(centricoCanvasX + " " + centricoCanvasY);
        /*canvas.scale(ESCALAX, ESCALAY);*/
    }

    public void prepareFrame() {

        while (!holder.getSurface().isValid()) ;
        canvas = holder.lockCanvas();               //Lockea el canvas para refrescarlo
        canvas.save();
        translate(centricoCanvasX, centricoCanvasY);
        canvas.drawColor(0xFFFFFFFF);               //Pinta de blanco
        //Pintar blanco //El coco del tf2 si quitas esto revienta todo, lo sentimos muchisimo tony
        //nos hemos fumado todo, siete porro'
        //setColor(0XFF000000);
    }

    public void finishFrame() {
        canvas.restore();
        holder.unlockCanvasAndPost(canvas);         //Desbloquea el canvas para mostrar lo pintado
    }

    public float getFontWidth(String text) {
        return paint.measureText(text, 0, text.length());
    }

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
