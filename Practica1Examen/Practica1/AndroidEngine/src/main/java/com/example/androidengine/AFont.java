package com.example.androidengine;

import android.content.res.AssetManager;
import android.graphics.Typeface;

import com.example.interfaces.IFont;

public class AFont implements IFont {

    Typeface font_;
    float size;
    AFont(String path, int style, int size_, AssetManager AssetsManager_){
        this.font_ = Typeface.create(Typeface.createFromAsset(AssetsManager_, path), style);
        this.size = size_;
    }

    @Override
    public float getSize() {
        return this.size;
    }
}
