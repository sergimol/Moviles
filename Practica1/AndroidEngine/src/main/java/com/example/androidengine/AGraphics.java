package com.example.androidengine;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.interfaces.IFont;
import com.example.interfaces.IGraphics;
import com.example.interfaces.IImage;

import java.io.IOException;

public class AGraphics implements IGraphics {
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
        canvas = new Canvas();    //se supone que sobra porque lo cogemos en cada prepareFrame

        paint = new Paint();
    }

    public void init() {
        //VALORES ORIGINALES DEL WINDOW
        ORIGINAL_CANVAS_WIDTH = myView.getWidth();
        ORIGINAL_CANVAS_HEIGHT = myView.getHeight();
        if (ORIGINAL_CANVAS_WIDTH <= ORIGINAL_CANVAS_HEIGHT * 2 / 3) {
            ORIGINAL_CANVAS_HEIGHT = ORIGINAL_CANVAS_WIDTH * 3.0f / 2.0f;
        } else {
            ORIGINAL_CANVAS_WIDTH = ORIGINAL_CANVAS_HEIGHT * 2.0f / 3.0f;
        }

        paint.setColor(0XFF000000);     //Color negro predefinido
        actualizaEscala();
    }

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
            AImage aImage = new AImage(name, assetManager);
            return aImage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public IFont newFont(String filename, int styleFlags, int size) {
        AFont aFont = new AFont(filename, styleFlags, size,assetManager);
        return aFont;
    }

    @Override
    public void clear(int color) {
        paint.setColor(color);
        canvas.drawColor(paint.getColor());
    }

    @Override
    public void translate(float x, float y) {
        canvas.translate(x, y);
    }

    @Override
    public void scale(float x, float y) {
        canvas.scale(x, y);
    }

    @Override
    public void save() {
        canvas.save();
    }

    @Override
    public void restore() {
        canvas.restore();
    }

    @Override
    public void setColor(int color) {
        paint.setColor(color);
    }

    @Override
    public void drawImage(IImage image, float x, float y, float width, float height) {
        canvas.drawBitmap(((AImage) image).getImage(), x, y, paint);
    }

    @Override
    public void drawCircle(float x, float y, float r) {
        canvas.drawCircle(x, y, r, paint);
    }

    @Override
    public void fillRect(float cx, float cy, float sideX, float sideY) {
        paint.setStrokeWidth(0);
        canvas.drawRect(cx, cy, cx + sideX, cy + sideY, paint);
    }

    @Override
    public void drawRect(float cx, float cy, float sideX, float sideY, float strokeSize) {
        paint.setStrokeWidth(strokeSize);
        canvas.drawRect(cx, cy, cx + sideX, cy + sideY, paint);
    }

    @Override
    public void drawLine(float initX, float initY, float endX, float endY) {
        canvas.drawLine(initX, initY, endX, endY, paint);
    }

    @Override
    public void drawText(String text, float x, float y) {
        canvas.drawText(text, x, y, paint);
    }

    @Override
    public void setFont(IFont font, float size) {
        paint.setTextSize(font.getSize()/scale);
        paint.setTypeface(((AFont)font).font_);
    }

    @Override
    public void setResolution() {

    }

    //DEVUELVEN TAMANO DEL CANVAS SIN ESCALA
    @Override
    public float getOriginalWidth() {
        return ORIGINAL_CANVAS_WIDTH;
    }

    @Override
    public float getOriginalHeight() {
        return ORIGINAL_CANVAS_HEIGHT;
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
    public float getScale() {
        return scale;
    }

    @Override
    public void actualizaEscala() {
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

        while (!holder.getSurface().isValid()) ;
        canvas = holder.lockCanvas();               //Lockea el canvas para refrescarlo
        canvas.drawColor(0xFFFFFFFF);               //Pinta de blanco

        //Pintar blanco //El coco del tf2 si quitas esto revienta todo, lo sentimos muchisimo tony
        //nos hemos fumado todo, siete porro'
        //setColor(0XFF000000);
    }

    @Override
    public void finishFrame() {
        holder.unlockCanvasAndPost(canvas);         //Desbloquea el canvas para mostrar lo pintado
    }

    @Override
    public float getFontWidth(String text) {
        return paint.measureText(text, 0, text.length());
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
