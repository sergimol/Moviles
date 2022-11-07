package com.example.androidengine;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.example.interfaces.ISound;

import java.io.IOException;

public class ASound implements ISound {

    MediaPlayer mediaPlayer;

    public ASound(String filename, AssetManager assetManager) throws IOException {
        AssetFileDescriptor assetDecriptor = assetManager.openFd(filename);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.reset();
        mediaPlayer.setDataSource(assetDecriptor.getFileDescriptor(), assetDecriptor.getStartOffset(), assetDecriptor.getLength());
        mediaPlayer.prepare();
    }


    @Override
    public void play() {
        mediaPlayer.start();
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public void resume() {
        mediaPlayer.start();
    }

    @Override
    public void stop() {
        mediaPlayer.stop();
    }

    @Override
    public void loop(boolean loopCondition) {
        mediaPlayer.setLooping(loopCondition);
    }
}
