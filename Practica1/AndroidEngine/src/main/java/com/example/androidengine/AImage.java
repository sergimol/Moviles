package com.example.androidengine;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.Serializable;

public class AImage implements Serializable {
    private Bitmap image_;
    private String name_;

    AImage(String name, AssetManager assetManager) throws IOException {
        image_ = BitmapFactory.decodeStream(assetManager.open(name));
        name_ = name;
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

    public void resizeImage(int width, int height) {
        image_ = Bitmap.createScaledBitmap(image_, width, height, true);
    }

    public String getName() {
        return name_;
    }
}
