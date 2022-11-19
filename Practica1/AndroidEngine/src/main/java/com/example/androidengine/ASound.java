package com.example.androidengine;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.io.IOException;

public class ASound {

    MediaPlayer mediaPlayer;

    public ASound(String filename, AssetManager assetManager) throws IOException {
        AssetFileDescriptor assetDecriptor = assetManager.openFd(filename);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.reset();
        mediaPlayer.setDataSource(assetDecriptor.getFileDescriptor(), assetDecriptor.getStartOffset(), assetDecriptor.getLength());
        mediaPlayer.prepare();
    }


    public void play() {
        mediaPlayer.start();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void resume() {
        mediaPlayer.start();
    }

    public void stop() {
        mediaPlayer.stop();
    }

    public void loop(boolean loopCondition) {
        mediaPlayer.setLooping(loopCondition);
    }
}
