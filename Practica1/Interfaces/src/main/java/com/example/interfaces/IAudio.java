package com.example.interfaces;

public interface IAudio {

    ISound newSound(String sampleName, String fileName);

    void playSound(String fileName);

    void pause(String fileName);

    void resume(String fileName);

    void stop(String fileName);

    void loop(String fileName, boolean loopCondition);
}
