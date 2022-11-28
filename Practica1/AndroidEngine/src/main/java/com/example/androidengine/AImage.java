package com.example.androidengine;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;

public class AImage {
    private Bitmap image_;

    AImage(String name, AssetManager assetManager) throws IOException {
        image_ = BitmapFactory.decodeStream(assetManager.open(name));
    }

    public Bitmap getImage() {
        return image_;
    }

    public int getWidth() {
        return image_.getWidth();
    }

    public int getHeight() {
        return image_.getHeight();
    }

    public Bitmap resizeImage(int width, int height){return Bitmap.createScaledBitmap(image_, width, height, true);}
}
