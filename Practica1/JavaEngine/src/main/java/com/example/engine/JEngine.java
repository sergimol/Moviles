package com.example.engine;

import com.example.interfaces.IAudio;
import com.example.interfaces.IGraphics;
import com.example.interfaces.IEngine;
import com.example.interfaces.IInput;

public class JEngine implements IEngine {

    @Override
    public IGraphics getGraphics() {
        return null;
    }

    @Override
    public IInput getInput() {
        return null;
    }

    @Override
    public IAudio getAudio() {
        return null;
    }
}