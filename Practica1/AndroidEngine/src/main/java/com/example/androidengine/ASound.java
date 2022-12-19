package com.example.androidengine;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.io.IOException;

public class ASound {

    boolean oneShot;
    MediaPlayer mediaPlayer;
    SoundPool soundPool;
    int soundId = -1;

    public ASound(String filename, AssetManager assetManager, boolean audioSystem) throws IOException {

        AssetFileDescriptor assetDecriptor = assetManager.openFd(filename);
        oneShot = audioSystem;
        if (oneShot) {
            soundPool = new SoundPool( 5, AudioManager.STREAM_MUSIC , 0);
            soundId = soundPool.load(assetDecriptor, 1);
        } else {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.reset();
            mediaPlayer.setDataSource(assetDecriptor.getFileDescriptor(), assetDecriptor.getStartOffset(), assetDecriptor.getLength());
            mediaPlayer.prepare();
        }
    }


    public void play() {
        if (oneShot)
            soundPool.play(soundId, 1, 1, 1, 0, 1);
        else
            mediaPlayer.start();
    }

    public void pause() {
        if (oneShot)
            soundPool.pause(soundId);
        else
            mediaPlayer.pause();
    }

    public void resume() {
        if (oneShot)
            soundPool.resume(soundId);
        else
            mediaPlayer.start();
    }

    public void stop() {
        if (oneShot)
            soundPool.stop(soundId);
        else
            mediaPlayer.stop();
    }

    public void loop(boolean loopCondition) {
        if (oneShot)
            soundPool.setLoop(soundId, loopCondition ? 0 : 1);
        else
            mediaPlayer.setLooping(loopCondition);
    }
}
