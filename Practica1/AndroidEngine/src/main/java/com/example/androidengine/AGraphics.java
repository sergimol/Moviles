package com.example.androidengine;

import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class AGraphics {
    private AssetManager assetManager;
    private Resources resourceManager;

    private SurfaceView myView;
    private SurfaceHolder holder;
    private Canvas canvas;

    private Paint paint;    //Para elegir colores en hexadecimal

    // VALORES DEL CANVAS ADAPTADO A LA PANTALLA
    private float ORIGINAL_CANVAS_WIDTH;
    private float ORIGINAL_CANVAS_HEIGHT;
    // VALORES DEL CANVAS ADAPTADO A LA PANTALLA CON RELACION 2/3
    private float ORIGINAL_CANVAS_WIDTH_RELATION;
    private float ORIGINAL_CANVAS_HEIGHT_RELATION;

    //COORDENADAS (0,0) DEL CANVAS
    private int centricoCanvasX;
    private int centricoCanvasY;

    //ESCALA ACTUALIZADA DEL CANVAS
    private float scale;

    public AGraphics(SurfaceView window, AssetManager aManager, Resources ResourceManager) {
        assetManager = aManager;
        resourceManager = ResourceManager;

        myView = window;
        holder = myView.getHolder();

        paint = new Paint();
    }

    public void init() {
        while (this.holder.getSurfaceFrame().width() == 0) ;

        //VALORES DEL CANVAS ADAPTADO A LA PANTALLA
        ORIGINAL_CANVAS_WIDTH = holder.getSurfaceFrame().width();
        ORIGINAL_CANVAS_HEIGHT = holder.getSurfaceFrame().height();

        //VALORES DEL CANVAS CON RELACION 2/3
        ORIGINAL_CANVAS_WIDTH_RELATION = ORIGINAL_CANVAS_WIDTH;
        ORIGINAL_CANVAS_HEIGHT_RELATION = ORIGINAL_CANVAS_HEIGHT;


        if (ORIGINAL_CANVAS_WIDTH_RELATION <= ORIGINAL_CANVAS_HEIGHT_RELATION * 2 / 3) {
            ORIGINAL_CANVAS_HEIGHT_RELATION = ORIGINAL_CANVAS_WIDTH_RELATION * 3.0f / 2.0f;
        } else {
            ORIGINAL_CANVAS_WIDTH_RELATION = ORIGINAL_CANVAS_HEIGHT_RELATION * 2.0f / 3.0f;
        }
        //ESCALAR A 2/3 EN VERTICAL
        if (resourceManager.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            ORIGINAL_CANVAS_WIDTH = ORIGINAL_CANVAS_WIDTH_RELATION;
            ORIGINAL_CANVAS_HEIGHT = ORIGINAL_CANVAS_HEIGHT_RELATION;
        }
        //No hace falta porque los valores por defecto son los de la pantalla
//        else if (resourceManager.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){}

        //Color negro predefinido
        paint.setColor(0XFF000000);
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

    public void drawImage(AImage image, float x, float y, int width, int height) {

        //Imprimir imagen
        image.resizeImage((int) width, (int) height);
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
        paint.setTextSize(size / scale);
        paint.setTypeface(font.font_);
    }

    //DEVUELVEN TAMANO DEL CANVAS SIN ESCALA
    public float getOriginalWidth() {
        return ORIGINAL_CANVAS_WIDTH;
    }

    public float getOriginalHeight() {
        return ORIGINAL_CANVAS_HEIGHT;
    }

    public float getCanvasAspectRelationWidth() {
        return ORIGINAL_CANVAS_WIDTH_RELATION;
    }

    public float getCanvasAspectRelationHeight() {
        return ORIGINAL_CANVAS_HEIGHT_RELATION;
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