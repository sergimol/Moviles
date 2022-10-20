package com.example.engine;

import com.example.interfaces.IImage;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class JImage implements IImage {
    private Image image_;

    JImage(String name) throws IOException {
        image_ = ImageIO.read(new File(name));
    }


    @Override
    public int getWidth() {
        return image_.getWidth(null);
    }

    @Override
    public int getHeight() {
        return image_.getHeight(null);
    }
}
