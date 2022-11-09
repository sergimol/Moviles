package com.example.androidengine;

import android.graphics.Typeface;

import com.example.interfaces.IFont;

public class AFont implements IFont {

    Typeface font_;

    AFont(String path, int style, int size){
        font_ = Typeface.createFromFile(path);
    }

    @Override
    public int getSize() {
        return 0;
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
