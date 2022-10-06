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
        return 0;
    }

    @Override
    public boolean isBold() {
        return false;
    }

    @Override
    public boolean isItalic() {
        return false;
    }

    @Override
    public boolean isUnderlined() {
        return false;
    }
}
