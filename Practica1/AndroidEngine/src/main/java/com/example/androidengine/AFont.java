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

    @Override
    public boolean isBold() {
        return font_.isBold();
    }

    @Override
    public boolean isItalic() {
        return font_.isItalic();
    }

    @Override
    public boolean isUnderlined() {
        return false;
    }

    @Override
    public void DoItalic() {

    }

    @Override
    public void DoBold() {

    }

    @Override
    public void DoUnderLined() {

    }
}
