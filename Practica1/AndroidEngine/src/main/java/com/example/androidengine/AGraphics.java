package com.example.androidengine;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.interfaces.IFont;
import com.example.interfaces.IGraphics;
import com.example.interfaces.IImage;

import java.io.IOException;

public class AGraphics implements IGraphics {
    private SurfaceView myView;
    private SurfaceHolder holder;
    private Canvas canvas;

    private Paint paint;    //Para elegir colores en hexadecimal

    private AssetManager assetManager;

    IImage image_;

    public AGraphics(SurfaceView window, AssetManager aManager) {
        myView = window;
        holder = window.getHolder();

        paint = new Paint();
        paint.setColor(0XFF000000);     //Color negro predefinido

        assetManager = aManager;
        image_ = newImage("apedra.png");
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
//        AFont aFont = new aFont(filename, styleFlags, size);
//        return aFont;
        return null;
    }

    @Override
    public void clear(int color) {
        paint.setColor(color);
        canvas.drawPaint(paint);
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
        canvas.drawBitmap(((AImage) image_).getImage(), 0, 0, paint);
    }

    @Override
    public void drawCircle(float x, float y, float r) {
        canvas.drawCircle(x, y, r, paint);
    }

    @Override
    public void fillRect(float cx, float cy, float sideX, float sideY) {

    }

    @Override
    public void drawRect(float cx, float cy, float sideX, float sideY) {
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
    public void setFont(IFont font) {

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
    public float getScale() {
        return 0;
    }

    @Override
    public void prepareFrame() {
        while (!holder.getSurface().isValid()) ;
        canvas = holder.lockCanvas();               //Lockea el canvas para refrescarlo
        canvas.drawColor(0xFF000000);               //Pinta de negro

        //Pintar blanco
        setColor(0XFFFFFFFF);
        drawRect(0, 900, 500, 500);

        if (image_ != null)
            canvas.drawBitmap(((AImage) image_).getImage(), 0, 0, paint);
    }

    @Override
    public void finishFrame() {
        holder.unlockCanvasAndPost(canvas);         //Desbloquea el canvas para mostrar lo pintado
    }

    @Override
    public float getFontWidth(String text) {
        return paint.measureText(text, 0, text.length());
    }
}
