package com.example.androidengine;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.interfaces.IImage;

import java.io.IOException;

public class AImage implements IImage {
    private Bitmap image_;

    AImage(String name, AssetManager assetManager) throws IOException {
        image_ = BitmapFactory.decodeStream(assetManager.open(name));
    }

    public Bitmap getImage() {
        return image_;
    }

    @Override
    public int getWidth() {
        return image_.getWidth();
    }

    @Override
    public int getHeight() {
        return image_.getHeight();
    }
}
