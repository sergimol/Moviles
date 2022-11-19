package com.example.androidengine;

import android.content.res.AssetManager;
import android.graphics.Typeface;

public class AFont {

    Typeface font_;
    float size;
    AFont(String path, int style, int size_, AssetManager AssetsManager_){
        this.font_ = Typeface.create(Typeface.createFromAsset(AssetsManager_, path), style);
        this.size = size_;
    }

    public float getSize() {
        return this.size;
    }

    public boolean isBold() {
        return font_.isBold();
    }

    public boolean isItalic() {
        return font_.isItalic();
    }

    public boolean isUnderlined() {
        return false;
    }

    public void DoItalic() {

    }

    public void DoBold() {

    }

    public void DoUnderLined() {

    }
}
