package com.example.engine;

import com.example.interfaces.IFont;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class JFont implements IFont {
    Font font_;
    int originalSize_;

    //styleFlags is for bold + italic...
    JFont(String name, int styleFlags, int size) throws IOException, FontFormatException {
        font_ = new Font(name, styleFlags, size);

        //path a ruta del asset a partir de la raiz del proyecto
        InputStream is = new FileInputStream(new File(name));
        font_ = Font.createFont(Font.TRUETYPE_FONT, is);
        font_ = font_.deriveFont((float) size);
        font_ = font_.deriveFont(styleFlags);
        originalSize_ = size;
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

    @Override
    public void DoItalic() {
        font_ = font_.deriveFont(Font.ITALIC);
    }

    @Override
    public void DoBold() {
        font_ = font_.deriveFont(Font.BOLD);
    }

    @Override
    public void DoUnderLined() {
        
    }
}
