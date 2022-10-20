package com.example.engine;

import com.example.interfaces.IFont;

import java.awt.Font;

public class JFont implements IFont {
    Font font_;

    //styleFlags is for bold + italic...
    JFont(String name, int styleFlags, int size) {
        font_ = new Font(name, styleFlags, size);
    }

    @Override
    public int getSize() {
        return font_.getSize();
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
        return isUnderlined();
    }
}
