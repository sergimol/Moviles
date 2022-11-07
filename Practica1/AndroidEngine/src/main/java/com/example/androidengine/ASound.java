package com.example.androidengine;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.SoundPool;

import com.example.interfaces.ISound;

import java.io.IOException;

public class ASound implements ISound {
    String fileName;
    int SoundId;

    public ASound(String filename, int Soundid) throws IOException {
        fileName = filename;
        SoundId = Soundid;
    }

    public int getSoundId() {
        return SoundId;
    }

    @Override
    public void play() {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loop(boolean loopCondition) {

    }
}
