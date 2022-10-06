package com.example.interfaces;

public interface IAudio {
    ISound newSound(String file);
    ISound playSound(String id);
}
